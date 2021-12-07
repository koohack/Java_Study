import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    //private final String ipAddress;
    static final int port=5000;

    static ServerSocket serverSocket = null;
    static List<Chatting> list;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(port);
        list=new ArrayList<Chatting>();

        // bind socket
        //InetAddress inetAddress = InetAddress.getLocalHost();
        //String localhost = inetAddress.getHostAddress();
        //serverSocket.bind(new InetSocketAddress(localhost, port));
        //System.out.println("[server] binding "+localhost);
        System.out.println("===========================");
        System.out.println("Waiting for connection");
        Socket socket = serverSocket.accept();
        System.out.println("connected");
        ObjectInputStream reader;
        ObjectOutputStream writer;
        reader=new ObjectInputStream(socket.getInputStream());


        makeInfo read= (makeInfo) reader.readObject();
        System.out.println(read.getMessage());

        while(true) {
            int a=1;
            if(a==1){
                break;
            }
            //Chatting chattingthread = new Chatting(socket, list);
            //list.add(chattingthread);
            //chattingthread.start();
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

                // cmd 1 is simple send check send
                sender.setCmd(1);
                sender.setMessage("okokok");
                broadCast(sender);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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


