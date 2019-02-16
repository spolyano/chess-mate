import java.util.ArrayList;
import java.util.Scanner;

public class ChessBoard {
	private ArrayList<ChessFigure> figures;
	Scanner keyRead;
	
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
	
	private int randomFigure() {
		int[] input = new int[2];
		int figureID;
		while(true) {
			input[0] = (int) (8 * Math.random());
			input[1] = (int) (8 * Math.random());
			
			figureID = findFigure(input[0], input[1]);
			if (figureID >= 0) break;		
		}	
		return figureID;
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
	
	public void playRandom(){
		int[][] posList = new int[2][64];
		
		int figureID;
		for (int i = 0; i < 10; i++) {	
			figureID = randomFigure();
			posList = figures.get(figureID).getPositionList();
			int randPos = (int) ((posList.length-1) * Math.random());
			figures.get(figureID).setPosition(posList[0][randPos], posList[1][randPos]);
			drawBoard();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				System.out.print("Iterrupted");
			}
		}		
	}
	
	public void play() {
		System.out.println("Game started!");
		keyRead = new Scanner(System.in);
		
		int now = ChessFigure.WHITE;
		int[] posXY = new int[2];
		int[][] posList = new int[2][64];
		
		System.out.println("Select WHITE (0) figure: ");
		posXY = inputPosition();

		int figureID = findFigure(posXY[0], posXY[1]);
		if (figureID >= 0) {
			
			System.out.println("Select new position: ");
			posXY = inputPosition();
			
			if (figures.get(figureID).canMove(posXY[0], posXY[1])) {
				figures.get(figureID).setPosition(posXY[0], posXY[1]);
				drawBoard();
			}
			else System.out.print("not a valid move");
		}
		
		keyRead.close();
	}
}
