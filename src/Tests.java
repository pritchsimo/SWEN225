import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Tests {

    @Test
    public void test1(){
        Cluedo cluedo = new Cluedo();
        setupMockPlayer(cluedo, new Point(7, 6), 0, null);
        cluedo.setup(true);
        Player p1 = cluedo.getPlayers().get(0);

        p1.translate("right", 1);
        assertTrue(p1.getCoords().equals(new Point(8, 6)));
        p1.enterRoom();
        assertTrue(p1.getCoords().equals(new Point(9, 6)));
        assertTrue(p1.getRoom() != null);
    }

    @Test
    public void test2(){
        Cluedo cluedo = new Cluedo();
        setupMockPlayer(cluedo, new Point(7, 6), 0, null);
        cluedo.setup(true);
        Player p1 = cluedo.getPlayers().get(0);
        Player p2 = cluedo.getPlayers().get(1);
        Player p3 = cluedo.getPlayers().get(2);


    }

    @Test
    public void testRefutableCards() {
        Cluedo cluedo = new Cluedo();
        List<Card> hand = Arrays.asList(new Card("Prof. Peacock", "Player"), new Card("Candlestick", "Weapon"), new Card("Lounge", "Room"));
        setupMockPlayer(cluedo, null, 0, hand);
        Player p1 = cluedo.getPlayers().get(0);
        List<String> suggestion = Arrays.asList("Prof. Peacock", "Dagger", "Lounge");
        List<String> correctAnswer = Arrays.asList("Prof. Peacock", "Lounge");

        assertTrue(p1.refutableCards(suggestion).get(0).getName().equals(correctAnswer.get(0)));
        assertTrue(p1.refutableCards(suggestion).get(1).getName().equals(correctAnswer.get(1)));

    }

    private void setupMockPlayer(Cluedo game, Point coords, int playerNumber, List<Card> hand) {
        Player p = new Player(coords, game.getAvailablePlayers().get(playerNumber).getName());
        game.getPlayers().add(p);
        if (hand != null){
            for (Card c: hand){
                p.giveCard(c);
            }
        }


    }



}


