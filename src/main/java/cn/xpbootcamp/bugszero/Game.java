package cn.xpbootcamp.bugszero;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
	List<Player> players = new ArrayList<Player>();

	LinkedList popQuestions = new LinkedList();
	LinkedList scienceQuestions = new LinkedList();
	LinkedList sportsQuestions = new LinkedList();
	LinkedList rockQuestions = new LinkedList();

	int currentPlayerIndex = 0;

	public Game() {
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast(createQuestion("Pop" , i));
			scienceQuestions.addLast(createQuestion("Science" , i));
			sportsQuestions.addLast(createQuestion("Sports" , i));
			rockQuestions.addLast(createQuestion("Rock", i));
		}
	}

	private String createQuestion(String category, int index) {
		return String.format("%s Question %d", category, index);
	}

	public boolean add(String playerName) {
		players.add(new Player(playerName));
		logInfo(playerName + " was added");
		logInfo("They are player number " + players.size());
		return true;
	}

	public void roll(int roll) {
		Player currentPlayer = currentPlayer();
		logInfo(currentPlayer + " is the current player");
		logInfo("They have rolled a " + roll);

		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				logInfo(currentPlayer + " is getting out of the penalty box");
				currentPlayer.setInPenaltyBox(false);
				movePlayerAndAskQuestion(roll);
			} else {
				logInfo(currentPlayer + " is not getting out of the penalty box");
			}
		} else {
			movePlayerAndAskQuestion(roll);
		}
	}

	public boolean canAnswer() {
		return !currentPlayer().isInPenaltyBox();
	}

	private void movePlayerAndAskQuestion(int roll) {
		Player currentPlayer = currentPlayer();
		currentPlayer.setPlace((currentPlayer.getPlace() + roll) % 12);

		logInfo(currentPlayer + "'s new location is " + currentPlayer.getPlace());
		logInfo("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			logInfo(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			logInfo(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			logInfo(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			logInfo(rockQuestions.removeFirst());
	}

	private String currentCategory() {
		Player currentPlayer = currentPlayer();

		if (currentPlayer.getPlace() == 0)
			return "Pop";
		if (currentPlayer.getPlace() == 4)
			return "Pop";
		if (currentPlayer.getPlace() == 8)
			return "Pop";
		if (currentPlayer.getPlace() == 1)
			return "Science";
		if (currentPlayer.getPlace() == 5)
			return "Science";
		if (currentPlayer.getPlace() == 9)
			return "Science";
		if (currentPlayer.getPlace() == 2)
			return "Sports";
		if (currentPlayer.getPlace() == 6)
			return "Sports";
		if (currentPlayer.getPlace() == 10)
			return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		Player currentPlayer = currentPlayer();
		logInfo("Answer was correct!!!!");
		currentPlayer.setPurses(currentPlayer.getPurses() + 1);
		logInfo(currentPlayer + " now has " + currentPlayer.getPurses() + " Gold Coins.");

		boolean winner = didPlayerWin();
		return winner;

	}

	public void setNextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size())
			currentPlayerIndex = 0;
	}

	private Player currentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public boolean wrongAnswer() {
		Player currentPlayer = currentPlayer();
		logInfo("Question was incorrectly answered");
		logInfo(currentPlayer + " was sent to the penalty box");
		currentPlayer.setInPenaltyBox(true);
		return true;
	}

	private boolean didPlayerWin() {
		Player currentPlayer = currentPlayer();
		return !(currentPlayer.getPurses() == 6);
	}

	private void logInfo(Object message) {
		System.out.println(message);
	}
}