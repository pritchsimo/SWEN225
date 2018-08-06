import java.awt.*;
import java.util.List;

public class Room {
	private String name;
	private List<Point> doorPos;
	private Point middleOfRoom;
	private List<Player> players;
	private Weapon weapon;
	
	public Room(String name, List<Point> doorPos, Point middleOfRoom) {
		this.name = name;
		this.doorPos = doorPos;
		this.middleOfRoom = middleOfRoom;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public String getName() {
		return name;
	}

	public List<Point> getDoorPos() {
		return doorPos;
	}
}