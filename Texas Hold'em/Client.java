import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class Client {

    static ObjectInputStream reader;
    static ObjectOutputStream writer;
    static Scanner scanner = new Scanner(System.in);
    static BufferedReader br;
    static PrintWriter pw;

    public static void main(String args[]) throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String localhost = inetAddress.getHostAddress();
        Socket socket=new Socket(localhost, 5000);
        //String line=scanner.next();

        br=new BufferedReader(new InputStreamReader(System.in));
        pw=new PrintWriter(socket.getOutputStream(), true);


        String line="";
        while((line=br.readLine())!=null){
            pw.println(line);
        }
    }
}
