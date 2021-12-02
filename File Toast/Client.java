import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
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

        threa t=new threa(socket);
        t.start();

        String line="";
        System.out.print("Requested file name : ");
        while((line=br.readLine())!=null){
            pw.println(line);
        }
    }
}

class threa extends Thread {
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private String nickName;
    BufferedReader br;

    public threa(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                if(line.split(" ")[0].equals("No")){
                    System.out.println("No such file.");
                    System.out.print("Requested file name : ");
                }else if(line.split(" ")[0].equals("Yes")){
                    System.out.print("Requested file name : ");
                }else{
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

