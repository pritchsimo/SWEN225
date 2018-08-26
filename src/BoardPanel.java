import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoardPanel extends JPanel {

    List<Player> players;
    List<Player> startingPlayers = new ArrayList<>();
    BufferedImage boardImage;
    public BoardPanel(){
        try {
            boardImage = ImageIO.read(new File("DiceImages/Board.png"));
            Graphics g = boardImage.getGraphics();
            g.drawImage(boardImage,0,0,this);
            g.dispose();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        startingPlayers.add(new Player(new Point(10, 25), "Miss Scarlett", null));
        startingPlayers.add(new Player(new Point(1, 18), "Col. Mustard", null));
        startingPlayers.add(new Player(new Point(10, 1), "Mrs. White", null));
        startingPlayers.add(new Player(new Point(16, 1), "Mr. Green", null));
        startingPlayers.add(new Player(new Point(25, 7), "Mrs. Peacock", null));
        startingPlayers.add(new Player(new Point(25, 19), "Prof. Plum", null));

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (boardImage != null){
            g.drawImage(boardImage,0,0,this);
        }

        if (players != null){
            for (Player p: players){
                p.draw(g, characterColor(p.getCharacterName()));
            }
        }
    }

    private Color characterColor(String characterName){
        if (characterName.equals("Mrs. White")){
            return Color.WHITE;
        } else if (characterName.equals("Mr. Green")){
            return Color.GREEN;
        } else if (characterName.equals("Mrs. Peacock")){
            return Color.BLUE;
        } else if (characterName.equals("Prof. Plum")){
            return new Color(128, 0, 128);
        } else if (characterName.equals("Miss Scarlett")){
            return Color.RED;
        } else {
            return Color.YELLOW;
        }
    }

    public void setPlayers(List<Player> players) {
        this.players = new ArrayList<>();
        for (Player p: players){
            this.players.add(p);
        }
        for (Player p1: startingPlayers){
            int counter = 0;
            for (Player p2: this.players){
                if (!p1.getCharacterName().equals(p2.getCharacterName())){
                    counter++;
                }
            }
            if (counter == this.players.size()){
                this.players.add(p1);
            }
        }
        repaint();

    }
}
