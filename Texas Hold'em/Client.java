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
    private String nickName;
    private Socket socket;

    public Client(String nickName) throws IOException {
        // set Nickname
        this.nickName=nickName;

        // connect to the server
        InetAddress inetAddress = InetAddress.getLocalHost();
        String localhost = inetAddress.getHostAddress();
        this.socket=new Socket(localhost, 5000);
        System.out.println("=========================");
        System.out.println("Connected");

        // for send to server
        writer=new ObjectOutputStream(socket.getOutputStream());
        reader=new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket(){
        return this.socket;
    }
    public ObjectOutputStream getWriter(){
        return writer;
    }
    public ObjectInputStream getReader(){
        return reader;
    }



}

