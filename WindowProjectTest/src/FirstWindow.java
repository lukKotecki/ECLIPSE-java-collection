import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.util.Scanner;
import java.util.Vector;

public class FirstWindow {

	private JFrame frame;
	JLabel imageAddressLabel_1;
	JLabel imageAddressLabel_2;
	JLabel imageAddressLabel_3;
	JFileChooser fileChooser;
	BufferedImage bufferedImage;
	JLabel imageViewLabel_1;
	JLabel imageViewLabel_2;
	JLabel imageViewLabel_3;

	public static int WIDTH = 0;
	public static int HEIGHT = 0;
	public static int NUMBER_OF_PHOTOS = 3;
	public static int pixelSizeX = 15;
	public static int pixelSizeY = 5;
	static BufferedImage outputImage = null;
	static File outputFile = null;
	static Vector<BufferedImage> image = new Vector<BufferedImage>(NUMBER_OF_PHOTOS);

	private static BufferedImage redImage = null;
	private static BufferedImage greenImage = null;
	private static BufferedImage blueImage = null;
	private static File redFile = null;
	private static File greenFile = null;
	private static File blueFile = null;
	
	
	private JLabel imageViewLabel_1RED;
	private JLabel imageViewLabel_2GREEN;
	private JLabel imageViewLabel_3BLUE;
	private JLabel outputViewImage;
	
