public class Board {
	private String boardRepresentation;
	private List<Card> solution;
	private List<Player> players;
	private List<Room> rooms;

	public Board(List<Card> solution, List<Player> players) {
		this.solution = solution, this.players = players;
	}

	/** Generates the board based on the amount of players
	 * 
	 *  # - Wall Block/Corner
	 *  [ - Left Wall
	 *  ] - Right Wall
	 *  ^ - Top Wall
	 *  _ - Bottom Wall
	 *  . - Empty Space
	 *  $ - Door
	 *  
	 *  K - Kitchen
	 *  B - Ballroom
	 *  C - Conservatory
	 *  I - Billiard Room
	 *  L - Library
	 *  S - Study
	 *  H - Hall
	 *  O - Lounge
	 *  D - Dining Room
	 *  A - Accusation Room
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
		board = ##########################
				##########1####2##########
				##^^^^##...#^^#...##^^^^##
				#[KKKK]..#^BBBB^#..[CCCC]#
				#[KKKK]..[BBBBBB]..[CCCC]#
				#[KKKK]..[BBBBBB]..[CCCC]#
				##KKKK].$BBBBBBBB$.$#__###
				###__K#..[BBBBBB].......3#
				#........#B____B#.......##
				##.................#^^^^##
				##^^^#............$IIIII]#
				#[DDDD^^#..#####...[IIII]#
				#[DDDDDD]..#####...[IIII]#
				#[DDDDDDD$.#####...#___I##
				#[DDDDDD]..#####.....$.$##
				#[DDDDDD]..#####...#^L^^##
				##_____D#..#####..#LLLLL##
				##.....$...##A##.$LLLLLL]#
				#6..........$$....#LLLLL##
				##........#^HH^#...#___###
				##^^^^^]..[HHHH]........4#
				#[OOOOO]..[HHHHH$.$.....##
				#[OOOOO]..[HHHH]..[^^^^^##
				#[OOOOO]..[HHHH]..[SSSSS]#
				#[OOOOO#..[HHHH]..#SSSSS]#
				##____##5##____##.##____##

	}
}