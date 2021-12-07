import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class Client {

    static ObjectInputStream reader;
    static ObjectOutputStream writer;
    static BufferedReader br;
    static PrintWriter pw;

    public static void main(String args[]) throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String localhost = inetAddress.getHostAddress();
        Socket socket=new Socket(localhost, 5000);
        Scanner scanner = new Scanner(System.in);
        // for read
        //clientThread thread = new clientThread(socket);
        System.out.println("test");
        writer=new ObjectOutputStream(socket.getOutputStream());
        System.out.println("mei");
        //reader=new ObjectInputStream(socket.getInputStream());

        // for write
        while(true){
            System.out.print("get line : ");
            String message=scanner.next();
            makeInfo sender=new makeInfo();
            sender.setMessage(message);
            System.out.println("sent");
            writer.writeObject(sender);
            writer.flush();
        }


    }
}

class clientThread extends Thread {
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private String nickName;

    public clientThread(Socket socket) throws IOException {
        this.socket=socket;
        writer=new ObjectOutputStream(socket.getOutputStream());
        reader=new ObjectInputStream(socket.getInputStream());
    }

    public void run(){
        while(true){
            try {
                makeInfo read= (makeInfo) reader.readObject();
                System.out.println(read.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }



    }



}
