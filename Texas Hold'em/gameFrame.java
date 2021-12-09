import javax.swing.*;
import java.awt.*;

public class gameFrame extends JFrame{
    JSplitPane sp1;
    public static void main(String args[]){
        gameFrame frame=new gameFrame();
        frame.setTitle("Texas Hold'em");
        frame.setBounds(200, 10, 1000, 1000);
        frame.setVisible(true);
    }

    public gameFrame(){
        sp1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);


    }


}
