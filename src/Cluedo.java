import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Cluedo {
    private Player move;
    private Board board;
    private List<Card> solution;
    private List<Player> players;

    public Cluedo(Player move, Board board) {
        this.move = move;
        this.board = board;
        solution = new ArrayList<>();
        players = new ArrayList<>();
        setup();
    }

    private void setup() {
        playerSetup();
        cardSetup();
    }

    private void playerSetup() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter the number of players");
        int p = reader.nextInt();
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
    }

    private void cardSetup() {
        List<Card> playerCards = new ArrayList<>();
        playerCards.add(new Card("Miss Scarlett", "Player"));
        playerCards.add(new Card("Col. Mustard", "Player"));
        playerCards.add(new Card("Mrs. White", "Player"));
        playerCards.add(new Card("Mr. Green", "Player"));
        playerCards.add(new Card("Mrs. Peacock", "Player"));
        playerCards.add(new Card("Prof. Plum", "Player"));

        List<Card> roomCards = new ArrayList<>();
        roomCards.add(new Card("Kitchen", "Room"));
        roomCards.add(new Card("Ballroom", "Room"));
        roomCards.add(new Card("Conservatory", "Room"));
        roomCards.add(new Card("Dining Room", "Room"));
        roomCards.add(new Card("Billiard Room", "Room"));
        roomCards.add(new Card("Library", "Room"));
        roomCards.add(new Card("Study", "Room"));
        roomCards.add(new Card("Hall", "Room"));
        roomCards.add(new Card("Lounge", "Room"));

        List<Card> weaponCards = new ArrayList<>();
        weaponCards.add(new Card("Candlestick", "Weapon"));
        weaponCards.add(new Card("Dagger", "Weapon"));
        weaponCards.add(new Card("Lead Pipe", "Weapon"));
        weaponCards.add(new Card("Revolver", "Weapon"));
        weaponCards.add(new Card("Rope", "Weapon"));
        weaponCards.add(new Card("Spanner", "Weapon"));

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
            if (counter > players.size())
                counter = 0;
        }
    }

    public static void main(String... args) {

    }
}