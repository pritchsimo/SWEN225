import java.util.ArrayList;
import java.util.List;

public class Cluedo {
	private Player move;
	private Board board;
	
	public Cluedo(Player move, Board board) {
		this.move = move;
		this.board = board;
	}
	
	private void setup() {
		cardSetup();
	}
	
	private void cardSetup() {
		List<Card> playerCards = new ArrayList<>();
		playerCards.add(new Card("Miss Scarlett", "Player"));
		playerCards.add(new Card("Col. Mustard", "Player"));
		playerCards.add(new Card("Mrs. White", "Player"));
		playerCards.add(new Card("Mr. Green", "Player"));
		playerCards.add(new Card("Mrs. Peacock", "Player"));
		playerCards.add(new Card("Prof. Plum", "Player"));
	}
	
	public static void main(String... args) {
	}
}