package com.lukaszkotecki;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Vector;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/Download")
@MultipartConfig(fileSizeThreshold=1024*1024,
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class Download extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	BufferedImage bufferedImage;
	public static int WIDTH = 0;
	public static int HEIGHT = 0;
	public static int NUMBER_OF_PHOTOS = 3;
	public static int pixelSizeX = 10;
	public static int pixelSizeY = 5;
	public static BufferedImage outputImage = null;
	public static File outputFile = null;
	public static Vector<BufferedImage> image = new Vector<BufferedImage>(NUMBER_OF_PHOTOS);
	private static BufferedImage redImage = null;
	private static BufferedImage greenImage = null;
	private static BufferedImage blueImage = null;
	public enum ImageTypeColor { RED, GREEN, BLUE};
	public static int contrastNumber = 100;
	
    public Download() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			prepareImages(request,response);
			prepareBufferedImages();
			pixelRainbowPainter(outputImage);
			sendingFileToDownload(request,response);
			
			
		}catch(Exception e) {
			System.out.println("Cos poszlo zle...");
			System.out.println("Exception message: "+e.getMessage());
		}
		
	}// END of doPost
	
	static void pixelRainbowPainter(BufferedImage img){
		
		int p=0; //pixelColorToSet
		int xPositionOrder =0;
		int yPositionOrder =0;
		int imageToReadOrder =0;
		
		for(int y=0; y<HEIGHT; y++){
			for(int x=0; x<WIDTH;x++){
				imageToReadOrder = (xPositionOrder+yPositionOrder)%NUMBER_OF_PHOTOS;
				p = image.get(imageToReadOrder).getRGB(x,y);
				img.setRGB(x,y,p);
				if(x%pixelSizeX ==0)
					xPositionOrder++;
			}
			if(y%pixelSizeY ==0)
				yPositionOrder++;
			xPositionOrder = 0; //yPositionOrder not work correctly without this
		}
		float contrast = 1;
		contrast = contrast * contrastNumber/100;
		RescaleOp rescaleop = new RescaleOp(contrast,1,null);
		rescaleop.filter(img,img);


	}
	
	public static BufferedImage changeColorRGB(BufferedImage inImage, ImageTypeColor imageType) {
		BufferedImage outImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Color grayColor;
		for(int y=0; y<HEIGHT; y++){
			for(int x=0; x<WIDTH; x++){
				
				Color c = new Color(inImage.getRGB(x,y));
				int red = (int)(c.getRed()*0.299);
				int green = (int)(c.getGreen()*0.587);
				int blue = (int)(c.getBlue()*0.114);
				
				int suma = red+green+blue;
				
				if(suma>127)
					suma*=1.2;
				else
					suma*=0.8;
				if(suma>255)
					suma = 255;
				int roznica = 255 - suma;
				int troszke1 = (int)(suma*0.1);//0,1
				int troszke2 = (int)(roznica*0.9);//0.5
				
				switch(imageType) {
				case RED:
					grayColor = new Color(suma,troszke1,0);//red(suma,troszke1,0)
					break;
				case GREEN:
					grayColor = new Color(troszke2,suma,troszke2);//green(troszke2,suma,troszke2)
					break;
				case BLUE:
					grayColor = new Color(0,troszke1,suma);//blue(0,troszke1,suma)
					break;
				default:
					grayColor = new Color(suma,suma,suma);
				}
				outImage.setRGB(x,y,grayColor.getRGB());
			}
		}	
		return outImage;
	}
	
	public static void prepareImages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Collection<Part> partCollection = request.getParts();
			int partCollectionSize = partCollection.size();
			contrastNumber = Integer.parseInt(request.getParameter("contrastNumber"));
			partCollection.removeIf((Part part) -> part.getSize() < 100);
			
			for(Part part : partCollection) {
				System.out.println("Part Collection size: "+ part.getSize());
			}
			
			
			Part [] partArray = partCollection.toArray(new Part[partCollectionSize]);
			System.out.println("Number of Part Collection elements: "+partCollectionSize);
			while(!partCollection.isEmpty()) {
				partCollection.clear();
			};
			
			redImage = null;
			greenImage = null;
			blueImage = null;
			WIDTH = 0;
			HEIGHT =0;
			boolean redSet = false;
			boolean greenSet = false;
			boolean blueSet = false;

			for(int i =0;i<3;i++){
				if(0!=partArray[i].getSize()) {
					if(!redSet){
						redImage = ImageIO.read(partArray[i].getInputStream());
						redSet= true;
						HEIGHT = HEIGHT < redImage.getHeight() ? redImage.getHeight() : HEIGHT;
						WIDTH = WIDTH < redImage.getWidth() ? redImage.getWidth() : WIDTH;
						continue;
					}
					if(!greenSet) {
						greenImage = ImageIO.read(partArray[i].getInputStream());
						greenSet=true;
						HEIGHT = HEIGHT < greenImage.getHeight() ? greenImage.getHeight() : HEIGHT;
						WIDTH = WIDTH < greenImage.getWidth() ? greenImage.getWidth() : WIDTH;
						continue;
					}
					if(!blueSet) {
						blueImage = ImageIO.read(partArray[i].getInputStream());
						blueSet=true;
						HEIGHT = HEIGHT < blueImage.getHeight() ? blueImage.getHeight() : HEIGHT;
						WIDTH = WIDTH < blueImage.getWidth() ? blueImage.getWidth() : WIDTH;
						continue;
					}
				}
			}
			
			redImage = resize(redImage,HEIGHT,WIDTH);
			greenImage = resize(greenImage,HEIGHT,WIDTH);
			blueImage = resize(blueImage,HEIGHT,WIDTH);
			
			}catch(Exception e) {
				System.out.println("Przygotowywanie plikow .... niedobrze");
				System.out.println("Exception e: "+e.getMessage());
				
			}finally {
				System.out.println("Block finally metody prepare images");
			}
	} // END of prepareImages()
	
	public static void prepareBufferedImages() {
		redImage = changeColorRGB(redImage,ImageTypeColor.RED);
		greenImage = changeColorRGB(greenImage,ImageTypeColor.GREEN);
		blueImage = changeColorRGB(blueImage,ImageTypeColor.BLUE);
		
		
		image.add(0,redImage);
		image.add(1,greenImage);
		image.add(2,blueImage);
		
		outputImage = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void sendingFileToDownload(HttpServletRequest request, HttpServletResponse response) {
		
		File tempFile = null;
		try {
			File appTempDir = (File)getServletContext().getAttribute(ServletContext.TEMPDIR);
			tempFile = File.createTempFile("outputImage", ".png", appTempDir);
			tempFile.deleteOnExit();
			
			ImageIO.write(outputImage,"png",tempFile);
			
			
			String filePath = tempFile.getPath();
			FileInputStream inStream = new FileInputStream(tempFile);
			
			 // obtains ServletContext
	        ServletContext context = getServletContext();
	         
	        // gets MIME type of the file
	        String mimeType = context.getMimeType(filePath);
	        if (mimeType == null) {        
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	        System.out.println("MIME type: " + mimeType);
	         
	        // modifies response
	        response.setContentType(mimeType);
	        response.setContentLengthLong((long) tempFile.length());
	        
	         
	        // forces download
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", tempFile.getName());
	        response.setHeader(headerKey, headerValue);
	         
	        // obtains response's output stream
	        OutputStream outStream = response.getOutputStream();
	         
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	        inStream.close();
	        outStream.close(); 
		}catch(Exception e) {
			System.out.println("Zapisywanie niepowiedzione");
			
		}finally {
		    tempFile.delete();    
		}
	}
	
	private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
	
} //END of Class
