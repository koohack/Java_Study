import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    //private final String ipAddress;
    static final int port=10800;

    static ServerSocket serverSocket = null;
    static InputStream inputStream=null;
    static BufferedReader bufferedReader= null;
    static OutputStream outputStream= null;
    static OutputStreamWriter outputStreamWriter = null;
    static PrintWriter printWriter=null;
    static Scanner scanner = new Scanner(System.in);
    static List<Chatting> list;

    public static void main(String args[]) throws IOException {
        serverSocket = new ServerSocket();
        list=new ArrayList<Chatting>();

        // bind socket
        InetAddress inetAddress = InetAddress.getLocalHost();
        String localhost = inetAddress.getHostAddress();
        serverSocket.bind(new InetSocketAddress(localhost, port));
        System.out.println("[server] binding "+localhost);

        while(true) {
            System.out.println("===========================");
            System.out.println("Waiting for connection");

            Socket socket = serverSocket.accept();
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
                info= (makeInfo) reader.readObject();

                nickName=info.getNickName();


                // 0 is end the system
                if(info.getCmd()==0){
                    //makeInfo sendInfo=new makeInfo();
                    //sendInfo.setMessage("exit");
                    //sendInfo.setCmd(0);
                    //writer.writeObject(sendInfo);
                    //writer.flush();
                    reader.close();
                    writer.close();
                    socket.close();
                    list.remove(this);
                }else if(info.getCmd()==1){
                    // 1 is join
                    makeInfo sendInfo = new makeInfo();
                    sendInfo.setCmd(2);
                    sendInfo.setMessage(nickName+"님이 입장하였습니다.");
                    //broadCast(sendInfo);
                    System.out.println("입장함");
                }else if(info.getCmd()==2){
                    makeInfo sendInfo=new makeInfo();
                    sendInfo.setMessage("["+nickName+"]"+" : "+info.getMessage());
                    //broadCast(sendInfo);
                }
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