	public enum ImageTypeColor { RED, GREEN, BLUE};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstWindow window = new FirstWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FirstWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton buttonUploadImage_1 = new JButton("image 1");
		buttonUploadImage_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					redFile = fileChooser.getSelectedFile();
					try {
					bufferedImage = ImageIO.read(redFile);
					Icon icon = new ImageIcon(resize(bufferedImage,220,120));
					imageViewLabel_1.setText("");
					imageViewLabel_1.setIcon(icon);
					WIDTH = WIDTH < bufferedImage.getWidth() ? bufferedImage.getWidth(): WIDTH;
					HEIGHT = HEIGHT < bufferedImage.getHeight() ? bufferedImage.getHeight(): HEIGHT;
					
					redImage = bufferedImageRGBchanger(bufferedImage,ImageTypeColor.RED);
					icon = new ImageIcon(resize(redImage,220,120));
					imageViewLabel_1RED.setText("");
					imageViewLabel_1RED.setIcon(icon);
					
					}catch(Exception ex) {ex.printStackTrace();};
					imageAddressLabel_1.setText(redFile.getAbsolutePath());
				}	
			}
		});
		buttonUploadImage_1.setBounds(10, 40, 89, 23);
		frame.getContentPane().add(buttonUploadImage_1);
		
		JButton buttonUploadImage_2 = new JButton("image 2");
		buttonUploadImage_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					greenFile = fileChooser.getSelectedFile();
					try {
					bufferedImage = ImageIO.read(greenFile);
					Icon icon = new ImageIcon(resize(bufferedImage,220,120));
					imageViewLabel_2.setText("");
					imageViewLabel_2.setIcon(icon);

					WIDTH = WIDTH < bufferedImage.getWidth() ? bufferedImage.getWidth(): WIDTH;
					HEIGHT = HEIGHT < bufferedImage.getHeight() ? bufferedImage.getHeight(): HEIGHT;
					
					greenImage = bufferedImageRGBchanger(bufferedImage,ImageTypeColor.GREEN);
					icon = new ImageIcon(resize(greenImage,220,120));
					imageViewLabel_2GREEN.setText("");
					imageViewLabel_2GREEN.setIcon(icon);
					
					}catch(Exception ex) {ex.printStackTrace();};
					imageAddressLabel_2.setText(greenFile.getAbsolutePath());
				}	
			}
		});
		buttonUploadImage_2.setBounds(10, 195, 89, 23);
		frame.getContentPane().add(buttonUploadImage_2);
		
		JButton buttonUploadImage_3 = new JButton("image 3");
		buttonUploadImage_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					blueFile = fileChooser.getSelectedFile();
					try {
					bufferedImage = ImageIO.read(blueFile);
					Icon icon = new ImageIcon(resize(bufferedImage,220,120));
					imageViewLabel_3.setText("");
					imageViewLabel_3.setIcon(icon);
					
					WIDTH = WIDTH < bufferedImage.getWidth() ? bufferedImage.getWidth(): WIDTH;
					HEIGHT = HEIGHT < bufferedImage.getHeight() ? bufferedImage.getHeight(): HEIGHT;
					
					blueImage = bufferedImageRGBchanger(bufferedImage,ImageTypeColor.BLUE);
					icon = new ImageIcon(resize(blueImage,220,120));
					imageViewLabel_3BLUE.setText("");
					imageViewLabel_3BLUE.setIcon(icon);
					
					}catch(Exception ex) {ex.printStackTrace();};
					imageAddressLabel_3.setText(blueFile.getAbsolutePath());
				}
			}
		});
		buttonUploadImage_3.setBounds(10, 350, 89, 23);
		frame.getContentPane().add(buttonUploadImage_3);
		
		JLabel uploadLabel = new JLabel("Upload images:");
		uploadLabel.setFont(new Font("Stencil", Font.PLAIN, 16));
		uploadLabel.setBounds(10, 11, 136, 29);
		frame.getContentPane().add(uploadLabel);
		
		imageViewLabel_1 = new JLabel("NO IMAGE");
		imageViewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		imageViewLabel_1.setBounds(10, 65, 220, 120);
		frame.getContentPane().add(imageViewLabel_1);
		
		imageViewLabel_2 = new JLabel("NO IMAGE");
		imageViewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		imageViewLabel_2.setBounds(10, 220, 220, 120);
		frame.getContentPane().add(imageViewLabel_2);
		
		imageViewLabel_3 = new JLabel("NO IMAGE");
		imageViewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		imageViewLabel_3.setBounds(10, 375, 220, 120);
		frame.getContentPane().add(imageViewLabel_3);
		
		imageAddressLabel_1 = new JLabel("please upload image 1 . . .");
		imageAddressLabel_1.setBounds(105, 40, 350, 23);
		frame.getContentPane().add(imageAddressLabel_1);
		
		imageAddressLabel_2 = new JLabel("please upload image 2 . . .");
		imageAddressLabel_2.setBounds(105, 199, 350, 14);
		frame.getContentPane().add(imageAddressLabel_2);
		
		imageAddressLabel_3 = new JLabel("please upload image 3 . . .");
		imageAddressLabel_3.setBounds(105, 354, 350, 14);
		frame.getContentPane().add(imageAddressLabel_3);
		
		imageViewLabel_1RED = new JLabel("NO IMAGE");
		imageViewLabel_1RED.setFont(new Font("Tahoma", Font.PLAIN, 16));
		imageViewLabel_1RED.setBounds(240, 65, 220, 120);
		frame.getContentPane().add(imageViewLabel_1RED);
		
		imageViewLabel_2GREEN = new JLabel("NO IMAGE");
		imageViewLabel_2GREEN.setFont(new Font("Tahoma", Font.PLAIN, 16));
		imageViewLabel_2GREEN.setBounds(240, 220, 220, 120);
		frame.getContentPane().add(imageViewLabel_2GREEN);
		
		imageViewLabel_3BLUE = new JLabel("NO IMAGE");
		imageViewLabel_3BLUE.setFont(new Font("Tahoma", Font.PLAIN, 16));
		imageViewLabel_3BLUE.setBounds(240, 375, 220, 120);
		frame.getContentPane().add(imageViewLabel_3BLUE);
		
		outputViewImage = new JLabel("GENERATE THE IMAGE");
		outputViewImage.setBounds(580, 10, 400, 300);
		frame.getContentPane().add(outputViewImage);
		
		JButton btnGenerate = new JButton("GENERATE");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				
				
				
				
				
				
				
				
				image.add(0,redImage);
				image.add(1,greenImage);
				image.add(2,blueImage);
				
				outputImage = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_ARGB);
				
				pixelRainbowPainter(outputImage);
				

				Icon icon = new ImageIcon(resize(outputImage,400,300));
				outputViewImage.setText("");
				outputViewImage.setIcon(icon);
				
				
				
				
				
				
				
				
				
				
				
				
			}
		});
		btnGenerate.setBounds(600, 330, 120, 50);
		frame.getContentPane().add(btnGenerate);
		
		JButton btnSave = new JButton("SAVE");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				writeImageToFile(outputImage);
				
				
				
				
				
				
				
			}
		});
		btnSave.setBounds(750, 330, 120, 50);
		frame.getContentPane().add(btnSave);
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}
	
	public static BufferedImage bufferedImageRGBchanger(BufferedImage inImage, ImageTypeColor imageType) {
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
				int troszke1 = (int)(suma*0.1);
				int troszke2 = (int)(roznica*0.5);
				
				switch(imageType) {
				case RED:
					grayColor = new Color(suma,troszke1,0);//red
					break;
				case GREEN:
					grayColor = new Color(troszke2,suma,troszke2);//green
					break;
				case BLUE:
					grayColor = new Color(0,troszke1,suma);//blue
					break;
				default:
					grayColor = new Color(suma,suma,suma);
				}
				outImage.setRGB(x,y,grayColor.getRGB());
			}
		}	
		return outImage;
	}
	
	
	
static void pixelRainbowPainter(BufferedImage img){
		
		int a=255;
		int r=0;
		int g=0;
		int b=255;
		
		int p=0; //pixelColorToSet
		
		int xPositionOrder =0;
		int yPositionOrder =0;
		int imageToReadOrder =0;
		
		//nt endOfRow = 0;
		
		for(int y=0; y<HEIGHT; y++){
			for(int x=0; x<WIDTH;x++){
				
				//p = pixelARGBtoInt(a,r,g,b); //this is just for tests - coloring output.png
				
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
	}





	static void writeImageToFile(BufferedImage imageToWrite){
		try{
			outputFile = new File("output.png");
			ImageIO.write(imageToWrite,"png",outputFile);
		}catch(IOException e){
			System.out.println("ERROR IOException");
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("ERROR (...)");
			
		}
		finally{
			System.out.println("FINALLY trying to close outputFile");
			//outputFile.close();
		}
	}












	
	
	
}
