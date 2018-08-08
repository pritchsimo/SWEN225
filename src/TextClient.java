import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TextClient {

    private static String inputString(String message) {
        System.out.print(message);
        while (true) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                return input.readLine();
            } catch (IOException e) {
                System.out.println("I/O Error.");
            }
        }
    }

    private static int inputNumber(String message) {
        System.out.print(message);
        while (true) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                String string = input.readLine();
                return Integer.parseInt(string);
            } catch (IOException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private static void playerSetup(Cluedo game){
        int numPlayers;
        while (true) {
            numPlayers = inputNumber("Enter the number of players: (Between 1 and 6)");
            if (numPlayers > 6 || numPlayers < 1) {
                System.out.println("Invalid number of players, there must be between 1 and 6 players.");
            } else {
                break;
            }
        }
        game.playerSetup(numPlayers);
    }

    private static void movePlayer(Player player, int diceRoll, Cluedo game){
        List<Point> doors;
        String response;
        while (true) {     //checks for correct input of room name
            response = inputString("Which room do you wish to head towards? ");
            if (game.getBoard().getRooms().get(response) != null){
                doors = game.getBoard().getRooms().get(response).getDoorPos();
                break;
            }
            System.out.println("Please enter a correct room name.");
        }

        double shortestPath = 1000;   //arbitrary large number
        Point closestDoor = new Point(0, 0);
        for (Point p : doors) {
            double distance = Math.sqrt(Math.pow(Math.abs(player.getCoords().x - p.x), 2) + Math.pow(Math.abs(player.getCoords().y - p.y), 2));
            if (distance < shortestPath) {    //finds closest door as some rooms have multiple doors
                shortestPath = distance;
                closestDoor = p;
            }
        }

        int dx = closestDoor.x - player.getCoords().x;
        int dy = closestDoor.y - player.getCoords().y;

        System.out.println("You need to move " + player.direction(dx, dy));


        for (int i = 0; i < diceRoll; i++) {
            //while (true) {
            if (player.getRoom() != null) {
                System.out.println("You are in the " + player.getRoom().getName() + ". Make a suggestion? (suggestion) or");

            }
            while (true){
                response = inputString("Move: (w/a/s/d)");
                if (response.equals("w")) {
                    if (player.translate("upwards", 1)) {
                        System.out.println("You moved upwards." + (diceRoll-i-1) + " moves remaining.");
                        break;
                    }
                } else if (response.equals("a")) {
                    if (player.translate("left", 1)) {
                        System.out.println("You moved left." + (diceRoll-i-1) + " moves remaining.");
                        break;
                    }
                } else if (response.equals("s")) {
                    if (player.translate("downwards", 1)) {
                        System.out.println("You moved downwards." + (diceRoll-i-1) + " moves remaining.");
                        break;
                    }
                } else if (response.equals("d")) {
                    if (player.translate("right", 1)) {
                        System.out.println("You moved right." + (diceRoll-i-1) + " moves remaining.");
                        break;
                    }
                } else if (response.equals("suggestion") && player.getRoom() != null){
                    //player.makeSuggestion();         need to fix
                    return;
                }
                System.out.println("Invalid input. Please enter a valid input.");
                //}
            }
        }
    }

    private static boolean enterRoom(Player player, Cluedo game){
        char square = game.getBoard().getBoard()[player.getCoords().x][player.getCoords().y];
        if (player.doorSquare(square) != null){
            System.out.println("You are outside the " + player.doorSquare(square).getName() + ".");
            while (true) {
                String response = inputString("Would you like to go in? (yes/no)");
                if (response.toLowerCase().equals("yes")) {
                    player.enterRoom();
                    return true;
                } else if (response.equals("no")) {
                    return true;
                }
                System.out.println("Please enter correct response.");

            }
        }
        return false;
    }

    public static void main(String... args) {
        Cluedo cluedo = new Cluedo();
        playerSetup(cluedo);
        cluedo.setup(false);

        while (!cluedo.isGameWon()){
            Player current = cluedo.getMove();
            int dice1 = (int) (Math.random() * 5) + 1;
            int dice2 = (int) (Math.random() * 5) + 1;
            System.out.println("It is " +  current.getName() + "'s turn. You roll a " + (dice1 + dice2) + ".");
            enterRoom(current, cluedo);
            movePlayer(current, (dice1+dice2), cluedo);


        }


    }
}
