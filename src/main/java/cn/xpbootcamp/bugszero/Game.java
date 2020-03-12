package cn.xpbootcamp.bugszero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

public class Game {
	private static final String[] ALL_CATEGORIES = new String[] { "Pop", "Science", "Sports", "Rock" };

	List<Player> players = new ArrayList<Player>();
	Map<String, LinkedList<String>> questions = new HashMap<>();
	int currentPlayerIndex = 0;

	public Game() {
		initQuestions();
	}

	public void add(String playerName) {
		players.add(new Player(playerName));
		logInfo("%s was added", playerName);
		logInfo("They are player number %d", players.size());
	}

	public void roll(int roll) {
		Player currentPlayer = currentPlayer();
		logInfo("%s is the current player", currentPlayer);
		logInfo("They have rolled a %d", roll);

		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				logInfo("%s is getting out of the penalty box", currentPlayer);
				currentPlayer.setInPenaltyBox(false);
				movePlayerAndAskQuestion(roll);
			} else {
				logInfo("%s is not getting out of the penalty box", currentPlayer);
			}
		} else {
			movePlayerAndAskQuestion(roll);
		}
	}

	public boolean canAnswer() {
		return !currentPlayer().isInPenaltyBox();
	}

	public void setNextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}

	public boolean didPlayerWin() {
		Player currentPlayer = currentPlayer();
		return currentPlayer.getPurses() == 6;
	}

	public void wrongAnswer() {
		Player currentPlayer = currentPlayer();
		logInfo("Question was incorrectly answered");
		logInfo("%s was sent to the penalty box", currentPlayer);
		currentPlayer.setInPenaltyBox(true);
	}

	public void wasCorrectlyAnswered() {
		Player currentPlayer = currentPlayer();
		logInfo("Answer was correct!!!!");
		currentPlayer.setPurses(currentPlayer.getPurses() + 1);
		logInfo("%s now has %d Gold Coins.", currentPlayer, currentPlayer.getPurses());
	}

	private void initQuestions() {
		Stream.of(ALL_CATEGORIES).forEach((category) -> {
			LinkedList<String> questionList = IntStream.range(0, 50)
					.mapToObj((index) -> createQuestion(category, index))
					.collect(Collectors.toCollection(LinkedList::new));
			questions.put(category, questionList);
		});
	}

	private String createQuestion(String category, int index) {
		return String.format("%s Question %d", category, index);
	}

	private void movePlayerAndAskQuestion(int roll) {
		Player currentPlayer = currentPlayer();
		currentPlayer.setPlace((currentPlayer.getPlace() + roll) % 12);

		logInfo("%s's new location is %d", currentPlayer, currentPlayer.getPlace());
		logInfo("The category is %s", currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		String currentCategory = currentCategory();
		logInfo(questions.get(currentCategory).removeFirst());
	}

	private String currentCategory() {
		Player currentPlayer = currentPlayer();
		return ALL_CATEGORIES[currentPlayer.getPlace() % 4];
	}

	private Player currentPlayer() {
		return players.get(currentPlayerIndex);
	}

	private void logInfo(String messageFormat, Object... args) {
		System.out.println(String.format(messageFormat, args));
	}
}