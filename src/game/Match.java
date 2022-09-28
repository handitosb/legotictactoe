package game;

import players.Player;

//In sich abgeschlossenes Spiel von Tic-Tac-Toe mit Anzahl n {1..*} an Runden 
public class Match {
		
	int[] score = new int[2]; 	//Beinhaltet zwei Zahlenwerte mit den Punktständen für je einen Spieler
	int countRound;				//Zählt Anzahl der Spielrunden n mit n = {1..*}			
	Player[] playerList = new Player[2];	//Speichert die beiden Spieler
	//int matchBeginner;			//Gibt an, wer erste Match beginnt; 1 = Spieler 1 und 2 = Spieler 2
	public Round round;
	
/*
 * Konstruktor	
 */
	
	

	//DEFAULT Konstruktor - (für zwei menschliche Spieler)
	public Match() {
		this.score = new int[]{0,0};
		this.countRound = 1;
		//this.matchBeginner = 1;
		
	}
	
	//mit Übergabe der beiden Spieler
		public Match(Player player1, Player player2) {
			this.playerList[0]=player1;
			this.playerList[1]=player2;
			this.round = new Round();
			this.score = new int[]{0,0};
		}
		
//	//für Mensch gegen Computer
//		public Match(int matchType, int beginner) {
//			this.score = new int[]{0,0};
//			this.countRound = 1;
//			this.matchBeginner = beginner;
//			if (this.matchType == 1) {
//				this.playerList[0] = new players.Human();				
//				this.playerList[1] = new players.Human();
//			}
//			if (this.matchType == 2) {
//					this.playerList[0] = new players.Human();
//					this.playerList[1] = new players.Computer();
//					
//				
//				
//			}
//			if (this.matchType == 3) {
//				this.playerList[0] = new players.Computer();
//				this.playerList[1] = new players.Computer();
//			} 
//			
//		 }
		
		
		
	/*
	 * 	Getter- und Setter-Methoden
	 */
		
		
		//Gibt den Spielstand zurueck
		public int getScore(int n) {
			return this.score[n];
		}

		//setzt den Spielstand
		public void setScore(int n, int newScore) {
			this.score[n] = newScore;
		}
		
		//Sobald Round gewonnen oder Draw, wird Spielstand aktualisiert
		public void refreshScore(int winner) {
			if (winner == 1 || winner == 2 ) {
			this.setScore(winner-1, ( this.getScore(winner-1)+1 ) );
			}
		}
				
		//Gibt Anzahl an gespielten Runden.
		public int getCountRound() {
			return countRound;
		}

		public void setCountRound(int countRound) {
			this.countRound = countRound;
		}
				
	/*
	 *	Klassenmethoden 
	 */
		
		

//		public void resetMatch(int newMatchType) {
//			if (newMatchType != this.matchType) {
//				new Match(newMatchType, this.matchBeginner);
//			}
//			this.countRound = 0;
//			this.matchType = newMatchType;
//		}

		public void printScore() {
			System.out.print("Spielstand: ");
			System.out.print(score[0] + " : ");
			System.out.print(score[1]);
		}
		
		
		
		
		
	}