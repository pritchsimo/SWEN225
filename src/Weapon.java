/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


// line 26 "model.ump"
// line 69 "model.ump"
public class Weapon {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Weapon Attributes
    private String name;

    //Weapon Associations
    private Room room;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Weapon(String aName, Room aRoom) {
        name = aName;
        boolean didAddRoom = setRoom(aRoom);
        if (!didAddRoom) {
            throw new RuntimeException("Unable to create weapon due to room");
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

    public String getName() {
        return name;
    }

    /* Code from template association_GetOne */
    public Room getRoom() {
        return room;
    }

    /* Code from template association_SetOneToOptionalOne */
    public boolean setRoom(Room aNewRoom) {
        boolean wasSet = false;
        if (aNewRoom == null) {
            //Unable to setRoom to null, as weapon must always be associated to a room
            return wasSet;
        }

        Weapon existingWeapon = aNewRoom.getWeapon();
        if (existingWeapon != null && !equals(existingWeapon)) {
            //Unable to setRoom, the current room already has a weapon, which would be orphaned if it were re-assigned
            return wasSet;
        }

        Room anOldRoom = room;
        room = aNewRoom;
        room.setWeapon(this);

        if (anOldRoom != null) {
            anOldRoom.setWeapon(null);
        }
        wasSet = true;
        return wasSet;
    }

    public void delete() {
        Room existingRoom = room;
        room = null;
        if (existingRoom != null) {
            existingRoom.setWeapon(null);
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "name" + ":" + getName() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "room = " + (getRoom() != null ? Integer.toHexString(System.identityHashCode(getRoom())) : "null");
    }
}