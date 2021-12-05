package edu.skku.map.final_pp;

public class shops_item_getter {
    private String name;
    private String phone;
    private String addr;
    private String e_mail;

    public shops_item_getter(){

    }

    public shops_item_getter(String name, String phone, String addr, String e_mail){
        this.name=name;
        this.phone=phone;
        this.addr=addr;
        this.e_mail=e_mail;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddr() {
        return addr;
    }

    public String getE_mail() {
        return e_mail;
    }
}
