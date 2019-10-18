import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class HTMLPanel extends JPanel {
	//pole do wpisywania kodu html
	private final JTextArea textArea = new JTextArea();
	//pole z wygenerowanym kodem
	private final JEditorPane editorPane = new JEditorPane();

	public HTMLPanel() {
		super();
		setLayout(new BorderLayout());
		createPanels();
	}

	private void createPanels() {
		//nie chcemy, aby mo¿na by³o edytowaæ wygenerowany html
		editorPane.setEditable(false);
		//ustawiamy nasz editorPane, aby rozpoznawa znaczniki html
		editorPane.setContentType("text/html");
		//przycisk generowania podgl¹du
		JButton actionButton = new JButton("Podgl¹d");
		actionButton.addActionListener(new ConvertListener());
		//panel pomocniczy do rozk³adu elementów
		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(new GridLayout(1, 2));
		textArea.setBackground(Color.lightGray);
		//dodajemy komponenty tekstowe do pomocniczego panelu
		helpPanel.add(textArea);
		helpPanel.add(editorPane);
		//dodajemy wszystko do g³ównego panelu
		this.add(helpPanel, BorderLayout.CENTER);
		this.add(actionButton, BorderLayout.SOUTH);
	}

	class ConvertListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			//zmiany wygl¹du wywo³ujemy w w¹tku dystrybucji zdarzeñ
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					String text = textArea.getText();
					editorPane.setText(text);
					editorPane.revalidate();
				}
			});
		}
	}
}