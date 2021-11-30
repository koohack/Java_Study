import java.io.Serializable;
class makeInfo implements Serializable{
    private String nickName;
    private String message;
    private int cmd;

    public String getNickName(){
        return nickName;
    }
    public String getMessage(){
        return message;
    }
    public int getCmd(){
        return cmd;
    }
    public void setNickName(String nickName){
        this.nickName=nickName;
    }
    public void setMessage(String message){
        this.message=message;
    }
    public void setCmd(int cmd){
        this.cmd=cmd;
    }





}
