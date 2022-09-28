package game;

import basics.*;
import controls.*;
import lejos.hardware.*;
import lejos.hardware.lcd.*;
import lejos.utility.Delay;
import lejos.hardware.Button;
import java.util.Scanner;

public class Logic {

	static int ButtonID;
	Scanner scanner = new Scanner(System.in);

	public static void main(String args[]) {
		Delay.msDelay(3000);
		Display.clear();
		Display.drawString("Push Button");
		ButtonID = Button.waitForAnyEvent(ButtonID);
		//System.out.println("buttonID "+ ButtonID);
		Display.drawString(Integer.toString(ButtonID));
		Delay.msDelay(1000);

	}

}
