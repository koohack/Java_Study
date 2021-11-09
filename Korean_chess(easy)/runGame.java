import java.util.Scanner;
import java.io.IOException;

public class runGame {
	static Board game;
	static boolean withFile;

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		String str;

		while(true) {
			System.out.println("Game with file? [Y/N]");

			str = scan.nextLine();
			if(str.equals("Y")) {
				withFile = true;
				break;
			} else if(str.equals("N")) {
				withFile = false;
				break;
			} else {
				System.out.println("Invalid command");
			}
		}

		game = new Board(withFile);
		
		while(!game.isFinish(withFile)) {
			game.printBoard(withFile);
			game.selectObject(withFile);
			game.moveObject(withFile);
		}
		scan.close();
	}
}


