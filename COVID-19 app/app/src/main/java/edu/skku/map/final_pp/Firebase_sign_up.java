package edu.skku.map.final_pp;

import java.util.HashMap;
import java.util.Map;

public class Firebase_sign_up {

    public String User_ID;
    public String Password;
    public String Name;
    public String xinbun;

    public Firebase_sign_up(){

    }

    public Firebase_sign_up(String User_ID, String Password, String Name, String xinbun){
        this.User_ID=User_ID;
        this.Password=Password;
        this.Name=Name;
        this.xinbun=xinbun;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result=new HashMap<>();
        result.put("User_ID", User_ID);
        result.put("Password", Password);
        result.put("Name", Name);
        result.put("xinbun", xinbun);
        return result;
    }

}
