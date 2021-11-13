import com.sun.media.sound.JARSoundbankReader;

import java.io.*;
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

	static gameObject selected;
	static String selectedPosition;
	static String moveposition;

	static int skip=0;

	static ArrayList<ArrayList<String>> filemove=new ArrayList<ArrayList<String>>();
	static int fileReadPoint=0;
	static int maxcom;
	static BufferedWriter writer;
	static File writefile;

	Board(boolean withFile) throws IOException {
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
		changeFinder(9,1,'g',' ', 'N');//변경
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
		changeFinder(6,2,'g',' ', 'P');//변경
		changeFinder(6,4,'g',' ', 'P');//변경
		changeFinder(6,6,'g',' ', 'P');
		changeFinder(6,8,'g',' ', 'P');

		if (withFile){
			String temp;
			BufferedReader reader=new BufferedReader(new FileReader("./input.txt"));
			writer=new BufferedWriter(new FileWriter(new File("./output.txt")));
			writer.write("");
			while ((temp=reader.readLine())!=null){
				String[] seperated=temp.split(" ");

				ArrayList<String> com = new ArrayList<String>();
				com.add(seperated[0]);
				com.add(seperated[1]);
				filemove.add(com);
			}
		}
		maxcom=filemove.size();
	}

	public boolean isFinish(boolean withFile) throws IOException {
		/* Your code */


		if (withFile){
			if(maxcom==fileReadPoint){
				writer.close();
				return true;
			}

			int count=0;
			for(String key : finder.keySet()){
				if(finder.get(key).getType()=='K'){
					count++;
				}
			}
			if(count<2){
				writer.close();
				//System.out.println("The game was finished!!");
				return true;
			}else{
				return false;
			}
		}else{
			int count=0;
			for(String key : finder.keySet()){
				if(finder.get(key).getType()=='K'){
					count++;
				}
			}
			if(count<2){
				System.out.println("The game was finished!!");
				return true;
			}else{
				return false;
			}
		}
	}
	
	public void selectObject(boolean withFile) throws IOException {
		/* Your code */
		skip=0;
		if(withFile){
			if (fileReadPoint==maxcom){
				return;
			}
			ArrayList<String> com=filemove.get(fileReadPoint);
			selectedPosition=com.get(0);
			selected=finder.get(selectedPosition);

			if (selected.getType()==' '){
				fileReadPoint++;
				selectObject(withFile);
				return;
			}

			char type=selected.getType();
			char color=selected.getColor();

			if (color=='g' && whosturn==0){

				whosturn=1;
				if (type=='K'){
					kingCanMove(selected, withFile);
				}else if(type=='R'){
					rookCanMove(selected, withFile);
				}else if(type=='E'){
					elephantCanMove(selected, withFile);
				}else if(type=='G'){
					guardCanMove(selected, withFile);
				}else if(type=='C'){
					cannonCanMove(selected, withFile);
				}else  if(type=='P'){
					pwanCanMove(selected, withFile);
				}else if(type=='N'){
					knightCanMove(selected, withFile);
				}
			}else if(color=='r' && whosturn==1){

				whosturn=0;
				if (type=='K'){
					kingCanMove(selected, withFile);
				}else if(type=='R'){
					rookCanMove(selected, withFile);
				}else if(type=='E'){
					elephantCanMove(selected, withFile);
				}else if(type=='G'){
					guardCanMove(selected, withFile);
				}else if(type=='C'){
					cannonCanMove(selected, withFile);
				}else  if(type=='P'){
					pwanCanMove(selected, withFile);
				}else if(type=='N'){
					knightCanMove(selected, withFile);
				}
			}

		}else {
			while(true){
				System.out.print("Select piece : ");
				String position=scanner.next();

				selected=finder.get(position);
				selectedPosition=position;

				if(selected!=null){
					char type=selected.getType();
					char color=selected.getColor();

					if (color=='g' && whosturn==0){
						System.out.println(selectedPosition);
						whosturn=1;
						if (type=='K'){
							kingCanMove(selected, withFile);
						}else if(type=='R'){
							rookCanMove(selected, withFile);
						}else if(type=='E'){
							elephantCanMove(selected, withFile);
						}else if(type=='G'){
							guardCanMove(selected, withFile);
						}else if(type=='C'){
							cannonCanMove(selected, withFile);
						}else  if(type=='P'){
							pwanCanMove(selected, withFile);
						}else if(type=='N'){
							knightCanMove(selected, withFile);
						}
						break;

					}else if(color=='r' && whosturn==1){

						whosturn=0;
						if (type=='K'){
							kingCanMove(selected, withFile);
						}else if(type=='R'){
							rookCanMove(selected, withFile);
						}else if(type=='E'){
							elephantCanMove(selected, withFile);
						}else if(type=='G'){
							guardCanMove(selected, withFile);
						}else if(type=='C'){
							cannonCanMove(selected, withFile);
						}else  if(type=='P'){
							pwanCanMove(selected, withFile);
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
					continue;
				}
			}
		}
	}

	//check point
	public void moveObject(boolean withFile) throws IOException {
		/* Your code */
		if(skip==1){
			skip=0;
			fileReadPoint++;
			return;
		}

		if(withFile){

			if (fileReadPoint==maxcom){
				return;
			}
			ArrayList<String> com=filemove.get(fileReadPoint);
			String position=com.get(1);
			gameObject movePoint=finder.get(position);

			if (movePoint.getTarget()!='*'){
				for (String key : finder.keySet()){
					gameObject temp=finder.get(key);
					if(temp.getTarget()=='*'){
						temp.setTarget(' ');
						finder.replace(key, temp);
					}
				}

				fileReadPoint++;
				return;
			}else{
				changeObject(selectedPosition, position);
				moveposition=position;
				fileReadPoint++;
				for (String key : finder.keySet()){
					gameObject temp=finder.get(key);
					if(temp.getTarget()=='*'){
						temp.setTarget(' ');
						finder.replace(key, temp);
					}
				}
				selectedPosition="";
				if (withFile){
					writer.append("Move piece : "+moveposition+"\n");
				}else{
					printBoard(withFile);
				}

			}

		}else {
			System.out.print("Move piece : ");
			String position=scanner.next();

			gameObject movePoint=finder.get(position);
			if (movePoint.getTarget()!='*'){
				System.out.println("Please select other move piece.");
				moveObject(withFile);
			}else{
				changeObject(selectedPosition, position);

				for (String key : finder.keySet()){
					gameObject temp=finder.get(key);
					if(temp.getTarget()=='*'){
						temp.setTarget(' ');
						finder.replace(key, temp);
					}
				}
			}
			selectedPosition="";
		}
	}

	public void changeObject(String sp, String mp){
		gameObject temp1=finder.get(sp);
		gameObject temp2=finder.get(mp);
		int x1=temp1.getX();
		int y1=temp1.getY();
		int x2=temp2.getX();
		int y2=temp2.getY();
		temp1.setX(x2);
		temp1.setY(y2);
		temp2.setX(x1);
		temp2.setY(y1);

		temp2.setType(' ');
		temp2.setColor(' ');

		finder.replace(sp, temp2);
		finder.replace(mp, temp1);
	}

	public void kingCanMove(gameObject king, boolean withFile) throws IOException {
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
			//System.out.println("Please select other piece.");
			if (whosturn==1){
				whosturn=0;
			}else{
				whosturn=1;
			}
			if (withFile){
				skip=1;
				return;
			}
			//fileReadPoint++;
			selectObject(withFile);
			//skip=1;
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

		if (withFile){
			writer.append("Select piece : "+selectedPosition+"\n");
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

	public void guardCanMove(gameObject guard, boolean withFile) throws IOException {
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
			//System.out.println("Please select other piece.");
			if (whosturn==1){
				whosturn=0;
			}else{
				whosturn=1;
			}
			if (withFile){
				skip=1;
				return;
			}

			selectObject(withFile);
			//skip=1;
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
		if (withFile){
			writer.append("Select piece : "+selectedPosition+"\n");
		}
		printBoard(withFile);
	}

	public void cannonCanMove(gameObject cannon, boolean withFile) throws IOException {
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
		for(int i=nowy+1; i<9; i++){
			gameObject temp=finder.get(marker[nowx][i]);
			if(temp.getType()=='C'){
				break;
			}
			else if(temp.getType()==' '){
				continue;
			}else{
				rightend=i;
				if(rightend<8){
					for(int j=rightend+1; j<9; j++){
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
			//System.out.println("Please select other piece.");
			if (whosturn==1){
				whosturn=0;
			}else{
				whosturn=1;
			}
			if (withFile){
				skip=1;
				return;
			}

			selectObject(withFile);
			//skip=1;
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
		if (withFile){
			writer.append("Select piece : "+selectedPosition+"\n");
		}
		printBoard(withFile);
	}

	public void elephantCanMove(gameObject elephant, boolean withFile) throws IOException {
		canmove=new ArrayList<ArrayList<Integer>>();
		int nowx=elephant.getX();
		int nowy=elephant.getY();

		if(nowx-1>=0){
			gameObject up=finder.get(marker[nowx-1][nowy]);
			if(up.getType()==' '){
				//right
				if(nowx-2>=0 && nowy+1<9){
					if (finder.get(marker[nowx-2][nowy+1]).getType()==' '){
						if (nowx-3>=0 && nowy+2<9){
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
				if(nowx+2<10 && nowy+1<9){
					if (finder.get(marker[nowx+2][nowy+1]).getType()==' '){
						if (nowx+3<10 && nowy+2<9){
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
			gameObject left=finder.get(marker[nowx][nowy-1]);
			if (left.getType()==' '){
				//right
				if(nowx+1<10 && nowy-2>=0){
					if (finder.get(marker[nowx+1][nowy-2]).getType()==' '){
						if (nowx+2<10 && nowy-3>=0){
							if (isNotMyTeam(elephant, nowx+2, nowy-3)){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(nowx+2);
								t.add(nowy-3);
								canmove.add(t);
							}
						}
					}
				}

				if(nowx-1>=0 && nowy-2>=0){
					if (finder.get(marker[nowx-1][nowy-2]).getType()==' '){
						if (nowx-2>=0 && nowy-3>=0){
							if (isNotMyTeam(elephant, nowx-2, nowy-3)){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(nowx-2);
								t.add(nowy-3);
								canmove.add(t);
							}
						}
					}
				}
			}
		}

		if(nowy+1<10){
			gameObject right=finder.get(marker[nowx][nowy-1]);
			if (right.getType()==' '){
				//right
				if(nowx+1<10 && nowy+2<9){
					if (finder.get(marker[nowx+1][nowy+2]).getType()==' '){
						if (nowx+2<10 && nowy+3<9){
							if (isNotMyTeam(elephant, nowx+2, nowy+3)){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(nowx+2);
								t.add(nowy+3);
								canmove.add(t);
							}
						}
					}
				}

				if(nowx-1>=0 && nowy+2<9){
					if (finder.get(marker[nowx-1][nowy+2]).getType()==' '){
						if (nowx-2>=0 && nowy+3<9){
							if (isNotMyTeam(elephant, nowx-2, nowy+3)){
								ArrayList<Integer> t=new ArrayList<Integer>();
								t.add(nowx-2);
								t.add(nowy+3);
								canmove.add(t);
							}
						}
					}
				}
			}
		}

		int length=canmove.size();
		if(length==0){
			//System.out.println("Please select other piece.");
			if (whosturn==1){
				whosturn=0;
			}else{
				whosturn=1;
			}
			if (withFile){
				skip=1;
				return;
			}

			selectObject(withFile);
			//skip=1;
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
		if (withFile){
			writer.append("Select piece : "+selectedPosition+"\n");
		}
		printBoard(withFile);
	}

	public void knightCanMove(gameObject knight, boolean withFile) throws IOException {
		canmove=new ArrayList<ArrayList<Integer>>();
		int nowx=knight.getX();
		int nowy=knight.getY();

		if(nowx-1>=0){
			gameObject up=finder.get(marker[nowx-1][nowy]);
			if (up.getType()==' '){
				if(nowx-2>=0 && nowy+1 < 9){
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
				if(nowx+2<10 && nowy+1<9){
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

		if(nowy+1 < 9){
			gameObject right=finder.get(marker[nowx][nowy+1]);
			if(right.getType()==' '){
				if (nowx+1<10 && nowy+2<9){
					if (isNotMyTeam(knight, nowx+1, nowy+2)){
						ArrayList<Integer> t=new ArrayList<Integer>();
						t.add(nowx+1);
						t.add(nowy+2);
						canmove.add(t);
					}
				}
				if(nowx-1>=0 && nowy+2<9){
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
			//System.out.println("Please select other piece.");
			if (whosturn==1){
				whosturn=0;
			}else{
				whosturn=1;
			}
			if (withFile){
				skip=1;
				return;
			}

			selectObject(withFile);
			//skip=1;
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
		if (withFile){
			writer.append("Select piece : "+selectedPosition+"\n");
		}
		printBoard(withFile);
	}

	public void rookCanMove(gameObject rook, boolean withFile) throws IOException {
		canmove=new ArrayList<ArrayList<Integer>>();
		int nowx=rook.getX();
		int nowy=rook.getY();

		if(nowx+1<10) {
			for (int i = nowx + 1; i < 10; i++) {
				gameObject temp = finder.get(marker[i][nowy]);
				if (temp.getType() != ' ') {
					if (isNotMyTeam(rook, i, nowy)) {
						ArrayList<Integer> t = new ArrayList<Integer>();
						t.add(i);
						t.add(nowy);
						canmove.add(t);
					}
					break;
				}else{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(i);
					t.add(nowy);
					canmove.add(t);
				}
			}
		}
		if(nowx-1>=0){
			for(int i=nowx-1; i>=0; i--){
				gameObject temp =finder.get(marker[i][nowy]);
				if (temp.getType()!=' '){
					if (isNotMyTeam(rook, i, nowy)){
						ArrayList<Integer> t = new ArrayList<Integer>();
						t.add(i);
						t.add(nowy);
						canmove.add(t);
					}
					break;
				}else{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(i);
					t.add(nowy);
					canmove.add(t);
				}
			}
		}
		if(nowy+1<9){
			for (int i=nowy+1; i<9; i++){
				gameObject temp=finder.get(marker[nowx][i]);
				if (temp.getType()!=' '){
					if (isNotMyTeam(rook, nowx, i)){
						ArrayList<Integer> t = new ArrayList<Integer>();
						t.add(nowx);
						t.add(i);
						canmove.add(t);
					}
				}else{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(nowx);
					t.add(i);
					canmove.add(t);
				}
			}
		}
		if(nowy-1>=0){
			for (int i=nowy-1; i>=0; i--){
				gameObject temp=finder.get(marker[nowx][i]);
				if (temp.getType()!=' '){
					if (isNotMyTeam(rook, nowx, i)){
						ArrayList<Integer> t = new ArrayList<Integer>();
						t.add(nowx);
						t.add(i);
						canmove.add(t);
					}else{
						ArrayList<Integer> t = new ArrayList<Integer>();
						t.add(nowx);
						t.add(i);
						canmove.add(t);
					}
				}
			}
		}

		int length=canmove.size();
		if(length==0){
			//System.out.println("Please select other piece.");
			if (whosturn==1){
				whosturn=0;
			}else{
				whosturn=1;
			}
			if (withFile){
				skip=1;
				return;
			}

			selectObject(withFile);
			//skip=1;
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
		if (withFile){
			writer.append("Select piece : "+selectedPosition+"\n");
		}
		printBoard(withFile);
	}

	public void pwanCanMove(gameObject pwan, boolean withFile) throws IOException {
		canmove=new ArrayList<ArrayList<Integer>>();
		int nowx=pwan.getX();
		int nowy=pwan.getY();

		if(pwan.getColor()=='r'){
			if (nowx+1 < 10){
				gameObject temp=finder.get(marker[nowx+1][nowy]);
				if (temp.getType()==' ' || isNotMyTeam(pwan, nowx+1, nowy)){
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(nowx+1);
					t.add(nowy);
					canmove.add(t);
				}
			}
			if(nowy+1 < 9){
				gameObject temp=finder.get(marker[nowx][nowy+1]);
				if (temp.getType()==' ' || isNotMyTeam(pwan, nowx, nowy+1)){
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(nowx);
					t.add(nowy+1);
					canmove.add(t);
				}
			}
			if(nowy-1 >=0){
				gameObject temp= finder.get(marker[nowx][nowy-1]);
				if (temp.getType()==' ' || isNotMyTeam(pwan, nowx, nowy-1)){
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(nowx);
					t.add(nowy-1);
					canmove.add(t);
				}
			}
		}else{
			if (nowx-1 >= 0){
				gameObject temp=finder.get(marker[nowx-1][nowy]);
				if (temp.getType()==' ' || isNotMyTeam(pwan, nowx-1, nowy)){
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(nowx-1);
					t.add(nowy);
					canmove.add(t);
				}
			}
			if(nowy+1 < 9){
				gameObject temp=finder.get(marker[nowx][nowy+1]);
				if (temp.getType()==' ' || isNotMyTeam(pwan, nowx, nowy+1)){
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(nowx);
					t.add(nowy+1);
					canmove.add(t);
				}
			}
			if(nowy-1 >=0){
				gameObject temp= finder.get(marker[nowx][nowy-1]);
				if (temp.getType()==' ' || isNotMyTeam(pwan, nowx, nowy-1)){
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(nowx);
					t.add(nowy-1);
					canmove.add(t);
				}
			}
		}

		int length=canmove.size();
		if(length==0){
			//System.out.println("Please select other piece.");
			if (whosturn==1){
				whosturn=0;
			}else{
				whosturn=1;
			}
			if (withFile){
				skip=1;
				return;
			}

			selectObject(withFile);
			//skip=1;
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
		if (withFile){
			writer.append("Select piece : "+selectedPosition+"\n");
		}
		printBoard(withFile);
	}
	
	public void printBoard(boolean withFile) throws IOException {
		/* Your code */
		if(withFile){
			//write the output

			for (String key : finder.keySet()){
				gameObject temp=finder.get(key);
				String m="";
				janggi[temp.getX()][temp.getY()]=m+temp.getColor()+temp.getType()+temp.getTarget();
			}

			writer.append("  ");
			for (int i=0; i<9; i++){
				writer.append(" "+(char)(97+i)+" ");
			}
			writer.append("\n");

			for(int i=0; i<10; i++){
				writer.append((char)(57-i)+" ");
				for(int j=0; j<9; j++){
					if(j==8){
						writer.append(janggi[i][j]+"\n");
					}else{
						writer.append(janggi[i][j]);
					}
				}
			}



		}else{
			for (String key : finder.keySet()){
				gameObject temp=finder.get(key);
				String m="";
				janggi[temp.getX()][temp.getY()]=m+temp.getColor()+temp.getType()+temp.getTarget();
			}

			System.out.print("  ");
			for (int i=0; i<9; i++){
				System.out.print(" "+(char)(97+i)+" ");
			}
			System.out.println("");

			for(int i=0; i<10; i++){
				System.out.print((char)(57-i)+" ");
				for(int j=0; j<9; j++){
					if(j==8){
						System.out.println(janggi[i][j]);
					}else{
						System.out.print(janggi[i][j]);
					}
				}
			}
		}
	}
}
