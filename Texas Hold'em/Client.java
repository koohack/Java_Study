import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client {

    static ObjectInputStream reader;
    static ObjectOutputStream writer;
    static String nickName;


    public static void main(String args[]) throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String localhost = inetAddress.getHostAddress();
        Socket socket=new Socket(localhost, 10800);
        System.out.println("==========================");
        System.out.println("Success to connect with server");

        writer=new ObjectOutputStream(socket.getOutputStream());
        reader=new ObjectInputStream(socket.getInputStream());

        makeInfo sendInfo=new makeInfo();
        sendInfo.setCmd(2);
        sendInfo.setMessage("이히");
        sendInfo.setNickName("망고");

        writer.writeObject(sendInfo);
        writer.flush();
    }
}
