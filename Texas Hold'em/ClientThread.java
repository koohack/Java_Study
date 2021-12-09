import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private String nickName;

    public ClientThread(Socket socket) throws IOException {
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
