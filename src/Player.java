import java.awt.*;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Player {
	private Point coords;
	private String name;
	private List<Card> cards;
	private List<Card> potentialSolutions;
	private boolean inRoom;
	private char squareChar;       //represents what tile is when they are not on it
    private HashMap<String, List<Point>> roomNames;
	
	public Player(Point startCoord, String name) {
		this.coords = startCoord;
		this.name = name;
		this.cards = new ArrayList<>();
		this.inRoom = false;
		this.squareChar = '#';       //starting point will be blocked once they leave
		roomCoords();
	}
	
	public void giveCard(Card card) {
		cards.add(card);
	}

	private void roomCoords(){
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
        while (true) {     //checks for correct input of room name
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
            if (distance < shortestPath){    //finds closest door as some rooms have multiple doors
                shortestPath = distance;
                closestDoor = p;
            }
        }

        int dx = closestDoor.x - this.coords.x;
        int dy = closestDoor.y - this.coords.y;

        System.out.println("You need to move " + direction(dx, dy));

        for (int i = 0; i < diceRoll; i++){
            reader = new Scanner(System.in);
            while (true) {
                System.out.println("Move: (w/a/s/d)");
                String move = reader.next();
                if (move.equals("w")) {
                    if (moveDirection("upwards", diceRoll-i-1, 0, -1)) break;
                } else if (move.equals("a")) {
                    if (moveDirection("right", diceRoll-i-1, 1, 0)) break;
                } else if (move.equals("s")) {
                    if (moveDirection("downwards", diceRoll-i-1, 0, 1)) break;
                } else if (move.equals("d")) {
                    if (moveDirection("left", diceRoll-i-1, -1, 0)) break;
                }
                System.out.println("Invalid key pressed. Please press a valid key.");
            }
        }
    }


    private boolean moveDirection(String direction, int movesRemaining, int dx, int dy){
        if (Board.getBoard()[coords.x + dx][coords.y + dy] == '.'){
            //need to update board

            coords.translate(dx, dy);
            System.out.println("You moved " + direction +". " + movesRemaining + " moves remaining.");
            squareChar = '.';
            return true;
        } else if (doorSquare(coords.x + dx, coords.y + dy) != null){
            System.out.println("You moved " + direction + ". " + movesRemaining + " moves remaining.");
            System.out.println("You are outside the " + doorSquare(coords.x + dx, coords.y + dy) + ".");
            Scanner reader = new Scanner(System.in);
            while (true){
                System.out.println("Would you like to go in? (yes/no)");
                String response = reader.next();
                if (response.toLowerCase().equals("yes")){
                    //need to update board

                    Point roomSquare = roomSquare(coords.x + dx, coords.y + dy);
                    coords.move(roomSquare.x, roomSquare.y);
                    squareChar = Board.getBoard()[roomSquare.x][roomSquare.y];
                    return true;
                } else if (response.equals("no")){
                    squareChar = Board.getBoard()[coords.x + dx][coords.y + dy];
                    coords.translate(dx, dy);
                    return true;
                }
                System.out.println("Please enter correct response.");
            }
        } else {
            System.out.println("There is a wall in the way. Please choose a different direction.");
            return false;
        }
    }

    private String direction(int dx, int dy){
	    if (dx > 0 && dy == 0) return dx + " to the right.";     //east
	    else if (dx > 0 && dy > 0) return dx + " to the right and " + dy + " downwards.";   //south-east
	    else if (dx == 0 && dy > 0) return dy + " downwards.";      //south
	    else if (dx < 0 && dy > 0) return dx*-1 + " to the left and " + dy + " downwards.";    //south-west
	    else if (dx < 0 && dy == 0) return dx*-1 + " to the left.";      //west
	    else if (dx < 0 && dy < 0) return dx*-1 + " to the left and " + dy*-1 + " upwards.";    //north-west
	    else if (dx == 0 && dy < 0)return dy*-1 + " upwards.";    //north
	    else return dx + " to the right and " + dy*-1 + " upwards.";      //north-east
    }

    private String doorSquare(int x, int y){
	    if (Board.getBoard()[x][y] == 'K') return "Kitchen";
	    else if(Board.getBoard()[x][y] == 'B')return "Ballroom";
	    else if (Board.getBoard()[x][y] == 'C')return "Conservatory";
	    else if (Board.getBoard()[x][y] == 'I')return "Billiard Room";
	    else if (Board.getBoard()[x][y] == 'L')return "Library";
	    else if (Board.getBoard()[x][y] == 'S')return "Study";
	    else if (Board.getBoard()[x][y] == 'H')return "Hall";
	    else if (Board.getBoard()[x][y] == 'O')return "Lounge";
	    else if (Board.getBoard()[x][y] == 'D')return "Dining Room";
	    else if (Board.getBoard()[x][y] == 'A')return "Accusation Room";

	    return null;
    }

    private Point roomSquare(int x, int y){
	    if (Board.getBoard()[x+1][y] == 'R' || Board.getBoard()[x+1][y] == '[' || Board.getBoard()[x+1][y] == ']') return new Point(x+1, y);
	    else if (Board.getBoard()[x][y+1] == 'R' || Board.getBoard()[x][y+1] == '[' || Board.getBoard()[x][y+1] == ']') return new Point(x, y+1);
        else if (Board.getBoard()[x-1][y] == 'R' || Board.getBoard()[x-1][y] == '[' || Board.getBoard()[x-1][y] == ']') return new Point(x-1, y);
        else return new Point(x, y-1);
    }

}