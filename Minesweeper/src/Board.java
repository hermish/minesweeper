import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Board {

	private static final double DEFAULT_DENSITY = 0.1;
	private enum State {
		WON, ONGOING, LOST
	}

	private int rows;
	private int cols;
	private Cell[][] board;
	private int remaining;
	private State gameState = State.ONGOING;

	public Board(int width, int length) {
		this(width, length, DEFAULT_DENSITY);
	}

	public Board(int width, int length, double density) {
		Cell[][] grid = new Cell[width][length];
		rows = width;
		cols = length;
		board = grid;
		
		fillBlankCells(board);
		placeMines(board, density);
		generateNumbers(board);

		remaining = countUnopenedNormals(board);
	}

	public Board(File inputFile) throws Exception {
		Cell[][] grid = readBoard(inputFile);

		if (validateBoard(grid)) {
			rows = grid.length;
			cols = grid[0].length;
			board = grid;
			remaining = countUnopenedNormals(grid);

		} else {
			throw new IOException("Invalid file format!");
		}
	}

	private static Cell[][] readBoard(File inputFile) throws Exception {
		Scanner inFile = new Scanner(inputFile);

		String line = inFile.nextLine();
		String[] sizes = line.split(" ");
		int rows = Integer.parseInt(sizes[0]);
		int cols = Integer.parseInt(sizes[1]);
		Cell[][] grid = new Cell[rows][cols];

		for (int row = 0; row < rows; row++) {
			line = inFile.nextLine();
			for (int col = 0; col < cols; col++) {
				char code = line.charAt(col);
				grid[row][col].parse(code);
			}
		}

		inFile.close();
		return grid;
	}

	private static void fillBlankCells(Cell[][] grid) {
		for (int row=0;row<grid.length;row++) {
			for (int col=0; col<grid[row].length;col++) {
				// Default constructor called
				gird[row][col] = new Cell();
			}
		}
	}

	private static void placeMines(Cell[][] grid, double density) {
		int width = grid.length;
		int length = grid[0].length;
		int area = length * width;

		int mines = (int) density * area;
		Random rand = new Random();

		// Note: This is a pretty bad algorithm, randomly distributed
		// mines will result in very few clusters (revisit later)
		for (int num = 0; num < mines; num++) {
			int row = rand.nextInt(width + 1);
			int col = rand.nextInt(length + 1);
			grid[row][col].setMine();
		}
	}

	private static ArrayList<Cell> surrondingSquares(Cell[][] grid,
		int row, int col) {
		int width = grid.length;
		int length = grid[0].length;
		int[] offsets = {-1, 1, 0};
		int targetRow, targetCol;

		ArrayList<Cell> surroundings = new ArrayList<Cell>();

		for (int deltaX : offsets) {
			for (int deltaY : offsets) {
				targetRow = row + deltaX;
				targetCol = col + deltaY;

				if (targetRow >= 0 && targetRow < width
					&& targetCol >= 0 && targetCol < length) {
					surroundings.add(grid[targetRow][targetCol]);
				}
			}
		}

		surroundings.remove(surroundings.size() - 1);
		return surroundings;
	}

	private static void generateNumbers(Cell[][] grid) {
		int width = grid.length;
		int length = grid[0].length;

		for (int row = 0; row < width; row++) {
			for (int col = 0; col < length; col++) {
				if (grid[row][col].isMine()) {
					for (Cell square : surrondingSquares(grid, row, col)) {
						square.increment();
					}
				}
			}
		}
	}

	private static int countUnopenedNormals(Cell[][] grid) {
		int count = 0;

		for (Cell[] row : grid) {
			for (Cell square : row) {
				if (square.isUnopened() && square.isNormal()) {
					count++;
				}
			}
		}

		return count;
	}

	private static int countSurroundingMines(Cell[][] grid,
		int row, int col) {

		int count = 0;
		for (Cell square : surrondingSquares(grid, row, col)) {
			if (square.isMine()) {
				count++;
			}
		}
		return count;
	}

	private static boolean validateBoard(Cell[][] grid) {
		int rows = grid.length;
		int cols = grid[0].length;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Cell square = grid[row][col];
				if (square.isMine() && square.isOpened())
					return false;
				int count = countSurroundingMines(grid, row, col);
				if (square.getNumber() != count)
					return false;
			}
		}

		int unopenedNormals = countUnopenedNormals(grid);
		if (unopenedNormals == 0)
			return false;
		return true;
	}

	private String toCharGrid(boolean readable) {
		char[][] characterMatrix = new char[rows][cols];
		String[] rowDisplays = new String[rows];

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (readable) {
					characterMatrix[row][col] = board[row][col].toDisplay();
				} else {
					characterMatrix[row][col] = board[row][col].toChar();
				}
			}
		}

		for (int row = 0; row < rows; row++) {
			rowDisplays[row] = new String(characterMatrix[row]);
		}

		String output = String.join("\n", rowDisplays);
		return output;
	}

	private void safeCheck(int row, int col) {
		Cell target = board[row][col];

		if (areValidIndicies(row, col)
			&& target.isUnopened() && target.isNormal()) {
			// Note this method should clear the marking too!
			target.open();
			remaining--;
			int value = target.getNumber();
			if (value == 0) {
				safeCheck(row - 1, col);
				safeCheck(row, col - 1);
				safeCheck(row + 1, col);
				safeCheck(row, col + 1);
			}
		}
	}

	public int getState() {
		switch (gameState) {
			case WON:
				return 1;
			case ONGOING:
				return 0;
			case LOST:
				return -1;
			default:
				return 0;
		}
	}

	public boolean areValidIndicies(int row, int col) {
		return row >= 0 && row < rows
			&& col >= 0 && col < cols;
	}

	@Override
	public String toString() {
		return toCharGrid(true);
	}

	public int checkCell(int row, int col) {
		Cell target = board[row][col];

		if (target.isUnopened()) {
			if (target.isNormal()) {
				safeCheck(row, col);
				if (remaining == 0) {
					gameState = State.WON;
				}
			} else {
				gameState = State.LOST;
			}
		}

		return getState();
	}

	public boolean markCell(int row, int col) {
		Cell target = board[row][col];

		if (target.isUnopened()) {
			target.mark();
			return true;
		}
		return false;
	}

	public void save(File outFile) throws IOException {
		// Similar to toString method
		String header = String.format("%s %s\n", rows, cols);
		String body = toCharGrid(false);

		FileWriter output = new FileWriter(outFile);
		PrintWriter writer = new PrintWriter(output);

		writer.print(header);
		writer.print(body);
	}
}
