package fr.gagoi.gps.window.components;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GPSLabel extends JLabel {

	private String text;
	private int x, y, width, height;

	public GPSLabel(String text, int x, int y, int width, int height, JPanel contentPane) {
		super(text);
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setBounds(x, y, width, height);
		setBackground(Color.LIGHT_GRAY);
		setForeground(Color.BLACK);
		setHorizontalAlignment(JLabel.CENTER);
		setOpaque(true);
		setBorder(new LineBorder(Color.GRAY, height/8));
		contentPane.add(this);
	}

	@Override
	public String toString() {
		return ("GPSLabel : " + text + " | x=" + x + " - y=" + y + " | width=" + width + " - height= " + height);
	}
}
