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
		remaining = countUnopened(grid);
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
			remaining = countUnopened(grid);
			board = grid;

		} else {
			throw new IOException();
		}
	}

	private static boolean isValidBoard(Cell[][] grid) {
		// TODO
		return true;
	}

	private static void validIncrement(Cell[][] grid, int row, int col) {
		int width = grid.length;
		int length = grid[0].length;

		if (row >= 0 && row < width &&
			col >=0 && col < length &&
			grid[row][col].isNormal()) {
			grid[row][col].increment();
		}
	}

	private static void fillBlankCells(Cell[][] grid) {
		for (Cell[] row : grid) {
			for (Cell square : row) {
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

		for (int num = 0; num < mines; num++) {
			int row = rand.nextInt(width + 1);
			int col = rand.nextInt(length + 1);
			grid[row][col].setMine();
		}
	}

	private static void generateNumbers(Cell[][] grid) {
		int width = grid.length;
		int length = grid[0].length;

		for (int row = 0; row < width; row++) {
			for (int col = 0; col < length; col++) {
				if (grid[row][col].isMine()) {
					validIncrement(grid, row - 1, col);
					validIncrement(grid, row, col - 1);
					validIncrement(grid, row + 1, col);
					validIncrement(grid, row, col + 1);
					validIncrement(grid, row - 1, col + 1);
					validIncrement(grid, row + 1, col - 1);
					validIncrement(grid, row - 1, col - 1);
					validIncrement(grid, row + 1, col + 1);
				}
			}
		}
	}

	private static int countUnopened(Cell[][] grid) {
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

	private void safeCheck(int row, int col) {
		Cell target = board[row][col];

		if (areValidIndicies(row, col) && target.isUnopened() && target.isNormal()) {
			target.open();
			remaining--;
			// Note this method should clear the marking too!
			int value = target.getNumber();
			if (value == 0) {
				safeCheck(row - 1, col);
				safeCheck(row, col - 1);
				safeCheck(row + 1, col);
				safeCheck(row, col + 1);
			}
		}
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

	public void checkCell(int row, int col) {
		Cell target = board[row][col];
		
		if (target.isUnopened()) {
			if (target.isNormal()) {
				safeCheck(row, col);
			} else {
				gameState = -1;
			}
		}

		if (remaining == 0) {
			gameState = 1;
		}
	}

	public void markCell(int row, int col) {
		Cell target = board[row][col];
		
		if (target.isUnopened()) {
			target.mark();
		}

	}

	public void save(File outFile) throws IOException{
		String header = String.format("%s %s\n", rows, cols);
		
		char[][] charMatrix = new char[rows][cols];
		String[] rowDisplays = new String[rows];

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				charMatrix[row][col] = board[row][col].toChar();
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