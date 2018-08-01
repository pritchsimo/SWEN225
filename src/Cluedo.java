/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.util.List;

// line 37 "model.ump"
// line 80 "model.ump"
public class Cluedo {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Cluedo Attributes
    private Player move;

    //Cluedo Associations
    private Board board;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Cluedo(Board aBoard) {
        board = aBoard;

    }


    //------------------------
    // INTERFACE
    //------------------------

    public boolean setMove(Player aMove) {
        boolean wasSet = false;
        move = aMove;
        wasSet = true;
        return wasSet;
    }

    public Player getMove() {
        return move;
    }

    /* Code from template association_GetOne */
    public Board getBoard() {
        return board;
    }

    public void delete() {
        Board existingBoard = board;
        board = null;
        if (existingBoard != null) {
            existingBoard.delete();
        }
    }

    // line 44 "model.ump"
    public void newGame() {

    }


    public String toString() {
        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "move" + "=" + (getMove() != null ? !getMove().equals(this) ? getMove().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "board = " + (getBoard() != null ? Integer.toHexString(System.identityHashCode(getBoard())) : "null");
    }
}