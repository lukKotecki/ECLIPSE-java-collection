import java.awt.EventQueue;
import javax.swing.JFrame;

public class Frame extends JFrame {
	public Frame() {
		super("MouseTest");

		add(new MouseTestPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Frame();
			}
		});
	}
}