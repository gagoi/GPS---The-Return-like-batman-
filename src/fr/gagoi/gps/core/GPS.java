package fr.gagoi.gps.core;

import fr.gagoi.gps.window.GPS_Window;

public class GPS {

	public boolean debug = true;

	public GPS() {
		new GPS_Window(this);
		new Map("map", this);
	}

	public static void main(String args[]) {
		new GPS();
	}

	public boolean getDebug() {
		return debug;
	}
	public void setDebug(boolean value) {
		this.debug = debug;
	}

	public void log(String TAG, String text) {
		if (getDebug()) {
			if (TAG.equals("ERROR")) System.err.println(TAG + " : " + text);
			else System.out.println(TAG + " : " + text);
		}
	}
}
