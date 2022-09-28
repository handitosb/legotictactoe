package players;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import game.*;

public class Human extends Player {

	@Override
	public int spielzug(Round round) {
		Scanner sc = new Scanner(System.in);
		while (round.getCountMoves() <= 9) {
			int zug;

			while (true) {
				try {
					zug = sc.nextInt();

					if (zug >= 1 && zug <= 9) {
						if (round.board.field[((zug - 1) % 3)][(zug - 1) / 3].isFree()) {
							return zug;
						} else {
							System.out.println("Das Feld ist belegt");
						}
					} else {
						System.out.println("Gib nur Zahlen von 1 bis 9 ein");
					}
				} catch (InputMismatchException e) {
					System.out.println("Bitte nur Zahlen eingeben");
				}
			}
		}
		return 0;
	}
}