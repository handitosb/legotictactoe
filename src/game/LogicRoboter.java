package game;

import java.util.InputMismatchException;
import java.util.Scanner;
import controls.*;

import basics.Roboter;
import players.Computer;
import players.HumanOnEV;
import players.Player;
import lejos.hardware.Button;
import lejos.hardware.lcd.*;
import lejos.utility.TextMenu;
import lejos.hardware.Battery;
import lejos.utility.Delay;

public class LogicRoboter {

	static Match match = null;
	static Player player1 = null;
	static Player player2 = null;
	static TextMenu textMenu;
	static Roboter roboter;

	// hier startet der Programmablauf des Spiels
	static void startGame() {
		int scanType = 0; // Variable zum Speichern des Match-Typs 1=PvP, 2 PvC, 3 CvC
		// Anzeigen des Hauptmenues auf der Konsole

		while (true) {

			// Auswahl der Spielmoeglichkeiten
			// 1 PvP - Mensch gegen Mensch
			// 2 PvC - Mensch gegen Computer
			// 3 CvC - Computer gegen Computer
			// 4 Stift kalibrieren
			// 5 Programmende
			scanType = mainmenu()+1;
			LCD.drawInt(scanType, 5, 7);

			// Auswertung des int scanType und Rueckmeldung an Nutzer
			// 1 PvP-Menu
			if (scanType == 1) {
				LCD.refresh();
				LCD.clear();
				LCD.drawString("PvP ausgewaehlt", 0, 0);
				// Delay, damit Anzeige auf Display 2 Sekunden angezeigt wird
				Delay.msDelay(2000);
//				// Initiallisieren der notwendigen Objekte mit Uebergabe des Spieltyps
				init(1);
//				break;
			}
			// 2 PvC-Menu
			else if (scanType == 2) {
				LCD.refresh();
				LCD.clear();
				LCD.drawString("PvC ausgewaehlt", 0, 0);
				Delay.msDelay(2000);

				// Staerke des Computers festlegen
				String[] stringStrenght = new String[] { "1 - Leicht", "2 - Mittel", "3 - Stark" };

				LCD.drawString("Spielstaerke Computers", 0, 1);
				textMenu = new TextMenu(stringStrenght);
				int strenght = textMenu.select();
//				break;
				// Rueckmeldung an Benutzer, welche Staerke gewaehlt wurde (1,2 oder 3)
				LCD.refresh();
				LCD.clear();
				LCD.drawString(stringStrenght[strenght] + " gewaehlt", 0, 0);
				Delay.msDelay(2000);

				// Festlegen, wer die erste Runde beginnt

				String[] stringBeginner = { "1 - Mensch", "2 - Computer" };

				LCD.drawString("Wer beginnt", 0, 1);
				textMenu = new TextMenu(stringBeginner);
				int beginner = textMenu.select();
				LCD.refresh();
				LCD.clear();
				LCD.drawString(stringBeginner[beginner] + " gewaehlt", 0, 0);
				Delay.msDelay(2000);
				

				// Initiallisieren der notwendigen Objekte mit uebergabe des Spieltyps
				LCD.refresh();
				LCD.clear();
				LCD.drawString("scanType "+scanType, 5, 4);
				LCD.drawString("strength "+strenght, 5, 5);
				LCD.drawString("beginner "+beginner, 5, 6);
				Delay.msDelay(4000);
				init(scanType, strenght+1, beginner+1);
				break;
			}
			// 3 CvC-Menu
			else if (scanType == 3) {
				LCD.refresh();
				LCD.clear();
				LCD.drawString("Computer gegen Computer ausgewaehlt", 0, 0);
				Delay.msDelay(2000);
				init(scanType);
				break;
			}
			// 4 Stift kalibrieren
			// TODO Methode, um den Stift zu kalibrieren
			else if (scanType == 4) {
				break;
			}

			// 5 Programm beenden
			else if (scanType == 5) {
				System.exit(scanType);
			}

		}

		// init beendet

		// Start des Match, bzw. 1. Spielrunde
		startMatch();
//		
//		// Runde ist zu Ende

	}

	private static void init(int a) {
		if (a == 1) {
			player1 = new HumanOnEV();
			player2 = new HumanOnEV();
		}
		if (a == 3) {
			player1 = new Computer();
			player2 = new Computer();
		}
		match = new Match(player1, player2);
	}

	// s = MatchType; l = Strength Computer; b = Beginner
	private static void init(int s, int l, int b) {
		if (b == 1) {
			player1 = new HumanOnEV();
			player2 = new Computer(l);
		} else if (b == 2) {
			player1 = new Computer(l);
			player2 = new HumanOnEV();
		}
		match = new Match(player1, player2);
	}

	private static void startMatch() {

		LCD.refresh();
		LCD.clear();
		LCD.drawString("Es beginnt Player " + match.round.getPlayerTurn(), 0, 0);
		int playerFeld = 0;

		while (match.round.getCountMoves() <= 9) {
			// System.out.println("1Moves " + match.round.getCountMoves());
			// System.out.println("1PlayerTurn " + match.round.getPlayerTurn());
			if (match.round.getPlayerTurn() == 1) {
				playerFeld = player1.spielzug(match.round);
			} else {
				playerFeld = player2.spielzug(match.round);
			}

			match.round.board.setBoard(playerFeld, match.round.getPlayerTurn(), match.round);
			match.round.board.printBoardConsole();
			match.round.changeTurn();
			// System.out.println("2Moves " + match.round.getCountMoves());
			// System.out.println("2PlayerTurn " + match.round.getPlayerTurn());

			if (match.round.getCountMoves() >= 5) {

				if (match.round.whoIsWinner() != 0) {
					LCD.refresh();
					LCD.clear();
					LCD.drawString("Gewonnen von Spieler " + match.round.whoIsWinner(), 0, 0);
					Delay.msDelay(2000);
					match.refreshScore(match.round.whoIsWinner());
					match.printScore();
					break;
				} else if (match.round.countMoves == 9) {
					LCD.refresh();
					LCD.clear();
					LCD.drawString("Unentschieden!", 0, 0);
					Delay.msDelay(2000);
//					match.printScore();
					break;
				}
			}
			LCD.clear();
			LCD.refresh();
			LCD.drawString("Player " + match.round.getPlayerTurn() + " is next!", 0, 0);
			Delay.msDelay(2000);

		}

		// Abfrage für weitere Spielrunde
		LCD.drawString("Noch eine Runde", 0, 2);
		LCD.refresh();
		Delay.msDelay(2000);

		String[] stringOneMoreRound = new String[] { "1 Ja", "2 Nein" };
		textMenu = new TextMenu(stringOneMoreRound,3);
		int oneMoreRound = textMenu.select()+1;
		if (oneMoreRound == 1) {
			match.round.startNextRound();
			startMatch();
		}
		if (oneMoreRound != 1) {
			mainmenu();
		}
	}

	

	// Zeigt das MainMenu in der Konsole an
	private static int mainmenu() {
		
		LCD.refresh();
		LCD.clear();
		LCD.drawString("Tic-Tac-Toe!", 0, 0);
		String[] stringmainmenu = new String[] { "1 Mensch gegen Mensch", "2 Mensch vs Computer",
				"3 Computer vs Computer", "4 Stift kalibrieren", "5 Programm beenden" };
		textMenu = new TextMenu(stringmainmenu);
		int Type = textMenu.select();
		return Type;
	}
}
