package Connect4;

import java.util.Scanner;

import Connect4.Constants.PlayerType;

public class GameRunner {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		GameBoard gameBoard = new GameBoard();
		gameBoard.displayBoard();
				
		Scanner scanner = new Scanner(System.in);
		
		PlayerType player1 = PlayerType.USER;
		PlayerType player2 = PlayerType.BOT;
		
		Agent agent = new Agent(player1, player2);
		
		while(!gameBoard.checkIfAllMovesPlayed()) {
			
			boolean validMoveMade = false;
			int move = 0;

			while(!validMoveMade) {
				System.out.print("Enter move: ");
				move = scanner.nextInt() - 1 ;
				validMoveMade = gameBoard.checkIfValid(move);
			}
			gameBoard.makeMove(move,player1);
			
			if (gameBoard.checkIfWon(player1)) {
				System.out.println("Game Won By "+player1);
				break;
			}
			
			move = agent.makeMove(gameBoard);
			
			gameBoard.makeMove(move, player2);
			System.out.println("BOT: "+(move + 1));
			if (gameBoard.checkIfWon(player2)) {
				System.out.println("Game Won By "+player2);
				break;
			}
			
		}
		
	}
}
