# Class Overview

## Board
1. Variables
- private int rows
- private int cols
- private Cell[][] board
- private int remaining
- private int gameState = 0

2. Methods
- public Board(int width, int length)
- public Board(int width, int length, double density)
- public Board(File inputFile)

- private static Cell[][] readBoard(File inputFile)
- private static void fillBlankCells(Cell[][] grid)
- private static void placeMines(Cell[][] grid, double density)
- private static ArrayList<Cell> surrondingSquares(Cell[][] grid, int row, int col)
- private static void generateNumbers(Cell[][] grid)
- private static int countUnopenedNormals(Cell[][] grid)
- private static int countSurroundingMines(Cell[][] grid, int row, int col)
- private static boolean validateBoard(Cell[][] grid)

- private void safeCheck(int row, int col)

- public int getState()
- public boolean areValidIndicies(int row, int col)
- public String toString()
- public int checkCell(int row, int col)
- public boolean markCell(int row, int col)
- public void save(File outFile)

## Cell
1. Variables
2. Methods