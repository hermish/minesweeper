package amy_minesweeper;

public class Cell {
	
	//Letting x represent mines, f represent flagged cells for toDisplay
	//Letting 0 represent flagged mines, f represent flagged non-mines, X represent unopened unflagged mines, x represent unopened unflagged non-mines, O represent opened mines
	
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
	
	public void parse(char code) {
		
		
	}

	public char toChar() {
		char 
		if (isMine){
			if (isOpen){
				if (isFlag){
					
				}
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
