package controls;

import lejos.hardware.lcd.*;

public class Display {

	private static int row = 1;
	
	public Display() {
		
	}
	
	public static void drawString(String message) {
		// LCD.clearDisplay();
		// LCD.clear();
		LCD.drawString("Hallo", 0, 1);
		LCD.drawString(message, 0, row++);
	}
	
	public static void drawStringpkg(String[] message) {
		// LCD.clearDisplay();
		// LCD.clear();
		for (int i = 0; i < message.length; i++) {
			Display.drawString(message[i]);
		}
		row = 1;
	}
	
	public static void drawStringConsole(String message) {
		// LCD.clearDisplay();
		// LCD.clear();
		System.out.println(message +" 1 " + " " + row);row++;
	}
	
	public static void drawStringConsolepkg(String[] message) {
		// LCD.clearDisplay();
		// LCD.clear();
		for (int i = 0; i < message.length; i++) {
			Display.drawStringConsole(message[i]);
		}
		row = 1;
	}
	
	public static void clear() {
		LCD.clear();
	}
	
}
