
public class Tester {
	
	public static void main(String args[]) {
		
		ChessBoard cb = new ChessBoard();
		cb.init();
		cb.drawBoard();
		
		cb.playRandom();
	}
}
