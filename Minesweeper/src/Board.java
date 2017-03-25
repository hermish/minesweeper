import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class Board {

	private static final double DEFAULT_DENSITY = 0.1;

	private int rows;
	private int cols;
	private Cell[][] board;
	private int remaining;
	private int gameState = 0;

	public Board(int width, int length) {
		this(width, length, DEFAULT_DENSITY);
	}

	public Board(int width, int length, double density) {
		Cell[][] grid = new Cell[width][length];
		fillBlankCells(grid);
		placeMines(grid, density);
		generateNumbers(grid);

		rows = width;
		cols = length;
		board = grid;
		remaining = countUnopenedNormals(grid);
	}

	public Board(File inputFile) throws Exception{
		Scanner inFile = new Scanner(inputFile);
		
		String line = inFile.nextLine();
		String[] sizes = line.split(" ");
		int width = Integer.parseInt(sizes[0]);
		int length = Integer.parseInt(sizes[1]);
		Cell[][] grid = new Cell[width][length];

		for (int row = 0; row < width; row++) {
			line = inFile.nextLine();
			for (int col = 0; col < length; col++) {
				char code = line.charAt(col);
				grid[row][col] = Cell.parseCell(code);
				// grid[row][col] = new Cell(code);
				// if implemented as a constructor
			}
		}

		inFile.close();

		if (isValidBoard(grid)) {
			rows = width;
			cols = length;
			remaining = countUnopenedNormals(grid);
			board = grid;

		} else {
			throw new IOException("Invalid file format!");
		}
	}

	private static void fillBlankCells(Cell[][] grid) {
		for (Cell[] row : grid) {
			for (Cell square : row) {
				// Default constructor called
				square = new Cell();
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

	private static boolean isValidMine(Cell[][] grid, int row, int col) {
		int width = grid.length;
		int length = grid[0].length;

		return row >= 0 && row < width && 
			col >= 0 && col < length &&
			grid[row][col].isMine();
	}

	private static void validIncrement(Cell[][] grid, int row, int col) {
		int width = grid.length;
		int length = grid[0].length;

		if (row >= 0 && row < width &&
			col >= 0 && col < length) {
			// && grid[row][col].isNormal() increments only non-mines too!
			grid[row][col].increment();
		}
	}

	private static void incrementSurrounding(Cell[][] grid, int row, int col) {
		validIncrement(grid, row - 1, col);
		validIncrement(grid, row, col - 1);
		validIncrement(grid, row + 1, col);
		validIncrement(grid, row, col + 1);
		validIncrement(grid, row - 1, col + 1);
		validIncrement(grid, row + 1, col - 1);
		validIncrement(grid, row - 1, col - 1);
		validIncrement(grid, row + 1, col + 1);
	}

	private static void generateNumbers(Cell[][] grid) {
		int width = grid.length;
		int length = grid[0].length;

		for (int row = 0; row < width; row++) {
			for (int col = 0; col < length; col++) {
				if (grid[row][col].isMine()) {
					incrementSurrounding(grid, row, col);
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

	private static int countSurroundingMines(Cell[][] grid, int row, int col) {
		int count = 0;

		if (isValidMine(grid, row - 1, col))
			count++;
		if (isValidMine(grid, row, col - 1))
			count++;
		if (isValidMine(grid, row + 1, col))
			count++;
		if (isValidMine(grid, row, col + 1))
			count++;
		if (isValidMine(grid, row - 1, col + 1))
			count++;
		if (isValidMine(grid, row + 1, col - 1))
			count++;
		if (isValidMine(grid, row - 1, col - 1))
			count++;
		if (isValidMine(grid, row + 1, col + 1))
			count++;

		return count;
	}

	private static boolean isValidBoard(Cell[][] grid) {
		int width = grid.length;
		int length = grid[0].length;
		int count = 0;

		for (int row = 0; row < width; row++) {
			for (int col = 0; col < length; col++) {
				Cell square = grid[row][col];

				if (square.isMine() && square.isOpened())
					return false;
				int surroundMines = countSurroundingMines(grid, row, col);
				if (square.getNumber() != count)
					return false;
			}
		}

		int unopenedNormals = countUnopenedNormals(grid);
		if (unopenedNormals == 0)
			return false;
		return true;
	}

	private void safeCheck(int row, int col) {
		Cell target = board[row][col];

		if (areValidIndicies(row, col) && target.isUnopened() && target.isNormal()) {
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

	public boolean isOn() {
		return gameState == 0;
	}

	public boolean isWon() {
		return gameState == 1;
	}

	public boolean isLost() {
		return gameState == -1;
	}

	public boolean areValidIndicies(int row, int col) {
		return row < rows && col < cols;
	}

	@Override
	public String toString() {
		char[][] characterMatrix = new char[rows][cols];
		String[] rowDisplays = new String[rows];

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				characterMatrix[row][col] = board[row][col].toDisplay();
			}
		}

		for (int row = 0; row < rows; row++) {
			rowDisplays[row] = new String(characterMatrix[row]);
		}

		String output = String.join("\n", rowDisplays);
		return output;
	}

	public int checkCell(int row, int col) {
		Cell target = board[row][col];
		
		if (target.isUnopened()) {
			if (target.isNormal()) {
				safeCheck(row, col);
				if (remaining == 0) {
					gameState = 1;
				}
			} else {
				gameState = -1;
			}
		}

		return gameState;
	}

	public void markCell(int row, int col) {
		Cell target = board[row][col];
		
		if (target.isUnopened()) {
			target.mark();
		}

	}

	public void save(File outFile) throws IOException{
		// Similar to toString method
		String header = String.format("%s %s\n", rows, cols); // Difference 1
		
		char[][] charMatrix = new char[rows][cols];
		String[] rowDisplays = new String[rows];

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				charMatrix[row][col] = board[row][col].toChar(); // Difference 2
			}
		}

		for (int row = 0; row < rows; row++) {
			rowDisplays[row] = new String(charMatrix[row]);
		}

		String body = String.join("\n", rowDisplays);

		FileWriter output = new FileWriter(outFile);
		PrintWriter writer = new PrintWriter(output);

		writer.print(header);
		writer.print(body);
	}
}