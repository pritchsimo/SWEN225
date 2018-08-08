
import java.awt.*;
import java.util.*;
import java.util.List;

public class Player {
    private Point coords;
    private String name;
    private List<Card> cards;
    private List<String> knownEvidence;
    private List<List<Card>> potentialSolutions;
    private Room room;
    private Board board;

    private HashMap<String, List<Point>> roomNames;

    public Player(Point startCoord, String name) {
        this.coords = startCoord;
        this.name = name;
        this.cards = new ArrayList<>();
        this.knownEvidence = new ArrayList<>();
        this.room = null;
        board = new Board();
    }

    public void giveCard(Card card) {
        cards.add(card);
        knownEvidence.add(card.getName());
    }

    public String getName() {
        return name;
    }

    public boolean translate(String direction, int distance){
        int dx = 0, dy = 0;
        if (direction.equals("left")) dx = -1;
        else if (direction.equals("downwards")) dy = 1;
        else if (direction.equals("right")) dx = 1;
        else if (direction.equals("upwards")) dy = -1;

        char square = board.getBoard()[coords.x + dx][coords.y + dy];

        for (int i = 0; i < distance; i++){
            if (room == null && (square == '.' || doorSquare(square) != null)) {
                coords.translate(dx, dy);
                //return true;
            } else if (room != null) {
                if (square == 'R') {
                    coords.translate(dx, dy);
                } else if (square == '[' && (direction.equals("left") || direction.equals("downwards") || direction.equals("upwards"))) {
                    coords.translate(dx, dy);
                } else if (square == '^' && (direction.equals("left") || direction.equals("upwards") || direction.equals("right"))) {
                    coords.translate(dx, dy);
                } else if (square == ']' && (direction.equals("right") || direction.equals("downwards") || direction.equals("upwards"))) {
                    coords.translate(dx, dy);
                } else if (square == '_' && (direction.equals("left") || direction.equals("downwards") || direction.equals("right"))) {
                    coords.translate(dx, dy);
                } else if (doorSquare(square) != null){
                    coords.translate(dx, dy);
                    System.out.println("You are exiting the " + room.getName());
                    room = null;
                } else {
                    System.out.println("There is a wall in the way. Please choose a different direction.");
//                  System.out.println("x: " + coords.x + " y: " + coords.y + " Token: " + board.getBoard()[coords.x][coords.y]);   //debugging
//                  System.out.println("Next x: " + (coords.x + dx) + " y: " + (coords.y + dy) + " Token: " + board.getBoard()[coords.x + dx][coords.y + dy]);    //debugging
                    return false;
                }

            } else if (room == null && square == 'R') {
                //add something to let them know they should have entered the room
            } else {
                return false;
            }
        }
        return true;
    }

    public void enterRoom(){
        Point roomSquare = roomSquare(coords.x, coords.y);
        room = doorSquare(board.getBoard()[coords.x][coords.y]);
        coords.move(roomSquare.x, roomSquare.y);
        System.out.println("You are in the " + room.getName() + ". Make a suggestion? (suggestion) or");
    }

    public String direction(int dx, int dy) {
        if (dx > 0 && dy == 0) return dx + " to the right.";     //east
        else if (dx > 0 && dy > 0) return dx + " to the right and " + dy + " downwards.";   //south-east
        else if (dx == 0 && dy > 0) return dy + " downwards.";      //south
        else if (dx < 0 && dy > 0) return dx * -1 + " to the left and " + dy + " downwards.";    //south-west
        else if (dx < 0 && dy == 0) return dx * -1 + " to the left.";      //west
        else if (dx < 0 && dy < 0) return dx * -1 + " to the left and " + dy * -1 + " upwards.";    //north-west
        else if (dx == 0 && dy < 0) return dy * -1 + " upwards.";    //north
        else return dx + " to the right and " + dy * -1 + " upwards.";      //north-east
    }

    public Room doorSquare(char square) {
        HashMap<String, Room> map = board.getRooms();

        if (square == 'K') return map.get("kitchen");
        else if (square == 'B') return map.get("ballroom");
        else if (square == 'C') return map.get("conservatory");
        else if (square == 'I') return map.get("billiard Room");
        else if (square == 'L') return map.get("library");
        else if (square == 'S') return map.get("study");
        else if (square == 'H') return map.get("hall");
        else if (square == 'O') return map.get("lounge");
        else if (square == 'D') return map.get("dining Room");
        else if (square == 'A') return map.get("accusation Room");

        return null;
    }

    private Point roomSquare(int x, int y) {
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

    public void makeSuggestion(Scanner s) {
        List<String> allPlayers = Arrays.asList("Miss Scarlett", "Col. Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum");
        List<String> allWeapons = Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner");
        //List<String> allRooms = Arrays.asList("Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Study", "Hall", "Lounge");
        List<String> suggestion = new ArrayList<>();

        while (true) {
            System.out.println("Which player would you like to suggest: ");
            for (int i = 0; i < allPlayers.size(); i++) {
                System.out.println(i + 1 + ". "+allPlayers.get(i));
            }
            int r = s.nextInt();
            if (r < 1 || r > 6) {
                System.out.println("Please enter a player between 1 and 6");
            } else {
                suggestion.add(allPlayers.get(r-1));
                break;
            }
        }

        while (true) {
            System.out.println("Which weapon would you like to suggest: ");
            for (int i = 0; i < allWeapons.size(); i++) {
                System.out.println(i + 1 + ". " + allWeapons.get(i));
            }
            int r = s.nextInt();
            if (r < 1 || r > 6) {
                System.out.println("Please enter a weapon between 1 and 6");
            } else {
                suggestion.add(allWeapons.get(r-1));
                break;
            }
        }

        suggestion.add(room.getName());
        //player to left.refuteSuggestion
    }

    private void refuteSuggestion(List<String> suggestion, Scanner s) {
        List<Card> refutables = new ArrayList<>();
        System.out.println("The suggestion made is: ");
        for (String string : suggestion) {
            System.out.println(string);
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

        while (true) {
            System.out.println("\nWhich card would you like to use to refute the suggestion?");
            for (int i = 0; i < refutables.size(); i++) {
                System.out.println(i + 1 + refutables.get(i).getName());
            }
            int r = s.nextInt();
            if (r < 1 || r > refutables.size()) {
                System.out.println("Please enter a card between 1 and " + refutables.size());
            } else {
                //recieveRefutable() NOT FINISHED
                break;
            }
        }
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
//        for (List<Card> s : potentialSolutions) {
//            System.out.println(s.get(0).getName());
//            System.out.println(s.get(1).getName());
//            System.out.println(s.get(2).getName() + "\n");
//        }
    }

    public Room getRoom() {
        return room;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public Point getCoords() {
        return coords;
    }
}