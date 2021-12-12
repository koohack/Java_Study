public class Card {
    int num;

    public Card(int num){
        this.num=num;
    }

    public String cardString(){
        return Integer.toString(this.num);
    }

    public void String2num(String numString){
        this.num=Integer.parseInt(numString);
    }

    public String Realcard(){
        int fron=this.num/13;
        int back=this.num%13;

        String str="";

        if(fron==0){
            str+="스페이드";
        }else if(fron==1){
            str+="다이아";
        }else if(fron==2){
            str+="하트";
        }else if(fron==3){
            str+="클로버";
        }
        str+=" ";
        if(back==9) {
            str += "J";
        }else if(back==10){
            str+="Q";
        }else if(back==11){
            str+="K";
        }else if (back==12){
            str+="A";
        }else{
            str+=Integer.toString(back+2);
        }

        return str;
    }

    public int getColor(int num){
        int out=num/13;
        return out;
    }
}
