/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Unable to update umple code due to error at null
 */
// line 3 "model.ump"
// line 49 "model.ump"
public class Board {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Board Attributes
    private String status;
    private List solution;

    //Board Associations
    private List<Player> players;
    private List<Room> rooms;
    private Cluedo cluedo;

    //------------------------
    // CONSTRUCTOR
    //------------------------


    public Board(String aStatus, List aSolution) {
        solution = aSolution;
        players = new ArrayList<Player>();
        rooms = new ArrayList<Room>();
    }

    //------------------------
    // INTERFACE
    //------------------------


    public String getStatus() {
        return status;
    }

    public List getSolution() {
        return solution;
    }

    /* Code from template association_GetMany */
    public Player getPlayer(int index) {
        Player aPlayer = players.get(index);
        return aPlayer;
    }

    public List<Player> getPlayers() {
        List<Player> newPlayers = Collections.unmodifiableList(players);
        return newPlayers;
    }

    public int numberOfPlayers() {
        int number = players.size();
        return number;
    }

    public boolean hasPlayers() {
        boolean has = players.size() > 0;
        return has;
    }

    public int indexOfPlayer(Player aPlayer) {
        int index = players.indexOf(aPlayer);
        return index;
    }

    /* Code from template association_GetMany */
    public Room getRoom(int index) {
        Room aRoom = rooms.get(index);
        return aRoom;
    }

    public List<Room> getRooms() {
        List<Room> newRooms = Collections.unmodifiableList(rooms);
        return newRooms;
    }

    public int numberOfRooms() {
        int number = rooms.size();
        return number;
    }

    public boolean hasRooms() {
        boolean has = rooms.size() > 0;
        return has;
    }

    public int indexOfRoom(Room aRoom) {
        int index = rooms.indexOf(aRoom);
        return index;
    }

    /* Code from template association_GetOne */
    public Cluedo getCluedo() {
        return cluedo;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfPlayers() {
        return 0;
    }

    /* Code from template association_AddManyToOne */
    public Player addPlayer(Point aCoord, String aName, Room aRoom) {
        return new Player(aCoord, aName, this, aRoom);
    }

    public boolean addPlayer(Player aPlayer) {
        boolean wasAdded = false;
        if (players.contains(aPlayer)) {
            return false;
        }
        Board existingBoard = aPlayer.getBoard();
        boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
        if (isNewBoard) {
            aPlayer.setBoard(this);
        } else {
            players.add(aPlayer);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removePlayer(Player aPlayer) {
        boolean wasRemoved = false;
        //Unable to remove aPlayer, as it must always have a board
        if (!this.equals(aPlayer.getBoard())) {
            players.remove(aPlayer);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addPlayerAt(Player aPlayer, int index) {
        boolean wasAdded = false;
        if (addPlayer(aPlayer)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfPlayers()) {
                index = numberOfPlayers() - 1;
            }
            players.remove(aPlayer);
            players.add(index, aPlayer);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMovePlayerAt(Player aPlayer, int index) {
        boolean wasAdded = false;
        if (players.contains(aPlayer)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfPlayers()) {
                index = numberOfPlayers() - 1;
            }
            players.remove(aPlayer);
            players.add(index, aPlayer);
            wasAdded = true;
        } else {
            wasAdded = addPlayerAt(aPlayer, index);
        }
        return wasAdded;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfRooms() {
        return 0;
    }

    /* Code from template association_AddManyToOne */
    public Room addRoom(String aName, Point aDoorPos) {
        return new Room(aName, aDoorPos, this);
    }

    public boolean addRoom(Room aRoom) {
        boolean wasAdded = false;
        if (rooms.contains(aRoom)) {
            return false;
        }
        Board existingBoard = aRoom.getBoard();
        boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
        if (isNewBoard) {
            aRoom.setBoard(this);
        } else {
            rooms.add(aRoom);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeRoom(Room aRoom) {
        boolean wasRemoved = false;
        //Unable to remove aRoom, as it must always have a board
        if (!this.equals(aRoom.getBoard())) {
            rooms.remove(aRoom);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addRoomAt(Room aRoom, int index) {
        boolean wasAdded = false;
        if (addRoom(aRoom)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfRooms()) {
                index = numberOfRooms() - 1;
            }
            rooms.remove(aRoom);
            rooms.add(index, aRoom);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveRoomAt(Room aRoom, int index) {
        boolean wasAdded = false;
        if (rooms.contains(aRoom)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfRooms()) {
                index = numberOfRooms() - 1;
            }
            rooms.remove(aRoom);
            rooms.add(index, aRoom);
            wasAdded = true;
        } else {
            wasAdded = addRoomAt(aRoom, index);
        }
        return wasAdded;
    }

    public void delete() {
        for (int i = players.size(); i > 0; i--) {
            Player aPlayer = players.get(i - 1);
            aPlayer.delete();
        }
        for (int i = rooms.size(); i > 0; i--) {
            Room aRoom = rooms.get(i - 1);
            aRoom.delete();
        }
        Cluedo existingCluedo = cluedo;
        cluedo = null;
        if (existingCluedo != null) {
            existingCluedo.delete();
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "status" + ":" + getStatus() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "solution" + "=" + (getSolution() != null ? !getSolution().equals(this) ? getSolution().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "cluedo = " + (getCluedo() != null ? Integer.toHexString(System.identityHashCode(getCluedo())) : "null");
    }
}