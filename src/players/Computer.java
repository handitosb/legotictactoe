package players;

import game.*;
import java.math.*;
import java.util.Random;

import game.*;

public class Computer extends Player implements AI {

	int strengthOpponent = 0;
	
	//Konstruktor
	public Computer() {
	}
	
	//falls Stärke übergeben wird
	public Computer(int sO) {
		this.strengthOpponent = sO;
	}
		
//	@Override
//	public Field AIZug(Round round) {
//		while (round.isEnd() != true && round.getCountMoves() < 9) {
//			Random rn = new Random();
//			int randomNumber = rn.nextInt(9) + 1;
//			if (round.board.field[(randomNumber % 3) - 1][(randomNumber - 1) / 3].isFree()) {
//				return round.board.field[(randomNumber % 3) - 1][(randomNumber - 1) / 3];
//			}
//		}
//		return null;
//	}
	
	// Spielzug fuer StrenghOpponent = 1; Computer nimmt Zufallsfeld ohne Beruecksichtigung des Spielfelds
	public int spielzug(Round round) {
			Random rn = new Random();
			int count = 0;
			//<9 gibt nur 9 Felder zu besetzen, damit Computer nicht in Endlosschleife hängt
			while (round.getCountMoves() < 20 ) {
				int randomNumber = rn.nextInt(9)+1;
				if (round.board.field[((randomNumber-1) % 3)][(randomNumber - 1) / 3].isFree()) {
								return randomNumber;
				}
			}
			return 0;
		}



}
