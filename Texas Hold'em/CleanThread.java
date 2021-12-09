import java.awt.*;

public class CleanThread extends Thread{
    private Container container;
    private Component component;

    public CleanThread(Container container, Component component){
        this.container=container;
        this.component=component;
    }

    public void run(){
        container.remove(component);
    }
}
