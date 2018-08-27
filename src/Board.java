import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class Board {
	private String boardRepresentation;
	private HashMap<String, Room> rooms;
	private char[][] board;

	public Board() {
		generateBoard();
		addRooms();
	}


	/* Generates the board based on the amount of players
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
	 *
	 *  1 - Mrs. White
	 *  2 - Mr. Green
	 *  3 - Mrs. Peacock
	 *  4 - Prof. Plum
	 *  5 - Miss Scarlett
	 *  6 - Col. Mustard
	 *
	 *  */
	public void generateBoard() {
        boardRepresentation =
                        "###########################" +
                        "##^^^^####1#^^^#2####^^^^##" +
                        "#[RRRR]#...[RRR]...#[RRRR]#" +
                        "#[RRRR]..#^RRRRR^#..[RRRR]#" +
                        "#[RRRR]..[RRRRRRR]..#RRRR]#" +
						"#[RRRR]..[RRRRRRR]..CRRRR]#" +
                        "##RRRR].BRRRRRRRRRB..#___##" +
                        "###____K.#R_____R#.......3#" +
                        "#.........B.....B........##" +
                        "##.................#^^^^^##" +
                        "##^^^#.....#####..IRRRRRR]#" +
                        "#[RRRR^^#..#####...[RRRRR]#" +
                        "#[RRRRRR]..#####...#____R##" +
                        "#[RRRRRRRD.#####......L.I##" +
                        "#[RRRRRR]..#####...#^^R^^##" +
                        "#[RRRRRR]..#####..#RRRRRR##" +
                        "##_____R#..#####.LRRRRRRR]#" +
                        "##.....D..........#RRRRRR]#" +
                        "#6..........HHH....#_____##" +
                        "##........#^RRR^^H.......4#" +
                        "##^^^^^^O.[RRRRR]..S.....##" +
                        "#[RRRRR]..[RRRRR]..[^^^^^##" +
                        "#[RRRRR]..[RRRRR]..[RRRRR]#" +
                        "#[RRRRR]..#RRRRR#..[RRRRR]#" +
                        "#[RRRRR#...[RRR]...#RRRRR]#" +
                        "##____####5#___#.####____##" +
                        "###########################";

		board = new char[27][27];
		char[] chars = boardRepresentation.toCharArray();
		int i = 0;
		for (int y = 0; y < 27; y++) {
			for (int x = 0; x < 27; x++) {
				board[x][y] = chars[i];
				i++;
			}
		}
	}

	private void addRooms() {
		rooms = new HashMap<>();
		rooms.put("kitchen", new Room("Kitchen", Arrays.asList(new Point(7, 7)), new Point(4, 5)));
		rooms.put("ballroom", new Room("Ballroom", Arrays.asList(new Point(8, 6), new Point(18, 6), new Point(10, 8), new Point(16, 8)), new Point(13, 6)));
		rooms.put("conservatory", new Room("Conservatory", Arrays.asList(new Point(20, 5)), new Point(23, 3)));
		rooms.put("dining room", new Room("Dining Room", Arrays.asList(new Point(7, 17), new Point(9, 13)), new Point(5, 13)));
		rooms.put("billiard room", new Room("Billiard Room", Arrays.asList(new Point(19, 10), new Point(24, 13)), new Point(22, 11)));
		rooms.put("library", new Room("Library", Arrays.asList(new Point(22, 13), new Point(17, 16)), new Point(21, 17)));
		rooms.put("study", new Room("Study", Arrays.asList(new Point(19, 20)), new Point(23, 23)));
		rooms.put("hall", new Room("Hall", Arrays.asList(new Point(12, 18), new Point(13, 18), new Point(14, 18), new Point(17, 19)), new Point(13, 21)));
		rooms.put("lounge", new Room("Lounge", Arrays.asList(new Point(7, 19)), new Point(4, 22)));
	}

	public HashMap<String, Room> getRooms() {
		return rooms;
	}

	public char[][] getBoard() {
		return board;
	}
}
