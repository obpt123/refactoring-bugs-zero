package cn.xpbootcamp.bugszero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

public class Game {
	private static final String[] ALL_CATEGORIES = new String[] { "Pop", "Science", "Sports", "Rock" };
	private static final int TOTAL_PLACES_COUNT = 12;

	List<Player> players = new ArrayList<Player>();
	Map<String, QuestionPool> questions = new HashMap<>();
	int currentPlayerIndex = 0;

	public Game() {
		Stream.of(ALL_CATEGORIES).forEach((category) -> {
			questions.put(category, new QuestionPool(category, 50));
		});
	}

	public Game(String... players) {
		this();
		Stream.of(players).forEach(this::addPlayer);
	}

	public void addPlayer(String playerName) {
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
				movePlayerPlace(roll);
				askQuestion();
			} else {
				logInfo("%s is not getting out of the penalty box", currentPlayer);
			}
		} else {
			movePlayerPlace(roll);
			askQuestion();
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

	public void correctAnswer() {
		Player currentPlayer = currentPlayer();
		logInfo("Answer was correct!!!!");
		currentPlayer.setPurses(currentPlayer.getPurses() + 1);
		logInfo("%s now has %d Gold Coins.", currentPlayer, currentPlayer.getPurses());
	}

	private void movePlayerPlace(int roll) {
		Player currentPlayer = currentPlayer();
		currentPlayer.setPlace((currentPlayer.getPlace() + roll) % TOTAL_PLACES_COUNT);
		logInfo("%s's new location is %d", currentPlayer, currentPlayer.getPlace());
	}

	private void askQuestion() {
		String currentCategory = currentCategory();
		logInfo("The category is %s", currentCategory);
		logInfo(questions.get(currentCategory).getNewQuestion());
	}

	private String currentCategory() {
		Player currentPlayer = currentPlayer();
		return ALL_CATEGORIES[currentPlayer.getPlace() % ALL_CATEGORIES.length];
	}

	private Player currentPlayer() {
		return players.get(currentPlayerIndex);
	}

	private void logInfo(String messageFormat, Object... args) {
		System.out.println(String.format(messageFormat, args));
	}
}