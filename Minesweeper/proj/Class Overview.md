# Class Overview

## Cell
1. Variables
- private boolean isMine
- private boolean isFlag
- private boolean isOpen
- private int displayMines
- private int row
- private int col

2. Methods
- public Cell(int row, int col)

- public void parse(char code)
- public char toChar()
- public char toDisplay()

- public boolean isUnopened()
- public boolean isOpened()
- public boolean isMine()
- public boolean isNormal()
- public int getNumber()
- public int getRow()
- public int getColumn()

- public void open()
- public void mark()
- public void setMine()
- public void increment()

## Board
1. Variables
- private static final double DEFAULT_DENSITY
- private enum State

- private int rows
- private int cols
- private Cell[][] board
- private int remaining
- private int gameState

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

- private String toCharGrid(boolean readable)
- private void safeCheck(int row, int col)

- public int getState()
- public Cell[][] getBoard()
- public boolean areValidIndicies(int row, int col)
- public String toString()
- public int checkCell(int row, int col)
- public boolean markCell(int row, int col)
- public void save(File outFile)

## Screen
1. Variables
- private static final double BUTTON_WIDTH = 150
- private static final double BUTTON_HEIGHT = 150
- private static int rows
- private static int cols
- private static Board board=new Board(rows,cols)

2. Methods
- public static void main(String[] args)

- public void start(Stage primaryStage)

- private void setBoard(Stage primaryStage)
- private void createBoard()
- private void revealCell(int row, int col)
- private void markCell(int row, int col)



 
