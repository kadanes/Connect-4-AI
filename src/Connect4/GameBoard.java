package Connect4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.xml.soap.Text;

import Connect4.Constants.PlayerType;

public class GameBoard {
	
	private final int maxCols = 6;
	private final int maxRows = 7;
	private int movesMade = 0;
	
	private Chip[][] gameBoard = new Chip[maxRows][maxCols];
	
	public GameBoard() {
		for(int row = 0 ; row < maxRows; row += 1 ) {
			for (int col = 0; col < maxCols; col+= 1) {
				gameBoard[row][col] = new Chip(maxRows - row - 1,col,PlayerType.NONE);
			}
		}
	}
	
	void printTopGrid(int length) {
		insertTabSpace();
		System.out.print("╔");
		for(int col = 1; col <  length; col += 1) {
			System.out.print("═╦");
		}
		System.out.print("═╗");
		System.out.println();
	}
	
	void printTopBox(int length) {
		insertTabSpace();
		System.out.print("╔");
		for(int col = 1; col <  length; col += 1) {
			System.out.print("══");
		}
		System.out.print("═╗");
		System.out.println();
	}
	
	void printBottomGrid(int length) {
		insertTabSpace();
		System.out.print("╚");
		for(int col = 1; col <  length; col += 1) {
			System.out.print("═╩");
		}
		System.out.print("═╝");
		System.out.println();
	}
	
	void printBottomBox(int length) {
		insertTabSpace();
		System.out.print("╚");
		for(int col = 1; col <  length; col += 1) {
			System.out.print("══");
		}
		System.out.print("═╝");
		System.out.println();
	}
	
	void printTitle() {
		printTopBox(maxCols);
		insertTabSpace();
		System.out.print("║");
		System.out.print(" Connect 4 ");
		System.out.println("║");
		insertTabSpace();
		System.out.print("║");
		String movesLabel = "Move "+movesMade+"/"+((maxCols*maxRows));
		int remainingChars = 2 * (maxCols-1) + 1- movesLabel.length();
		int leftSpace = (int)remainingChars/2;
		int rightSpace = remainingChars - leftSpace;
		
		if (leftSpace == 0) { 
			movesLabel = "Move  "+movesMade+"/"+((maxCols*maxRows));
			System.out.print(movesLabel);
		} else {
			for (int i = 1; i <= leftSpace; i+= 1 ) {
				System.out.print(" ");
			}
			System.out.print(movesLabel);
			
			for (int i = 1; i <= rightSpace; i+= 1 ) {
				System.out.print(" ");
			}
		}
		
		
		System.out.println("║");
		printBottomBox(maxCols);
	}
	
	void insertTabSpace() {
		System.out.print("					");
	}
	void displayBoard() {
		//System.out.print("\033\143");
		
		System.out.print(Color.BLACK_BOLD_BRIGHT);
		
		printTitle();
		
		printTopGrid(maxCols);
		
		for (int row = maxRows - 1; row >= 0; row -= 1 ) {
			Chip[] chips = gameBoard[row];
			insertTabSpace();
			System.out.print("║");
			for (Chip chip: chips) {
				
				chip.switchChipColor();
				System.out.print(chip.getChipSymbol());
				System.out.print(Color.BLACK_BOLD_BRIGHT);
				System.out.print("║");
				
			} 
			System.out.println();
		}
		
		printBottomGrid(maxCols);
		
		printTopGrid(maxCols);
		insertTabSpace();
		System.out.print("║");
		for (int ind = 1; ind <= maxCols; ind += 1) {
			System.out.print(ind+"║");
		}
		System.out.println();
		
		printBottomGrid(maxCols);
		
		Chip bot = new Chip(0, 0, PlayerType.BOT);
		Chip user = new Chip(0, 0, PlayerType.USER);
		Chip none = new Chip();
		
		insertTabSpace();
		System.out.print(Color.RESET);
		System.out.print("BOT ");
		bot.switchChipColor();
		System.out.print(bot.getChipSymbol()+"  ");
		System.out.print(Color.RESET);
		System.out.print("USER ");
		user.switchChipColor();
		System.out.print(user.getChipSymbol());
		
		System.out.print(Color.RESET);
		System.out.println();
		
		
	}
	
