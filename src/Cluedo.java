import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class Cluedo {
    private int move;
    private Board board = new Board();
    private List<Card> solution = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private List<Player> availablePlayers = new ArrayList<>();
    private List<String> playerOptions = new ArrayList<>();
    private List<String> weaponOptions = new ArrayList<>();
    private List<String> roomOptions = new ArrayList<>();
    private boolean gameWon = false;

    public Cluedo(){
        listSetup();
    }

    public void setup(boolean test) {
        cardSetup(test);
        weaponSetup();
        selectFirstTurn();
    }

    private void selectFirstTurn() {
        move = (int) (Math.random() * players.size());
    }


    public void playerSetup(int numPlayers) {
        for (int i = 0; i < numPlayers; i++)
            players.add(availablePlayers.get(i));

        for (int i = 0; i < players.size(); i++) {     //might add to text client
            System.out.println("Player " + (i+1) + ": " + players.get(i).getName());
        }
    }

    private void listSetup(){
        //playerOptions = new ArrayList<>();
        playerOptions.add("Miss Scarlett");
        playerOptions.add("Col. Mustard");
        playerOptions.add("Mrs. White");
        playerOptions.add("Mr. Green");
        playerOptions.add("Mrs. Peacock");
        playerOptions.add("Prof. Plum");

        //weaponOptions = new ArrayList<>();
        weaponOptions.add("Candlestick");
        weaponOptions.add("Dagger");
        weaponOptions.add("Lead Pipe");
        weaponOptions.add("Revolver");
        weaponOptions.add("Rope");
        weaponOptions.add("Spanner");

        //roomOptions = new ArrayList<>();
        roomOptions.add("Kitchen");
        roomOptions.add("Ballroom");
        roomOptions.add("Conservatory");
        roomOptions.add("Dining Room");
        roomOptions.add("Billiard Room");
        roomOptions.add("Library");
        roomOptions.add("Study");
        roomOptions.add("Hall");
        roomOptions.add("Lounge");

        availablePlayers.add(new Player(new Point(8, 25), "Miss Scarlett"));
        availablePlayers.add(new Player(new Point(1, 18), "Col. Mustard"));
        availablePlayers.add(new Player(new Point(10, 1), "Mrs. White"));
        availablePlayers.add(new Player(new Point(15, 1), "Mr. Green"));
        availablePlayers.add(new Player(new Point(24, 7), "Mrs. Peacock"));
        availablePlayers.add(new Player(new Point(24, 20), "Prof. Plum"));
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

        generateSolutions(playerCards, roomCards, weaponCards);

        Collections.shuffle(playerCards);
        Collections.shuffle(roomCards);
        Collections.shuffle(weaponCards);

        solution.add(playerCards.remove(0));
        solution.add(roomCards.remove(0));
        solution.add(weaponCards.remove(0));

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

            for (Player player : players) {
                player.setPotentialSolutions();
            }
        }
    }

    private void generateSolutions(List<Card> playerCards, List<Card> roomCards, List<Card> weaponCards) {
        List<List<Card>> sols = new ArrayList<>();
        List<Card> sol;
        for (int i = 0; i < playerCards.size(); i++) {
            for (int j = 0; j < roomCards.size(); j++) {
                for (int k = 0; k < weaponCards.size(); k++) {
                    sol = Arrays.asList(playerCards.get(i), roomCards.get(j), weaponCards.get(k));
                    sols.add(sol);
                }
            }
        }

        for (Player player : players) {
            player.setPotentialSolutions(sols);
        }
    }

    private void weaponSetup() {
        Collection<Room> rooms = board.getRooms().values();
        List<Room> roomShuffler = new ArrayList<>(rooms);
        Collections.shuffle(roomShuffler);
        for (int i = 0; i < 6; i++){
            roomShuffler.get(i).setWeapon(new Weapon(weaponOptions.get(i), roomShuffler.get(i)));
        }
    }

    public Player getMove(){
        if (move < players.size()-1){
            move++;
            return players.get(move);
        }
        move = 0;
        return players.get(move);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getAvailablePlayers() {
        return availablePlayers;
    }


    public Board getBoard() {
        return board;
    }

    public boolean isGameWon() {
        return gameWon;
    }




}