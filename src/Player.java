import java.awt.*;
import java.util.List;

public class Player {
	private Point coord;
	private String name;
	private List<Card> cards;
	private List<Card> potentialSolutions;
	//private Room headingTo;
	
	public Player(Point startCoord, String name) {
		this.coord = startCoord;
		this.name = name;
	}
	
	public void giveCards() {
		
	}
}