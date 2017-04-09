import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Screen extends Application{
	
	private static final double BUTTON_WIDTH = 150;
	private static final double BUTTON_HEIGHT = 150;
	private static Board board = new Board(7,7);
	private int tempRow = 0;
	private int tempCol = 0;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {				
		GridPane gridPane = new GridPane();		
		Scene mySceneGraph = new Scene(gridPane,500,500);	
		gridPane.setAlignment(Pos.TOP_CENTER);
		
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
					tempRow=i;
					tempCol=j;
					board.getBoard()[i][j].setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
					board.getBoard()[i][j].setStyle("-fx-base: #C0C0C0;");
					board.getBoard()[i][j].setText(Character.toString(board.getBoard()[i][j].toDisplay()));
					board.getBoard()[i][j].setOnAction(new EventHandler<ActionEvent>() {
			            @Override
			            public void handle(ActionEvent event) {
			            	Cell eventCell = (Cell)event.getSource();
			            	board.checkCell(eventCell.getRow(),eventCell.getCol());
			            	eventCell.setText(Character.toString(eventCell.toDisplay()));
			            }
			        });
			}
		}
		
	
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				gridPane.add(board.getBoard()[i][j], j,i+1);
				gridPane.setHgrow(board.getBoard()[i][j], Priority.SOMETIMES);
			}
		}
		
		
		

		primaryStage.setScene(mySceneGraph);
		primaryStage.show();		
	}

	
}
