import java.awt.*;
import java.util.List;

public class Room {
	private String name;
	private List<Point> doorPos;
	private List<Player> players;
	private Weapon weapon;
	
	public Room(String name, List<Point> doorPos, Weapon weapon) {
		this.name = name;
		this.doorPos = doorPos;
		this.weapon = weapon;
	}
}