import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChessBoard {
	private ArrayList<ChessFigure> figures;
	Scanner keyRead;
	
	private static int[][] boardCells = new int[8][8];
	
	// **internal methods **

	private int[] inputPosition() {
		int[] input = new int[2];
		System.out.print("x [0-7] ");
		input[0] = keyRead.nextInt();
		System.out.print("y [0-7] ");
		input[1] = keyRead.nextInt();
		return input;
	}
	
	private void changePosition(int figureID, int[] posCurrentXY, int[] posNewXY) {
		boardCells[posCurrentXY[0]][posCurrentXY[1]] = -1;
		boardCells[posNewXY[0]][posNewXY[1]] = figureID;
		figures.get(figureID).setPosition(posNewXY[0], posNewXY[1]);
	}

	// ** static methods **
	
	public static int[][] getBoardCells(){
		return boardCells;
	}
	
	// ** public methods **
	
	public void init() {
		figures = new ArrayList<ChessFigure>();
		
		// -1 == no figure
		for (int i = 0; i< 8; i++)
			for (int j = 0; j< 8; j++)
				boardCells[i][j] = -1;
		
		int figureID = 0;	
		//create 8 pawns
		for (int i = 0; i < 8; i++) {
			figures.add(figureID, new Pawn(figureID, ChessFigure.WHITE, i, 1));
			boardCells[i][1] = figureID;
			figureID++;
			
			figures.add(figureID, new Pawn(figureID, ChessFigure.BLACK, i, 6));
			boardCells[i][6] = figureID;
			figureID++;
		}
		
		//create 2 bishops
		for (int i = 2; i < 6; i+=3) {
			figures.add(figureID, new Bishop(figureID, ChessFigure.WHITE, i, 0));
			boardCells[i][0] = figureID;
			figureID++;
			
			figures.add(figureID, new Bishop(figureID, ChessFigure.BLACK, i, 7));
			boardCells[i][7] = figureID;
			figureID++;
		}
		
	}
	
	public void drawBoard() {
		for (int i = 0; i < 8; i++) {
			if (i == 0) {
				System.out.print("[---]");
				for (int j = 0; j < 8; j++)
					System.out.print("[x:"+ j +"]");
				System.out.print("\n\n");
			}
			System.out.print("[y:"+ i +"]");
			for (int j = 0; j < 8; j++) {
				int fPos = boardCells[j][i];
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
		
		int[] posCurrentXY = new int[2];
		int[] posNewXY = new int[2];
		int[][] posList = new int[64][2];
		
		//test for - 4-mal
		//any key but number will break
		for(int i = 0; i < 4; i++) {
		
		System.out.println("Select figure: ");
		posCurrentXY = inputPosition();
		
		/*viable solution
		 * figureID = boardCells[posCurrentXY[0]][posCurrentXY[1]];
		 * posList = figures.get(figureID).getPositionList();
		 * changePosition(figureID, posCurrentXY, posNewXY);
		*/
		
		int figureID = boardCells[posCurrentXY[0]][posCurrentXY[1]];
		if (figureID >= 0) {
			//print possible positions
			posList = figures.get(figureID).getPositionList();
			System.out.println(Arrays.deepToString(posList));
			
			if (posList[0][0] > 0) {
				//set to new position
				System.out.println("Select new position: ");
				posNewXY = inputPosition();
				
				//test set
				changePosition(figureID, posCurrentXY, posNewXY);
				drawBoard();
			}
		}
		
		}//end test-for
		
		keyRead.close();
	}
}
