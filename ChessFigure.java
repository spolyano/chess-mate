import java.util.ArrayList;

public abstract class ChessFigure {
	
	public final static int ENEMY = 1;
	public final static int YOU = -1;
	
	int pos_j;
	int pos_i;
	int color;
	int score;
	
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
	
	public int getScore() {
		return score;
	}

	public void printFigure() {
		System.out.print("["+ getClass().getSimpleName() + "-" + color + "]");	
	}
		
	public abstract ArrayList<int[]> getPositionList();
	
	protected void castTile(ChessFigure[][] figures, ArrayList<int[]> res, int i, int j) {
		int tmpi = pos_i + i;
		int tmpj = pos_j + j;
		if ((tmpi >= 0)&&(tmpi <= 7)&&(tmpj >= 0)&&(tmpj <= 7))
			if(figures[tmpi][tmpj] == null) res.add(new int[] {tmpi, tmpj});
			else executeAttack(figures, res, tmpi, tmpj);
	}
	
	protected void executeAttack(ChessFigure[][] figures, ArrayList<int[]> res, int i, int j) {
		if ((figures[i][j] != null) && (figures[i][j].getColor() != color)) {
			res.add(new int[] {i, j});
			
			if(figures[i][j].getClass().getSimpleName() == "Pawn") score += 1;
			else if(figures[i][j].getClass().getSimpleName() == "King") score += 1000; //mate
			else score += 10;

		}	
	}
	
	protected void castRay(ChessFigure[][] figures, ArrayList<int[]> res, int di, int dj) {
		int tmpi, tmpj, cnt;
		for(tmpi = pos_i + di, tmpj = pos_j + dj, cnt = 1; (tmpi >= 0)&&(tmpi <= 7)&&(tmpj >= 0)&&(tmpj <= 7); tmpi+=di, tmpj+=dj, cnt++) {
			castTile(figures, res, di*cnt, dj*cnt);
			if(figures[tmpi][tmpj] != null) break;
		}
	}
	
}//ChessFigure

final class Pawn extends ChessFigure{
	
	public Pawn(int color, int pos_i, int pos_j) {
		super(color, pos_i, pos_j);
	}

	private void castPawnStep(ChessFigure[][] figures, ArrayList<int[]> res, int di, int dj, int turns) {
		int currentTurn = 0;	
		int tmpi = pos_i + di;
		int tmpj = pos_j + dj;
		for (int d = -1; d <= 1; d+=2)
			if ((tmpi >= 0)&&(tmpi <= 7)&&((tmpj + d) >= 0)&&((tmpj + d) <= 7)) 
				executeAttack(figures, res, tmpi, tmpj + d);
		
		for(; (tmpi >= 0)&&(tmpi <= 7)&&(tmpj >= 0)&&(tmpj <= 7); tmpi+=di, tmpj+=dj) {
			currentTurn++;
			if(figures[tmpi][tmpj] == null)
				res.add(new int[] {tmpi, tmpj});
			if(figures[tmpi][tmpj] != null) break;
			if(currentTurn >= turns) break;
		}
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		ChessFigure[][] figures = ChessBoard.getFigureCells();
		score = 0;
		if (color == ENEMY)
			if (pos_i == 1)	castPawnStep(figures, posArray, 1, 0, 2);
			else			castPawnStep(figures, posArray, 1, 0, 1);
		else if (color == YOU)
			if (pos_i == 6)	castPawnStep(figures, posArray,-1, 0, 2);
			else			castPawnStep(figures, posArray,-1, 0, 1);
		return posArray;
	}
	
}//Pawn

final class Rook extends ChessFigure{
	
	public Rook(int color, int pos_i, int pos_j) {
		super(color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		ChessFigure[][] figures = ChessBoard.getFigureCells();
		score = 0;
		castRay(figures, posArray,-1, 0);
		castRay(figures, posArray, 0,-1);
		castRay(figures, posArray, 0, 1);
		castRay(figures, posArray, 1, 0); 
		return posArray;
	}	
}//Rook

final class Knight extends ChessFigure{
	
	public Knight(int color, int pos_i, int pos_j) {
		super(color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		ChessFigure[][] figures = ChessBoard.getFigureCells();
		score = 0;
		castTile(figures, posArray,-2, 1);
		castTile(figures, posArray,-2,-1);
		castTile(figures, posArray, 1,-2);
		castTile(figures, posArray,-1,-2);
		castTile(figures, posArray, 1, 2);
		castTile(figures, posArray,-1, 2);
		castTile(figures, posArray, 2, 1);
		castTile(figures, posArray, 2,-1);
		return posArray;
	}	
}//Knight

final class Bishop extends ChessFigure{
	
	public Bishop(int color, int pos_i, int pos_j) {
		super(color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		ChessFigure[][] figures = ChessBoard.getFigureCells();
		score = 0;
		castRay(figures, posArray,-1, 1);
		castRay(figures, posArray,-1,-1);
		castRay(figures, posArray, 1, 1);
		castRay(figures, posArray, 1,-1);
		return posArray;
	}	
}//Bishop

final class Queen extends ChessFigure{
	
	public Queen(int color, int pos_i, int pos_j) {
		super(color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		ChessFigure[][] figures = ChessBoard.getFigureCells();
		score = 0;
		castRay(figures, posArray,-1, 1);
		castRay(figures, posArray,-1,-1);
		castRay(figures, posArray, 1, 1);
		castRay(figures, posArray, 1,-1);
		castRay(figures, posArray,-1, 0);
		castRay(figures, posArray, 0,-1);
		castRay(figures, posArray, 0, 1);
		castRay(figures, posArray, 1, 0);
		return posArray;
	}	
}//Queen

final class King extends ChessFigure{
	
	public King(int color, int pos_i, int pos_j) {
		super(color, pos_i, pos_j);
	}

	public ArrayList<int[]> getPositionList(){
		ArrayList<int[]> posArray = new ArrayList<int[]>();
		ChessFigure[][] figures = ChessBoard.getFigureCells();
		score = 0;
		castTile(figures, posArray,-1, 1);
		castTile(figures, posArray,-1,-1);
		castTile(figures, posArray, 1, 1);
		castTile(figures, posArray, 1,-1);
		castTile(figures, posArray,-1, 0);
		castTile(figures, posArray, 0,-1);
		castTile(figures, posArray, 0, 1);
		castTile(figures, posArray, 1, 0);
		return posArray;
	}	
}//King