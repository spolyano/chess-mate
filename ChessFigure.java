import java.util.ArrayList;

public abstract class ChessFigure {
	
	public final static int ENEMY = 1;
	public final static int YOU = -1;
	
	int pos_j;
	int pos_i;
	int color;
	
	public ChessFigure(int color, int pos_i, int pos_j) {
		this.pos_j = pos_j;
		this.pos_i = pos_i;
		this.color = color;
	}
	
	public void setPosition(int pos_i, int pos_j) {
		this.pos_j = pos_j;
		this.pos_i = pos_i;
	}
	
	public int getColor() {
		return color;
	}

	public void printFigure() {
		System.out.print("["+ getClass().getSimpleName() + "-" + color + "]");	
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
		super(color, pos_i, pos_j);
	}

	private void castPawnStep(ArrayList<int[]> res, int di, int dj, int turns) {
		ChessFigure[][] figures = ChessBoard.getFigureCells();
		int tmpi, tmpj;
		int currentTurn = 0;
		for(tmpi = pos_i + di, tmpj = pos_j + dj; (tmpi >= 0)&&(tmpi <= 7)&&(tmpj >= 0)&&(tmpj <= 7); tmpi+=di, tmpj+=dj) {
			currentTurn++;
			if(figures[tmpi][tmpj] == null) {
				res.add(new int[] {tmpi, tmpj});
				castPawnDiag(res, tmpi + di, tmpj - 1);
				castPawnDiag(res, tmpi + di, tmpj + 1);	
			}
			castPawnDiag(res, tmpi, tmpj - 1);
			castPawnDiag(res, tmpi, tmpj + 1);
			if(figures[tmpi][tmpj] != null) break;
			if(currentTurn >= turns) break;
		}
	}
	
	private void castPawnDiag(ArrayList<int[]> res, int di, int dj) {
		if ((di >= 0)&&(di <= 7)&&(dj >= 0)&&(dj <= 7)) {
			ChessFigure[][] figures = ChessBoard.getFigureCells();
			if(figures[di][dj] != null && figures[di][dj].getColor() != color)
				res.add(new int[] {di, dj});
		}
	}
	
	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		if (color == ENEMY)
			if (pos_i == 1) castPawnStep(posArray, 1, 0, 2);
			else 			castPawnStep(posArray, 1, 0, 1);
		else if (color == YOU)
			if (pos_i == 6) castPawnStep(posArray,-1, 0, 2);
			else			castPawnStep(posArray,-1, 0, 1);
		return posArray;
	}
	
}//Pawn

final class Rook extends ChessFigure{
	
	public Rook(int color, int pos_i, int pos_j) {
		super(color, pos_i, pos_j);
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
		super(color, pos_i, pos_j);
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
		super(color, pos_i, pos_j);
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
		super(color, pos_i, pos_j);
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
		super(color, pos_i, pos_j);
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