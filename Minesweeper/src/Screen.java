import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Screen extends Application{
	
	private static final double BUTTON_WIDTH = 150;
	private static final double BUTTON_HEIGHT = 150;
	private static int rows = 7;
	private static int cols = 7;
	private static Board board = new Board(rows,cols);
	
	public static void main(String[] args) {	
		launch(args);
	}

	// Note that this originally creates a gridPane for user input, then calls setBoard to create the gameboard
	@Override
	public void start(Stage primaryStage) throws Exception {				
		//Asking user for specifications
		GridPane gridPaneInit = new GridPane();	
		Scene initScreen = new Scene(gridPaneInit,400,200);
		Label gridRows = new Label ("Enter Rows:");
		Label gridCols = new Label ("Enter Columns:");
		GridPane.setHalignment(gridRows, HPos.CENTER);
		GridPane.setHalignment(gridCols, HPos.CENTER);
		GridPane.setValignment(gridRows, VPos.CENTER);
		GridPane.setValignment(gridCols, VPos.CENTER);
		TextField rowsField = new TextField();
		TextField colsField = new TextField();
		Button button = new Button("Create Grid");
		GridPane.setValignment(button, VPos.CENTER);

		
		ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(50);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(50);
	    gridPaneInit.getColumnConstraints().addAll(column1, column2);
	    
		RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(35);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(35);
	    RowConstraints row3 = new RowConstraints();
	    row2.setPercentHeight(30);
	    gridPaneInit.getRowConstraints().addAll(row1, row2, row3);
		
		//Styling
		gridRows.setFont(Font.font("Avenir", 24));
		gridRows.setTextFill(Color.LIGHTGREY);
		gridCols.setFont(Font.font("Avenir", 24));
		gridCols.setTextFill(Color.LIGHTGREY);
		rowsField.setFont(Font.font("Avenir", 16));
		colsField.setFont(Font.font("Avenir", 16));
		button.setFont(Font.font("Avenir", 16));
		
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String rowInput = rowsField.getText();
				String colInput = colsField.getText();
				try {
					rows = Integer.parseInt(rowInput);
					cols = Integer.parseInt(colInput);
				
				board = new Board(rows,cols);
				
				//Creating board based on user input
				setBoard(primaryStage);
				}
				catch (Exception e){
					System.out.println(e.getMessage());
				}
			}
		});
		
		gridPaneInit.setStyle("-fx-background-color: #A9A9A9;");
		gridPaneInit.add(gridRows, 0,0);
		gridPaneInit.add(gridCols, 0,1);
		gridPaneInit.add(rowsField,1,0);
		gridPaneInit.add(colsField,1,1);
		gridPaneInit.add(button,1,2,2,1);
		
		primaryStage.setScene(initScreen);
		primaryStage.show();		
		primaryStage.toFront();
	}
	
	//Creates GridPane for game board, sets scene of primaryStage to be the game board
	private void setBoard(Stage primaryStage){
		GridPane gridPaneGame = new GridPane();
		Scene gameBoard = new Scene(gridPaneGame,cols*30,(rows*30)+40);	
		gridPaneGame.setAlignment(Pos.TOP_CENTER);
		
		Button restart = new Button("Restart Game");
		restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				board = new Board(rows,cols);
				setBoard(primaryStage);
			}
		});
		Button quit = new Button("Quit Game");
		quit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event){
				System.exit(0);
			}
		});
		
		if (board.getState()==0){
			createBoard();
		}
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				gridPaneGame.add(board.getBoard()[i][j], j,i+1);
			}
		}
		
		restart.setMaxWidth(Double.MAX_VALUE);
		restart.setFont(Font.font("Avenir",14));
		quit.setMaxWidth(Double.MAX_VALUE);
		quit.setFont(Font.font("Avenir",14));
		gridPaneGame.setStyle("-fx-background-color: #A9A9A9;");
	    
		gridPaneGame.add(restart,0,rows+1);
		gridPaneGame.setColumnSpan(restart, cols/2);
		gridPaneGame.add(quit, cols-(cols/2), rows+1);
		gridPaneGame.setColumnSpan(quit, cols/2);
		
		primaryStage.setScene(gameBoard);
	}
	
	//This creates a board by initializing all the buttons in the private variable board
	private void createBoard(){
		
		if (board.getState()==1){
			System.out.println("You Won!");
		}
		else if (board.getState()==-1){
			System.out.println("Game Over :(");
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
					
				Cell cell = board.getBoard()[i][j];
					cell.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
					cell.setText(Character.toString(board.getBoard()[i][j].toDisplay()));
					cell.setFont(Font.font("Avenir"));
					if (cell.isOpened()){
						cell.setStyle("-fx-base:#A9A9A9");
						cell.setTextFill(Color.LIGHTGRAY);
						if (cell.isMine()){
							cell.setStyle("-fx-base:#ff4c4c");
						}
					}
					
					if (cell.isMarked()){
						cell.setStyle("-fx-base:#ffe5e5");
					}
					board.getBoard()[i][j].setOnMouseClicked(new EventHandler<MouseEvent>(){
						@Override
			            public void handle(MouseEvent event) {
			            	Cell eventCell = (Cell)event.getSource();
			            	if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
			            		if(((MouseEvent) event).getButton().equals(MouseButton.PRIMARY)){
						            revealCell(eventCell.getRow(),eventCell.getCol());
						            createBoard();
			            		}
			            		else if (((MouseEvent) event).getButton().equals(MouseButton.SECONDARY)){
			                        markCell(eventCell.getRow(),eventCell.getCol());
			                        createBoard();
			            		}
			            	}
			       
			            }
					});
			}
		}
	}

	//Reveals a cell if this is called (if a button is clicked)
	private void revealCell(int row, int col){
		if (board.areValidIndicies(row, col)){
			board.checkCell(row,col);
		}
	}
	
	//Marks a cell if this method is called (if a button is right clicked)
	private void markCell(int row, int col){
		if (board.areValidIndicies(row, col)){
			board.markCell(row,col);
		}
	}
	
}
