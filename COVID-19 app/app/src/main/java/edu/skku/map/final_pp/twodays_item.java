package edu.skku.map.final_pp;

public class twodays_item {
    private String store_name;
    private String addr;
    private String date;
    private String phone;

    public twodays_item(){

    }

    public twodays_item(String store_name, String addr, String date, String phone){
        this.store_name=store_name;
        this.addr=addr;
        this.date=date;
        this.phone=phone;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getAddr() {
        return addr;
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }
}
