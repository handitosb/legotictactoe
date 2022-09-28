package game;

public class Main {

	public static void main(String args[]) {

		// gameType auswählen 
		//		1 fuer "Console(Java)"
		// 		2 fuer "LEGO EV3"
		int gameType = 2;

		if (gameType == 1) {
//			LogicConsole logicConsole = new LogicConsole();
//			logicConsole.startGame();
			LogicConsole.startGame();
		}

		if (gameType == 2) {
			LogicRoboter logicRoboter = new LogicRoboter();
			logicRoboter.startGame();

		}
	}
}
