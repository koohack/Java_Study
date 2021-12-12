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
    static String nickName;
    static String otherName;
    static ObjectOutputStream user1writer;
    static ObjectOutputStream user2writer;
    static int count=10000;
    static int betCount=0;
    static int preRaise=0;

    // Spliter
    static JSplitPane sp1;
    static BorderLayout bl=new BorderLayout();

    // Main 2 GUI
    static JPanel gameBackGround=new JPanel();
    static JPanel chat=new JPanel();

    // Chat GUI
    static JTextArea chatStore=new JTextArea();
    static JTextField chatLine=new JTextField();
    static JScrollPane chatScroll;
    static JButton chatButton=new JButton("입력");

    // Start GUI
    static JButton firstButton=new JButton("시작");
    static JTextField nickNameSetting=new JTextField();
    static JLabel startLable=new JLabel("Nickname : ");

    // Client System
    static Client client;
    static Socket socket;

    // For server side
    static ObjectInputStream reader;
    static ObjectOutputStream writer;

    // Game buttons
    static JButton startButton=new JButton("시작");
    static JButton raiseButton=new JButton("Raise");
    static JButton callButton=new JButton("Call");
    static JButton dieButton=new JButton("Die");
    static JTextField raiseMoney=new JTextField();

    // Share card
    static JLabel shareText=new JLabel("Shared Card");
    static JLabel shareCard1=new JLabel("x");
    static JLabel shareCard2=new JLabel("x");
    static JLabel shareCard3=new JLabel("x");
    static JLabel shareCard4=new JLabel("x");
    static JLabel shareCard5=new JLabel("x");
    static JPanel shareBox=new JPanel();
    static GridLayout shareCardLay=new GridLayout(1, 5);

    // total money
    static JLabel totalMoneyText=new JLabel("Total Money : ");
    static JLabel totalMoney=new JLabel("x");
    static JLabel preText=new JLabel("Previous bet : ");
    static JLabel pre=new JLabel("0");

    // User Info
    // User1
    static JLabel user1Name=new JLabel("No User");
    static JLabel user1Card1=new JLabel("No Card");
    static JLabel user1Card2=new JLabel("No Card");
    static JLabel user1CoinText=new JLabel("Current Coin : ");
    static JLabel user1CointRemain=new JLabel("0");
    static JLabel user1State=new JLabel("x");
    static JPanel user1CardBox=new JPanel();
    static JLabel user1Final=new JLabel();
    static JLabel user1Turn=new JLabel("Not My Turn");
    static GridLayout user1CardLay=new GridLayout(1,2);

    // User 2
    static JLabel user2Name=new JLabel("No User");
    static JLabel user2Card1=new JLabel("No Card");
    static JLabel user2Card2=new JLabel("No Card");
    static JLabel user2CoinText=new JLabel("Current Coin : ");
    static JLabel user2CointRemain=new JLabel("0");
    static JLabel user2State=new JLabel("x");
    static JPanel user2CardBox=new JPanel();
    static JLabel user2Final=new JLabel();
    static JLabel user2Turn=new JLabel("Not My Turn");
    static GridLayout user2CardLay=new GridLayout(1,2);

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
    static JLabel user4Name=new JLabel("No User");
    static JLabel user4Card1=new JLabel("No Card");
    static JLabel user4Card2=new JLabel("No Card");
    static JLabel user4CoinText=new JLabel("Current Coin : ");
    static JLabel user4CointRemain=new JLabel("0");
    static JLabel user4State=new JLabel("x");
    static JPanel user4CardBox=new JPanel();
    static JLabel user4Final=new JLabel();
    static JLabel user4Turn=new JLabel("Not My Turn");
    static GridLayout user4CardLay=new GridLayout(1,2);

    public static void main(String args[]){
        gameFrame frame=new gameFrame();
        frame.setTitle("Texas Hold'em");
        frame.setBounds(200, 10, 1000, 500);
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
        chatStore.setBounds(700, 0, 300, 430);
        chatLine.setBounds(700, 430, 220, 30);
        chatButton.setBounds(920, 430, 65, 30);

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
        startLable.setBounds(250, 200, 100, 30);
        nickNameSetting.setBounds(320, 200, 150, 30);
        firstButton.setBounds(250, 235, 220, 30);

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
        user2Turn.setBounds(0, 150, 150, 30);
        user2Turn.setHorizontalAlignment(JLabel.CENTER);
        user2Turn.setForeground(Color.blue);
        container.add(user2Turn);

        user2Name.setBounds(0, 160, 150, 50);
        user2Name.setHorizontalAlignment(JLabel.CENTER);
        user2Name.setFont(new Font("Serif", Font.BOLD, 20));
        container.add(user2Name);

        user2CardBox.setBorder(new TitledBorder(new LineBorder(Color.red, 4)));
        user2CardBox.setBounds(0, 205, 150, 100);
        user2CardBox.setLayout(user2CardLay);
        user2Card1.setHorizontalAlignment(JLabel.CENTER);
        user2Card2.setHorizontalAlignment(JLabel.CENTER);
        user2CardBox.add(user2Card1);
        user2CardBox.add(user2Card2);
        container.add(user2CardBox);

        user2CoinText.setBounds(0, 305, 100, 30);
        container.add(user2CoinText);

        user2CointRemain.setBounds(100, 305, 40, 30);
        container.add(user2CointRemain);

        user2State.setBounds(180, 235, 50, 50);
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
        user4Turn.setBounds(550, 150, 150, 30);
        user4Turn.setHorizontalAlignment(JLabel.CENTER);
        user4Turn.setForeground(Color.blue);
        container.add(user4Turn);

        user4Name.setBounds(550, 160, 150, 50);
        user4Name.setHorizontalAlignment(JLabel.CENTER);
        user4Name.setFont(new Font("Serif", Font.BOLD, 20));
        container.add(user4Name);

        user4CardBox.setBorder(new TitledBorder(new LineBorder(Color.red, 4)));
        user4CardBox.setBounds(550, 205, 150, 100);
        user4CardBox.setLayout(user4CardLay);
        user4Card1.setHorizontalAlignment(JLabel.CENTER);
        user4Card2.setHorizontalAlignment(JLabel.CENTER);
        user4CardBox.add(user4Card1);
        user4CardBox.add(user4Card2);
        container.add(user4CardBox);

        user4CoinText.setBounds(550, 305, 100, 30);
        container.add(user4CoinText);

        user4CointRemain.setBounds(650, 305, 40, 30);
        container.add(user4CointRemain);

        user4State.setBounds(510, 235, 50, 50);
        container.add(user4State);

        // game object
        raiseMoney.setBounds(20, 350, 150, 40);
        container.add(raiseMoney);

        raiseButton.setBounds(20, 390, 150, 40);
        raiseButton.addActionListener(this);
        container.add(raiseButton);

        callButton.setBounds(190, 390, 150, 40);
        callButton.addActionListener(this);
        callButton.setEnabled(false);
        container.add(callButton);

        dieButton.setBounds(360, 390, 150, 40);
        dieButton.addActionListener(this);
        container.add(dieButton);

        startButton.setBounds(530, 390, 150, 40);
        startButton.addActionListener(this);
        container.add(startButton);

        // Share Card
        shareText.setBounds(320, 35, 150, 30);
        container.add(shareText);

        shareBox.setBorder(new TitledBorder(new LineBorder(Color.orange, 3)));
        shareBox.setLayout(shareCardLay);
        shareBox.setBounds(130, 60, 450, 100);
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
        totalMoneyText.setBounds(310, 10, 100, 30);
        container.add(totalMoneyText);

        totalMoney.setBounds(400, 10, 150, 30);
        container.add(totalMoney);

        preText.setBounds(0, 10, 100, 30);
        container.add(preText);

        pre.setBounds(100, 10, 150, 30);
        container.add(pre);


        // Get server socket
        Client client=new Client(nickName);
        socket=client.getSocket();
        writer=client.getWriter();
        reader=client.getReader();
        revalidate();
        repaint();

        receiverThread t=new receiverThread(reader, nickName, writer);
        t.start();

        // set user infomation
        user2State.setText("wait");
        user2CointRemain.setText("10000");

        // Send inital message
        makeInfo sender=new makeInfo();
        sender.setCmd(0);
        sender.setNickName(nickName);
        writer.writeObject(sender);
        writer.flush();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==firstButton && nickNameSetting.getText()!=""){
            String nick=nickNameSetting.getText();
            try {
                // remove first page
                Container container=this.getContentPane();
                container.remove(firstButton);
                container.remove(startLable);
                container.remove(nickNameSetting);

                // set name
                user2Name.setText(nick);
                nickName=nick;

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
                sender.setCmd(-1);
                writer.writeObject(sender);
                writer.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if(e.getSource()==startButton){
            try {
                shareCard1.setText("x");
                shareCard2.setText("x");
                shareCard3.setText("x");
                shareCard4.setText("x");
                shareCard5.setText("x");

                user2Card1.setText("No Card");
                user2Card2.setText("No Card");
                user4Card1.setText("No Card");
                user4Card2.setText("No Card");

                makeInfo sender = new makeInfo();
                sender.setCmd(1);
                startButton.setEnabled(false);

                writer.writeObject(sender);
                writer.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if(e.getSource()==raiseButton){
            try {
                String raise=gameFrame.raiseMoney.getText();
                int money=Integer.parseInt(raise);
                String nowm=gameFrame.user2CointRemain.getText();
                int rmoney=Integer.parseInt(nowm);
                raiseMoney.setText("");
                //if (money > rmoney){
                  //  return;
                //}

                System.out.println(money);
                makeInfo sender = new makeInfo();
                sender.setCmd(2);
                sender.setNickName(nickName);
                sender.setMessage(raise);

                writer.writeObject(sender);
                writer.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if (e.getSource()==callButton){
            try {
                makeInfo sender=new makeInfo();
                String preraise=pre.getText();
                sender.setCmd(3);
                sender.setMessage(preraise);
                sender.setNickName(nickName);

                writer.writeObject(sender);
                writer.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if(e.getSource()==dieButton){
            makeInfo sender=new makeInfo();
            sender.setCmd(3);
            sender.setNickName(nickName);
            try {
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
    private String nickName;
    private ObjectOutputStream writer;

    public receiverThread(ObjectInputStream reader, String nickName, ObjectOutputStream wrier) throws IOException {
        this.reader=reader;
        this.nickName=nickName;
        this.writer=wrier;
    }

    public void run(){
        while(true){
            try {
                makeInfo read= (makeInfo) reader.readObject();
                int cmd=read.getCmd();
                if(cmd==0){
                    if(!(read.getNickName()).equals(gameFrame.user2Name.getText())){
                        gameFrame.user4Name.setText(read.getNickName());
                        gameFrame.user4State.setText("wait");
                        gameFrame.user4CointRemain.setText("10000");
                    }else{
                        gameFrame.user4Name.setText(read.getMessage());
                        gameFrame.user4State.setText("wait");
                        gameFrame.user4CointRemain.setText("10000");
                    }
                }else if(cmd==1){//get first card
                    String name=read.getNickName();
                    if(name.equals(nickName)){
                        String[] massage=read.getMessage().split(" ");
                        int index=Integer.parseInt(massage[0]);
                        int card=Integer.parseInt(massage[1]);
                        if(index==0){
                            Card getstr=new Card(card);
                            String realCard=getstr.Realcard();
                            gameFrame.user2Card1.setText(realCard);
                        }else{
                            Card getstr=new Card(card);
                            String realCard=getstr.Realcard();
                            gameFrame.user2Card2.setText(realCard);
                        }
                    }else{
                        String[] massage=read.getMessage().split(" ");
                        int index=Integer.parseInt(massage[0]);
                        int card=Integer.parseInt(massage[1]);
                        if(index==0){
                            gameFrame.user4Card1.setText("Secret");
                        }else{
                            gameFrame.user4Card2.setText("Secret");
                        }
                    }
                }else if(cmd==2){//turn
                    String name=read.getNickName();
                    int bet=Integer.parseInt(read.getMessage());
                    if (nickName.equals(name)){
                        gameFrame.betCount=bet;
                        gameFrame.user4State.setText("Betting");
                        gameFrame.user4Turn.setText("It's my turn");
                        gameFrame.user2State.setText("wait");
                        gameFrame.user2Turn.setText("Not my turn");
                    }else{
                        gameFrame.betCount=bet;
                        gameFrame.user2State.setText("Betting");
                        gameFrame.user2Turn.setText("It's my turn");
                        gameFrame.user4State.setText("wait");
                        gameFrame.user4Turn.setText("Not my turn");
                    }
                }else if (cmd==3){
                    String name=read.getNickName();
                    String mess=read.getMessage();
                    if (nickName.equals(name)){
                        System.out.println("=================");
                        System.out.println("chang my value");
                        System.out.println("=================");
                        String re=gameFrame.user2CointRemain.getText();
                        System.out.println(re);
                        gameFrame.user2CointRemain.setText(mess);
                        gameFrame.user4State.setText("Betting");
                        gameFrame.user4Turn.setText("It's my turn");
                        gameFrame.user2State.setText("wait");
                        gameFrame.user2Turn.setText("Not my turn");
                    }else{
                        System.out.println("=================");
                        System.out.println("others value");
                        System.out.println("=================");
                        String re=gameFrame.user4CointRemain.getText();
                        System.out.println(re);
                        gameFrame.user4CointRemain.setText(mess);
                        gameFrame.user2State.setText("Betting");
                        gameFrame.user2Turn.setText("It's my turn");
                        gameFrame.user4State.setText("wait");
                        gameFrame.user4Turn.setText("Not my turn");
                    }
                }else if(cmd==4){
                    String total=read.getMessage();
                    int to=Integer.parseInt(total);
                    gameFrame.totalMoney.setText(total);
                }else if (cmd==5){
                    String pre=read.getMessage();
                    gameFrame.pre.setText(pre);
                }else if (cmd==6){
                    String[] temp=read.getMessage().split(" ");
                    int index=Integer.parseInt(temp[0]);
                    String cardName=temp[1]+temp[2];

                    if(index==0){
                        gameFrame.shareCard1.setText(cardName);
                    }else if(index==1){
                        gameFrame.shareCard2.setText(cardName);
                    }else if(index==2){
                        gameFrame.shareCard3.setText(cardName);
                    }else if (index==3){
                        gameFrame.shareCard4.setText(cardName);
                    }else if(index==4){
                        gameFrame.shareCard5.setText(cardName);
                    }
                }else if(cmd==7){
                    String name=read.getNickName();
                    if(name.equals(nickName)){
                        gameFrame.user2State.setText("Win!!!");
                        gameFrame.user4State.setText("Lose...");
                    }else if(name.equals("same")){
                        gameFrame.user2State.setText("Same");
                        gameFrame.user4State.setText("Same");
                    }else{
                        gameFrame.user4State.setText("Win!!!");
                        gameFrame.user2State.setText("Lose...");
                    }
                    gameFrame.totalMoney.setText("0");
                    gameFrame.pre.setText("0");
                    gameFrame.startButton.setEnabled(true);
                }else if(cmd==8){
                    String name=read.getNickName();
                    String count=read.getMessage();
                    int ct=Integer.parseInt(count);
                    if(nickName.equals(name)){
                        gameFrame.user2CointRemain.setText(count);
                    }else{
                        gameFrame.user4CointRemain.setText(count);
                    }
                    gameFrame.startButton.setEnabled(true);
                }else if(cmd==9){
                    gameFrame.totalMoney.setText("0");
                    gameFrame.user2State.setText("wait");
                    gameFrame.user4State.setText("wait");
                    gameFrame.user2Turn.setText("Not my turn");
                    gameFrame.user4Turn.setText("Not my turn");
                    gameFrame.startButton.setEnabled(true);
                }else if(cmd==10){//포기
                    String name=read.getNickName();
                    String mess=read.getMessage();
                    String[] temp=mess.split(" ");
                    if(name.equals(gameFrame.nickName)){
                        gameFrame.user2CointRemain.setText(temp[0]);
                        gameFrame.user4CointRemain.setText(temp[1]);
                        gameFrame.user4State.setText("Win!!!");
                        gameFrame.user2State.setText("Lose...");
                    }else{
                        gameFrame.user2CointRemain.setText(temp[1]);
                        gameFrame.user4CointRemain.setText(temp[0]);
                        gameFrame.user2State.setText("Win!!!");
                        gameFrame.user4State.setText("Lose...");
                    }
                    gameFrame.totalMoney.setText("0");
                    gameFrame.user2State.setText("wait");
                    gameFrame.user4State.setText("wait");
                    gameFrame.user2Turn.setText("Not my turn");
                    gameFrame.user4Turn.setText("Not my turn");
                    gameFrame.startButton.setEnabled(true);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
