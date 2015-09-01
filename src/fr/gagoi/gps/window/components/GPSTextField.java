package fr.gagoi.gps.window.components;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GPSTextField extends JTextField {
	
	private int x, y, width, height;
	
	public GPSTextField( int x, int y, int width, int height, JPanel contentPane) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setBounds(x, y, width, height);
		setBackground(Color.LIGHT_GRAY);
		setForeground(Color.BLACK);
		setOpaque(true);
		setBorder(new LineBorder(Color.GRAY, height/8));
		contentPane.add(this);
	}
	
	@Override
	public String toString() {
		return ("GPSTextField : " + getText() + " | x=" + x + " - y=" + y + " | width=" + width + " - height= " + height);
	}
}
