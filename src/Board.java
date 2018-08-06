import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Board {
	private String boardRepresentation;
	private HashMap<String, Room> rooms;
	private char[][] board;

	public Board() {
		generateBoard();
		addRooms();
	}


	/** Generates the board based on the amount of players
	 *
	 *  # - Wall Block/Corner
	 *  [ - Left Wall
	 *  ] - Right Wall
	 *  ^ - Top Wall
	 *  _ - Bottom Wall
	 *  . - Empty Space
	 *  R - Room
	 *
	 *  K - Kitchen Door
	 *  B - Ballroom Door
	 *  C - Conservatory Door
	 *  I - Billiard Room Door
	 *  L - Library Door
	 *  S - Study Door
	 *  H - Hall Door
	 *  O - Lounge Door
	 *  D - Dining Room Door
	 *  A - Accusation Room Door
	 *
	 *  1 - Mrs. White
	 *  2 - Mr. Green
	 *  3 - Mrs. Peacock
	 *  4 - Prof. Plum
	 *  5 - Miss Scarlett
	 *  6 - Col. Mustard
	 *
	 *  **/
	public void generateBoard() {
        boardRepresentation =
                        "##########################" +
                        "##########1####2##########" +
                        "##^^^^##...#^^#...##^^^^##" +
                        "#[RRRR]..#^RRRR^#..[RRRR]#" +
                        "#[RRRR]..[RRRRRR]..[RRRR]#" +
                        "#[RRRR]..[RRRRRR]..[RRRR]#" +
                        "##RRRR].BRRRRRRRRB.C#__###" +
                        "###__R#..[RRRRRR].......3#" +
                        "#....K...#R____R#.......##" +
                        "##........B....B...#^^^^##" +
                        "##^^^#............IRRRRR]#" +
                        "#[RRRR^^#..#####...[RRRR]#" +
                        "#[RRRRRR]..#####...[RRRR]#" +
                        "#[RRRRRRRD.#####...#___R##" +
                        "#[RRRRRR]..#####.....L.I##" +
                        "#[RRRRRR]..#####...#^R^^##" +
                        "##_____R#..#####..#RRRRR##" +
                        "##.....D...##A##.LRRRRRR]#" +
                        "#6..........HH....#RRRRR##" +
                        "##.....O..#^RR^#...#___###" +
                        "##^^^^^]..[RRRR]........4#" +
                        "#[RRRRR]..[RRRRRH.S.....##" +
                        "#[RRRRR]..[RRRR]..[^^^^^##" +
                        "#[RRRRR]..[RRRR]..[RRRRR]#" +
                        "#[RRRRR#..[RRRR]..#RRRRR]#" +
                        "##____##5##____##.##____##" +
                        "##########################";

		board = new char[26][27];
		char[] chars = boardRepresentation.toCharArray();
		int i = 0;
		for (int y = 0; y < 27; y++) {
			for (int x = 0; x < 26; x++) {
				board[x][y] = chars[i];
				i++;
			}
		}
//
//        for (int y = 0; y < 27; y++) {
//            for (int x = 0; x < 26; x++) {
//                System.out.print(board[x][y]);
//            }
//            System.out.print("\n");
//        }
	}

	private void addRooms() {
		rooms = new HashMap<>();
		rooms.put("kitchen", new Room("Kitchen", Arrays.asList(new Point(5, 8)), new Point(4, 5)));
		rooms.put("ballroom", new Room("Ballroom", Arrays.asList(new Point(8, 6), new Point(17, 6), new Point(10, 9), new Point(15, 9)), new Point(13, 6)));
		rooms.put("conservatory", new Room("Conservatory", Arrays.asList(new Point(19, 6)), new Point(21, 4)));
		rooms.put("dining room", new Room("Dining Room", Arrays.asList(new Point(7, 17), new Point(9, 13)), new Point(5, 13)));
		rooms.put("billiard room", new Room("Billiard Room", Arrays.asList(new Point(18, 10), new Point(23, 14)), new Point(22, 11)));
		rooms.put("library", new Room("Library", Arrays.asList(new Point(21, 14), new Point(17, 17)), new Point(21, 17)));
		rooms.put("study", new Room("Study", Arrays.asList(new Point(18, 21)), new Point(21, 23)));
		rooms.put("hall", new Room("Hall", Arrays.asList(new Point(16, 21), new Point(12, 18), new Point(13, 18)), new Point(13, 20)));
		rooms.put("lounge", new Room("Lounge", Arrays.asList(new Point(7, 19)), new Point(4, 22)));
	}

	public HashMap<String, Room> getRooms() {
		return rooms;
	}

	public char[][] getBoard() {
		return board;
	}
}
