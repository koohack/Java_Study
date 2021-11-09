import com.sun.media.sound.JARSoundbankReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// cho(green) first,
// must use hashmap
class King extends gameObject{

	public King(int x, int y, char type, char color, char target) {
		super(x, y, type, color, target);
		type='K';
	}
}


class Guard extends gameObject{

	public Guard(int x, int y, char type, char color, char target) {
		super(x, y, type, color, target);
		type='G';
	}
}

class Pawn extends gameObject{

	public Pawn(int x, int y, char type, char color, char target) {
		super(x, y, type, color, target);
		type='P';
	}
}

class Cannon extends gameObject{

	public Cannon(int x, int y, char type, char color, char target) {
		super(x, y, type, color, target);
		type='C';
	}
}

class Rock extends gameObject{

	public Rock(int x, int y, char type, char color, char target) {
		super(x, y, type, color, target);
		type='R';
	}
}

class Knight extends gameObject{

	public Knight(int x, int y, char type, char color, char target) {
		super(x, y, type, color, target);
		type='N';
	}
}

class Elephant extends gameObject{

	public Elephant(int x, int y, char type, char color, char target) {
		super(x, y, type, color, target);
		type='E';
	}
}

public class Board {

	static String[][] janggi=new String[10][9];
	static String[][] temp_janggi=new String[10][9];
	static String emptySpace="   ";
	static String[][] marker=new String[10][9];

	static HashMap<String, gameObject> finder = new HashMap<String, gameObject>();
	static Scanner scanner = new Scanner(System.in);

	static int whosturn=0;// 0 for green, 1 for red

	static ArrayList<ArrayList<Integer>> canmove=new ArrayList<ArrayList<Integer>>();


	Board(boolean withFile) {
		/* Your code */

		// Han(Red)
		// First Line
		Rock redRock1=new Rock(0, 0, 'R', 'r', '1');
		Rock redRock2=new Rock(0, 8, 'R', 'r', '2');
		Knight redKnight1=new Knight(0, 1, 'N', 'r', '1');
		Knight redKnight2=new Knight(0, 7, 'N', 'r', '2');
		Elephant redElephant1=new Elephant(0, 2, 'E', 'r', '1');
		Elephant redElephant2=new Elephant(0, 6, 'E', 'r', '2');
		Guard redGuard1=new Guard(0, 3, 'G', 'r', '1');
		Guard redGuard2=new Guard(0, 5, 'G', 'r', '2');
		finder.put("a9", redRock1);
		finder.put("i9", redRock2);
		finder.put("b9", redKnight1);
		finder.put("h9", redKnight2);
		finder.put("c9", redElephant1);
		finder.put("g9", redElephant2);
		finder.put("d9", redGuard1);
		finder.put("f9", redGuard2);

		// Second Line
		King redKing=new King(1, 4, 'K', 'r', '1');
		finder.put("e8", redKing);

		// Third Line
		Cannon redCannon1=new Cannon(2, 1, 'C', 'r', '1');
		Cannon redCannon2=new Cannon(2, 7, 'C', 'r', '2');
		finder.put("b7", redCannon1);
		finder.put("h7", redCannon2);

		// Last Line
		Pawn redPawn1=new Pawn(3, 0, 'P', 'r', '1');
		Pawn redPawn2=new Pawn(3, 2, 'P', 'r', '2');
		Pawn redPawn3=new Pawn(3, 4, 'P', 'r', '3');
		Pawn redPawn4=new Pawn(3, 6, 'P', 'r', '4');
		Pawn redPawn5=new Pawn(3, 8, 'P', 'r', '5');
		finder.put("a6", redPawn1);
		finder.put("c6", redPawn2);
		finder.put("e6", redPawn3);
		finder.put("g6", redPawn4);
		finder.put("i6", redPawn5);

		// Cho(Green)
		// First Line
		Rock greenRock1=new Rock(9, 0, 'R', 'r', '1');
		Rock greenRock2=new Rock(9, 8, 'R', 'r', '2');
		Knight greenKnight1=new Knight(9, 1, 'N', 'r', '1');
		Knight greenKnight2=new Knight(9, 7, 'N', 'r', '2');
		Elephant greenElephant1=new Elephant(9, 2, 'E', 'r', '1');
		Elephant greenElephant2=new Elephant(9, 6, 'E', 'r', '2');
		Guard greenGuard1=new Guard(9, 3, 'G', 'r', '1');
		Guard greenGuard2=new Guard(9, 5, 'G', 'r', '2');
		finder.put("a0", greenRock1);
		finder.put("i0", greenRock2);
		finder.put("b0", greenKnight1);
		finder.put("h0", greenKnight2);
		finder.put("c0", greenElephant1);
		finder.put("g0", greenElephant2);
		finder.put("d0", greenGuard1);
		finder.put("f0", greenGuard2);

		// Second Line
		King greenKing=new King(8, 4, 'K', 'r', '1');
		finder.put("e1", greenKing);

		// Third Line
		Cannon greenCannon1=new Cannon(7, 1, 'C', 'r', '1');
		Cannon greenCannon2=new Cannon(7, 7, 'C', 'r', '2');
		finder.put("b2", greenCannon1);
		finder.put("h2", greenCannon2);

		// Last Line
		Pawn greenPawn1=new Pawn(6, 0, 'P', 'r', '1');
		Pawn greenPawn2=new Pawn(6, 2, 'P', 'r', '2');
		Pawn greenPawn3=new Pawn(6, 4, 'P', 'r', '3');
		Pawn greenPawn4=new Pawn(6, 6, 'P', 'r', '4');
		Pawn greenPawn5=new Pawn(6, 8, 'P', 'r', '5');
		finder.put("a3", greenPawn1);
		finder.put("c3", greenPawn2);
		finder.put("e3", greenPawn3);
		finder.put("g3", greenPawn4);
		finder.put("i3", greenPawn5);
	}

