package edu.skku.map.final_pp;

import java.util.HashMap;
import java.util.Map;

public class make_data {
    public String store_name;
    public String address;
    public String date;
    public String phone;

    public make_data(){

    }

    public make_data(String store_name, String address, String date, String phone){
        this.store_name=store_name;
        this.address=address;
        this.date=date;
        this.phone=phone;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result=new HashMap<>();
        result.put("store_name", store_name);
        result.put("address", address);
        result.put("date", date);
        result.put("phone", phone);
        return result;
    }


}
