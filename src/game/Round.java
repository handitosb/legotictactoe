package game;

import lejos.hardware.lcd.LCD;
import game.Match;
import players.Player;

public class Round {

	public Board board; // Jede Spielrunde besitzt ein Board
	int playerTurn = 1; // gibt an, wer am Zug ist; 1 = Spieler 1 "X"; 2 = Spieler 2 "O"
	int countMoves = 0; // Zaehlt Anzahl der Spielzuege {0 bis 9}
	// int beginnerRound = 1; // Gibt an, wer Runde beginnt; 1 fuer Spieler 1; 2
	// fuer Spieler 2
	static int countRound = 1; // Zaehlt Spielrunden, kann über Spielstand nicht abgebildet werden,
								// da Spiele unentschieden ausgehen koennen

	/*
	 * //Konstruktor
	 */
	public Round() {
		this.board = new Board();
	}

	public Round(Board b, int playerTurn, int cM) {
		this.board = new Board();
		this.playerTurn = playerTurn;
		this.countMoves = 0;
	}

// Klassenmethoden	

	// Methode prueft, ob ein Spieler gewonnen hat und gibt Spieler als int
	// zurueck

//	public boolean isWon() {
//		// Zeilenweise waagrecht
//		for (int j = 0; j <= 2; j++) {
//			int i = 0;
//			if (board.field[i][j].getFieldValue() == 1 && board.field[i + 1][j].getFieldValue() == 1
//					&& board.field[i + 2][j].getFieldValue() == 1) {
//				// match.refreshScore(1);
//				setRoundWinner(1);
//				return true;
//			}
//			if (board.field[i][j].getFieldValue() == 2 && board.field[i + 1][j].getFieldValue() == 2
//					&& board.field[i + 2][j].getFieldValue() == 2) {
//				// refreshScore(2);
//				setRoundWinner(2);
//				return true;
//			}
//		}
//
//		// Spaltenweise senkrecht
//		for (int i = 0; i <= 2; i++) {
//			int j = 0;
//			if (board.field[i][j].getFieldValue() == 1 && board.field[i][j + 1].getFieldValue() == 1
//					&& board.field[i][j + 2].getFieldValue() == 1) {
//				// refreshScore(1);
//				setRoundWinner(1);
//				return true;
//			}
//
//			if (board.field[i][j].getFieldValue() == 2 && board.field[i][j + 1].getFieldValue() == 2
//					&& board.field[i][j + 2].getFieldValue() == 2) {
//				// refreshScore(2);
//				setRoundWinner(2);
//				return true;
//			}
//		}
//
//		// diagonal
//		// oben links - Mitte - unten rechts
//		if (board.field[0][0].getFieldValue() == 1 && board.field[1][1].getFieldValue() == 1
//				&& board.field[2][2].getFieldValue() == 1) {
//			setRoundWinner(1);
//
//			return true;
//		}
//		if (board.field[0][0].getFieldValue() == 2 && board.field[1][1].getFieldValue() == 2
//				&& board.field[2][2].getFieldValue() == 2) {
//			setRoundWinner(2);
//
//			return true;
//		}
//		// oben rechts - Mitte - unten links
//		if (board.field[2][0].getFieldValue() == 1 && board.field[1][1].getFieldValue() == 1
//				&& board.field[0][2].getFieldValue() == 1) {
//			setRoundWinner(1);
//
//			return true;
//		}
//		if (board.field[2][0].getFieldValue() == 2 && board.field[1][1].getFieldValue() == 2
//				&& board.field[0][2].getFieldValue() == 2) {
//			setRoundWinner(2);
//			return true;
//		}
//		return false;
//	}

	public int whoIsWinner() {
		// Zeilenweise waagrecht **********
		for (int s = 1; s <= 2; s++) {
			for (int j = 0; j <= 2; j++) {
				int i = 0;
				if (board.field[i][j].getFieldValue() == s && board.field[i + 1][j].getFieldValue() == s
						&& board.field[i + 2][j].getFieldValue() == s) {
					// match.refreshScore(1);
					return s;
				}
			}

			// Spaltenweise senkrecht ********
			for (int i = 0; i <= 2; i++) {
				int j = 0;
				if (board.field[i][j].getFieldValue() == s && board.field[i][j + 1].getFieldValue() == s
						&& board.field[i][j + 2].getFieldValue() == s) {
					// refreshScore(1);
					return s;
				}
			}

			// diagonal ********
			// oben links - Mitte - unten rechts
			if (board.field[0][0].getFieldValue() == s && board.field[1][1].getFieldValue() == s
					&& board.field[2][2].getFieldValue() == s) {
				return s;
			}

			// oben rechts - Mitte - unten links
			if (board.field[2][0].getFieldValue() == s && board.field[1][1].getFieldValue() == s
					&& board.field[0][2].getFieldValue() == s) {
				return s;
			}
		}
		return 0;
	}

	public void resetRound() {
		Round.setCountRound(1);
		this.board.resetBoard();
	}

	// Spielerwechsel nach Zug
	public void changeTurn() {
		this.setCountMoves(this.getCountMoves() + 1);
		if (playerTurn == 1) {
			setPlayerTurn(2);
		} else if (playerTurn == 2) {
			setPlayerTurn(1);
		}
	}

	/*
	 * Getter- und Setter-Methoden
	 */

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	public int getCountMoves() {
		return this.countMoves;
	}

	public void setCountMoves(int countMoves) {
		this.countMoves = countMoves;
	}

	public static int getCountRound() {
		return countRound;
	}

	public static void setCountRound(int countRound) {
		Round.countRound = countRound;
	}

	public void startNextRound() {
		this.board.resetBoard();
		setCountRound(getCountRound() + 1);
		setCountMoves(0);
		// Bestimmt Beginner einer Runde anhand der Rundennummer; ungerade 1, gerade 2
		setPlayerTurn((getCountRound() + 1) % 2 + 1);
	}

}
