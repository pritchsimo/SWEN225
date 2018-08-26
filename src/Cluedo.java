import java.awt.*;
import java.util.*;
import java.util.List;

public class Cluedo {
    private int move;
    private Board board = new Board();
    private List<Card> solution = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private HashMap<String, Point> availablePlayers = new HashMap<>();
    private List<String> playerOptions = new ArrayList<>();
    private List<String> weaponOptions = new ArrayList<>();
    private List<String> roomOptions = new ArrayList<>();
    private Map<Room, Weapon> weaponMap = new HashMap<>();
    private boolean gameWon = false;

    public Cluedo(){
        listSetup();
    }

    /**
     * Game initialisation
     * @param test If setting up for a test
     */
    public void setup(boolean test) {
        cardSetup(test);
        weaponSetup();
        selectFirstTurn();
    }

    private void selectFirstTurn() {
        move = (int) (Math.random() * players.size());
    }

    public void addPlayer(String characterName, String playerName){
        players.add(new Player(availablePlayers.get(characterName), characterName, playerName));
    }

    public void playerSetup() {
        //give each player a list of players in a clockwise direction
        for (int i = 0; i < players.size(); i++) {
            List<Player> plrs = new ArrayList<>();
            int k = i + 1;
            for (int j = 0; j < players.size() - 1; j++) {
                if (k == players.size()) {
                    k = 0;
                }
                plrs.add(players.get(k));
                k++;
            }
            players.get(i).setNextPlayers(plrs);
        }
    }

    private void listSetup(){
        playerOptions.add("Miss Scarlett");
        playerOptions.add("Col. Mustard");
        playerOptions.add("Mrs. White");
        playerOptions.add("Mr. Green");
        playerOptions.add("Mrs. Peacock");
        playerOptions.add("Prof. Plum");

        weaponOptions.add("Candlestick");
        weaponOptions.add("Dagger");
        weaponOptions.add("Lead Pipe");
        weaponOptions.add("Revolver");
        weaponOptions.add("Rope");
        weaponOptions.add("Spanner");

        roomOptions.add("Kitchen");
        roomOptions.add("Ballroom");
        roomOptions.add("Conservatory");
        roomOptions.add("Dining Room");
        roomOptions.add("Billiard Room");
        roomOptions.add("Library");
        roomOptions.add("Study");
        roomOptions.add("Hall");
        roomOptions.add("Lounge");

        availablePlayers.put("Miss Scarlett", new Point(10, 25));
        availablePlayers.put("Col. Mustard", new Point(1, 18));
        availablePlayers.put("Mrs. White", new Point(10, 1));
        availablePlayers.put("Mr. Green", new Point(16, 1));
        availablePlayers.put("Mrs. Peacock", new Point(25, 7));
        availablePlayers.put("Prof. Plum", new Point(25, 19));

    }

    private void cardSetup(boolean test) {
        List<Card> playerCards = new ArrayList<>();
        for (String s : playerOptions){
            playerCards.add(new Card(s, "Player"));
        }

        List<Card> roomCards = new ArrayList<>();
        for (String s : roomOptions){
            roomCards.add(new Card(s, "Room"));
        }

        List<Card> weaponCards = new ArrayList<>();
        for (String s : weaponOptions){
            weaponCards.add(new Card(s, "Weapon"));
        }

        Collections.shuffle(playerCards);
        Collections.shuffle(roomCards);
        Collections.shuffle(weaponCards);

        solution.add(playerCards.remove(0));
        solution.add(weaponCards.remove(0));
        solution.add(roomCards.remove(0));

        List<Card> remainingCards = new ArrayList<>();
        remainingCards.addAll(playerCards);
        remainingCards.addAll(roomCards);
        remainingCards.addAll(weaponCards);

        Collections.shuffle(remainingCards);
        if (!test){
            int counter = 0;
            for (Card card : remainingCards) {
                players.get(counter).giveCard(card);
                counter++;
                if (counter > players.size() - 1)
                    counter = 0;
            }
        }
    }

    private void weaponSetup() {
        Collection<Room> rooms = board.getRooms().values();
        List<Room> roomShuffler = new ArrayList<>(rooms);
        Collections.shuffle(roomShuffler);
        for (int i = 0; i < 6; i++){
            Weapon w = new Weapon(weaponOptions.get(i), roomShuffler.get(i));
            roomShuffler.get(i).setWeapon(w);
            //weaponMap.put(roomShuffler.get(i), w);
        }
    }

    public Player getMove(){
        if (players.size() == 0){
            return null;
        }
        if (move < players.size()-1){
            move++;
            return players.get(move);
        }
        move = 0;
        return players.get(move);
    }

    public boolean accuse(List<String> accusation) {
        for (int i = 0; i < accusation.size(); i++) {
            if (!accusation.get(i).equals(solution.get(i).getName())) {
                doPlayerLose();
                return false;
            }
        }
        doPlayerWin();
        return true;
    }

    public List<Player> getPlayers() {
        return players;
    }

  //  public List<Player> getAvailablePlayers() {
   //     return availablePlayers;
   // }

    public Board getBoard() {
        return board;
    }

    public Map<Room, Weapon> getWeaponMap() {
        return weaponMap;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    //only for testing purposes
    public List<Card> getSolution() {
        return solution;
    }

    private void doPlayerWin() {
        System.out.println("The accusation is correct, the game has ended.");
        gameWon = true;
    }

    private void doPlayerLose() {
        System.out.println("The accusation is incorrect, you have been removed from the game");
        players.remove(move);
    }


    public List<String> getPlayerOptions() {
        return playerOptions;
    }

    public List<String> getWeaponOptions() {
        return weaponOptions;
    }

    public List<String> getRoomOptions() {
        return roomOptions;
    }

}