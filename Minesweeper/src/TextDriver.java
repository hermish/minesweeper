import java.util.Scanner;

public class TextDriver {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.print("Enter rows: ");
		int rows = input.nextInt();
		System.out.print("Enter columns: ");
		int cols = input.nextInt();

		Board game = new Board(rows, cols);
		int code, row, col, action;

		while(game.getState() == 0) {
			System.out.println(game);
			System.out.println();

			System.out.print("Select square: ");
			code = input.nextInt();
			row = code / 100;
			col = code % 100;

			if (game.areValidIndicies(row, col)) {
				System.out.print("Enter action: ");
				action = input.nextInt();
				// Performs command below!
				if(!act(game, row, col, action)) {
					System.out.println("Enter a valid command next time.");
				}
			} else {
				System.out.println("Enter a valid square next time.");
			}
			
			System.out.println();
		}

		input.close();
	}

	public static boolean act(Board game, int row, int col, int action) {
		switch (action) {
			case 0:
				game.checkCell(row, col);
				return true;
			case 1:
				game.markCell(row, col);
				return true;
			default:
				return false;
		}
	}
}
