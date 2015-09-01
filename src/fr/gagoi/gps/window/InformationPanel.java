package fr.gagoi.gps.window;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JPanel;

import fr.gagoi.gps.window.components.GPSButton;
import fr.gagoi.gps.window.components.GPSLabel;
import fr.gagoi.gps.window.components.GPSTextField;

@SuppressWarnings("serial")
public class InformationPanel extends JPanel {

	private GPSButton button_startSearch;
	private GPSLabel label_startPoint;
	private GPSTextField textField_startPoint;

	public InformationPanel() {
		setOpaque(true);
		setLayout(null);
		setBackground(Color.DARK_GRAY);
		createComponents();
	}

	private void createComponents() {
		button_startSearch = new GPSButton("Start search", 50, 50, 100, 100, this);
		label_startPoint = new GPSLabel("Starting point :", 50, 200, 100, 20, this);
		textField_startPoint = new GPSTextField(50, 250, 150, 20, this);
	}
}
