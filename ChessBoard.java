import java.util.ArrayList;
import java.util.Scanner;

public class ChessBoard {
	
	private ArrayList<ChessFigure> figures;
	
	public void init() {
		figures = new ArrayList<ChessFigure>();
		
		//create 8 pawns
		for (int i = 0; i < 8; i++) {
			figures.add(new Pawn(ChessFigure.WHITE, i + 1, 2));
			figures.add(new Pawn(ChessFigure.BLACK, i + 1, 7));
		}
	}
	
	public int findFigure(int pos_x, int pos_y) {
		for (int i = 0; i < figures.size(); i++)
			if(figures.get(i).onPosition(pos_x, pos_y)) return i;
		return -1;		
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
	}
	
	public void play() {
		System.out.println("Game started!");
		
		int now = ChessFigure.WHITE;
		int key_x;
		int key_y;
		System.out.println("Select WHITE (0) figure: ");
		
		Scanner keyRead = new Scanner(System.in);
		
		System.out.print("x [1-8] ");
		key_x = keyRead.nextInt();
		System.out.print("y [1-8] ");
		key_y = keyRead.nextInt();
		
		int figureID = findFigure(key_x, key_y);
		if (figureID >= 0) {
			System.out.println("Select new position: ");
			
			System.out.print("x [1-8] ");
			key_x = keyRead.nextInt();
			System.out.print("y [1-8] ");
			key_y = keyRead.nextInt();
			
			
			if (figures.get(figureID).canMove(key_x, key_y)) {
				figures.get(figureID).setPosition(key_x, key_y);
				drawBoard();
			}
			else System.out.print("not a valid move");
			
		}
		
		keyRead.close();
		
	}
	
}
