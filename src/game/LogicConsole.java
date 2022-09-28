package game;

import java.util.InputMismatchException;
import java.util.Scanner;

import players.*;
//import game.Match;

public class LogicConsole {

	// Scanner wird benoetigt fuer Tastatureingaben
	static Scanner scanner = new Scanner(System.in);
	static Match match = null;
	static Player player1 = null;
	static Player player2 = null;

	// hier startet der Programmablauf des Spiels
	static void startGame() {
		int scanType = 0; // Variable zum Speichern des Match-Typs 1=PvP, 2 PvC, 3 CvC
		// Anzeigen des Hauptmenues auf der Konsole

		while (true) {
			showmainmenu();

			// Auswahl der Spielmoeglichkeiten
			// 1 PvP - Mensch gegen Mensch
			// 2 PvC - Mensch gegen Computer
			// 3 CvC - Computer gegen Computer
			// 4 Programmende
			while (true) {
				// Abfangen von Fehleingaben, die nicht int sind
				try {
					scanType = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Simon sagt: Nur Zahlen von 1 bis 3!");
					scanner.next();
				}
				// Einlesen des int-Wet erfolgreich
				// Auswertung des int s nach Wert und Rueckmeldung an Nutzer
				if (scanType == 1) {
					System.out.println("\n1 Spieler gegen Spieler ausgewaehlt");
					// Initiallisieren der notwendigen Objekte mit uebergabe des Spieltyps
					init(1);
					break;
				} else if (scanType == 2) {
					System.out.println("\n2 Spieler gegen Computer ausgewaehlt");
					int scanStrenght = 0; // Variable zur Speicherung der Spielstärke des Computergegners
					// Zweite Schleife zur Auswahl der Spielstaerke des Computergegners
					// Drei Stufen: Leicht, Mittel, Stark
					while (true) {
						System.out.print("\nSpielstaerke des Computers auswaehlen");
						System.out.print("\n1 - Leicht   2 - Mittel    3 - Stark");
						System.out.println("Auswahl:");
						// Abfangen von Fehleingaben, die nicht int sind
						try {
							scanStrenght = scanner.nextInt();
						} catch (InputMismatchException e) {
							System.out.println("Simon sagt: Nur Zahlen von 1 bis 3!");
							scanner.next();
						}
						// While-Schleife zur Eingabe wird wiederholt, wenn nicht 1,2 oder 3
						if (scanStrenght == 1 || scanStrenght == 2 || scanStrenght == 3) {
							break;
						}
					}
					// Rueckmeldung an Benutzer, welche Stuerke gewuehlt wurde (1,2 oder 3)
					switch (scanStrenght) {
					case 1:
						System.out.print("\n1 - Leicht gewaehlt");
						break;
					case 2:
						System.out.print("\n2 - Mittel gewaehlt");
						break;
					case 3:
						System.out.print("\n3 - Stark gewaehlt");
						break;
					}
					// Variable zum Speichern des Beginners
					int scanBeginner = 0;
					while (true) {
						String[] menueBeginner = { "Wer beginnt", "1 - Mensch", "2 - Computer", "Auswahl: " };
						for (String s : menueBeginner) {
							System.out.print("\n" + s);
						}
						// Abfangen von Fehleingaben, die nicht int sind
						try {
							scanBeginner = scanner.nextInt();
						} catch (InputMismatchException e) {
							System.out.println("Simon sagt: Nur Zahlen von 1 bis 2!");
							scanner.next();
						}
						// While-Schleife zur Eingabe wird wiederholt, wenn nicht 1,2 oder 3
						if (scanBeginner == 1 || scanBeginner == 2) {
							break;
						}
					}
					// Initiallisieren der notwendigen Objekte mit uebergabe des Spieltyps
					init(scanType, scanStrenght, scanBeginner);
					break;
				} else if (scanType == 3) {
					System.out.println("\n3 Computer gegen Computer ausgewaehlt");
					// Initiallisieren der notwendigen Objekte mit Uebergabe des Spieltyps
					init(3);
					break;
				} else if (scanType == 4) {
					System.exit(scanType);
				}
				// Erste While-Schleife zur Eingabe wird wiederholt, wenn Wert nicht 1,2 oder 3
			}

			// init beendet

			// Start des Match, bzw. 1. Spielrunde
			startMatch();
			// Runde ist zu Ende

		}
	}

	private static void init(int a) {
		if (a == 1) {
			player1 = new Human();
			player2 = new Human();
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
			player1 = new Human();
			player2 = new Computer(l);
		} else if (b == 2) {
			player1 = new Computer(l);
			player2 = new Human();
		}
		match = new Match(player1, player2);
	}

	private static void startMatch() {
		System.out.println("\n******** Runde " + match.round.getCountRound() + " ********");
		System.out.println("Es beginnt Player " + match.round.getPlayerTurn());
		int playerFeld = 0;

		while (match.round.getCountMoves() <= 9) {
			if (match.round.getPlayerTurn() == 1) {
				playerFeld = player1.spielzug(match.round);
			} else {
				playerFeld = player2.spielzug(match.round);
			}

			match.round.board.setBoard(playerFeld, match.round.getPlayerTurn(), match.round);
			match.round.board.printBoardConsole();
			match.round.changeTurn();

			if (match.round.getCountMoves() >= 5) {

				if (match.round.whoIsWinner() != 0) {
					System.out.println("Gewonnen von Spieler " + match.round.whoIsWinner());
					match.refreshScore(match.round.whoIsWinner());
					match.printScore();
					break;
				} else if (match.round.countMoves == 9) {
					System.out.println("Unentschieden!");
					match.printScore();
					break;
				}
			}
			System.out.println("Player " + match.round.getPlayerTurn() + " is next!");

		}

		// Abfrage für weitere Spielrunde
		char scanOneMoreRound = 0;
		System.out.println("\nEine weitere Runde spielen?");
		while (true) {
			// Abfangen von Fehleingaben, die nicht string sind
			try {
				scanOneMoreRound = scanner.next().charAt(0); // Nur erstes Zeichen einlesen
			} catch (InputMismatchException e) {
				System.out.println("Simon sagt: Nur 'j' oder 'n' !");
				scanner.next().charAt(0);
			}
			// Einlesen des string-Wert erfolgreich
			// Auswertung und Rueckmeldung an Nutzer
			if (Character.toLowerCase(scanOneMoreRound) == 'j') {
				// TODO implement method
				match.round.startNextRound();
				startMatch();
			}
			if (Character.toLowerCase(scanOneMoreRound) == 'n') {

			}
			break;
		}
	}

	// Zeigt das MainMenu in der Konsole an
	private static void showmainmenu() {
		String[] stringmainmenu = new String[] { "Tic-Tac-Toe", "1 Mensch gegen Mensch", "2 Mensch vs Computer",
				"3 Computer vs Computer", "4 Programm beenden", "Bitte Auswahl treffen:" };
		for (int i = 0; i < stringmainmenu.length; i++) {
			System.out.println(stringmainmenu[i]);
		}
	}
}
