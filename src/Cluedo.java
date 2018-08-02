import java.awt.*;
import java.util.*;
import java.util.List;

public class Cluedo {
    private Player move;
    private Board board;
    private List<Card> solution;
    private List<Player> players;
    private List<String> playerOptions;
    private List<String> weaponOptions;
    private List<String> roomOptions;

    public Cluedo() {
        solution = new ArrayList<>();
        players = new ArrayList<>();
        setup();
    }

    private void setup() {
        board = new Board();
        playerSetup();
        listSetup();
        cardSetup();
        weaponSetup();
        selectFirstTurn();
    }

    private void selectFirstTurn() {
        move = players.get((int) (Math.random() * players.size()));
    }

    private void playerSetup() {
        Scanner reader = new Scanner(System.in);
        int p;
        while (true) {
            System.out.println("Enter the number of players (Between 1 and 6)");
            p = reader.nextInt();
            if (p > 6 || p < 1) {
                System.out.println("Invalid number of players, there must be between 1 and 6 players.");
            } else {
                break;
            }
        }
        reader.close();

        List<Player> availablePlayers = new ArrayList<>();
        availablePlayers.add(new Player(new Point(8, 25), "Miss Scarlett"));
        availablePlayers.add(new Player(new Point(1, 18), "Col. Mustard"));
        availablePlayers.add(new Player(new Point(10, 1), "Mrs. White"));
        availablePlayers.add(new Player(new Point(15, 1), "Mr. Green"));
        availablePlayers.add(new Player(new Point(24, 7), "Mrs. Peacock"));
        availablePlayers.add(new Player(new Point(24, 20), "Prof. Plum"));

        for (int i = 0; i < p; i++)
            players.add(availablePlayers.get(i));

        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player " + i + ": " + players.get(i).getName());
        }
    }

    private void listSetup(){
        playerOptions = new ArrayList<>();
        playerOptions.add("Miss Scarlett");
        playerOptions.add("Col. Mustard");
        playerOptions.add("Mrs. White");
        playerOptions.add("Mr. Green");
        playerOptions.add("Mrs. Peacock");
        playerOptions.add("Prof. Plum");

        weaponOptions = new ArrayList<>();
        weaponOptions.add("Candlestick");
        weaponOptions.add("Dagger");
        weaponOptions.add("Lead Pipe");
        weaponOptions.add("Revolver");
        weaponOptions.add("Rope");
        weaponOptions.add("Spanner");

        roomOptions = new ArrayList<>();
        roomOptions.add("Kitchen");
        roomOptions.add("Ballroom");
        roomOptions.add("Conservatory");
        roomOptions.add("Dining Room");
        roomOptions.add("Billiard Room");
        roomOptions.add("Library");
        roomOptions.add("Study");
        roomOptions.add("Hall");
        roomOptions.add("Lounge");
    }

    private void cardSetup() {
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
        solution.add(roomCards.remove(0));
        solution.add(weaponCards.remove(0));

        List<Card> remainingCards = new ArrayList<>();
        remainingCards.addAll(playerCards);
        remainingCards.addAll(roomCards);
        remainingCards.addAll(weaponCards);

        Collections.shuffle(remainingCards);

        int counter = 0;
        for (Card card : remainingCards) {
            players.get(counter).giveCard(card);
            counter++;
            if (counter > players.size() - 1)
                counter = 0;
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

    public static void main(String... args) {
        Cluedo cluedo = new Cluedo();
    }
}