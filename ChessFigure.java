
public abstract class ChessFigure {
	
	public final static int WHITE = 0;
	public final static int BLACK = 1;
	
	public final static int PAWN = 1;
	public final static int ROOK = 2;

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
	
	public boolean onPosition(int pos_x, int pos_y){
		if (this.pos_x == pos_x && this.pos_y == pos_y)
			return true;
		else return false;
	}
	
	public void printFigure() {
		System.out.print("["+ rank + "-" + color + "]");	
	}
	
	public void setPosition(int pos_x, int pos_y) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
	}
	
	
	public abstract boolean canMove(int pos_x, int pos_y);
	
}

final class Pawn extends ChessFigure{
	
	public Pawn(int color, int pos_x, int pos_y) {
		super(PAWN, color, pos_x, pos_y);
	}
	
	public boolean canMove(int pos_x, int pos_y) {
		if (color == WHITE) {
			if ((pos_x == this.pos_x) && ((pos_y == this.pos_y + 1)|| ((pos_y == this.pos_y + 2)&&(this.pos_y == 2))) && (pos_y < 9))
				return true;
			else return false;
			}
		else{
			if ((pos_x == this.pos_x) && ((pos_y == this.pos_y - 1)|| ((pos_y == this.pos_y - 2)&&(this.pos_y == 7))) && (pos_y > 0))
				return true;
			else return false;
		}
	}
	
}