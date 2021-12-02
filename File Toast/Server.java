import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    //private final String ipAddress;
    static final int port=5000;
    static Scanner scanner = new Scanner(System.in);
    static BufferedReader br;
    static ArrayList<th> list;

    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        list=new ArrayList<th>();
        System.out.println("server is ready");
        while(true) {
            //System.out.println("waiting socket");
            Socket socket = serverSocket.accept();
            //System.out.println("socket connected");
            th t= new th(socket);
            list.add(t);
            t.start();
        }
    }
}

class th extends Thread {
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private String nickName;
    BufferedReader br;
    PrintWriter pw;

    public th(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw=new PrintWriter(socket.getOutputStream(), true);

            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("File Name : " + line);

                File f = new File(line);
                if (f.exists()) {
                    BufferedReader infile=new BufferedReader(new FileReader(f));
                    String wline="";
                    while ((wline=infile.readLine())!=null){
                        pw.println(wline);
                    }
                    pw.println("Yes Yes");
                } else {
                    pw.println("No such file");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}