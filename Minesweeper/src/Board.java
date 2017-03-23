import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class Board {
	private int rows;
	private int cols;
	private Cell[][] board;

	private static final double DEFAULT_DENSITY = 0.15;

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
			board = grid;
		} else {
			throw new IOException();
		}
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
		// TO FINISH
		// Implement recursively for adjacent blank cells
	}

	public void markCell(int row, int col) {
		// TO FINISH
		// Need to figure out what level all checking is going to happen
		Cell target = board[row][col];
		if (!target.isOpened()) {
			// Do things?
		}

	}

	public void save(File outFile) throws IOException{
		String header = String.format("%s %s\n", rows, cols);
		String body = this.toString();

		FileWriter output = new FileWriter(outFile);
		PrintWriter writer = new PrintWriter(output);

		writer.print(header);
		writer.print(body);
	}
}