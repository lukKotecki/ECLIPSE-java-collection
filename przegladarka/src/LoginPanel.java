import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	private JTextField nameField; //pole na nazw�
	private JPasswordField passField; //pole na has�o
	private JButton loginButton; //przycisk logowania
	private LoginListener listener; //s�uchacz przycisku

	/**
	 * @return wprowadzona nazwa u�ytkownika
	 */
	public String getName() {
		return nameField.getText();
	}

	/**
	 * @return wprowadzone przez u�ytkownika has�o
	 */
	public String getPassword() {
		String password = "";
		char[] pass = passField.getPassword();
		for(int i=0; i<pass.length; i++) {
			password += pass[i];
		}
		return password;
	}

	public LoginPanel(LoginListener listener) {
		super();
		// ustawiamy layout
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.CENTER;
		gridBag.setConstraints(this, constraints);
		setLayout(gridBag);
		// tworzymy komponenty logowania
		this.listener = listener;
		this.listener.setPanel(this);
		createComponents();
	}

	/**
	 * Metoda, kt�ra tworzy etykiety i pola do wprowadzania danych.
	 */
	private void createComponents() {
		JLabel name = new JLabel("Name: ");
		JLabel password = new JLabel("Password: ");
		nameField = new JTextField();
		passField = new JPasswordField();

		//pomocniczy panel do wprowadzania danych
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2, 2));
		inputPanel.add(name);
		inputPanel.add(nameField);
		inputPanel.add(password);
		inputPanel.add(passField);
		//tworzymy przycisk logowania
		loginButton = new JButton("Zaloguj");
		loginButton.addActionListener(listener);

		//pomocniczy panel do wy�rodkowania element�w
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BorderLayout());
		parentPanel.add(inputPanel, BorderLayout.CENTER);
		parentPanel.add(loginButton, BorderLayout.SOUTH);

		// dodajemy do g��wnego panelu
		this.add(parentPanel);
	}
}