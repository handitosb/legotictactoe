package game;

//Board ist ein das Spielbrett, es besteht aus 9 Feldern in quadratischer Anordung
public class Board {

	// 2-dimensionales Array aus Typ "field"
	public Field[][] field = new Field[3][3];

	// Konstuktor:
	// Durchlaeuft alle Felder des Boards in einer 2-stufigen Schleife
	// und erstellt leere Felder durch Aufruf des field-Konstruktors
	public Board() {
		// j vertikal |
		for (int j = 0; j <= 2; j++) {
			// i horizontal --
			for (int i = 0; i <= 2; i++) {
				this.field[i][j] = new Field();
			}
		}
	}

//werden zunächst nicht benötigt
//	public field[][] getBoard() {
//		return board;
//	}

	/*
	 * Klassenmethoden
	 */
	public void setBoard(int playerFeld, int player, Round round) {
		int i = (playerFeld - 1) % 3;
		int j = (playerFeld - 1) / 3;
		
		if (this.field[i][j].isFree()) {

			this.field[i][j].setFieldValue(player);

			
		} else
			System.out.println("Ungültig: Feld besetzt");
	}

	public void printBoardConsole() {
		System.out.println();
		for (int j = 0; j <= 2; j++) {
			for (int i = 0; i <= 2; i++) {
				int temp;
				temp = this.field[i][j].getFieldValue();
				if (temp == 1) {
					System.out.print("|X");
				} else if (temp == 2) {
					System.out.print("|O");
				} else {
					System.out.print("| ");
				}
			}
			System.out.println("|");
		}
		System.out.println();
	}

	public void resetBoard() {
		for (int i = 0; i < this.field.length; i++) {
			for (int j = 0; j < this.field.length; j++) {
				this.field[i][j].setFieldValue(0);
				;
			}
		}
	}

}
