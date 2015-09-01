package fr.gagoi.gps.window;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import fr.gagoi.gps.core.GPS;

@SuppressWarnings("serial")
public class GPS_Window extends JFrame{

	private GPS gps;
	private InformationPanel informationPanel;
	
	public GPS_Window(GPS gps) {
		this.gps = gps;
		setTitle("GPS - The Return (like Batman)");
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(1280, 720));
		informationPanel = new InformationPanel();
		getContentPane().add(informationPanel);
		
		
		
		

		setVisible(true);
	}
}
