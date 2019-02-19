
public abstract class ChessFigure {
	
	public final static int WHITE = 0;
	public final static int BLACK = 1;
	
	public final static int PAWN = 1;
	public final static int ROOK = 2;
	public final static int BISHOP = 3;
	
	int pos_x;
	int pos_y;
	int rank;
	int color;
	
	public ChessFigure(int rank, int color, int pos_x, int pos_y) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.rank = rank;
		this.color = color;
	}
	
	// true: input = current position
	public boolean onPosition(int pos_x, int pos_y){
		if (this.pos_x == pos_x && this.pos_y == pos_y)
			return true;
		else return false;
	}
	
	public void setPosition(int pos_x, int pos_y) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
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
		
	public abstract int[][] getPositionList();
}

final class Pawn extends ChessFigure{
	
	public Pawn(int color, int pos_x, int pos_y) {
		super(PAWN, color, pos_x, pos_y);
	}

	public int[][] getPositionList(){
		int[][] posList = new int[64][2];
		
		if (color == WHITE) {
			if (pos_y == 2) {
				posList[0][0] = pos_x;
				posList[0][1] = 3;
				posList[1][0] = pos_x;
				posList[1][1] = 4;
			}
			else if (pos_y < 8) {
				posList[0][0] = pos_x;
				posList[0][1] = pos_y + 1;
			}	
		}
		else if (color == BLACK) {
			if (pos_y == 7) {
				posList[0][0] = pos_x;
				posList[0][1] = 6;
				posList[1][0] = pos_x;
				posList[1][1] = 5;
			}
			else if (pos_y > 1)  {
				posList[0][0] = pos_x;
				posList[0][1] = pos_y - 1;
			}	
		}	
		return posList;
	}	
}//Pawn
