public class cardValue {
    private int[] cardsame;
    private int[][] cardflu;
    int sameSize;
    int fluSize1;
    int fluSize2;
    public cardValue(int[] cardsame, int[][] cardflu){
        this.cardsame=cardsame;
        this.cardflu=cardflu;
        sameSize=cardsame.length;
        fluSize1=cardflu.length;
        fluSize2=cardflu[0].length;
    }

    public boolean straightFlu(){
        for(int i=0; i<fluSize1; i++){
            for(int j=0; j<fluSize2-4; j++){
                if(cardflu[i][j]>0 && cardflu[i][j+1]>0 && cardflu[i][j+2]>0 && cardflu[i][j+3]>0 && cardflu[i][j+4]>0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean fourCard(){
        for(int i=0; i<sameSize; i++){
            if(cardsame[i] == 4){
                return true;
            }
        }
        return false;
    }

    public boolean fullHouse(){
        int check=0;
        for(int i=0; i<sameSize; i++){
            if(cardsame[i]==3){
                check=1;
                break;
            }
        }
        if(check==1){
            for(int i=0; i<sameSize; i++){
                if(cardsame[i]==2){
                    return true;
                }
            }
        }else{
            return false;
        }
        return false;
    }

    public boolean flush(){
        for(int i=0; i<fluSize1; i++){
            int hap=0;
            for(int j=0; j<fluSize2; j++){
                if(cardflu[i][j]>0){
                    hap+=1;
                }
            }
            if(hap>=5){
                return true;
            }
        }
        return false;
    }

    public boolean straight(){
        for(int i=0; i<sameSize-4; i++){
            if(cardsame[i]>0 && cardsame[i+1]>0 && cardsame[i+2]>0 && cardsame[i+3]>0 && cardsame[i+4]>0){
                return true;
            }
        }
        return false;
    }

    public boolean triple(){
        for (int i=0; i<sameSize; i++) {
            if (cardsame[i] == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean twoPair(){
        int index=0;
        for(int i=0; i<sameSize; i++){
            if(cardsame[i]==2){
                index=i;
                break;
            }
        }
        for(int i=0; i<sameSize; i++){
            if(cardsame[i]==2 && i!=index){
                return true;
            }
        }
        return false;
    }

    public boolean onePair(){
        for(int i=0; i<sameSize; i++){
            if(cardsame[i]==2){
                return true;
            }
        }
        return false;
    }
}
