import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class KeyTest extends JFrame implements KeyListener {

	private int counter = 0;
	private String userInput = "";
	private final String secret = "secret";

	public KeyTest() {
		super("KeyListener Test");
		setPreferredSize(new Dimension(300, 100));
		addKeyListener(this);

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void keyPressed(KeyEvent evt) {
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		char c = evt.getKeyChar();
		if(counter < secret.length())
			checkSecret(c);
	}

	@Override
	public void keyTyped(KeyEvent evt) {

	}

	private void checkSecret(char c) {
		if(c == secret.charAt(counter)) {
			counter++;
			userInput = userInput+c;
		}
		else {
			counter = 0;
			userInput = "";
		}

		if(userInput.equals(secret))
			setTitle("Sekretne has³o");
	}
}