
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
	int figureID;//not sure if needed
	
	public ChessFigure(int figureID, int rank, int color, int pos_x, int pos_y) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.rank = rank;
		this.color = color;
		this.figureID = figureID;
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
	
	public Pawn(int figureID, int color, int pos_x, int pos_y) {
		super(figureID, PAWN, color, pos_x, pos_y);
	}

	public int[][] getPositionList(){
		int[][] posList = new int[64][2];
		int[][] cells = ChessBoard.getBoardCells();
		
		if (color == WHITE) {
			if (pos_y == 1) {
				if ((cells[pos_x][pos_y + 1] < 0)&&(cells[pos_x][pos_y + 2] < 0)) {	
					posList[0][0] = pos_x;
					posList[0][1] = pos_y + 1;
					posList[1][0] = pos_x;
					posList[1][1] = pos_y + 2;
				}
				else if ((cells[pos_x][pos_y + 1] < 0)&&(cells[pos_x][pos_y + 2] >= 0)) {
					posList[0][0] = pos_x;
					posList[0][1] = pos_y + 1;
				}
			}
			else if (pos_y < 7) {
				if (cells[pos_x][pos_y + 1] < 0) {
					posList[0][0] = pos_x;
					posList[0][1] = pos_y + 1;
				}
			}	
		}
		else if (color == BLACK) {
			if (pos_y == 6) {
				if ((cells[pos_x][pos_y - 1] < 0)&&(cells[pos_x][pos_y - 2] < 0)) {	
					posList[0][0] = pos_x;
					posList[0][1] = pos_y - 1;
					posList[1][0] = pos_x;
					posList[1][1] = pos_y - 2;
				}
				else if ((cells[pos_x][pos_y - 1] < 0)&&(cells[pos_x][pos_y - 2] >= 0)) {
					posList[0][0] = pos_x;
					posList[0][1] = pos_y - 1;
				}
			}
			else if (pos_y > 0)  {
				if (cells[pos_x][pos_y - 1] < 0) {
					posList[0][0] = pos_x;
					posList[0][1] = pos_y - 1;
				}
			}	
		}	
		return posList;
	}	
	
}//Pawn

final class Bishop extends ChessFigure{
	
	public Bishop(int figureID, int color, int pos_x, int pos_y) {
		super(figureID, BISHOP, color, pos_x, pos_y);
	}

	public int[][] getPositionList(){
		int[][] posList = new int[64][2];
		int[][] cells = ChessBoard.getBoardCells();
		
		boolean canMove = true;
		int i;
		int amount = 0;
		
		//N-W
		i = 1;
		canMove = true;
		while (canMove) {
			if ((pos_x + i <= 7)&&(pos_y - i >= 0))
				if ((cells[pos_x + i][pos_y - i] < 0)) {
					posList[amount][0] = pos_x + i;
					posList[amount][1] = pos_y - i;	
					i++;
					amount++;
				}
				else {
					canMove = false;
				}
			else canMove = false;
		}
		//S-W
		i = 1;
		canMove = true;
		while (canMove) {
			if ((pos_x + i <= 7)&&(pos_y + i <= 7))
				if ((cells[pos_x + i][pos_y + i] < 0)) {
					posList[amount][0] = pos_x + i;
					posList[amount][1] = pos_y + i;	
					i++;
					amount++;
				}
				else {
					canMove = false;
				}
			else canMove = false;
		}
		//S-E
		i = 1;
		canMove = true;
		while (canMove) {
			if((pos_x - i >= 0)&&(pos_y + i <= 7))
				if ((cells[pos_x - i][pos_y + i] < 0)) {
					posList[amount][0] = pos_x - i;
					posList[amount][1] = pos_y + i;	
					i++;
					amount++;
				}
				else {
					canMove = false;
				}
			else canMove = false;
		}
		//N-E
		i = 1;
		canMove = true;
		while (canMove) {
			if((pos_x - i >= 0)&&(pos_y - i >= 0))
				if ((cells[pos_x - i][pos_y - i] < 0)) {
					posList[amount][0] = pos_x - i;
					posList[amount][1] = pos_y - i;	
					i++;
					amount++;
				}
				else {
					canMove = false;
				}
			else canMove = false;
		}
		return posList;
	}	
	
}//Bishop
