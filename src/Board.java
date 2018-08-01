import java.util.List;

public class Board {
	private String boardRepresentation;
	private List<Card> solution;
	private List<Player> players;
	private List<Room> rooms;

	public Board(List<Card> solution, List<Player> players) {
		this.solution = solution;
		this.players = players;
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
				"########################## \n" +
				"##########1####2##########\n" +
				"##^^^^##...#^^#...##^^^^##\n" +
				"#[RRRR]..#^RRRR^#..[RRRR]#\n" +
				"#[RRRR]..[RRRRRR]..[RRRR]#\n" +
				"#[RRRR]..[RRRRRR]..[RRRR]#\n" +
				"##RRRR].BRRRRRRRRB.C#__###\n" +
				"###__R#..[RRRRRR].......3#\n" +
				"#....K...#R____R#.......##\n" +
				"##........B....B...#^^^^##\n" +
				"##^^^#............IRRRRR]#\n" +
				"#[RRRR^^#..#####...[RRRR]#\n" +
				"#[RRRRRR]..#####...[RRRR]#\n" +
				"#[RRRRRRRD.#####...#___R##\n" +
				"#[RRRRRR]..#####.....L.I##\n" +
				"#[RRRRRR]..#####...#^R^^##\n" +
				"##_____R#..#####..#RRRRR##\n" +
				"##.....D...##A##.LRRRRRR]#\n" +
				"#6..........HH....#RRRRR##\n" +
				"##.....O..#^RR^#...#___###\n" +
				"##^^^^^]..[RRRR]........4#\n" +
				"#[RRRRR]..[RRRRRH.S.....##\n" +
				"#[RRRRR]..[RRRR]..[^^^^^##\n" +
				"#[RRRRR]..[RRRR]..[RRRRR]#\n" +
				"#[RRRRR#..[RRRR]..#RRRRR]#\n" +
				"##____##5##____##.##____##";

	}
}