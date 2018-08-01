/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.awt.*;
import java.util.*;
import java.util.List;

// line 18 "model.ump"
// line 63 "model.ump"
public class Room {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Room Attributes
    private String name;
    private Point doorPos;

    //Room Associations
    private List<Player> players;
    private Weapon weapon;
    private Board board;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Room(String aName, Point aDoorPos, Board aBoard) {
        name = aName;
        doorPos = aDoorPos;
        players = new ArrayList<Player>();
        boolean didAddBoard = setBoard(aBoard);
        if (!didAddBoard) {
            throw new RuntimeException("Unable to create room due to board");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setName(String aName) {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public boolean setDoorPos(Point aDoorPos) {
        boolean wasSet = false;
        doorPos = aDoorPos;
        wasSet = true;
        return wasSet;
    }

    public String getName() {
        return name;
    }

    public Point getDoorPos() {
        return doorPos;
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

    /* Code from template association_GetOne */
    public Weapon getWeapon() {
        return weapon;
    }

    public boolean hasWeapon() {
        boolean has = weapon != null;
        return has;
    }

    /* Code from template association_GetOne */
    public Board getBoard() {
        return board;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfPlayers() {
        return 0;
    }

    /* Code from template association_AddManyToOne */
    public Player addPlayer(Point aCoord, String aName, Board aBoard) {
        return new Player(aCoord, aName, aBoard, this);
    }

    public boolean addPlayer(Player aPlayer) {
        boolean wasAdded = false;
        if (players.contains(aPlayer)) {
            return false;
        }
        Room existingRoom = aPlayer.getRoom();
        boolean isNewRoom = existingRoom != null && !this.equals(existingRoom);
        if (isNewRoom) {
            aPlayer.setRoom(this);
        } else {
            players.add(aPlayer);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removePlayer(Player aPlayer) {
        boolean wasRemoved = false;
        //Unable to remove aPlayer, as it must always have a room
        if (!this.equals(aPlayer.getRoom())) {
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

    /* Code from template association_SetOptionalOneToOne */
    public boolean setWeapon(Weapon aNewWeapon) {
        boolean wasSet = false;
        if (weapon != null && !weapon.equals(aNewWeapon) && equals(weapon.getRoom())) {
            //Unable to setWeapon, as existing weapon would become an orphan
            return wasSet;
        }

        weapon = aNewWeapon;
        Room anOldRoom = aNewWeapon != null ? aNewWeapon.getRoom() : null;

        if (!this.equals(anOldRoom)) {
            if (anOldRoom != null) {
                anOldRoom.weapon = null;
            }
            if (weapon != null) {
                weapon.setRoom(this);
            }
        }
        wasSet = true;
        return wasSet;
    }

    /* Code from template association_SetOneToMany */
    public boolean setBoard(Board aBoard) {
        boolean wasSet = false;
        if (aBoard == null) {
            return wasSet;
        }

        Board existingBoard = board;
        board = aBoard;
        if (existingBoard != null && !existingBoard.equals(aBoard)) {
            existingBoard.removeRoom(this);
        }
        board.addRoom(this);
        wasSet = true;
        return wasSet;
    }

    public void delete() {
        for (int i = players.size(); i > 0; i--) {
            Player aPlayer = players.get(i - 1);
            aPlayer.delete();
        }
        Weapon existingWeapon = weapon;
        weapon = null;
        if (existingWeapon != null) {
            existingWeapon.delete();
        }
        Board placeholderBoard = board;
        this.board = null;
        if (placeholderBoard != null) {
            placeholderBoard.removeRoom(this);
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "name" + ":" + getName() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "doorPos" + "=" + (getDoorPos() != null ? !getDoorPos().equals(this) ? getDoorPos().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "weapon = " + (getWeapon() != null ? Integer.toHexString(System.identityHashCode(getWeapon())) : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "board = " + (getBoard() != null ? Integer.toHexString(System.identityHashCode(getBoard())) : "null");
    }
}