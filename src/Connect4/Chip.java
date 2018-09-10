package Connect4;

import Connect4.Color;
import Connect4.Constants.PlayerType;

public class Chip {

	public final int row,col;
	public final PlayerType playerType;
	
	
	public Chip(int row, int col, PlayerType playerType) {
		this.row = row;
		this.col = col;
		this.playerType = playerType;
	}
	
	public Chip() {
		this.row  = 0;
		this.col = 0;
		this.playerType = PlayerType.NONE;
	}
	
	void printChip() {
		System.out.print("("+row+","+col+","+playerType+")");
	}

	String getChipSymbol() {
		switch (playerType) {
		case USER:
			return "●";
			
		case BOT: 
			return "◍";
			
		case NONE: 
			return "○";
			
		default:
			return "E";
		}
	}
	
	void switchChipColor() {
		switch (playerType) {
		case USER:
			System.out.print(Color.RED_BRIGHT);
			break;
		case BOT: 
			System.out.print(Color.YELLOW_BRIGHT);
			break;
		case NONE: 
			System.out.print(Color.BLACK_BOLD_BRIGHT);
			break;
		default:
			System.out.print(Color.BLACK_BOLD_BRIGHT);
			break;
		}
	}
	
}
