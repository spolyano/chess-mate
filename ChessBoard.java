import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChessBoard {
	private ArrayList<ChessFigure> figures;
	Scanner keyRead;
	
	public final static int MOVE = 1;
	public final static int ATTACK = 2;
	
	// **internal methods **
	
	// return ID of figure on [x,y] position
	private int findFigure(int pos_x, int pos_y) {
		for (int i = 0; i < figures.size(); i++)
			if(figures.get(i).onPosition(pos_x, pos_y)) return i;
		return -1;		
	}
	
	private int[] inputPosition() {
		int[] input = new int[2];
		System.out.print("x [1-8] ");
		input[0] = keyRead.nextInt();
		System.out.print("y [1-8] ");
		input[1] = keyRead.nextInt();
		return input;
	}
	
	private int[][] generatePath(int figureID){
		int[][] path = new int[64][3];
		int[][] posList = figures.get(figureID).getPositionList();
		int rank = figures.get(figureID).getRank();
		
		if (rank == ChessFigure.PAWN) {
			int i = 0;
			while (posList[i][0] > 0) {
				int onPos = findFigure(posList[i][0], posList[i][1]);				
				if (onPos < 0) {
					path[i][0] = posList[i][0];
					path[i][1] = posList[i][1];
					path[i][2] = MOVE;
				}
				else break;
				
				i++;
			}
		}
		return path;
	}
	
	// ** public methods **
	
	public void init() {
		figures = new ArrayList<ChessFigure>();
		
		//create 8 pawns
		for (int i = 0; i < 8; i++) {
			figures.add(new Pawn(ChessFigure.WHITE, i + 1, 2));
			figures.add(new Pawn(ChessFigure.BLACK, i + 1, 7));
		}
	}
	
	public void drawBoard() {
		for (int i = 0; i < 8; i++) {
			if (i == 0) {
				System.out.print("[---]");
				for (int j = 0; j < 8; j++)
					System.out.print("[x:"+ (j + 1) +"]");
				System.out.print("\n\n");
			}
			System.out.print("[y:"+ (i + 1) +"]");
			for (int j = 0; j < 8; j++) {
				int fPos = findFigure(j + 1, i + 1);
				if (fPos >= 0)
					figures.get(fPos).printFigure();
				else System.out.print("[ x ]");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	public void play() {
		System.out.println("Game started!");
		keyRead = new Scanner(System.in);
		
		int now = ChessFigure.WHITE;
		int[] posXY = new int[2];
		int[][] posList = new int[64][3];
		
		//test for - 4-mal
		//any key but number will break
		for(int i = 0; i < 4; i++) {
		
		System.out.println("Select figure: ");
		posXY = inputPosition();

		int figureID = findFigure(posXY[0], posXY[1]);
		if (figureID >= 0) {
			//print possible positions
			posList = generatePath(figureID);
			System.out.println(Arrays.deepToString(posList));
			
			if (posList[0][0] > 0) {
				//set to new position
				System.out.println("Select new position: ");
				posXY = inputPosition();
				
				//test set
				figures.get(figureID).setPosition(posXY[0], posXY[1]);
				drawBoard();
			}
		}
		
		}//end test-for
		
		keyRead.close();
	}
}
