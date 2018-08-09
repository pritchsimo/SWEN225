import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Allows a player to make a suggestion about the murder using a person, weapon or room
     *
     * @param player the player making the suggestion
     * @param isAccusation the suggestion will be differend depending on weather it is a suggestion or an accusation
     */
    private static void makeSuggestion(Player player, boolean isAccusation) {
        List<String> allPlayers = Arrays.asList("Miss Scarlett", "Col. Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum");
        List<String> allWeapons = Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner");
        List<String> allRooms = Arrays.asList("Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Study", "Hall", "Lounge");
        List<String> suggestion = new ArrayList<>();

        if (isAccusation) System.out.println("Please enter your accusation: ");
        else System.out.println("Please enter your suggestion");

        //Player
        while (true) {
            int response = inputNumber("Which player?" + listToOutputString(allPlayers));
            if (response < 1 || response > 6) {
                System.out.println("Please enter a player between 1 and 6");
            } else {
                suggestion.add(allPlayers.get(response-1));
                break;
            }
        }

        //Weapon
        while (true) {
            int response = inputNumber("Which weapon?" + listToOutputString(allWeapons));
            if (response < 1 || response > 6) {
                System.out.println("Please enter a weapon between 1 and 6");
            } else {
                suggestion.add(allWeapons.get(response-1));
                break;
            }
        }

        //Room if accusation
        if (isAccusation) {
            while (true) {
                int response = inputNumber("Which room?" + listToOutputString(allRooms));
                if (response < 1 || response > 9) {
                    System.out.println("Please enter a room between 1 and 9");
                } else {
                    suggestion.add(allRooms.get(response-1));
                    break;
                }
            }
            player.setCurrentSuggestion(true, suggestion);
        } else {
            suggestion.add(player.getRoom().getName());
            player.setCurrentSuggestion(false, suggestion);
        }
    }

    /**
     * Allows the refutor to refute the suggestion made by a player
     * @param suggestor the player making the suggestion
     * @param refutor the player that has the opportunity to refute the suggestion
     * @return true/false depending on weather the player was able to refute the suggestion
     */
    private static boolean refuteSuggestion(Player suggestor, Player refutor) {
        List<String> refutables = new ArrayList<>();
        List<String> suggestion = suggestor.getCurrentSuggestion();
        List<Card> cards = refutor.getCards();

        System.out.println("The suggestion made is: ");
        for (String string : suggestor.getCurrentSuggestion()) {
            System.out.println(string);
        }

        System.out.println("\nYou have the following conflicting cards: ");
        for (int i = 0; i < refutor.getCards().size(); i++) {
            for (String suggestive : suggestion) {
                if (cards.get(i).getName().equals(suggestive)) {
                    refutables.add(cards.get(i).getName());
                    System.out.println(cards.get(i).getName());
                }
            }
        }
        if (refutables.isEmpty()) return false;

        System.out.println("Which card would you like to use the refute the suggestion? ");
        while (true) {
            int response = inputNumber(listToOutputString(refutables));
            if (response < 1 || response > cards.size()) {
                System.out.println("Please enter a card between 1 and " + cards.size());
            } else {
                suggestor.successfullyRefuted(cards.get(response - 1));
                System.out.println(cards.get(response - 1) + " has been refuted by " + refutor.getName());
                return true;
            }
        }
    }

    private static void suggest(Player current) {
        for (int i = 0; i < current.getNextPlayers().size(); i++) {
            if (refuteSuggestion(current, current.getNextPlayers().get(i))) {
                return;
            }
        }
        System.out.println("No players were able to refute your suggestion");
    }

    /** Returns a string from a list of strings that can be displayed to the player when they need to make an input.
     *  The format of the string is as follows:
     *
     *  "
     *  1. String1
     *  2. String2
     *  3. String3
     *  "
     *
     * **/
    private static String listToOutputString(List<String> list) {
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            s += "\n" + i + ". " + list.get(i);
        }
        return s;
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

/*            if (current.getRoom().getName().equals("Accusation")) {
                List<String> accusation = current.makeSuggestion(true);
                for (int i = 0; i < accusation.size(); i++) {
                    if (!accusation.get(i).equals(solution.get(i).getName())) {
                        doPlayerLose();
                        break;
                    }
                }
                doPlayerWin();
            }*/

//            makeSuggestion(current, false)
//            suggest(current);

        }

    }
}
