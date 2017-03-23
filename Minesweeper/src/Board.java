public class Board {
	private int size;
	private Cell[][] board;

	public Board(int size) {
		// TODO
	}

	public Board(int size, double density) {
		// TODO
	}

	public Board(String filename) throws Exception {
		// TODO
	}

	private static Cell readCell(String input) throws Exception{
		// TODO
		return new Cell();
	}

	private static boolean isValidBoard(Cell[][] grid) {
		// TODO
		return true;
	}

	private void placeMines() {
		// TODO
	}

	private void generateNumbers() {
		// TODO
	}

	private boolean isMine(int row, int col) {
		// TODO
		return true;
	}

	@Override 
	public String toString() {
		// TODO
		return "";
	}

	public void checkCell(int row, int col) {
		// TODO
	}

	public void markCell(int row, int col) {
		// TODO
	}

	public void save(String filename) {
		// TODO
	}
}