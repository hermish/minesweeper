
public class Cell {
	
	//Letting x represent mines, f represent flagged cells for toDisplay

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
		int result = (int) code;
		displayMines = result/10;
		int remainder = result % 10;
		
		if ((remainder/4)==1){
			isMine = true;
		}
		else{
			isMine=false;
		}
		
		remainder/=2;
		if ((remainder/2)==1){
			isOpen = true;
		}
		else{
			isOpen = false;
		}
		remainder/=2;
		if (remainder==1){
			isFlag=true;
		}
		else{
			isFlag=false;
		}	
	}

	public char toDisplay() {
		if(!isOpen) {
			if (isFlag) {
				return '?';
			} else {
				return '*';
			}
		} else {
			if (isMine) {
				return 'x';
			} else {
				return (char) ('0' + displayMines);
			}
		}
	}

	public char toChar() {
		int mine = (isMine) ? 1 : 0;
		int open = (isOpen) ? 1 : 0;
		int flag = (isFlag) ? 1 : 0;
		
		int result = (mine*4+open*2+flag*1)*10+displayMines;
		return (char) result;
		
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
