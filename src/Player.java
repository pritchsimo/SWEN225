
import java.awt.*;
import java.util.*;
import java.util.List;

public class Player {
    private Point coords;
    private String name;
    private List<Card> cards;
    private List<String> knownEvidence;
    private List<Player> nextPlayers;
    private List<String> currentSuggestion;
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
            } else if (room == null && !(square == '.' || doorSquare(square) != null)) {
                System.out.println("There is a wall in the way. Please choose a different direction.");
                return false;
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


    public void setCurrentSuggestion(List<String> suggestion) {
        currentSuggestion = suggestion;
    }

    public List<Card> refutableCards(List<String> suggestion){
        List<Card> refutables = new ArrayList<>();

        System.out.println("\n" + this.name + ", you have the following conflicting cards: ");
        for (int i = 0; i < cards.size(); i++) {
            for (String suggestive : suggestion) {
                if (cards.get(i).getName().equals(suggestive)) {
                    refutables.add(cards.get(i));
                    System.out.println(cards.get(i).getName());
                }
            }
        }
        return refutables;
    }


    public List<String> getCurrentSuggestion() {
        return currentSuggestion;
    }

    public void successfullyRefuted(Card card) {
        knownEvidence.add(card.getName());
    }

    public List<Card> getCards() {
        return cards;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public Point getCoords() {
        return coords;
    }

    public List<Player> getNextPlayers() {
        return nextPlayers;
    }

    public void setNextPlayers(List<Player> players) {
        nextPlayers = players;
    }

    public List<String> getKnownEvidence() {
        return knownEvidence;
    }
}
