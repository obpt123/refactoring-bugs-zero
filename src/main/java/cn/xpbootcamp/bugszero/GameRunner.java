package cn.xpbootcamp.bugszero;

import java.util.Random;

public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Random rand = new Random();
		playGame(rand);

	}

	public static void playGame(Random rand) {
		Game aGame = new Game();

		aGame.addPlayer("Chet");
		aGame.addPlayer("Pat");
		aGame.addPlayer("Sue");

		do {

			aGame.roll(rand.nextInt(6) + 1);
			if (aGame.canAnswer()) {
				if (rand.nextInt(9) == 7) {
					aGame.wrongAnswer();
				} else {
					aGame.correctAnswer();
				}
			}
			if (aGame.didPlayerWin()) {
				break;
			}
			aGame.setNextPlayer();

		} while (true);
	}
}