	public boolean isFinish(boolean withFile) {
		/* Your code */
		return false;
	}
	
	public void selectObject(boolean withFile) {
		/* Your code */
		if(withFile){

		}else {
			while(true){
				System.out.print("Select piece : ");
				String position=scanner.next();

				gameObject selected=finder.get(position);

				if(selected!=null){
					char type=selected.getType();
					char color=selected.getColor();

					if (color=='g' && whosturn==0){

						whosturn=1;
						if (type=='K'){

						}else if(type=='R'){

						}else if(type=='E'){

						}else if(type=='G'){

						}else if(type=='C'){

						}else  if(type=='P'){

						}


					}else if(color=='r' && whosturn==1){

						whosturn=0;
						if (type=='K'){

						}else if(type=='R'){

						}else if(type=='E'){

						}else if(type=='G'){

						}else if(type=='C'){

						}else  if(type=='P'){

						}



					}else{
						System.out.println("Please select other piece.");
						continue;
					}

					break;
				}else{
					System.out.println("Please select other piece.");
				}
			}
		}
	}
	
	public void moveObject(boolean withFile) {
		/* Your code */
		if(withFile){

		}else {

		}
	}

	public void kingCanMove(gameObject king){
		// Red and Green Part

		canmove=new ArrayList<ArrayList<Integer>>();
		int nowx=king.getX();
		int nowy=king.getY();

		if(king.getColor()=='r'){
			int maxup=0;
			int maxdown=2;
			int maxleft=3;
			int maxright=5;

			if(nowx-1 >= maxup){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy);
				canmove.add(temp);
			}






		}else if(king.getColor()=='g'){

		}
	}

	public void guardCanMove(gameObject guard){

	}

	public void cannonCanMove(gameObject cannon){

	}

	public void elephantCanMove(gameObject elephant){

	}

	public void knightCanMove(gameObject knight){

	}

	public void rookCanMove(gameObject rook){

	}

	public void pawnCanMove(gameObject pwan){

	}
	
	public void printBoard(boolean withFile) {
		/* Your code */
		if (withFile){
			//write the output
		}else{
			for (String key : finder.keySet()){
				gameObject temp=finder.get(key);
				String m="";
				janggi[temp.getX()][temp.getY()]=m+temp.getColor()+temp.getType()+" ";
			}

			for(int i=0; i<10; i++){
				for(int j=0; j<9; j++){
					if(janggi[i][j]==null){
						janggi[i][j]=emptySpace;
					}
				}
			}

			for(int i=0; i<10; i++){
				for(int j=0; j<9; j++){
					if(j==8){
						System.out.println(janggi[i][j]);
					}else{
						System.out.print(janggi[i][j]);
					}
				}
			}
		}


		/* Sample print sudo code */
		/*
		ANSI_RESET = "\033[0m";
		ANSI_FG_BLACK = "\033[30m";
		String ANSI_FG_WHITE = "\033[37m";
		String ANSI_BG_BLACK = "\033[40m";
		ANSI_BG_WHITE = "\033[47m";

		Print("   a  b  c  d  e  f  g  h \n");
		for(i = 0; i < 8; i++) {
			Print(8-i + " ");
			for (int j = 0; j < 8; j++) {
				if(isBlackSquare(i, j)) {
					// Black background, white character
					Print(ANSI_BG_BLACK + ANSI_FG_WHITE
							+ Piece Color
							+ Piece Type
							+ Possible Move Position
							+ ANSI_RESET + ANSI_RESET);
				} else {
					/// White background, black character
					Print(ANSI_BG_WHITE + ANSI_FG_BLACK
							+ Piece Color
							+ Piece Type
							+ Possible Move Position
							+ ANSI_RESET + ANSI_RESET);
				}
			}
			Print("\n");
		}
		*/
	}
}
