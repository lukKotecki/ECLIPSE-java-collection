import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MouseTestPanel extends JPanel implements MouseListener {

	private static final int WIDTH = 200;
	private static final int HEIGHT = 200;

	private int x, y;

	ArrayList<Point> points = new ArrayList<Point>();

	public MouseTestPanel() {
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		points.add(new Point(x, y));
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.setColor(Color.BLACK);
		drawRectangles(g2d);
	}

	private void drawRectangles(Graphics2D g2d) {
		int x, y;
		for(Point p: points) {
			x = (int)p.getX();
			y = (int)p.getY();
			g2d.fillRect(x, y, 10, 10);
		}
	}
}