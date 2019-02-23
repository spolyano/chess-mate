import java.util.ArrayList;

public abstract class ChessFigure {
	
	public final static int ENEMY = 0;
	public final static int YOU = 1;
	
	public final static int PAWN = 1;
	public final static int ROOK = 2;
	public final static int KNIGHT = 3;
	public final static int BISHOP = 4;
	public final static int QUEEN = 5;
	public final static int KING = 6;
	
	int pos_j;
	int pos_i;
	int rank;
	int color;
	
	public ChessFigure(int rank, int color, int pos_i, int pos_j) {
		this.pos_j = pos_j;
		this.pos_i = pos_i;
		this.rank = rank;
		this.color = color;
	}
	
	public void setPosition(int pos_i, int pos_j) {
		this.pos_j = pos_j;
		this.pos_i = pos_i;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getRank() {
		return rank;
	}
	
	public void printFigure() {
		System.out.print("["+ rank + "-" + color + "]");	
	}
		
	public abstract ArrayList<int[]> getPositionList();
	
	protected void castRay(ArrayList<int[]> res, int di, int dj) {
		ChessFigure[][] figures = ChessBoard.getFigureCells();
		int tmpi, tmpj;
		for(tmpi = pos_i + di, tmpj = pos_j + dj; (tmpi >= 0)&&(tmpi <= 7)&&(tmpj >= 0)&&(tmpj <= 7); tmpi+=di, tmpj+=dj) {
			if(figures[tmpi][tmpj] == null || figures[tmpi][tmpj].getColor() != color)
				res.add(new int[] {tmpi, tmpj});
			if(figures[tmpi][tmpj] != null) break;
		}
	}
	
	protected void castRay(ArrayList<int[]> res, int di, int dj, int turns) {
		ChessFigure[][] figures = ChessBoard.getFigureCells();
		int tmpi, tmpj;
		int currentTurn = 0;
		for(tmpi = pos_i + di, tmpj = pos_j + dj; (tmpi >= 0)&&(tmpi <= 7)&&(tmpj >= 0)&&(tmpj <= 7); tmpi+=di, tmpj+=dj) {
			currentTurn++;
			if(figures[tmpi][tmpj] == null || figures[tmpi][tmpj].getColor() != color)
				res.add(new int[] {tmpi, tmpj});
			if(figures[tmpi][tmpj] != null) break;
			if(currentTurn >= turns) break;
		}
	}

}

final class Pawn extends ChessFigure{
	
	public Pawn(int color, int pos_i, int pos_j) {
		super(PAWN, color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		ChessFigure[][] figures = ChessBoard.getFigureCells();
		
		if (color == ENEMY) {
			if (pos_i == 1) {
				if ((figures[pos_i + 1][pos_j] == null)&&(figures[pos_i + 2][pos_j] == null)) {	
					posArray.add(new int[] {pos_i + 1, pos_j});
					posArray.add(new int[] {pos_i + 2, pos_j});
				}
				else if ((figures[pos_i + 1][pos_i] == null)&&(figures[pos_i + 2][pos_j] != null))
					posArray.add(new int[] {pos_i + 1, pos_j});
			}
			else if (pos_i < 7) {
				if (figures[pos_i + 1][pos_j] == null)
					posArray.add(new int[] {pos_i + 1, pos_j});
			}	
		}
		else if (color == YOU) {
			if (pos_i == 6) {
				if ((figures[pos_i - 1][pos_j] == null)&&(figures[pos_i - 2][pos_j] == null)) {	
					posArray.add(new int[] {pos_i - 1, pos_j});
					posArray.add(new int[] {pos_i - 2, pos_j});
				}
				else if ((figures[pos_i - 1][pos_j] == null)&&(figures[pos_i - 2][pos_j] != null))
					posArray.add(new int[] {pos_i - 1, pos_j});
			}
			else if (pos_i > 0)  {
				if (figures[pos_i - 1][pos_j] == null)
					posArray.add(new int[] {pos_i - 1, pos_j});
			}	
		}	
		return posArray;
	}	
	
}//Pawn

final class Rook extends ChessFigure{
	
	public Rook(int color, int pos_i, int pos_j) {
		super(ROOK, color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		castRay(posArray,-1, 0);
		castRay(posArray, 0,-1);
		castRay(posArray, 0, 1);
		castRay(posArray, 1, 0); 
		return posArray;
	}	
}//Rook

final class Knight extends ChessFigure{
	
	public Knight(int color, int pos_i, int pos_j) {
		super(KNIGHT, color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		castRay(posArray,-2, 1,1);
		castRay(posArray,-2,-1,1);
		castRay(posArray, 1,-2,1);
		castRay(posArray,-1,-2,1);
		castRay(posArray, 1, 2,1);
		castRay(posArray,-1, 2,1);
		castRay(posArray, 2, 1,1);
		castRay(posArray, 2,-1,1);
		return posArray;
	}	
}//Knight

final class Bishop extends ChessFigure{
	
	public Bishop(int color, int pos_i, int pos_j) {
		super(BISHOP, color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		castRay(posArray,-1, 1);
		castRay(posArray,-1,-1);
		castRay(posArray, 1, 1);
		castRay(posArray, 1,-1);
		return posArray;
	}	
}//Bishop

final class Queen extends ChessFigure{
	
	public Queen(int color, int pos_i, int pos_j) {
		super(QUEEN, color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		castRay(posArray,-1, 1);
		castRay(posArray,-1,-1);
		castRay(posArray, 1, 1);
		castRay(posArray, 1,-1);
		castRay(posArray,-1, 0);
		castRay(posArray, 0,-1);
		castRay(posArray, 0, 1);
		castRay(posArray, 1, 0);
		return posArray;
	}	
}//Queen

final class King extends ChessFigure{
	
	public King(int color, int pos_i, int pos_j) {
		super(KING, color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		castRay(posArray,-1, 1,1);
		castRay(posArray,-1,-1,1);
		castRay(posArray, 1, 1,1);
		castRay(posArray, 1,-1,1);
		castRay(posArray,-1, 0,1);
		castRay(posArray, 0,-1,1);
		castRay(posArray, 0, 1,1);
		castRay(posArray, 1, 0,1);
		return posArray;
	}	
}//King