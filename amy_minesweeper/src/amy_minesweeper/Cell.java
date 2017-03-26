package amy_minesweeper;

public class Cell {
	
	//Letting x represent mines, f represent flagged cells for toDisplay
	//Letting F represent flagged mines, f represent flagged non-mines, X represent unopened unflagged mines, x represent unopened unflagged non-mines, O represent opened mines
	
	private boolean isMine;
	private boolean isFlag;
	private boolean isOpen;
	private int displayMines;
	
	
	public Cell(){
		isMine = false;
		isOpen = false;
		isFlag = false;
		displayMines = 0;
	}
	
	public static Cell parseCell(char code) throws Exception{
		return new Cell();
	}

	public char toChar() {
		if (isOpen){
			if (isMine){
				return 'O';
			}
			return (char)displayMines;
		}
		
		else if (isFlag){
			if (isMine){
				return 'F';
			}
			return 'f';
		}
		
		if (isMine){
			return 'X';
		}
		return 'x';
	}

	public char toDisplay() {
		if (isOpen){
			if (isMine){
				return 'x';
			}
			return (char)displayMines;
		}
		else if (isFlag){
			return 'f';
		}
		return ' ';
			
		//note for later: displaying all mines after game over?
	}

	public boolean isUnopened() {
		return !isOpen;
	}

	public boolean isOpened() {
		return isOpen;
	}

	public boolean isMine() {
		return isMine;
	}

	public boolean isNormal() {
		return !isMine;
	}

	public void open() {
		isOpen = true;
	}

	public int getNumber() {
		return displayMines;
	}

	public void mark() {
		isFlag = true;
	}

	public void setMine() {
		isMine = true;
	}

	public void increment() {
		displayMines += 1;
	}
}