	boolean makeMove(int col, PlayerType playerType) {
		
		int indexedCol = col;
		
		
		if (checkIfValid(indexedCol)) {
			
			int row = getInsertRowIndex(indexedCol);
			Chip chip = new Chip(row,indexedCol,playerType);
			gameBoard[row ][indexedCol] = chip;
			
			movesMade += 1;
			
			displayBoard();
			return true;
		}
		
		return false;
	}
	
	boolean checkIfValid(int col) {
		
		if (col > maxCols - 1 || col < 0) {
		
			return false;
		} else if (gameBoard[maxRows - 1][col].playerType != PlayerType.NONE) {
			return false;
		} else {
			return true;
		}
		
	}
	
	int getInsertRowIndex(int col) {
		for(int row = 0; row < maxRows; row += 1) {
			if (gameBoard[row][col].playerType == PlayerType.NONE) {
				return row;
			}
		}
		return 0;
	}
	
	Boolean checkIfRowEmpty(int row) {
		if (row < maxRows && row >= 0) {
			boolean isEmpty = true;
			
			for (Chip chip: gameBoard[row]) {
				if (chip.playerType != PlayerType.NONE) {
					isEmpty = false;
					break;
				}
			}
			return isEmpty;
		}
		return false;
	}
	
	Set<Integer> checkForPattern(int size, PlayerType type) {
		
		int rowCount = 0;
		int colCount = 0;
		int consecutiveCount = 0;

		Set<Integer> moves = new HashSet<>();
		
		//Horizontal
		int gameOverID = 100;
		

		for (Chip[] row: gameBoard) {
			
			consecutiveCount = 0;
			colCount = 0;
			
			for (Chip chip: row) {
								
				if (checkIfRowEmpty(rowCount)) {
					break;
				}
				
				if (chip.playerType == type ) {
					consecutiveCount += 1;
				
				} else {
					consecutiveCount = 0;
					
				}
				
				if (consecutiveCount == size) {
					
					System.out.println(rowCount+","+colCount);
					
					if (consecutiveCount == 4) {
						moves.add(gameOverID);
					}
					
					int beforCol = colCount - size;
					int afterCol = colCount + 1;
					
					if (checkIfValid(beforCol)  && getInsertRowIndex(beforCol) == rowCount) {
						moves.add(beforCol);
					}
					
					if (checkIfValid(afterCol)  && getInsertRowIndex(afterCol) == rowCount ) {
						moves.add(afterCol);
					}					
				}				
				colCount += 1;
			}
			rowCount += 1;
		}
		//Vertical
			
		for (int col = 0; col < maxCols; col += 1) {
			
			consecutiveCount = 0;
			
			for (int row = 0; row < maxRows; row += 1) {
				
				if (gameBoard[row][col].playerType == type) {
					consecutiveCount += 1;
				} else {
					consecutiveCount = 0;
				}
				
				if (consecutiveCount == size) {
					
					if (consecutiveCount == 4) {
						moves.add(gameOverID);
					}
					
					if (checkIfValid(col)) {
						moves.add(col);
					}
					
				}
			}
		}
		
		//Digonal
		//P - - -
		//- P - -
		//- - P -
		//-	- - -
		
		for (int row = 0; row < maxRows; row += 1) {
			consecutiveCount = 0;
			colCount = 0;
			rowCount = row;
			while(true) {
				
				
				if (rowCount < 0 || colCount >= maxCols) {
					break;
				}
				
				if(gameBoard[rowCount][colCount].playerType == type) {
					consecutiveCount += 1;
				} else {
					consecutiveCount = 0;
				}
				
				if (consecutiveCount == size) {
					
					if (consecutiveCount == 4) {
						moves.add(gameOverID);
					}
					
					int afterX = colCount + 1, afterY = rowCount - 1;
					int beforeX = colCount - size, beforeY = rowCount + size;
										
					if (checkIfValid(afterX) && getInsertRowIndex(afterX) == afterY) {
						moves.add(afterX);
					}
					
					if (checkIfValid(beforeX) && getInsertRowIndex(beforeX) == beforeY) {
						moves.add(beforeX);
					}
					
				}
				rowCount -= 1;
				colCount += 1;
				
			}
			
		}
		
		for (int col = 0; col < maxCols; col += 1) {
			consecutiveCount = 0;
			colCount = col;
			rowCount = maxRows - 1;
			while(true) {
				
				
				if (rowCount < 0 || colCount >= maxCols) {
					break;
				}
								
				if(gameBoard[rowCount][colCount].playerType == type) {
					consecutiveCount += 1;
				} else {
					consecutiveCount = 0;
				}
				
				if (consecutiveCount == size) {
					
					if (consecutiveCount == 4) {
						moves.add(gameOverID);
					}
										
					int afterX = colCount + 1, afterY = rowCount - 1;
					int beforeX = colCount - size, beforeY = rowCount + size;
									
					if (checkIfValid(afterX) && getInsertRowIndex(afterX) == afterY) {
						moves.add(afterX);
					}
					
					if (checkIfValid(beforeX) && getInsertRowIndex(beforeX) == beforeY) {
						moves.add(beforeX);
					}
				}
				rowCount -= 1;
				colCount += 1;

			}
			
		}
		
		
		//Digonal
		//- - P -
		//- P - -
		//P - - -
		//-	- - -
		
		for (int row = maxRows - 1; row >= 0; row -= 1) {
			consecutiveCount = 0;
			colCount = 0;
			rowCount = row;
			
			while(true) {
				
				if (rowCount >= maxRows || colCount >= maxCols) {
					break;
				}
				
				if(gameBoard[rowCount][colCount].playerType == type) {
					consecutiveCount += 1;
				} else {
					consecutiveCount = 0;
				}
				
				if (consecutiveCount == size) {
					
					if (consecutiveCount == 4) {
						moves.add(gameOverID);
					}
										
					int afterX = colCount + 1, afterY = rowCount - 1;
					int beforeX = colCount - size, beforeY = rowCount - size;
					
					if (checkIfValid(afterX) && getInsertRowIndex(afterX) == afterY) {
						moves.add(afterX);
					}
					
					if (checkIfValid(beforeX) && getInsertRowIndex(beforeX) == beforeY) {
						moves.add(beforeX);
					}
				}
				
				rowCount += 1;
				colCount += 1;
			}
			
		}
		

		for (int col = 1; col < maxCols; col += 1) {
			consecutiveCount = 0;
			colCount = col;
			rowCount = 0;
			
			while(true) {
				
				if (rowCount >= maxRows || colCount >= maxCols) {
					break;
				}
				
				if(gameBoard[rowCount][colCount].playerType == type) {
					consecutiveCount += 1;
				} else {
					consecutiveCount = 0;
				}
				
				if (consecutiveCount == size) {
					
					if (consecutiveCount == 4) {
						moves.add(gameOverID);
					}
										
					int afterX = colCount + 1, afterY = rowCount + 1;
					int beforeX = colCount - size, beforeY = rowCount - size;
					
					if (checkIfValid(afterX) && getInsertRowIndex(afterX) == afterY) {
						moves.add(afterX);
					}
					
					if (checkIfValid(beforeX) && getInsertRowIndex(beforeX) == beforeY) {
						moves.add(beforeX);
					}
				}
				rowCount += 1;
				colCount += 1;
			}	
		}
	
		return moves;			
		
	}
	
	boolean checkIfWon(PlayerType type) {
		Set<Integer> moves = checkForPattern(4, type);
		return moves.size() > 0; 
	}
	
	int getRandomMove() {
		
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>(); 
		
		for (int col = 0; col < maxCols; col += 1 ) {
			if (checkIfValid(col))
				possibleMoves.add(col);
		}
		
		int randomIndex = (int)(Math.random() * ((possibleMoves.size()) + 1));
		
		if (possibleMoves.size() > randomIndex) {
			return possibleMoves.get(randomIndex);
		} 
		
		return 0;
	}
	
	boolean checkIfAllMovesPlayed()  {
		return movesMade == maxCols * maxRows;
	}
}
