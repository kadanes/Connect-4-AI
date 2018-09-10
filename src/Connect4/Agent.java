package Connect4;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import Connect4.Constants.PlayerType;

public class Agent {
	
	private PlayerType opponentType,selfType; 
	
	public Agent(PlayerType opponentType, PlayerType selfType) {
		this.opponentType = opponentType;
		this.selfType = selfType;
	}
	
	int makeMove(GameBoard board) {
		
		Set<Integer> selfPossibleMoves = new HashSet<>();
		Set<Integer> opponentPossibleMoves = new HashSet<>();

		
		//Check for win
		selfPossibleMoves = board.checkForPattern(3, selfType);
		opponentPossibleMoves = board.checkForPattern(3,opponentType);
		Set<Integer> commonMoves;
		
		//if (selfPossibleMoves.size() > 0 ) {
			for(Integer move: selfPossibleMoves) {
				return move;
			}	
		//}
		System.out.println("OPPONENT SIZE 3");
		//if (opponentPossibleMoves.size() > 0 ) {
			for(Integer move: opponentPossibleMoves) {
				System.out.println("Found size 3 move");
				return move;
		//	}	
		}
		
		selfPossibleMoves = board.checkForPattern(2, selfType);
		opponentPossibleMoves = board.checkForPattern(2,opponentType);

		commonMoves = new HashSet<Integer>(selfPossibleMoves);
		commonMoves.retainAll(opponentPossibleMoves);
		
		for (Integer move: commonMoves) {
			return move;
		}

		for(Integer move: selfPossibleMoves) {
			return move;
		}
		
		selfPossibleMoves = board.checkForPattern(1, selfType);
		selfPossibleMoves = new TreeSet<Integer>(selfPossibleMoves);
		
		for(Integer move: selfPossibleMoves) {
			return move;
		}
		
		return board.getRandomMove();
	}
}
