import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChessBoard {
	Scanner keyRead;
	private static ChessFigure[][] figureCells = new ChessFigure[8][8];
	private ArrayList<int[]> posArray;
	
	// **internal methods **

	private int[] inputPosition() {
		int[] input = new int[2];
		System.out.print("i [0-7] ");
		input[0] = keyRead.nextInt();
		System.out.print("j [0-7] ");
		input[1] = keyRead.nextInt();
		return input;
	}
	
	private void changePosition(int[] posCurrentXY, int[] posNewXY) {
		figureCells[posNewXY[0]][posNewXY[1]] = figureCells[posCurrentXY[0]][posCurrentXY[1]];
		figureCells[posNewXY[0]][posNewXY[1]].setPosition(posNewXY[0], posNewXY[1]);
		figureCells[posCurrentXY[0]][posCurrentXY[1]] = null;
	}

	// ** static methods **
	
	public static ChessFigure[][] getFigureCells(){
		return figureCells;
	}
	
	// ** public methods **
	
	public ArrayList<int[]> getPosArray(int i, int j){
		return figureCells[i][j].getPositionList();
	}
	
	public boolean canMoveToPosition(int[] posNew) {
		if (posArray.size() > 0)
			for (int pos = 0; pos < posArray.size(); pos++) 
			    if (Arrays.equals(posArray.get(pos), posNew))
			    	return true;
		return false;
	}
	
	public void init() {
		//create 8 pawns
		for (int j = 0; j < 8; j++) {	
			figureCells[1][j] = new Pawn(ChessFigure.ENEMY, 1, j);
			figureCells[6][j] = new Pawn(ChessFigure.YOU, 6, j);
		}
		//create 2 bishops
		for (int j = 2; j < 6; j+=3) {		
			figureCells[0][j] = new Bishop(ChessFigure.ENEMY, 0, j);
			figureCells[7][j] = new Bishop(ChessFigure.YOU, 7, j);
		}	
	}
	
	public void drawBoard() {
		System.out.print("\n[---]");
		for (int j = 0; j < 8; j++)
			System.out.print("[j:"+ j +"]");
		System.out.print("\n\n");
		for (int i = 0; i < 8; i++) {
			System.out.print("[i:"+ i +"]");
			for (int j = 0; j < 8; j++)
				if(figureCells[i][j] != null)
					figureCells[i][j].printFigure();
				else System.out.print("[ x ]");
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	public void play() {
		System.out.println("Game started!");
		keyRead = new Scanner(System.in);
		
		int[] posCurrent = new int[2];
		int[] posNew = new int[2];
		
		//test for - 4-mal
		//any key but number will break
		for(int i = 0; i < 4; i++) {
		
		System.out.println("Select figure: ");
		posCurrent = inputPosition();
		
		if (figureCells[posCurrent[0]][posCurrent[1]] != null) {
			
			posArray = getPosArray(posCurrent[0], posCurrent[1]);
			System.out.println(Arrays.deepToString(posArray.toArray()));
			
			if (posArray.size() > 0){
				//set to new position
				System.out.println("Select new position: ");
				posNew = inputPosition();
				
				if (canMoveToPosition(posNew)) {
					changePosition(posCurrent, posNew);
					drawBoard();
				}
			}
		}
		
		}//end test-for
		
		keyRead.close();
	}
}
