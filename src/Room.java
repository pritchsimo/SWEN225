import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Room {
	private String name;
	private List<Point> doorPos;
	private Point middleOfRoom;
	private List<Player> players = new ArrayList<>();
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

	public Point getMiddleOfRoom() {
		return middleOfRoom;
	}

	public List<Point> getDoorPos() {
		return doorPos;
	}

	public void playerEnter(Player p){
		players.add(p);
		if (players.size() == 1){
			players.get(0).setCoords(middleOfRoom);
		} else if (players.size() == 2){
			players.get(0).setCoords(new Point(middleOfRoom.x-1, middleOfRoom.y));
			players.get(1).setCoords(middleOfRoom);
		} else if (players.size() == 3){
			players.get(0).setCoords(new Point(middleOfRoom.x-1, middleOfRoom.y));
			players.get(1).setCoords(middleOfRoom);
			players.get(2).setCoords(new Point(middleOfRoom.x+1, middleOfRoom.y));
		} else if (players.size() == 4){
			players.get(0).setCoords(new Point(middleOfRoom.x-1, middleOfRoom.y-1));
			players.get(1).setCoords(new Point(middleOfRoom.x, middleOfRoom.y-1));
			players.get(2).setCoords(new Point(middleOfRoom.x-1, middleOfRoom.y));
			players.get(3).setCoords(middleOfRoom);
		} else if (players.size() == 5){
			players.get(0).setCoords(new Point(middleOfRoom.x-1, middleOfRoom.y-1));
			players.get(1).setCoords(new Point(middleOfRoom.x, middleOfRoom.y-1));
			players.get(2).setCoords(new Point(middleOfRoom.x+1, middleOfRoom.y-1));
			players.get(3).setCoords(new Point(middleOfRoom.x-1, middleOfRoom.y));
			players.get(4).setCoords(middleOfRoom);
		} else if (players.size() == 6){
			players.get(0).setCoords(new Point(middleOfRoom.x-1, middleOfRoom.y-1));
			players.get(1).setCoords(new Point(middleOfRoom.x, middleOfRoom.y-1));
			players.get(2).setCoords(new Point(middleOfRoom.x+1, middleOfRoom.y-1));
			players.get(3).setCoords(new Point(middleOfRoom.x-1, middleOfRoom.y));
			players.get(4).setCoords(middleOfRoom);
			players.get(5).setCoords(new Point(middleOfRoom.x+1, middleOfRoom.y));
		}
	}

	public void playerLeave(Player p){
		players.remove(p);
	}
}