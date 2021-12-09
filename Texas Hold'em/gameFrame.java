import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class gameFrame extends JFrame implements ActionListener {
    // Spliter
    private JSplitPane sp1;
    private BorderLayout bl=new BorderLayout();

    // Main 2 GUI
    private JPanel game=new JPanel();
    private JPanel chat=new JPanel();

    // Chat GUI
    private JTextArea chatStore=new JTextArea();
    private JTextField chatLine=new JTextField();
    private JScrollPane chatScroll;
    private JButton chatButton=new JButton("입력");

    // Start GUI
    private JButton firstButton=new JButton("시작");
    private JTextField nickNameSetting=new JTextField();
    private JLabel startLable=new JLabel("Nickname : ");

    // Client System
    private Client client;
    private Socket socket;

    // For server side
    private ObjectInputStream reader;
    private ObjectOutputStream writer;

    public static void main(String args[]){
        gameFrame frame=new gameFrame();
        frame.setTitle("Texas Hold'em");
        frame.setBounds(200, 10, 1000, 1000);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public gameFrame(){
        this.chatInit();
        this.startPage();
    }

    public void chatInit(){
        // chatting history should not be modified
        chatStore.setEditable(false);
        chatLine.setEditable(false);// After starting the game it should be changed
        chatButton.setEnabled(false);

        // set scroll as needs add some scroll bar
        chatScroll=new JScrollPane(chatStore);
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // no horizontal scroll
        chatScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // set position
        chatStore.setBounds(700, 0, 300, 930);
        chatLine.setBounds(700, 931, 220, 30);
        chatButton.setBounds(920, 931, 65, 30);

        // add component
        this.setLayout(null);
        Container container = this.getContentPane();
        container.add(chatStore);
        container.add(chatLine);
        container.add(chatButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startPage(){
        // set position
        Container container = this.getContentPane();
        startLable.setBounds(250, 400, 100, 30);
        nickNameSetting.setBounds(320, 400, 150, 30);
        firstButton.setBounds(250, 450, 220, 30);

        // add button click event
        firstButton.addActionListener(this);

        // add component
        container.add(startLable);
        container.add(nickNameSetting);
        container.add(firstButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==firstButton){
            String nickName=nickNameSetting.getText();
            try {
                // Get server socket
                Client client=new Client(nickName);
                socket=client.getSocket();
                writer=client.getWriter();
                reader=client.getReader();

                // Make server reciver
                //ClientThread clientThread=new ClientThread(socket);
                //clientThread.start();

                // remove first page
                Container container=this.getContentPane();
                container.remove(firstButton);
                container.remove(startLable);
                container.remove(nickNameSetting);

                // set chatting
                chatButton.setEnabled(true);
                chatLine.setEditable(true);
                chatButton.addActionListener(this);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if(e.getSource()==chatButton){
            try {
                String message=chatLine.getText();
                chatLine.setText("");

                makeInfo sender=new makeInfo();
                sender.setMessage(message);

                writer.writeObject(sender);
                writer.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
