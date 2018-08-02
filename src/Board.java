import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
	private String boardRepresentation;
	private static List<Room> rooms;
	private static char[][] board;

	public Board() {
		generateBoard();
		addRooms();
	}

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
				"########################## " +
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
						"##____##5##____##.##____##";

		board = new char[26][25];
		char[] chars = boardRepresentation.toCharArray();
		int i = 0;
		for (int x = 0; x < 26; x++) {
			for (int y = 0; y < 25; y++) {
				board[x][y] = chars[i];
				i++;
			}
		}
	}

	private void addRooms() {
		rooms = new ArrayList<>();
		rooms.add(new Room("Kitchen", Arrays.asList(new Point(5, 8))));
		rooms.add(new Room("Ballroom", Arrays.asList(new Point(8, 6), new Point(17, 6), new Point(10, 9), new Point(15, 9))));
		rooms.add(new Room("Conservatory", Arrays.asList(new Point(19, 6))));
		rooms.add(new Room("Dining Room", Arrays.asList(new Point(7, 17), new Point(9, 13))));
		rooms.add(new Room("Billiard Room", Arrays.asList(new Point(18, 10), new Point(23, 14))));
		rooms.add(new Room("Library", Arrays.asList(new Point(21, 14), new Point(17, 17))));
		rooms.add(new Room("Study", Arrays.asList(new Point(18, 21))));
		rooms.add(new Room("Hall", Arrays.asList(new Point(16, 21), new Point(12, 18), new Point(13, 18))));
		rooms.add(new Room("Lounge", Arrays.asList(new Point(7, 19))));
	}

	public static List<Room> getRooms() {
		return rooms;
	}

	public static char[][] getBoard() {
		return board;
	}

	private void addRooms() {
	    rooms = new ArrayList<>();
	    rooms.add(new Room("Kitchen", Arrays.asList(new Point(5, 8))));
	    rooms.add(new Room("Ballroom", Arrays.asList(new Point(8, 6), new Point(17, 6), new Point(10, 9), new Point(15, 9))));
	    rooms.add(new Room("Conservatory", Arrays.asList(new Point(19, 6))));
	    rooms.add(new Room("Dining Room", Arrays.asList(new Point(7, 17), new Point(9, 13))));
	    rooms.add(new Room("Billiard Room", Arrays.asList(new Point(18, 10), new Point(23, 14))));
	    rooms.add(new Room("Library", Arrays.asList(new Point(21, 14), new Point(17, 17))));
	    rooms.add(new Room("Study", Arrays.asList(new Point(18, 21))));
	    rooms.add(new Room("Hall", Arrays.asList(new Point(16, 21), new Point(12, 18), new Point(13, 18))));
	    rooms.add(new Room("Lounge", Arrays.asList(new Point(7, 19))));
    }

    public List<Room> getRooms() {
        return rooms;
    }
}