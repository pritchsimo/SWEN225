/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


// line 31 "model.ump"
// line 74 "model.ump"
public class Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Card Attributes
    private String name;
    private String type;

    //Card Associations
    private Player player;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Card(String aName, String aType, Player aPlayer) {
        name = aName;
        type = aType;
        boolean didAddPlayer = setPlayer(aPlayer);
        if (!didAddPlayer) {
            throw new RuntimeException("Unable to create card due to player");
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

    public boolean setType(String aType) {
        boolean wasSet = false;
        type = aType;
        wasSet = true;
        return wasSet;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    /* Code from template association_GetOne */
    public Player getPlayer() {
        return player;
    }

    /* Code from template association_SetOneToMany */
    public boolean setPlayer(Player aPlayer) {
        boolean wasSet = false;
        if (aPlayer == null) {
            return wasSet;
        }

        Player existingPlayer = player;
        player = aPlayer;
        if (existingPlayer != null && !existingPlayer.equals(aPlayer)) {
            existingPlayer.removeCard(this);
        }
        player.addCard(this);
        wasSet = true;
        return wasSet;
    }

    public void delete() {
        Player placeholderPlayer = player;
        this.player = null;
        if (placeholderPlayer != null) {
            placeholderPlayer.removeCard(this);
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "name" + ":" + getName() + "," +
                "type" + ":" + getType() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "player = " + (getPlayer() != null ? Integer.toHexString(System.identityHashCode(getPlayer())) : "null");
    }
}