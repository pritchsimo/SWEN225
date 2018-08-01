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
		boardRepresentation = "
				########################## 
				##########1####2##########
				##^^^^##...#^^#...##^^^^##
				#[RRRR]..#^RRRR^#..[RRRR]#
				#[RRRR]..[RRRRRR]..[RRRR]#
				#[RRRR]..[RRRRRR]..[RRRR]#
				##RRRR].BRRRRRRRRB.C#__###
				###__R#..[RRRRRR].......3#
				#....K...#R____R#.......##
				##........B....B...#^^^^##
				##^^^#............IRRRRR]#
				#[RRRR^^#..#####...[RRRR]#
				#[RRRRRR]..#####...[RRRR]#
				#[RRRRRRRD.#####...#___R##
				#[RRRRRR]..#####.....L.I##
				#[RRRRRR]..#####...#^R^^##
				##_____R#..#####..#RRRRR##
				##.....D...##A##.LRRRRRR]#
				#6..........HH....#RRRRR##
				##.....O..#^RR^#...#___###
				##^^^^^]..[RRRR]........4#
				#[RRRRR]..[RRRRRH.S.....##
				#[RRRRR]..[RRRR]..[^^^^^##
				#[RRRRR]..[RRRR]..[RRRRR]#
				#[RRRRR#..[RRRR]..#RRRRR]#
				##____##5##____##.##____## ";


	}
}