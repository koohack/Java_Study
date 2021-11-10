import com.sun.media.sound.JARSoundbankReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// cho(green) first,
// must use hashmap

class piece extends gameObject{

	public piece(int x, int y, char type, char color, char target) {
		super(x, y, type, color, target);
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

	static int skip=0;

	Board(boolean withFile) {
		/* Your code */
		// Make mark
		for(int i=0; i<10; i++){
			for(int j=0; j<9; j++){
				String hap="";
				char one=(char)(97+j);
				char two=(char)(57-i);
				hap=hap+one+two;
				marker[i][j]=hap;
			}
		}

		// Make finder
		for (int i=0; i< 10; i++){
			for(int j=0; j<9; j++){
				piece empty=new piece(i, j, ' ', ' ', ' ');
				finder.put(marker[i][j], empty);
			}
		}


		// Han(Red)
		// First Line
		changeFinder(0,0,'r',' ', 'R');
		changeFinder(0,8,'r',' ', 'R');
		changeFinder(0,1,'r',' ', 'N');
		changeFinder(0,7,'r',' ', 'N');
		changeFinder(0,2,'r',' ', 'E');
		changeFinder(0,6,'r',' ', 'E');
		changeFinder(0,3,'r',' ', 'G');
		changeFinder(0,5,'r',' ', 'G');

		// Second Line
		changeFinder(1,4, 'r',' ', 'K');

		// Third Line
		changeFinder(2,1,'r',' ', 'C');
		changeFinder(2,7,'r',' ', 'C');


		// Last Line
		changeFinder(3,0,'r',' ', 'P');
		changeFinder(3,2,'r',' ', 'P');
		changeFinder(3,4,'r',' ', 'P');
		changeFinder(3,6,'r',' ', 'P');
		changeFinder(3,8,'r',' ', 'P');

		// Cho(Green)
		// First Line
		changeFinder(9,0,'g',' ', 'R');
		changeFinder(9,8,'g',' ', 'R');
		changeFinder(8,1,'g',' ', 'N');//변경
		changeFinder(9,7,'g',' ', 'N');
		changeFinder(9,2,'g',' ', 'E');
		changeFinder(9,6,'g',' ', 'E');
		changeFinder(9,3,'g',' ', 'G');
		changeFinder(9,5,'g',' ', 'G');

		// Second Line
		changeFinder(8,4, 'g',' ', 'K');

		// Third Line
		changeFinder(7,1,'g',' ', 'C');
		changeFinder(7,7,'g',' ', 'C');


		// Last Line
		changeFinder(6,0,'g',' ', 'P');
		changeFinder(6,1,'g',' ', 'P');//변경
		changeFinder(7,4,'g',' ', 'P');//변경
		changeFinder(6,6,'g',' ', 'P');
		changeFinder(6,8,'g',' ', 'P');
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
							kingCanMove(selected, withFile);
						}else if(type=='R'){

						}else if(type=='E'){
							elephantCanMove(selected, withFile);
						}else if(type=='G'){
							guardCanMove(selected, withFile);
						}else if(type=='C'){
							cannonCanMove(selected, withFile);
						}else  if(type=='P'){

						}else if(type=='N'){
							knightCanMove(selected, withFile);
						}
						break;

					}else if(color=='r' && whosturn==1){

						whosturn=0;
						if (type=='K'){
							kingCanMove(selected, withFile);
						}else if(type=='R'){

						}else if(type=='E'){
							elephantCanMove(selected, withFile);
						}else if(type=='G'){
							guardCanMove(selected, withFile);
						}else if(type=='C'){
							cannonCanMove(selected, withFile);
						}else  if(type=='P'){

						}else if(type=='N'){
							knightCanMove(selected, withFile);
						}
						break;

					}else{
						System.out.println("Please select other piece.");
						continue;
					}
				}else{
					System.out.println("Please select other piece.");
				}
			}
		}
	}
	
	public void moveObject(boolean withFile) {
		/* Your code */
		if(skip==1){
			skip=0;
			return;
		}

		if(withFile){

		}else {
			System.out.print("Move piece : ");
			String position=scanner.next();
		}
	}

	public void kingCanMove(gameObject king, boolean withFile){
		// Red and Green Part

		canmove=new ArrayList<ArrayList<Integer>>();
		int nowx=king.getX();
		int nowy=king.getY();

		if(king.getColor()=='r'){
			int maxup=0;
			int maxdown=2;
			int maxleft=3;
			int maxright=5;

			if(nowx-1 >= maxup && isNotMyTeam(king, nowx-1, nowy)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && isNotMyTeam(king, nowx+1, nowy)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy);
				canmove.add(temp);
			}
			if(nowy-1 >= maxleft && isNotMyTeam(king, nowx, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowy+1 <= maxright && isNotMyTeam(king, nowx, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx);
				temp.add(nowy+1);
				canmove.add(temp);
			}
			if(nowx-1 >= maxup && nowy-1 >= maxleft && isNotMyTeam(king, nowx-1, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowx-1 >= maxup && nowy+1 >= maxright && isNotMyTeam(king, nowx-1, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy+1);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && nowy-1 >= maxleft && isNotMyTeam(king, nowx+1, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && nowy+1 <= maxright && isNotMyTeam(king, nowx+1, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy+1);
				canmove.add(temp);
			}


		}else if(king.getColor()=='g'){
			int maxup=7;
			int maxdown=9;
			int maxleft=3;
			int maxright=5;

			if(nowx-1 >= maxup && isNotMyTeam(king, nowx-1, nowy)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && isNotMyTeam(king, nowx+1, nowy)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy);
				canmove.add(temp);
			}
			if(nowy-1 >= maxleft && isNotMyTeam(king, nowx, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowy+1 <= maxright && isNotMyTeam(king, nowx, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx);
				temp.add(nowy+1);
				canmove.add(temp);
			}
			if(nowx-1 >= maxup && nowy-1 >= maxleft && isNotMyTeam(king, nowx-1, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowx-1 >= maxup && nowy+1 >= maxright && isNotMyTeam(king, nowx-1, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy+1);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && nowy-1 >= maxleft && isNotMyTeam(king, nowx+1, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && nowy+1 <= maxright && isNotMyTeam(king, nowx+1, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy+1);
				canmove.add(temp);
			}
		}

		int length=canmove.size();
		if(length==0){
			System.out.println("Please select other piece.");
			skip=1;
			return;
		}

		for(ArrayList<Integer> item : canmove){
			int x=item.get(0);
			int y=item.get(1);
			String position=marker[x][y];

			gameObject temp=finder.get(position);
			temp.setTarget('*');
			finder.replace(position, temp);
		}

		printBoard(withFile);
	}

	public boolean isNotMyTeam(gameObject me, int targetx, int targety){
		char myColor=me.getColor();

		gameObject target=finder.get(marker[targetx][targety]);
		if(target==null){
			return true;
		}else if(target.getColor()!=myColor){
			return true;
		}else{
			return false;
		}
	}

	public void changeFinder(int x, int y, char color, char target, char type){
		piece temp;
		temp= (piece) finder.get(marker[x][y]);
		temp.setColor(color);
		temp.setTarget(target);
		temp.setType(type);
		finder.replace(marker[x][y], temp);
	}

	public void guardCanMove(gameObject guard, boolean withFile){
		canmove=new ArrayList<ArrayList<Integer>>();
		int nowx=guard.getX();
		int nowy=guard.getY();

		if(guard.getColor()=='r'){
			int maxup=0;
			int maxdown=2;
			int maxleft=3;
			int maxright=5;

			if(nowx-1 >= maxup && isNotMyTeam(guard, nowx-1, nowy)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && isNotMyTeam(guard, nowx+1, nowy)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy);
				canmove.add(temp);
			}
			if(nowy-1 >= maxleft && isNotMyTeam(guard, nowx, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowy+1 <= maxright && isNotMyTeam(guard, nowx, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx);
				temp.add(nowy+1);
				canmove.add(temp);
			}
			if(nowx-1 >= maxup && nowy-1 >= maxleft && isNotMyTeam(guard, nowx-1, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowx-1 >= maxup && nowy+1 >= maxright && isNotMyTeam(guard, nowx-1, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy+1);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && nowy-1 >= maxleft && isNotMyTeam(guard, nowx+1, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && nowy+1 <= maxright && isNotMyTeam(guard, nowx+1, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy+1);
				canmove.add(temp);
			}


		}else if(guard.getColor()=='g'){
			int maxup=7;
			int maxdown=9;
			int maxleft=3;
			int maxright=5;

			if(nowx-1 >= maxup && isNotMyTeam(guard, nowx-1, nowy)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && isNotMyTeam(guard, nowx+1, nowy)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy);
				canmove.add(temp);
			}
			if(nowy-1 >= maxleft && isNotMyTeam(guard, nowx, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowy+1 <= maxright && isNotMyTeam(guard, nowx, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx);
				temp.add(nowy+1);
				canmove.add(temp);
			}
			if(nowx-1 >= maxup && nowy-1 >= maxleft && isNotMyTeam(guard, nowx-1, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowx-1 >= maxup && nowy+1 >= maxright && isNotMyTeam(guard, nowx-1, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx-1);
				temp.add(nowy+1);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && nowy-1 >= maxleft && isNotMyTeam(guard, nowx+1, nowy-1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy-1);
				canmove.add(temp);
			}
			if(nowx+1 <= maxdown && nowy+1 <= maxright && isNotMyTeam(guard, nowx+1, nowy+1)){
				ArrayList<Integer> temp=new ArrayList<Integer>();
				temp.add(nowx+1);
				temp.add(nowy+1);
				canmove.add(temp);
			}
		}

		int length=canmove.size();
		if(length==0){
			System.out.println("Please select other piece.");
			skip=1;
			return;
		}

		for(ArrayList<Integer> item : canmove) {
			int x = item.get(0);
			int y = item.get(1);
			String position = marker[x][y];

			gameObject temp = finder.get(position);
			temp.setTarget('*');
			finder.replace(position, temp);
		}

		printBoard(withFile);
	}

	public void cannonCanMove(gameObject cannon, boolean withFile){
		canmove=new ArrayList<ArrayList<Integer>>();
		int nowx=cannon.getX();
		int nowy=cannon.getY();

		int downend;
		for(int i=nowx+1; i<10; i++){
			gameObject temp=finder.get(marker[i][nowy]);
			if(temp.getType()=='C'){
				break;
			}
			else if(temp.getType()==' '){
				continue;
			} else{
				downend=i;
				if(downend<9){
					for(int j=downend+1; j<10; j++){
						if(finder.get(marker[j][nowy]).getType()!=' '){
							if(isNotMyTeam(cannon, j, nowy) || finder.get(marker[j][nowy]).getType()!='C'){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(j);
								t.add(nowy);
								canmove.add(t);
							}else{
								break;
							}
						}
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(j);
						t.add(nowy);
						canmove.add(t);
					}
				}
				break;
			}
		}

		int upend;
		for(int i=nowx-1; i>=0; i--){
			gameObject temp=finder.get(marker[i][nowy]);
			if(temp.getType()=='C'){
				break;
			}
			else if(temp.getType()==' '){
				continue;
			}else{
				upend=i;
				if(upend>0){
					for(int j=upend-1; j>=0; j--){
						if(finder.get(marker[j][nowy]).getType()!=' '){
							if(isNotMyTeam(cannon, j, nowy) && finder.get(marker[j][nowy]).getType()!='C'){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(j);
								t.add(nowy);
								canmove.add(t);
							}else{
								break;
							}
						}
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(j);
						t.add(nowy);
						canmove.add(t);
					}
				}
				break;
			}
		}

		int leftend;
		for(int i=nowy-1; i>=0; i--){
			gameObject temp=finder.get(marker[nowx][i]);
			if(temp.getType()=='C'){
				break;
			}
			else if(temp.getType()==' '){
				continue;
			}else{
				leftend=i;
				if(leftend>0){
					for(int j=leftend-1; j>=0; j--){
						if(finder.get(marker[nowx][j]).getType()!=' '){
							if(isNotMyTeam(cannon, nowx, j) && finder.get(marker[nowx][j]).getType()!='C'){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(nowx);
								t.add(j);
								canmove.add(t);
							}else{
								break;
							}
						}
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(nowx);
						t.add(j);
						canmove.add(t);
					}
				}
				break;
			}
		}

		int rightend;
		for(int i=nowy+1; i<10; i++){
			gameObject temp=finder.get(marker[nowx][i]);
			if(temp.getType()=='C'){
				break;
			}
			else if(temp.getType()==' '){
				continue;
			}else{
				rightend=i;
				if(rightend<9){
					for(int j=rightend+1; j<10; j++){
						if(finder.get(marker[nowx][j]).getType()!=' '){
							if(isNotMyTeam(cannon, nowx, j) && finder.get(marker[nowx][j]).getType()!='C'){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(nowx);
								t.add(j);
								canmove.add(t);
							}else{
								break;
							}
						}
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(nowx);
						t.add(j);
						canmove.add(t);
					}
				}
				break;
			}
		}

		int length=canmove.size();
		if(length==0){
			System.out.println("Please select other piece.");
			skip=1;
			return;
		}

		for(ArrayList<Integer> item : canmove) {
			int x = item.get(0);
			int y = item.get(1);
			String position = marker[x][y];

			gameObject temp = finder.get(position);
			temp.setTarget('*');
			finder.replace(position, temp);
		}
		printBoard(withFile);
	}

	public void elephantCanMove(gameObject elephant, boolean withFile){
		canmove=new ArrayList<ArrayList<Integer>>();
		int nowx=elephant.getX();
		int nowy=elephant.getY();

		if(nowx-1>=0){
			gameObject up=finder.get(marker[nowx-1][nowy]);
			if(up.getType()==' '){
				//right
				if(nowx-2>=0 && nowy+1<10){
					if (finder.get(marker[nowx-2][nowy+1]).getType()==' '){
						if (nowx-3>=0 && nowy+2<10){
							if (isNotMyTeam(elephant, nowx-3, nowy+2)){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(nowx-3);
								t.add(nowy+2);
								canmove.add(t);
							}
						}
					}
				}

				if(nowx-2>=0 && nowy-1>=0){
					if(finder.get(marker[nowx-2][nowy-1]).getType()==' '){
						if(nowx-3>=0 && nowy-2>=0){
							if (isNotMyTeam(elephant, nowx-3, nowy-2)){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(nowx-3);
								t.add(nowy-2);
								canmove.add(t);
							}
						}
					}
				}
			}
		}

		if(nowx+1<10){
			gameObject down=finder.get(marker[nowx+1][nowy]);
			if(down.getType()==' '){
				//right
				if(nowx+2<10 && nowy+1<10){
					if (finder.get(marker[nowx+2][nowy+1]).getType()==' '){
						if (nowx+3<10 && nowy+2<10){
							if (isNotMyTeam(elephant, nowx+3, nowy+2)){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(nowx+3);
								t.add(nowy+2);
								canmove.add(t);
							}
						}
					}
				}

				if(nowx+2<10 && nowy-1>=0){
					if(finder.get(marker[nowx+2][nowy-1]).getType()==' '){
						if(nowx+3<10 && nowy-2>=0){
							if (isNotMyTeam(elephant, nowx+3, nowy-2)){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(nowx+3);
								t.add(nowy-2);
								canmove.add(t);
							}
						}
					}
				}
			}
		}

		if(nowy-1>=0){
			gameObject left=finder.get(marker[nowx+1][nowy]);
			if (left.getType()==' '){

			}
		}

		if(nowy+1<10){

		}


		int length=canmove.size();
		if(length==0){
			System.out.println("Please select other piece.");
			skip=1;
			return;
		}

		for(ArrayList<Integer> item : canmove) {
			int x = item.get(0);
			int y = item.get(1);
			String position = marker[x][y];

			gameObject temp = finder.get(position);
			temp.setTarget('*');
			finder.replace(position, temp);
		}
		printBoard(withFile);
	}

	public void knightCanMove(gameObject knight, boolean withFile){
		canmove=new ArrayList<ArrayList<Integer>>();
		int nowx=knight.getX();
		int nowy=knight.getY();

		if(nowx-1>=0){
			gameObject up=finder.get(marker[nowx-1][nowy]);
			if (up.getType()==' '){
				if(nowx-2>=0 && nowy+1 < 10){
					if(isNotMyTeam(knight, nowx-2, nowy+1)){
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(nowx-2);
						t.add(nowy+1);
						canmove.add(t);
					}
				}
				if(nowx-2>=0 && nowy-1 >=0){
					if (isNotMyTeam(knight, nowx-2, nowy-1)){
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(nowx-2);
						t.add(nowy-1);
						canmove.add(t);
					}

				}
			}
		}

		if(nowx+1<10){
			gameObject down=finder.get(marker[nowx+1][nowy]);
			if (down.getType()==' '){
				if(nowx+2<10 && nowy+1<10){
					if (isNotMyTeam(knight, nowx+2, nowy+1)){
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(nowx+2);
						t.add(nowy+1);
						canmove.add(t);
					}
				}
				if(nowx+2<10 && nowy-1>=0){
					ArrayList<Integer> t=new ArrayList<Integer>();
					t.add(nowx+2);
					t.add(nowy-1);
					canmove.add(t);
				}
			}
		}

		if(nowy+1 < 10){
			gameObject right=finder.get(marker[nowx][nowy+1]);
			if(right.getType()==' '){
				if (nowx+1<10 && nowy+2<10){
					if (isNotMyTeam(knight, nowx+1, nowy+2)){
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(nowx+1);
						t.add(nowy+2);
						canmove.add(t);
					}
				}
				if(nowx-1>=0 && nowy+2<10){
					if (isNotMyTeam(knight, nowx-1, nowy+2)){
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(nowx-1);
						t.add(nowy+2);
						canmove.add(t);
					}
				}
			}
		}

		if(nowy-1 >= 0){
			gameObject right=finder.get(marker[nowx][nowy-1]);
			if(right.getType()==' '){
				if (nowx+1<10 && nowy-2>=0){
					if (isNotMyTeam(knight, nowx+1, nowy-2)){
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(nowx+1);
						t.add(nowy-2);
						canmove.add(t);
					}
				}
				if(nowx-1>=0 && nowy-2>=0){
					if (isNotMyTeam(knight, nowx-1, nowy-2)){
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(nowx-1);
						t.add(nowy-2);
						canmove.add(t);
					}
				}
			}
		}

		int length=canmove.size();
		if(length==0){
			System.out.println("Please select other piece.");
			skip=1;
			return;
		}

		for(ArrayList<Integer> item : canmove) {
			int x = item.get(0);
			int y = item.get(1);
			String position = marker[x][y];

			gameObject temp = finder.get(position);
			temp.setTarget('*');
			finder.replace(position, temp);
		}
		printBoard(withFile);
	}

	public void rookCanMove(gameObject rook){

	}

	public void pawnCanMove(gameObject pwan){

	}
	
	public void printBoard(boolean withFile) {
		/* Your code */
		if(withFile){
			//write the output
		}else{
			for (String key : finder.keySet()){
				gameObject temp=finder.get(key);
				String m="";
				janggi[temp.getX()][temp.getY()]=m+temp.getColor()+temp.getType()+temp.getTarget();
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
