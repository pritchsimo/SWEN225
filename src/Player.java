import java.awt.*;
import java.util.*;
import java.util.List;

public class Player {
	private Point coords;
	private String name;
	private List<Card> cards;
	private List<Card> potentialSolutions;
	//private Room headingTo;
    private HashMap<String, List<Point>> roomNames;
	
	public Player(Point startCoord, String name) {
		this.coords = startCoord;
		this.name = name;
		cards = new ArrayList<>();
//		roomNames = Arrays.asList("kitchen", "ballroom", "conservatory", "billiard room", "library", "study",
//                                    "hall", "lounge", "dining room");
	}
	
	public void giveCard(Card card) {
		cards.add(card);
	}

	public void roomCoords(){
	    roomNames = new HashMap<>();
	    roomNames.put("kitchen", Arrays.asList(new Point(5, 8)));
        roomNames.put("ballroom", Arrays.asList(new Point(8, 6), new Point(17, 6), new Point(10, 9), new Point(15, 9)));
        roomNames.put("conservatory", Arrays.asList(new Point(19, 6)));
        roomNames.put("billiard room", Arrays.asList(new Point(18, 10), new Point(23, 14)));
        roomNames.put("library", Arrays.asList(new Point(21, 14), new Point(17, 17)));
        roomNames.put("study", Arrays.asList(new Point(18, 21)));
        roomNames.put("hall", Arrays.asList(new Point(16, 21), new Point(12, 18), new Point(13, 18)));
        roomNames.put("lounge", Arrays.asList(new Point(7, 19)));
        roomNames.put("dining room", Arrays.asList(new Point(7, 17), new Point(9, 13)));

    }

	public void move(int diceRoll){
        List<Point> doors;

        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("Which room do you wish to head towards? ");
            String r = reader.next();
            if (roomNames.containsKey(r.toLowerCase())) {
                doors = roomNames.get(r);
                break;
            }
            System.out.println("Please enter a correct room name.");
        }
        reader.close();

        double shortestPath = 1000;   //arbitrary large number
        Point closestDoor = new Point(0,0);
        for (Point p: doors){
            double distance = Math.sqrt(Math.pow(Math.abs(this.coords.x - p.x),2) + Math.pow(Math.abs(this.coords.y - p.y),2));
            if (distance < shortestPath){
                shortestPath = distance;
                closestDoor = p;
            }
        }

        int dx = Math.abs(this.coords.x - closestDoor.x);
        int dy = Math.abs(this.coords.y - closestDoor.y);

        //not finished yet
    }
}