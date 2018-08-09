import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.*;
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


