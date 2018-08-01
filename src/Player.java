/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.awt.*;
import java.util.*;
import java.util.List;

// line 11 "model.ump"
// line 57 "model.ump"
public class Player {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Player Attributes
    private Point coord;
    private String name;

    //Player Associations
    private List<Card> cards;
    private Board board;
    private Room room;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Player(Point aCoord, String aName, Board aBoard, Room aRoom) {
        coord = aCoord;
        name = aName;
        cards = new ArrayList<Card>();
        boolean didAddBoard = setBoard(aBoard);
        if (!didAddBoard) {
            throw new RuntimeException("Unable to create player due to board");
        }
        boolean didAddRoom = setRoom(aRoom);
        if (!didAddRoom) {
            throw new RuntimeException("Unable to create player due to room");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setCoord(Point aCoord) {
        boolean wasSet = false;
        coord = aCoord;
        wasSet = true;
        return wasSet;
    }

    public boolean setName(String aName) {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public Point getCoord() {
        return coord;
    }

    public String getName() {
        return name;
    }

    /* Code from template association_GetMany */
    public Card getCard(int index) {
        Card aCard = cards.get(index);
        return aCard;
    }

    public List<Card> getCards() {
        List<Card> newCards = Collections.unmodifiableList(cards);
        return newCards;
    }

    public int numberOfCards() {
        int number = cards.size();
        return number;
    }

    public boolean hasCards() {
        boolean has = cards.size() > 0;
        return has;
    }

    public int indexOfCard(Card aCard) {
        int index = cards.indexOf(aCard);
        return index;
    }

    /* Code from template association_GetOne */
    public Board getBoard() {
        return board;
    }

    /* Code from template association_GetOne */
    public Room getRoom() {
        return room;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfCards() {
        return 0;
    }

    /* Code from template association_AddManyToOne */
    public Card addCard(String aName, String aType) {
        return new Card(aName, aType, this);
    }

    public boolean addCard(Card aCard) {
        boolean wasAdded = false;
        if (cards.contains(aCard)) {
            return false;
        }
        Player existingPlayer = aCard.getPlayer();
        boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
        if (isNewPlayer) {
            aCard.setPlayer(this);
        } else {
            cards.add(aCard);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeCard(Card aCard) {
        boolean wasRemoved = false;
        //Unable to remove aCard, as it must always have a player
        if (!this.equals(aCard.getPlayer())) {
            cards.remove(aCard);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addCardAt(Card aCard, int index) {
        boolean wasAdded = false;
        if (addCard(aCard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfCards()) {
                index = numberOfCards() - 1;
            }
            cards.remove(aCard);
            cards.add(index, aCard);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveCardAt(Card aCard, int index) {
        boolean wasAdded = false;
        if (cards.contains(aCard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfCards()) {
                index = numberOfCards() - 1;
            }
            cards.remove(aCard);
            cards.add(index, aCard);
            wasAdded = true;
        } else {
            wasAdded = addCardAt(aCard, index);
        }
        return wasAdded;
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
            existingBoard.removePlayer(this);
        }
        board.addPlayer(this);
        wasSet = true;
        return wasSet;
    }

    /* Code from template association_SetOneToMany */
    public boolean setRoom(Room aRoom) {
        boolean wasSet = false;
        if (aRoom == null) {
            return wasSet;
        }

        Room existingRoom = room;
        room = aRoom;
        if (existingRoom != null && !existingRoom.equals(aRoom)) {
            existingRoom.removePlayer(this);
        }
        room.addPlayer(this);
        wasSet = true;
        return wasSet;
    }

    public void delete() {
        for (int i = cards.size(); i > 0; i--) {
            Card aCard = cards.get(i - 1);
            aCard.delete();
        }
        Board placeholderBoard = board;
        this.board = null;
        if (placeholderBoard != null) {
            placeholderBoard.removePlayer(this);
        }
        Room placeholderRoom = room;
        this.room = null;
        if (placeholderRoom != null) {
            placeholderRoom.removePlayer(this);
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "name" + ":" + getName() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "coord" + "=" + (getCoord() != null ? !getCoord().equals(this) ? getCoord().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "board = " + (getBoard() != null ? Integer.toHexString(System.identityHashCode(getBoard())) : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "room = " + (getRoom() != null ? Integer.toHexString(System.identityHashCode(getRoom())) : "null");
    }
}