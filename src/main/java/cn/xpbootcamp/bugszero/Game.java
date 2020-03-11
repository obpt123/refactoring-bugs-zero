package cn.xpbootcamp.bugszero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

public class Game {
	private static final String[] CATEGORYS = new String[] { "Pop", "Science", "Sports", "Rock" };

	List<Player> players = new ArrayList<Player>();
	Map<String, LinkedList<String>> questions = new HashMap<>();
	int currentPlayerIndex = 0;

	public Game() {
		initQuestions();
	}

	private void initQuestions() {
		Stream.of(CATEGORYS).forEach((category) -> {
			LinkedList<String> questionList = IntStream.range(0, 50)
					.mapToObj((index) -> createQuestion(category, index))
					.collect(Collectors.toCollection(LinkedList::new));
			questions.put(category, questionList);
		});
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
		String currentCategory = currentCategory();
		logInfo(questions.get(currentCategory).removeFirst());
	}

	private String currentCategory() {
		Player currentPlayer = currentPlayer();
		return CATEGORYS[currentPlayer.getPlace() % 4];
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