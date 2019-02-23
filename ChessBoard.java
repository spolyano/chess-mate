import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChessBoard {
	private static ChessFigure[][] figureCells = new ChessFigure[8][8];
	private ArrayList<int[]> posArray;
	private int[] posCurrent = new int[2];
	
	// ** create singleton **
	
    private ChessBoard(){}
	
	private static ChessBoard cboard;
    public static ChessBoard getBoard(){
        if (cboard==null) {
            return (cboard = new ChessBoard());
        } else
            return cboard;
    }

	// ** static methods **
	
	public static ChessFigure[][] getFigureCells(){
		return figureCells;
	}
	
	// ** public methods **
	
	//save current position and return it
	public ArrayList<int[]> getPosArray(int[] pos){
		posCurrent = pos;
		posArray = figureCells[pos[0]][pos[1]].getPositionList();
		return posArray;
	}
	
	public boolean canMoveToPosition(int[] posNew) {
		if (posArray.size() > 0)
			for (int pos = 0; pos < posArray.size(); pos++) 
			    if (Arrays.equals(posArray.get(pos), posNew))
			    	return true;
		return false;
	}
	
	public void changePosition(int[] posNew) {
		figureCells[posNew[0]][posNew[1]] = figureCells[posCurrent[0]][posCurrent[1]];
		figureCells[posNew[0]][posNew[1]].setPosition(posNew[0], posNew[1]);
		figureCells[posCurrent[0]][posCurrent[1]] = null;
	}
	
	public void init() {
        //set to null if re-init
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++)
				figureCells[i][j] = null;
		//create 8 pawns
		for (int j = 0; j < 8; j++) {	
			figureCells[1][j] = new Pawn(ChessFigure.ENEMY, 1, j);
			figureCells[6][j] = new Pawn(ChessFigure.YOU, 6, j);
		}
		//create 2 rooks
		for (int j = 0; j < 8; j+=7) {		
			figureCells[0][j] = new Rook(ChessFigure.ENEMY, 0, j);
			figureCells[7][j] = new Rook(ChessFigure.YOU, 7, j);
		}
		//create 2 knights
		for (int j = 1; j < 7; j+=5) {		
			figureCells[0][j] = new Knight(ChessFigure.ENEMY, 0, j);
			figureCells[7][j] = new Knight(ChessFigure.YOU, 7, j);
		}	
		//create 2 bishops
		for (int j = 2; j < 6; j+=3) {		
			figureCells[0][j] = new Bishop(ChessFigure.ENEMY, 0, j);
			figureCells[7][j] = new Bishop(ChessFigure.YOU, 7, j);
		}
		//create queen
		figureCells[0][3] = new Queen(ChessFigure.ENEMY, 0, 3);
		figureCells[7][3] = new Queen(ChessFigure.YOU, 7, 3);
		//create king
		figureCells[0][4] = new King(ChessFigure.ENEMY, 0, 4);
		figureCells[7][4] = new King(ChessFigure.YOU, 7, 4);
	}
	
	// ** AI **
	//select coords of random enemy figure, who can move
	public int[] getEnemyCurrent() {
		int[] pos = new int[2];
		ArrayList<ChessFigure> movableEnemy = new ArrayList<ChessFigure>();
		
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++)
            	if (figureCells[i][j] != null)
                	if (figureCells[i][j].getColor() == ChessFigure.ENEMY) {
                		pos[0] = i;
                		pos[1] = j;
                		posArray = getPosArray(pos);
                		if (posArray.size() > 0)
                			movableEnemy.add(figureCells[i][j]);
                	}
		
		int cnt = movableEnemy.size();
		if (cnt > 0) {
			cnt = (int) (Math.random() * cnt);
			pos[0] = movableEnemy.get(cnt).pos_i;
			pos[1] = movableEnemy.get(cnt).pos_j;
			posArray = getPosArray(pos);
		}
		else return null;
		
		return pos;
	}
	
	public int[] getEnemyNew() {
		int[] pos = new int[2];
		
		int cnt = posArray.size();
		if (cnt > 0) {
			cnt = (int) (Math.random() * cnt);
			pos[0] = posArray.get(cnt)[0];
			pos[1] = posArray.get(cnt)[1];
		}
		else return null;
		
		return pos;
	}
	
	// ** debug-case with console **
	Scanner keyRead;
	
	private int[] inputPosition() {
		int[] input = new int[2];
		System.out.print("i [0-7] ");
		input[0] = keyRead.nextInt();
		System.out.print("j [0-7] ");
		input[1] = keyRead.nextInt();
		return input;
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
		
		int[] posNew = new int[2];
		
		//test for - 4-mal
		//any key but number will break
		for(int i = 0; i < 4; i++) {
		
		System.out.println("Select figure: ");
		posCurrent = inputPosition();
		
		if (figureCells[posCurrent[0]][posCurrent[1]] != null) {
			posArray = getPosArray(posCurrent);
	
			if (posArray.size() > 0){
				System.out.println("Select new position: ");
				posNew = inputPosition();
				if (canMoveToPosition(posNew)) {
					changePosition(posNew);
					drawBoard();
				}
			}
		}
		
		//enemy
		posCurrent = getEnemyCurrent();
		posNew = getEnemyNew();
		changePosition(posNew);
		drawBoard();
		
		}//end test-for
		
		keyRead.close();
	}
}
