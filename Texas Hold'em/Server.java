import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    // private final String ipAddress;
    static final int port=5000;
    static ServerSocket serverSocket = null;
    static List<Chatting> list;

    //
    static int[] userlist=new int[4];


    public static void main(String args[]) throws IOException, ClassNotFoundException {
        // get serverSocket
        serverSocket = new ServerSocket(port);
        System.out.println("[server] binding localhost");
        list=new ArrayList<Chatting>();

        while(true) {
            System.out.println("===========================");
            System.out.println("Waiting for socket connection");
            Socket socket = serverSocket.accept();
            System.out.println("===========================");
            System.out.println("connected");

            //make thread for one client
            Chatting chattingthread = new Chatting(socket, list);
            list.add(chattingthread);
            chattingthread.start();
        }
    }
}

class Chatting extends Thread{
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private List <Chatting> list;
    private String nickName;
    private makeInfo info=null;

    public Chatting(Socket socket, List<Chatting> list) throws IOException {
        this.socket=socket;
        this.list=list;
        writer=new ObjectOutputStream(socket.getOutputStream());
        reader=new ObjectInputStream(socket.getInputStream());
    }

    public void run(){

        while (true){
            try {
                if(socket.isConnected()){
                    info= (makeInfo) reader.readObject();
                }else{
                    break;
                }
                nickName=info.getNickName();

                System.out.println(info.getMessage());

                makeInfo sender = new makeInfo();

            } catch (IOException e) {
                e.printStackTrace();
                this.list.remove(this);
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                this.list.remove(this);
                break;
            }
        }// while loop
    }

    public void broadCast(makeInfo sendInfo) throws IOException {
        for(Chatting chatthread : list){
            chatthread.writer.writeObject(sendInfo);
            chatthread.writer.flush();
        }
    }
}

class gameThread extends Thread{

    public gameThread(){

    }

    public void run(){
        while(true){





        }
    }

}


