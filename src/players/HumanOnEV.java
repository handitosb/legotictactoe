package players;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import game.*;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.utility.TextMenu;

public class HumanOnEV extends Player {

	@Override
	public int spielzug(Round round) {
		LCD.refresh();
		LCD.clear();
		LCD.drawString("Feld auswaehlen", 0, 0);
		Delay.msDelay(1000);
		String[] sfields = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		TextMenu textMenu = new TextMenu(sfields, 1);
		boolean isFree = false;
		int zug = 0;
		while (!isFree) {
			zug = textMenu.select() + 1;
			isFree = round.board.field[((zug - 1) % 3)][(zug - 1) / 3].isFree();
		}
		return zug;
	}
}