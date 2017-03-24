import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class Board {
	private static final double DEFAULT_DENSITY = 0.1;

	private int rows;
	private int cols;
	private Cell[][] board;
	private int remaining;
	private int gameState = 0;

	public Board(int size) {
		this(size, DEFAULT_DENSITY);
	}

	public Board(int size, double density) {
		// TODO
	}

	public Board(File inputFile) throws Exception{
		Scanner inFile = new Scanner(inputFile);
		
		String line = inFile.nextLine();
		String[] sizes = line.split(" ");
		int width = Integer.parseInt(sizes[0]);
		int length = Integer.parseInt(sizes[1]);
		Cell[][] grid = new Cell[width][length];
		int row = 0;

		for (int r = 0; r < width; r++) {
			line = inFile.nextLine();
			for (int c = 0; c < length; c++) {
				char code = line.charAt(c);
				grid[r][c] = Cell.parseCell(code);
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

	private void placeMines() {
		// TODO
	}

	private void generateNumbers() {
		// TODO
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
				characterMatrix[row][col] = board[row][col].toChar();
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
		String body = toString();

		FileWriter output = new FileWriter(outFile);
		PrintWriter writer = new PrintWriter(output);

		writer.print(header);
		writer.print(body);
	}
}