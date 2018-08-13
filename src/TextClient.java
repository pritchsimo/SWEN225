import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextClient {

    /**
     * Takes user input of a string
     */
    private static String inputString(String message) {
        System.out.print(message);
        while (true) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                return input.readLine().toLowerCase();
            } catch (IOException e) {
                System.out.println("I/O Error.");
            }
        }
    }

    /**
     * Takes user input of a number
     */
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

    /**
     * Used to setup the amount of players
     */
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

    /**
     * Asks the player which room they wish to head towards, then takes user input to move
     *
     * @param player the player moving
     * @param diceRoll how many spaces the player can move
     * @param game
     */
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

        System.out.println("You need to move " + direction(dx, dy));


        for (int i = 0; i < diceRoll; i++) {
            if (!enterRoom(player, game)){
                if (player.getRoom() != null) {
                    System.out.println("You are in the " + player.getRoom().getName() + ". Do you wish to stop moving? (yes) or ");

                }
                while (true){
                    response = inputString("Move: (w/a/s/d)");
                    if (response.equals("w")) {
                        if (player.translate("upwards", 1)) {
                            System.out.println("You moved upwards. " + (diceRoll-i-1) + " moves remaining.");
                            break;
                        }

                    } else if (response.equals("a")) {
                        if (player.translate("left", 1)) {
                            System.out.println("You moved left. " + (diceRoll-i-1) + " moves remaining.");
                            break;
                        }
                    } else if (response.equals("s")) {
                        if (player.translate("downwards", 1)) {
                            System.out.println("You moved downwards. " + (diceRoll-i-1) + " moves remaining.");
                            break;
                        }
                    } else if (response.equals("d")) {
                        if (player.translate("right", 1)) {
                            System.out.println("You moved right. " + (diceRoll-i-1) + " moves remaining.");
                            break;
                        }
                    } else if (response.equals("yes") && player.getRoom() != null){
                        System.out.println();
                        return;
                    } else if (!response.equals("w") || !response.equals("a") || !response.equals("s") || !response.equals("d") || !(response.equals("yes") && player.getRoom() != null)){
                        System.out.println("Invalid input. Please enter a valid input.");
                    }

                }
            }

        }
    }

    private static String direction(int dx, int dy) {
        if (dx > 0 && dy == 0) return dx + " to the right.";     //east
        else if (dx > 0 && dy > 0) return dx + " to the right and " + dy + " downwards.";   //south-east
        else if (dx == 0 && dy > 0) return dy + " downwards.";      //south
        else if (dx < 0 && dy > 0) return dx * -1 + " to the left and " + dy + " downwards.";    //south-west
        else if (dx < 0 && dy == 0) return dx * -1 + " to the left.";      //west
        else if (dx < 0 && dy < 0) return dx * -1 + " to the left and " + dy * -1 + " upwards.";    //north-west
        else if (dx == 0 && dy < 0) return dy * -1 + " upwards.";    //north
        else return dx + " to the right and " + dy * -1 + " upwards.";      //north-east
    }

    /**
     * Asks the player whether they want to enter the room and moves them inside
     *
     * @param player the player outside a room
     * @param game
     */
    private static boolean enterRoom(Player player, Cluedo game){
        char square = game.getBoard().getBoard()[player.getCoords().x][player.getCoords().y];
        if (player.doorSquare(square) != null){
            System.out.println("You are outside the " + player.doorSquare(square).getName() + ".");
            while (true) {
                String response = inputString("Would you like to go in? (yes/no)");
                if (response.equals("yes")) {
                    System.out.println();
                    player.enterRoom();
                    return true;
                } else if (response.equals("no")) {
                    return false;
                }
                System.out.println("Invalid input. Please enter a valid input.");

            }
        }
        return false;
    }

    /**
     * Allows a player to make a suggestion about the murder using a person, weapon or room
     *
     * @param player the player making the suggestion
     * @param game
     * @return False if it was a suggestion, True if was an accusation or no suggestion
     */
    private static boolean makeSuggestion(Player player, Cluedo game) {
        List<String> allPlayers = Arrays.asList("Miss Scarlett", "Col. Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum");
        List<String> allWeapons = Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner");
        List<String> allRooms = Arrays.asList("Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Study", "Hall", "Lounge");
        List<String> suggestion = new ArrayList<>();

        boolean isAccusation = false;
        while (true){
            String input = inputString("Do you wish to make an suggestion or accusation? (suggestion/accusation/no)");
            if (input.equals("suggestion")){
                break;
            } else if (input.equals("accusation")){
                isAccusation = true;
                break;
            } else if (input.equals("no")){
                return true;
            }
            System.out.println("Invalid input. Please enter a valid input.");
        }

        System.out.println("\nYour evidence:\n");
        printKnownEvidence(player, game);

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

        if (isAccusation) {
            suggestion.add(player.getRoom().getName());
            game.accuse(suggestion);
            return true;
        } else {
            suggestion.add(player.getRoom().getName());
            player.setCurrentSuggestion(suggestion);
            return false;
        }
    }

    private static void moveSuggestionToRoom(Player player, Cluedo game) {
        for (Player p : game.getPlayers()) {
            if (p.getName().equals(player.getCurrentSuggestion().get(0))) {
                p.setCoords(player.getRoom().getMiddleOfRoom());
                p.setRoom(player.getRoom());
                break;
            }
        }

        Map<Room, Weapon> map = game.getWeaponMap();
        Weapon temp;
        for (Room r : map.keySet()) {
            if (r.getName().equals(player.getCurrentSuggestion().get(2))) {
                temp = map.get(r);
                for (Room ro : map.keySet()) {
                    if (map.get(ro).getName().equals(player.getCurrentSuggestion().get(1))) {
                        map.put(r, map.get(ro));
                        map.put(ro, temp);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Allows the refutor to refute the suggestion made by a player
     * @param suggestor the player making the suggestion
     * @param refutor the player that has the opportunity to refute the suggestion
     * @return true/false depending on weather the player was able to refute the suggestion
     */
    private static boolean refuteSuggestion(Player suggestor, Player refutor) {
        List<String> suggestion = suggestor.getCurrentSuggestion();

        System.out.println("The suggestion made is: ");
        for (String string : suggestion) {
            System.out.println(string);
        }

        List<Card> refutables = refutor.refutableCards(suggestion);
        if (refutables.isEmpty()) return false;

        //converts refutables to a list of strings for printing purposes
        List<String> refutableStrings = new ArrayList<>();
        for (Card c : refutables) {
            refutableStrings.add(c.getName());
        }

        System.out.println("Which card would you like to use the refute the suggestion? ");
        while (true) {
            int response = inputNumber(listToOutputString(refutableStrings));
            if (refuteSelection(response, suggestor, refutor, refutables)) break;
        }
        return true;
    }

    private static boolean refuteSelection(int response, Player suggestor, Player refutor, List<Card> cards){
        if (response < 1 || response > cards.size()) {
            System.out.println("Please enter a card between 1 and " + cards.size());
            return false;
        } else {
            suggestor.successfullyRefuted(cards.get(response - 1));
            System.out.println(cards.get(response - 1).getName() + " has been refuted by " + refutor.getName());
            return true;
        }
    }


    /** Lets each player refute the suggestion if applicable
     *
     * @param current player making the suggestion
     */
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
            s += "\n" + (i+1) + ". " + list.get(i);
        }
        return s;
    }

    /**
     * Prints out the players known evidence
     */
    private static void printKnownEvidence(Player player, Cluedo game) {
        System.out.println("Suspects:");
        for (String s : game.getPlayerOptions()) {
            if (player.getKnownEvidence().contains(s)) System.out.println(String.format("%-20s[X]", s));
            else System.out.println(String.format("%-20s[ ]", s));
        }
        System.out.println();

        System.out.println("Weapons:");
        for (String s : game.getWeaponOptions()) {
            if (player.getKnownEvidence().contains(s)) System.out.println(String.format("%-20s[X]", s));
            else System.out.println(String.format("%-20s[ ]", s));
        }
        System.out.println();

        System.out.println("Rooms:");
        for (String s : game.getRoomOptions()) {
            if (player.getKnownEvidence().contains(s)) System.out.println(String.format("%-20s[X]", s));
            else System.out.println(String.format("%-20s[ ]", s));
        }
        System.out.println();
    }

    public static void main(String... args) {
        Cluedo cluedo = new Cluedo();
        playerSetup(cluedo);
        cluedo.setup(false);

        while (!cluedo.isGameWon() || !cluedo.getPlayers().isEmpty()){
            if (cluedo.getMove() == null){
                break;
            }
            Player current = cluedo.getMove();
            int dice1 = (int) (Math.random() * 5) + 1;
            int dice2 = (int) (Math.random() * 5) + 1;

            System.out.println("It is " +  current.getName() + "'s turn. You roll a " + (dice1+dice2) + ".");
            enterRoom(current, cluedo);
            movePlayer(current, (dice1+dice2), cluedo);

            if (current.getRoom() != null){
                if (!makeSuggestion(current, cluedo)){
                    moveSuggestionToRoom(current, cluedo);
                    suggest(current);
                }

            }
        }

        if (cluedo.getPlayers().isEmpty()){
            System.out.println("Every player has been eliminated, the game has ended.");
        }

    }
}
