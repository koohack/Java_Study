import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsAdapter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class gameFrame extends JFrame implements ActionListener {
    // value
    private static String nickName;

    // Spliter
    private static JSplitPane sp1;
    private static BorderLayout bl=new BorderLayout();

    // Main 2 GUI
    private static JPanel gameBackGround=new JPanel();
    private static JPanel chat=new JPanel();

    // Chat GUI
    private static JTextArea chatStore=new JTextArea();
    private static JTextField chatLine=new JTextField();
    private static JScrollPane chatScroll;
    private static JButton chatButton=new JButton("입력");

    // Start GUI
    private static JButton firstButton=new JButton("시작");
    private static JTextField nickNameSetting=new JTextField();
    private static JLabel startLable=new JLabel("Nickname : ");

    // Client System
    private static Client client;
    private static Socket socket;

    // For server side
    private static ObjectInputStream reader;
    private static ObjectOutputStream writer;

    // Game buttons
    private static JButton startButton=new JButton("시작");
    private static JButton raiseButton=new JButton("Raise");
    private static JButton callButton=new JButton("Call");
    private static JButton dieButton=new JButton("Die");
    private static JTextField raiseMoney=new JTextField();

    // Share card
    private static JLabel shareText=new JLabel("Shared Card");
    private static JLabel shareCard1=new JLabel("x");
    private static JLabel shareCard2=new JLabel("x");
    private static JLabel shareCard3=new JLabel("x");
    private static JLabel shareCard4=new JLabel("x");
    private static JLabel shareCard5=new JLabel("x");
    private static JPanel shareBox=new JPanel();
    private static GridLayout shareCardLay=new GridLayout(1, 5);

    // total money
    private static JLabel totalMoneyText=new JLabel("Total Money : ");
    private static JLabel totalMoney=new JLabel("x");

    // User Info
    // User1
    private static JLabel user1Name=new JLabel("No User");
    private static JLabel user1Card1=new JLabel("No Card");
    private static JLabel user1Card2=new JLabel("No Card");
    private static JLabel user1CoinText=new JLabel("Current Coin : ");
    private static JLabel user1CointRemain=new JLabel("0");
    private static JLabel user1State=new JLabel("x");
    private static JPanel user1CardBox=new JPanel();
    private static JLabel user1Final=new JLabel();
    private static JLabel user1Turn=new JLabel("Not My Turn");
    private static GridLayout user1CardLay=new GridLayout(1,2);

    // User 2
    private static JLabel user2Name=new JLabel("No User");
    private static JLabel user2Card1=new JLabel("No Card");
    private static JLabel user2Card2=new JLabel("No Card");
    private static JLabel user2CoinText=new JLabel("Current Coin : ");
    private static JLabel user2CointRemain=new JLabel("0");
    private static JLabel user2State=new JLabel("x");
    private static JPanel user2CardBox=new JPanel();
    private static JLabel user2Final=new JLabel();
    private static JLabel user2Turn=new JLabel("Not My Turn");
    private static GridLayout user2CardLay=new GridLayout(1,2);

    // User 3
    private static JLabel user3Name=new JLabel("No User");
    private static JLabel user3Card1=new JLabel("No Card");
    private static JLabel user3Card2=new JLabel("No Card");
    private static JLabel user3CoinText=new JLabel("Current Coin : ");
    private static JLabel user3CointRemain=new JLabel("0");
    private static JLabel user3State=new JLabel("x");
    private static JPanel user3CardBox=new JPanel();
    private static JLabel user3Final=new JLabel();
    private static JLabel user3Turn=new JLabel("Not My Turn");
    private static GridLayout user3CardLay=new GridLayout(1,2);

    // User 4
    private static JLabel user4Name=new JLabel("No User");
    private static JLabel user4Card1=new JLabel("No Card");
    private static JLabel user4Card2=new JLabel("No Card");
    private static JLabel user4CoinText=new JLabel("Current Coin : ");
    private static JLabel user4CointRemain=new JLabel("0");
    private static JLabel user4State=new JLabel("x");
    private static JPanel user4CardBox=new JPanel();
    private static JLabel user4Final=new JLabel();
    private static JLabel user4Turn=new JLabel("Not My Turn");
    private static GridLayout user4CardLay=new GridLayout(1,2);

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

    public void setGameBackGround() throws IOException {
        Container container=this.getContentPane();

        // User Infomation
        /*
        // User 1
        user1Turn.setBounds(0, 180, 150, 30);
        user1Turn.setHorizontalAlignment(JLabel.CENTER);
        user1Turn.setForeground(Color.blue);
        container.add(user1Turn);

        user1Name.setBounds(0, 200, 150, 50);
        user1Name.setHorizontalAlignment(JLabel.CENTER);
        user1Name.setFont(new Font("Serif", Font.BOLD, 20));
        container.add(user1Name);

        user1CardBox.setBorder(new TitledBorder(new LineBorder(Color.red, 4)));
        user1CardBox.setBounds(0, 250, 150, 100);
        user1CardBox.setLayout(user1CardLay);
        user1Card1.setHorizontalAlignment(JLabel.CENTER);
        user1Card2.setHorizontalAlignment(JLabel.CENTER);
        user1CardBox.add(user1Card1);
        user1CardBox.add(user1Card2);
        container.add(user1CardBox);

        user1CoinText.setBounds(0, 350, 100, 30);
        container.add(user1CoinText);

        user1CointRemain.setBounds(100, 350, 40, 30);
        container.add(user1CointRemain);

        user1State.setBounds(180, 275, 50, 50);
        container.add(user1State);

         */

        // User 2
        user2Turn.setBounds(0, 480, 150, 30);
        user2Turn.setHorizontalAlignment(JLabel.CENTER);
        user2Turn.setForeground(Color.blue);
        container.add(user2Turn);

        user2Name.setBounds(0, 500, 150, 50);
        user2Name.setHorizontalAlignment(JLabel.CENTER);
        user2Name.setFont(new Font("Serif", Font.BOLD, 20));
        container.add(user2Name);

        user2CardBox.setBorder(new TitledBorder(new LineBorder(Color.red, 4)));
        user2CardBox.setBounds(0, 550, 150, 100);
        user2CardBox.setLayout(user2CardLay);
        user2Card1.setHorizontalAlignment(JLabel.CENTER);
        user2Card2.setHorizontalAlignment(JLabel.CENTER);
        user2CardBox.add(user2Card1);
        user2CardBox.add(user2Card2);
        container.add(user2CardBox);

        user2CoinText.setBounds(0, 650, 100, 30);
        container.add(user2CoinText);

        user2CointRemain.setBounds(100, 650, 40, 30);
        container.add(user2CointRemain);

        user2State.setBounds(180, 575, 50, 50);
        container.add(user2State);

        /*
        // User 3
        user3Turn.setBounds(550, 180, 150, 30);
        user3Turn.setHorizontalAlignment(JLabel.CENTER);
        user3Turn.setForeground(Color.blue);
        container.add(user3Turn);

        user3Name.setBounds(550, 200, 150, 50);
        user3Name.setHorizontalAlignment(JLabel.CENTER);
        user3Name.setFont(new Font("Serif", Font.BOLD, 20));
        container.add(user3Name);

        user3CardBox.setBorder(new TitledBorder(new LineBorder(Color.red, 4)));
        user3CardBox.setBounds(550, 250, 150, 100);
        user3CardBox.setLayout(user3CardLay);
        user3Card1.setHorizontalAlignment(JLabel.CENTER);
        user3Card2.setHorizontalAlignment(JLabel.CENTER);
        user3CardBox.add(user3Card1);
        user3CardBox.add(user3Card2);
        container.add(user3CardBox);

        user3CoinText.setBounds(550, 350, 100, 30);
        container.add(user3CoinText);

        user3CointRemain.setBounds(650, 350, 40, 30);
        container.add(user3CointRemain);

        user3State.setBounds(510, 275, 50, 50);
        container.add(user3State);

         */

        // User 4
        user4Turn.setBounds(550, 480, 150, 30);
        user4Turn.setHorizontalAlignment(JLabel.CENTER);
        user4Turn.setForeground(Color.blue);
        container.add(user4Turn);

        user4Name.setBounds(550, 500, 150, 50);
        user4Name.setHorizontalAlignment(JLabel.CENTER);
        user4Name.setFont(new Font("Serif", Font.BOLD, 20));
        container.add(user4Name);

        user4CardBox.setBorder(new TitledBorder(new LineBorder(Color.red, 4)));
        user4CardBox.setBounds(550, 550, 150, 100);
        user4CardBox.setLayout(user4CardLay);
        user4Card1.setHorizontalAlignment(JLabel.CENTER);
        user4Card2.setHorizontalAlignment(JLabel.CENTER);
        user4CardBox.add(user4Card1);
        user4CardBox.add(user4Card2);
        container.add(user4CardBox);

        user4CoinText.setBounds(550, 650, 100, 30);
        container.add(user4CoinText);

        user4CointRemain.setBounds(650, 650, 40, 30);
        container.add(user4CointRemain);

        user4State.setBounds(510, 575, 50, 50);
        container.add(user4State);

        // game object
        raiseMoney.setBounds(20, 860, 150, 40);
        container.add(raiseMoney);

        raiseButton.setBounds(20, 900, 150, 40);
        raiseButton.addActionListener(this);
        container.add(raiseButton);

        callButton.setBounds(190, 900, 150, 40);
        callButton.addActionListener(this);
        container.add(callButton);

        dieButton.setBounds(360, 900, 150, 40);
        dieButton.addActionListener(this);
        container.add(dieButton);

        startButton.setBounds(530, 900, 150, 40);
        startButton.addActionListener(this);
        container.add(startButton);

        // Share Card
        shareText.setBounds(320, 370, 150, 30);
        container.add(shareText);

        shareBox.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
        shareBox.setLayout(shareCardLay);
        shareBox.setBounds(130, 400, 450, 100);
        shareCard1.setHorizontalAlignment(JLabel.CENTER);
        shareCard2.setHorizontalAlignment(JLabel.CENTER);
        shareCard3.setHorizontalAlignment(JLabel.CENTER);
        shareCard4.setHorizontalAlignment(JLabel.CENTER);
        shareCard5.setHorizontalAlignment(JLabel.CENTER);
        shareBox.add(shareCard1);
        shareBox.add(shareCard2);
        shareBox.add(shareCard3);
        shareBox.add(shareCard4);
        shareBox.add(shareCard5);
        container.add(shareBox);

        // Total Money
        totalMoneyText.setBounds(320, 100, 100, 30);
        container.add(totalMoneyText);

        totalMoney.setBounds(410, 100, 150, 30);
        container.add(totalMoney);

        // Get server socket
        Client client=new Client(nickName);
        socket=client.getSocket();
        writer=client.getWriter();
        reader=client.getReader();

        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==firstButton){
            String nickName=nickNameSetting.getText();
            try {
                // Get server socket

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

                // remove
                revalidate();
                repaint();

                // start the game
                setGameBackGround();

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

class receiverThread extends Thread {
    private ObjectInputStream reader;

    public receiverThread(ObjectInputStream reader) throws IOException {
        this.reader=reader;
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
