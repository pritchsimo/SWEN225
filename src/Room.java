import java.awt.*;
import java.util.List;

public class Room {
	private String name;
	private List<Point> doorPos;
	private List<Player> players;
	private Weapon weapon;
	
	public Room(String name, List<Point> doorPos) {
		this.name = name;
		this.doorPos = doorPos;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}