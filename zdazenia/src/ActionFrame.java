

import javax.swing.*;

public class ActionFrame extends JFrame {

	public ActionFrame() {
		super("Test akcji");

		JPanel buttonPanel = new ButtonPanel();
		add(buttonPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}