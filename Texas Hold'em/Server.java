import javax.print.DocFlavor;
import java.io.*;
import java.lang.reflect.GenericArrayType;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server extends Thread{
    // private final String ipAddress;
    static final int port=5000;
    static ServerSocket serverSocket = null;
    static List<ObjectOutputStream> list;

    // user list
    static int user1Count=10000;
    static int user2Count=10000;
    static int count=0;
    static int turn=0;
    static int total=0;
    static int betCount=-1;
    static int timeNow=0;
    static String user1Name;
    static String user2Name;
    static ArrayList<String> userlist=new ArrayList<String>(2);
    static int[] user2same=new int[13];
    static int[][] user2flu=new int[4][13];
    static int[] user1same=new int[13];
    static int[][] user1flu=new int[4][13];
    static int[] totalCard=new int[52];
    static ArrayList<ObjectOutputStream> writerStore=new ArrayList<ObjectOutputStream>(2);
    static Socket socket;
    static ObjectInputStream reader;
    static ObjectOutputStream writer;
    static int user1Pre=0;
    static int user2Pre=0;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        // get serverSocket
        serverSocket = new ServerSocket(port);
        System.out.println("[server] binding localhost");
        list=new ArrayList<ObjectOutputStream>();

        while(true) {
            System.out.println("===========================");
            System.out.println("Waiting for socket connection");
            socket = serverSocket.accept();
            System.out.println("===========================");
            System.out.println("connected");

            // reader and writer
            writer=new ObjectOutputStream(socket.getOutputStream());
            reader=new ObjectInputStream(socket.getInputStream());

            //make thread for one client
            Server server=new Server();
            Thread t=new Thread(server);
            t.start();

            //getInfo chattingthread = new getInfo(socket, list, user1Name, user2Name);
            list.add(writer);
            //chattingthread.start();
        }
    }

    @Override
    public void run() {
        super.run();
        ObjectInputStream read;
        read=reader;
        while (true){

            try {
                makeInfo info=null;
                if(this.socket.isConnected()){
                    info= (makeInfo) read.readObject();
                }else{
                    break;
                }
                int cmd=info.getCmd();

                if(cmd==0){
                    String name=info.getNickName();
                    if(user1Name==null){
                        user1Name=name;
                    }else{
                        user2Name=name;
                        makeInfo sender=new makeInfo();
                        sender.setCmd(0);
                        sender.setNickName(name);
                        sender.setMessage(user1Name);
                        broadCast(sender);
                    }
                }else if(cmd==-1){//send chat
                    System.out.println(info.getMessage());
                }else if(cmd==1){
                    count+=1;
                    System.out.println(count);
                    if(count == 2){//game start
                        count=0;
                        turn=0;
                        user1Pre=0;
                        user2Pre=0;
                        timeNow=0;
                        user2same=new int[13];
                        user2flu=new int[4][13];
                        user1same=new int[13];
                        user1flu=new int[4][13];
                        totalCard=new int[52];

                        System.out.println("game start");
                        int u1card1=getCard();
                        int u1card2=getCard();
                        int u2card1=getCard();
                        int u2card2=getCard();

                        String u1str1="0 "+Integer.toString(u1card1);
                        String u1str2="1 "+Integer.toString(u1card2);
                        String u2str1="0 "+Integer.toString(u2card1);
                        String u2str2="1 "+Integer.toString(u2card2);
                        // user card store
                        int u1s1f = u1card1/13;
                        int u1s1b = u1card1%13;
                        int u1s2f = u1card2/13;
                        int u1s2b = u1card1%13;

                        int u2s1f = u2card1/13;
                        int u2s1b = u2card1%13;
                        int u2s2f = u2card2/13;
                        int u2s2b = u2card1%13;

                        user1same[u1s1b]+=1;
                        user1same[u1s2b]+=1;
                        user2same[u2s1b]+=1;
                        user2same[u2s2b]+=1;

                        user1flu[u1s1f][u1s1b]+=1;
                        user1flu[u1s2f][u1s2b]+=1;
                        user2flu[u2s1f][u2s1b]+=1;
                        user2flu[u2s2f][u2s2b]+=1;

                        //user card store****
                        makeInfo sender1 = new makeInfo();
                        sender1.setCmd(1);
                        sender1.setMessage(u1str1);
                        sender1.setNickName(user1Name);
                        broadCast(sender1);

                        makeInfo sender2 = new makeInfo();
                        sender2.setCmd(1);
                        sender2.setMessage(u1str2);
                        sender2.setNickName(user1Name);
                        broadCast(sender2);

                        makeInfo sender3 = new makeInfo();
                        sender3.setCmd(1);
                        sender3.setMessage(u2str1);
                        sender3.setNickName(user2Name);
                        broadCast(sender3);

                        makeInfo sender4 = new makeInfo();
                        sender4.setCmd(1);
                        sender4.setMessage(u2str2);
                        sender4.setNickName(user2Name);
                        broadCast(sender4);

                        makeInfo sender5 = new makeInfo();
                        sender5.setCmd(2);
                        sender5.setMessage("0");
                        sender5.setNickName(user1Name);
                        broadCast(sender5);

                    }
                }else if(cmd==2){// bet
                    String name=info.getNickName();
                    String mo=info.getMessage();
                    int raise=Integer.parseInt(mo);

                    if (turn==1){
                        int pre=user1Pre;
                        int tempint=pre+raise;

                        System.out.println("=====================");
                        System.out.println("user1's turn");
                        System.out.println("=====================");

                        if (tempint > user2Pre){// if sum of bet bigger than user2pre
                            turn=0;
                            tempint=pre+raise;
                            int diff=tempint-user2Pre;
                            if(user1Pre==0){
                                user1Count=user1Count-raise;//차이 나는 만큼만 뺌
                                total+=raise;
                                user1Pre=tempint;
                            }else{
                                user1Count=user1Count-raise;
                                total+=raise;
                                user1Pre+=diff;
                            }

                            System.out.println("user1 bet more "+Integer.toString(diff));

                            makeInfo sender=new makeInfo();
                            sender.setCmd(3);
                            sender.setNickName(user1Name);
                            System.out.println(user1Count);
                            sender.setMessage(Integer.toString(user1Count));
                            broadCast(sender);

                            makeInfo sender2=new makeInfo();
                            sender2.setCmd(5);
                            sender2.setMessage(Integer.toString(user1Pre));
                            broadCast(sender2);

                            makeInfo sender1=new makeInfo();
                            sender1.setCmd(4);
                            sender1.setMessage(Integer.toString(total));
                            broadCast(sender1);

                        }else if (tempint==user2Pre){
                            turn=0;
                            user2Pre=0;
                            user1Pre=0;
                            user1Count-=raise;
                            System.out.println("user1 bet more "+Integer.toString(raise));
                            total+=raise;
                            makeInfo sender=new makeInfo();
                            sender.setCmd(3);
                            sender.setNickName(user1Name);
                            System.out.println(user1Count);
                            sender.setMessage(Integer.toString(user1Count));
                            broadCast(sender);

                            makeInfo sender2=new makeInfo();
                            sender2.setCmd(5);
                            sender2.setMessage("0");
                            broadCast(sender2);

                            makeInfo sender1=new makeInfo();
                            sender1.setCmd(4);
                            sender1.setMessage(Integer.toString(total));
                            broadCast(sender1);

                            if(timeNow==0){//three card
                                timeNow+=1;
                                threeCard();
                            }else if(timeNow==1){//last second card
                                timeNow+=1;
                                oneCard(3);
                            }else if(timeNow==2){//last card
                                timeNow+=1;
                                oneCard(4);
                            }else{//end
                                cardValue user1val = new cardValue(user1same, user1flu);
                                cardValue user2val = new cardValue(user2same, user2flu);
                                timeNow=0;
                                int user1value=lastCheck(user1val);
                                int user2value=lastCheck(user2val);

                                if(user1value==2 && user2value==2){
                                    int dex1=0;
                                    for(int i=0; i< user1same.length; i++){
                                        if(user1same[i]>0){
                                            dex1=i;
                                        }
                                    }
                                    int dex2=0;
                                    for(int i=0; i< user2same.length; i++){
                                        if(user2same[i]>0){
                                            dex2=i;
                                        }
                                    }
                                    if(dex1>dex2){
                                        user1value=1;
                                        user2value=0;
                                    }else if(dex2>dex1){
                                        user1value=0;
                                        user2value=1;
                                    }
                                }

                                makeInfo lastsender=new makeInfo();
                                String winner="";
                                if(user1value > user2value){
                                    lastsender.setCmd(7);
                                    lastsender.setNickName(user1Name);
                                    winner=user1Name;
                                    user1Count+=total;
                                    total=0;
                                }else if(user1value < user2value){
                                    lastsender.setCmd(7);
                                    lastsender.setNickName(user2Name);
                                    winner=user2Name;
                                    user2Count+=total;
                                    total=0;
                                }else{
                                    lastsender.setCmd(7);
                                    lastsender.setNickName("same");
                                    user1Count+=total/2;
                                    user2Count+=total/2;
                                }
                                broadCast(lastsender);
                                total=0;
                                count=0;
                                turn=0;
                                timeNow=0;
                                user1Pre=0;
                                user2Pre=0;
                                makeInfo reset=new makeInfo();
                                reset.setCmd(8);
                                reset.setMessage(Integer.toString(user1Count));
                                reset.setNickName(user1Name);
                                broadCast(reset);

                                makeInfo reset1=new makeInfo();
                                reset1.setCmd(8);
                                reset1.setMessage(Integer.toString(user2Count));
                                reset1.setNickName(user2Name);
                                broadCast(reset1);
                                user2same=new int[13];
                                user2flu=new int[4][13];
                                user1same=new int[13];
                                user1flu=new int[4][13];
                                totalCard=new int[52];
                            }
                        }
                    }else{
                        int pre=user2Pre;
                        int tempint=pre+raise;
                        System.out.println("=====================");
                        System.out.println("user2's turn");
                        System.out.println("=====================");

                        if (tempint > user1Pre){
                            turn=1;
                            tempint=pre+raise;
                            int diff=tempint-user1Pre;
                            if(user2Pre==0){
                                user2Count=user2Count-raise;//차이 나는 만큼만 뺌
                                total+=raise;
                                user2Pre=tempint;
                            }else{
                                user2Count=user2Count-raise;
                                total+=raise;
                                user2Pre+=diff;
                            }
                            user2Pre=tempint;

                            System.out.println("user2 bet more "+Integer.toString(diff));


                            makeInfo sender=new makeInfo();
                            sender.setCmd(3);
                            sender.setNickName(user2Name);
                            System.out.println(user2Count);
                            sender.setMessage(Integer.toString(user2Count));
                            broadCast(sender);
                            makeInfo sender2=new makeInfo();

                            sender2.setCmd(5);
                            sender2.setMessage(Integer.toString(user2Pre));
                            broadCast(sender2);

                            makeInfo sender1=new makeInfo();
                            sender1.setCmd(4);
                            sender1.setMessage(Integer.toString(total));
                            broadCast(sender1);
                        }else if (tempint==user1Pre){
                            turn=1;
                            user2Pre=0;
                            user1Pre=0;
                            user2Count-=raise;
                            System.out.println("user2 bet more "+Integer.toString(raise));
                            total+=raise;
                            makeInfo sender=new makeInfo();
                            sender.setCmd(3);
                            sender.setNickName(user2Name);
                            System.out.println(user2Count);
                            sender.setMessage(Integer.toString(user2Count));
                            broadCast(sender);

                            makeInfo sender2=new makeInfo();
                            sender2.setCmd(5);

                            sender2.setMessage("0");
                            broadCast(sender2);

                            makeInfo sender1=new makeInfo();
                            sender1.setCmd(4);
                            sender1.setMessage(Integer.toString(total));
                            broadCast(sender1);

                            if(timeNow==0){//three card
                                timeNow+=1;
                                threeCard();
                            }else if(timeNow==1){//last second card
                                timeNow+=1;
                                oneCard(3);
                            }else if(timeNow==2){//last card
                                timeNow+=1;
                                oneCard(4);
                            }else{//end
                                timeNow=0;
                                cardValue user1val = new cardValue(user1same, user1flu);
                                cardValue user2val = new cardValue(user2same, user2flu);

                                int user1value=lastCheck(user1val);
                                int user2value=lastCheck(user2val);

                                if(user1value==2 && user2value==2){
                                    int dex1=0;
                                    for(int i=0; i< user1same.length; i++){
                                        if(user1same[i]>0){
                                            dex1=i;
                                        }
                                    }
                                    int dex2=0;
                                    for(int i=0; i< user2same.length; i++){
                                        if(user2same[i]>0){
                                            dex2=i;
                                        }
                                    }
                                    if(dex1>dex2){
                                        user1value=1;
                                        user2value=0;
                                    }else if(dex2>dex1){
                                        user1value=0;
                                        user2value=1;
                                    }
                                }

                                makeInfo lastsender=new makeInfo();
                                String winner="";
                                if(user1value > user2value){
                                    lastsender.setCmd(7);
                                    lastsender.setNickName(user1Name);
                                    winner=user1Name;
                                    user1Count+=total;
                                    total=0;
                                }else if(user1value < user2value){
                                    lastsender.setCmd(7);
                                    lastsender.setNickName(user2Name);
                                    winner=user2Name;
                                    user2Count+=total;
                                    total=0;
                                }else{
                                    lastsender.setCmd(7);
                                    lastsender.setNickName("same");
                                    user1Count+=total/2;
                                    user2Count+=total/2;
                                }
                                broadCast(lastsender);
                                count=0;
                                turn=0;
                                timeNow=0;
                                user1Pre=0;
                                user2Pre=0;
                                makeInfo reset=new makeInfo();
                                reset.setCmd(8);
                                reset.setMessage(Integer.toString(user1Count));
                                reset.setNickName(user1Name);
                                broadCast(reset);

                                makeInfo reset1=new makeInfo();
                                reset1.setCmd(8);
                                reset1.setMessage(Integer.toString(user2Count));
                                reset1.setNickName(user2Name);
                                broadCast(reset1);

                                makeInfo newgame=new makeInfo();
                                newgame.setCmd(9);
                                broadCast(newgame);
                                user2same=new int[13];
                                user2flu=new int[4][13];
                                user1same=new int[13];
                                user1flu=new int[4][13];
                                totalCard=new int[52];
                            }
                        }
                    }
                }else if(cmd==3){
                    String name=info.getNickName();

                    makeInfo sender=new makeInfo();
                    sender.setCmd(10);
                    sender.setNickName(name);//포기한 사람

                    if(name.equals(user1Name)){
                        user2Count+=total;
                        total=0;
                        sender.setMessage(Integer.toString(user1Count)+" "+Integer.toString(user2Count));
                    }else{
                        user1Count+=total;
                        total=0;
                        sender.setMessage(Integer.toString(user2Count)+" "+Integer.toString(user1Count));
                    }
                    count=0;
                    turn=0;
                    timeNow=0;
                    user1Pre=0;
                    user2Pre=0;
                    broadCast(sender);
                    user2same=new int[13];
                    user2flu=new int[4][13];
                    user1same=new int[13];
                    user1flu=new int[4][13];
                    totalCard=new int[52];
                }

            } catch (IOException e) {
                //e.printStackTrace();
                //System.out.println("error1");
                this.list.remove(this);
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
                //System.out.println("error2");
                this.list.remove(this);
            }
        }

    }

    public void broadCast(makeInfo sendInfo) throws IOException {
        for(ObjectOutputStream chatthread : list){
            chatthread.writeObject(sendInfo);
            chatthread.flush();
        }
    }

    public int getCard(){
        Random random = new Random();
        int temp1;
        while(true) {
            int temp = random.nextInt(51);
            if (totalCard[temp] == 0) {
                totalCard[temp] += 1;
                temp1 = temp;
                break;
            }
        }
        return temp1;
    }

    public void threeCard() throws IOException {
        int scard1=getCard();
        int scard2=getCard();
        int scard3=getCard();
        Card tempcard1=new Card(scard1);
        Card tempcard2=new Card(scard2);
        Card tempcard3=new Card(scard3);

        // store the card
        int fron1=scard1/13;
        int fron2=scard2/13;
        int fron3=scard3/13;
        int back1=scard1%13;
        int back2=scard2%13;
        int back3=scard3%13;

        user1same[back1]+=1;
        user1same[back2]+=1;
        user1same[back3]+=1;
        user2same[back1]+=1;
        user2same[back2]+=1;
        user2same[back3]+=1;

        user1flu[fron1][back1]+=1;
        user1flu[fron2][back2]+=1;
        user1flu[fron3][back3]+=1;
        user2flu[fron1][back1]+=1;
        user2flu[fron2][back2]+=1;
        user2flu[fron3][back3]+=1;

        // store the card

        makeInfo card1=new makeInfo();
        card1.setCmd(6);
        card1.setMessage("0 "+tempcard1.Realcard());
        broadCast(card1);

        makeInfo card2=new makeInfo();
        card2.setCmd(6);
        card2.setMessage("1 "+tempcard2.Realcard());
        broadCast(card2);

        makeInfo card3=new makeInfo();
        card3.setCmd(6);
        card3.setMessage("2 "+tempcard3.Realcard());
        broadCast(card3);

    }

    public void oneCard(int num) throws IOException {
        int scard=getCard();
        Card tempcard1=new Card(scard);

        // store the card
        int fron=scard/13;
        int back=scard%13;

        user1same[back]+=1;
        user2same[back]+=1;

        user1flu[fron][back]+=1;
        user2flu[fron][back]+=1;
        // store the card

        makeInfo card=new makeInfo();
        card.setCmd(6);
        card.setMessage(Integer.toString(num)+" "+tempcard1.Realcard());
        broadCast(card);
    }

    public int lastCheck(cardValue value){
        if(value.straightFlu()){
            return 10;
        }else if(value.fourCard()){
            return 9;
        }else if(value.fullHouse()){
            return 8;
        }else if(value.flush()){
            return 7;
        }else if(value.straight()){
            return 6;
        }else if(value.triple()){
            return 5;
        }else if(value.twoPair()){
            return 4;
        }else if(value.onePair()){
            return 3;
        }else{
            return 2;
        }
    }

    public void firstTurn(ObjectInputStream reader, ObjectOutputStream writer)
            throws IOException, ClassNotFoundException {
        makeInfo read= (makeInfo) reader.readObject();
        int cmd=read.getCmd();
        if(cmd==2){
            String name=read.getNickName();
            String raise=read.getMessage();
            System.out.println(raise);
            int raiseInt=Integer.parseInt(raise);
            String nextTurn="";
            if(raiseInt != betCount){
                betCount=raiseInt;
                if (name.equals(user1Name)){//user1
                    turn=1;
                    user1Count-=raiseInt;
                    nextTurn=user2Name;
                }else{//user2
                    turn=0;
                    user2Count-=raiseInt;
                    nextTurn=user1Name;
                }
                System.out.println(raiseInt);

                // send whos turn
                makeInfo sender=new makeInfo();
                sender.setCmd(3);
                sender.setNickName(name);
                sender.setNickName(nextTurn);
                broadCast(sender);


                firstTurn(reader, writer);
            }else if(raiseInt==gameFrame.betCount){
                betCount=raiseInt;
                if (turn==0){//user1
                    user1Count-=raiseInt;
                }else{//user2
                    user2Count-=raiseInt;
                }
            }
        }

    }

}






