
import java.awt.*;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Player {
    private Point coords;
    private String name;
    private List<Card> cards;
    private List<String> knownEvidence;
    private List<List<Card>> potentialSolutions; //probably redundant
    private List<Player> nextPlayers;
    private Room room;
    //private char squareChar;       //represents what tile is when they are not on it

    private HashMap<String, List<Point>> roomNames;

    public Player(Point startCoord, String name) {
        this.coords = startCoord;
        this.name = name;
        this.cards = new ArrayList<>();
        this.knownEvidence = new ArrayList<>();
        this.room = null;
        //this.squareChar = '#';       //starting point will be blocked once they leave
    }

    public void giveCard(Card card) {
        cards.add(card);
        knownEvidence.add(card.getName());
    }

    public String getName() {
        return name;
    }

    public void move(int diceRoll, Board board, Scanner s) {
        List<Point> doors;

        while (true) {     //checks for correct input of room name
            System.out.println("Which room do you wish to head towards? ");
            String r = s.nextLine();
            //doors = board.getRooms().get("kitchen").getDoorPos();

            if (board.getRooms().containsKey(r.toLowerCase())) {
                doors = board.getRooms().get(r).getDoorPos();
                break;
            }
            //break;
            System.out.println("Please enter a correct room name.");
        }

        double shortestPath = 1000;   //arbitrary large number
        Point closestDoor = new Point(0, 0);
        for (Point p : doors) {
            double distance = Math.sqrt(Math.pow(Math.abs(this.coords.x - p.x), 2) + Math.pow(Math.abs(this.coords.y - p.y), 2));
            if (distance < shortestPath) {    //finds closest door as some rooms have multiple doors
                shortestPath = distance;
                closestDoor = p;
            }
        }

        int dx = closestDoor.x - this.coords.x;
        int dy = closestDoor.y - this.coords.y;

        System.out.println("You need to move " + direction(dx, dy));

        for (int i = 0; i < diceRoll; i++) {
            while (true) {
                if (room != null) {
                    System.out.println("You are in the " + room.getName() + ". Make an suggestion? (suggestion) or");
                }
                System.out.println("Move: (w/a/s/d)");
                String move = s.nextLine();
                if (move.equals("w")) {
                    if (moveDirection("upwards", diceRoll - i - 1, 0, -1, board, s)) break;
                } else if (move.equals("a")) {
                    if (moveDirection("right", diceRoll - i - 1, 1, 0, board, s)) break;
                } else if (move.equals("s")) {
                    if (moveDirection("downwards", diceRoll - i - 1, 0, 1, board, s)) break;
                } else if (move.equals("d")) {
                    if (moveDirection("left", diceRoll - i - 1, -1, 0, board, s)) break;
                } else if (move.equals("suggestion")){
                    makeSuggestion();
                    return;
                }
                System.out.println("Invalid input. Please enter a valid input.");
            }
        }
        if (room != null) {
            System.out.println("You are in the " + room.getName() + ". Make an suggestion? (yes/no)");
        }
        String r = s.nextLine();
        if (r.equals("yes")){
            makeSuggestion();
        }

    }


    private boolean moveDirection(String direction, int movesRemaining, int dx, int dy, Board board, Scanner s) {
        if (board.getBoard()[coords.x + dx][coords.y + dy] == '.') {
            coords.translate(dx, dy);
            System.out.println("You moved " + direction + ". " + movesRemaining + " moves remaining.");
            //squareChar = '.';
            return true;
        } else if (doorSquare(coords.x + dx, coords.y + dy, board) != null && room == null) {
            System.out.println("You moved " + direction + ". " + movesRemaining + " moves remaining.");
            System.out.println("You are outside the " + doorSquare(coords.x + dx, coords.y + dy, board) + ".");
            while (true) {
                System.out.println("Would you like to go in? (yes/no)");
                String response = s.nextLine();
                if (response.toLowerCase().equals("yes")) {
                    Point roomSquare = roomSquare(coords.x + dx, coords.y + dy, board);
                    coords.move(roomSquare.x, roomSquare.y);
                    room = board.getRooms().get(doorSquare(coords.x + dx, coords.y + dy, board).toLowerCase());
                    //squareChar = board.getBoard()[roomSquare.x][roomSquare.y];
                    return true;
                } else if (response.equals("no")) {
                    //squareChar = board.getBoard()[coords.x + dx][coords.y + dy];
                    coords.translate(dx, dy);
                    return true;
                }
                System.out.println("Please enter correct response.");
            }
        } else if (board.getBoard()[coords.x + dx][coords.y + dy] == 'R' && room != null) {
            coords.translate(dx, dy);
            System.out.println("You moved " + direction + ". " + movesRemaining + " moves remaining.");
            //squareChar = 'R';
            return true;
        } else {
            System.out.println("There is a wall in the way. Please choose a different direction.");
            return false;
        }
    }

    private String direction(int dx, int dy) {
        if (dx > 0 && dy == 0) return dx + " to the right.";     //east
        else if (dx > 0 && dy > 0) return dx + " to the right and " + dy + " downwards.";   //south-east
        else if (dx == 0 && dy > 0) return dy + " downwards.";      //south
        else if (dx < 0 && dy > 0) return dx * -1 + " to the left and " + dy + " downwards.";    //south-west
        else if (dx < 0 && dy == 0) return dx * -1 + " to the left.";      //west
        else if (dx < 0 && dy < 0) return dx * -1 + " to the left and " + dy * -1 + " upwards.";    //north-west
        else if (dx == 0 && dy < 0) return dy * -1 + " upwards.";    //north
        else return dx + " to the right and " + dy * -1 + " upwards.";      //north-east
    }

    private String doorSquare(int x, int y, Board board) {
        if (board.getBoard()[x][y] == 'K') return "Kitchen";
        else if (board.getBoard()[x][y] == 'B') return "Ballroom";
        else if (board.getBoard()[x][y] == 'C') return "Conservatory";
        else if (board.getBoard()[x][y] == 'I') return "Billiard Room";
        else if (board.getBoard()[x][y] == 'L') return "Library";
        else if (board.getBoard()[x][y] == 'S') return "Study";
        else if (board.getBoard()[x][y] == 'H') return "Hall";
        else if (board.getBoard()[x][y] == 'O') return "Lounge";
        else if (board.getBoard()[x][y] == 'D') return "Dining Room";
        else if (board.getBoard()[x][y] == 'A') return "Accusation Room";

        return null;
    }

    private Point roomSquare(int x, int y, Board board) {
        if (board.getBoard()[x + 1][y] == 'R' || board.getBoard()[x + 1][y] == '[' || board.getBoard()[x + 1][y] == ']')
            return new Point(x + 1, y);
        else if (board.getBoard()[x][y + 1] == 'R' || board.getBoard()[x][y + 1] == '[' || board.getBoard()[x][y + 1] == ']')
            return new Point(x, y + 1);
        else if (board.getBoard()[x - 1][y] == 'R' || board.getBoard()[x - 1][y] == '[' || board.getBoard()[x - 1][y] == ']')
            return new Point(x - 1, y);
        else return new Point(x, y - 1);
    }

    public void makeAccusation() {
        System.out.println("Which player would you like to accuse: ");

        //not finished
    }

    public void makeSuggestion() {
        List<String> allPlayers = Arrays.asList("Miss Scarlett", "Col. Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum");
        List<String> allWeapons = Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner");
        //List<String> allRooms = Arrays.asList("Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Study", "Hall", "Lounge");
        List<String> suggestion = new ArrayList<>();

        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("Which player would you like to suggest: ");
            for (int i = 0; i < allPlayers.size(); i++) {
                System.out.println(i + 1 + allPlayers.get(i));
            }
            int r = reader.nextInt();
            if (r < 1 || r > 6) {
                System.out.println("Please enter a player between 1 and 6");
            } else {
                suggestion.add(allPlayers.get(r-1));
                break;
            }
        }
        reader.close();

        reader = new Scanner(System.in);
        while (true) {
            System.out.println("Which weapon would you like to suggest: ");
            for (int i = 0; i < allWeapons.size(); i++) {
                System.out.println(i + 1 + allWeapons.get(i));
            }
            int r = reader.nextInt();
            if (r < 1 || r > 6) {
                System.out.println("Please enter a weapon between 1 and 6");
            } else {
                suggestion.add(allWeapons.get(r-1));
                break;
            }
        }
        reader.close();

        suggestion.add(room.getName());
        for (int i = 0; i < nextPlayers.size(); i++) {
            if (nextPlayers.get(i).refuteSuggestion(suggestion) != null) {
                successfullyRefuted(refuteSuggestion(suggestion));
                return;
            }
        }
        System.out.println("No players were able to refute your suggestion...");
    }

    public Card refuteSuggestion(List<String> suggestion) {
        List<Card> refutables = new ArrayList<>();
        System.out.println("The suggestion made is: ");
        for (String s : suggestion) {
            System.out.println(s);
        }

        System.out.println("\nYou have the following conflicting cards: ");
        for (int i = 0; i < cards.size(); i++) {
            for (String suggestive : suggestion) {
                if (cards.get(i).equals(suggestive)) {
                    refutables.add(cards.get(i));
                    System.out.println(cards.get(i).getName());
                }
            }
        }

        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("\nWhich card would you like to use to refute the suggestion?");
            for (int i = 0; i < refutables.size(); i++) {
                System.out.println(i + 1 + refutables.get(i).getName());
            }
            int r = reader.nextInt();
            if (r < 1 || r > refutables.size()) {
                System.out.println("Please enter a card between 1 and " + refutables.size());
            } else {
                //recieveRefutable() NOT FINISHED
                break;
            }
        }
        return null; /*FIXME*/
    }

    private void successfullyRefuted(Card card) {
        knownEvidence.add(card.getName());
    }

    public void printKnownEvidence(List<String> p, List<String> w, List<String> r) {
        System.out.println("Suspects:");
        for (String s : p) {
            System.out.println(evidence(s));
        }

        System.out.println("Weapons:");
        for (String s : w) {
            System.out.println(evidence(s));
        }

        System.out.println("Rooms:");
        for (String s : r) {
            System.out.println(evidence(s));
        }
    }

    private String evidence(String suspect) {
        if (knownEvidence.contains(suspect)) return suspect + " [X]";
        else return suspect + " [ ]";
    }

    public void setPotentialSolutions(List<List<Card>> sols) {
        potentialSolutions = new ArrayList<>();
        potentialSolutions = sols;
    }

    public void setPotentialSolutions() {
        for (int i = 0; i < potentialSolutions.size(); i++) {
            for (Card card : cards) {
                if (card.equals(potentialSolutions.get(i).get(0)) || card.equals(potentialSolutions.get(i).get(1)) || card.equals(potentialSolutions.get(i).get(2))) {
                    potentialSolutions.remove(i);
                    i--;
                    break;
                }
            }
        }

        //for debugging prints all combos
        /*for (List<Card> s : potentialSolutions) {
            System.out.println(s.get(0).getName());
            System.out.println(s.get(1).getName());
            System.out.println(s.get(2).getName() + "\n");
        }*/

    }

    public Room getRoom() {
        return room;
    }

    public void setNextPlayers(List<Player> Players) {

    }
}