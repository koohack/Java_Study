package edu.skku.map.final_pp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class data_page extends AppCompatActivity {

    String to_num, yes_num, death_num, today, yesterday, subber, User_ID, xinbun;
    TextView today_text, yesterday_text, death_text, compare_text;
    Intent getdata;
    Button xincheng;
    twodays_adapter adapter;
    ArrayList<twodays_item> twodays_items;
    ListView listView;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    int to, yes, sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_page);

        //call_119
        Button call_119=findViewById(R.id.call_119);
        call_119.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionresult=checkSelfPermission(Manifest.permission.CALL_PHONE);

                if(permissionresult== PackageManager.PERMISSION_DENIED){

                    if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                        AlertDialog.Builder builder=new AlertDialog.Builder(data_page.this);
                        builder.setTitle("권한 필요").setMessage("기능을 사용하기 위해 단말기의 '전화걸기' 권한이 필요합니다.").setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                            }
                        }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    }else{
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                    }

                }else{
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:119")));
                }
            }
        });
        //call_119

        //date
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        today=simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat dsdf=new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar calendar1=Calendar.getInstance();
        calendar1.add(Calendar.DATE,-2);
        yesterday=dsdf.format(calendar1.getTime());
        //date



        //view init
        twodays_items=new ArrayList<twodays_item>();
        getdata=getIntent();
        listView=findViewById(R.id.days2);
        User_ID=getdata.getStringExtra("User_ID");
        xinbun=getdata.getStringExtra("xinbun");
        storageReference= FirebaseStorage.getInstance().getReference("market");
        databaseReference=FirebaseDatabase.getInstance().getReference("market");



        final ValueEventListener postListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                twodays_items.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.child(today).getChildren()){
                    make_data get=postSnapshot.getValue(make_data.class);
                    twodays_item i1=new twodays_item(get.store_name, get.address, get.date, get.phone);
                    twodays_items.add(i1);
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(yesterday).getChildren()){
                    make_data get=postSnapshot.getValue(make_data.class);
                    twodays_item i2=new twodays_item(get.store_name, get.address, get.date, get.phone);
                    twodays_items.add(i2);
                }

                //twodays_item i2=new twodays_item("jijij", "jaja", "202002020", "gegegeg");
                //twodays_items.add(i2);
                adapter=new twodays_adapter(data_page.this, twodays_items);
                //adapter=new twodays_adapter(data_page.this, twodays_items);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(postListener);

        //view init

        //
        final ScrollView scrollView=findViewById(R.id.scroll);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        //



        //set text
        today_text=findViewById(R.id.today);
        yesterday_text=findViewById(R.id.yesterday);
        death_text=findViewById(R.id.death);
        compare_text=findViewById(R.id.compare);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    getXmlData();
                }catch (XmlPullParserException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        today_text.setText(to_num);
                        yesterday_text.setText(yes_num);
                        death_text.setText(death_num);
                        to=Integer.parseInt(to_num);
                        yes=Integer.parseInt(yes_num);
                        sub=to-yes;
                        subber=String.valueOf(sub);
                        compare_text.setText(subber);
                        if(sub<10){
                            compare_text.setTextColor(Color.parseColor("#0000FF"));
                            compare_text.setText(subber);
                        }else if(sub>=10 && sub<40){
                            compare_text.setTextColor(Color.parseColor("#FFFF00"));
                            compare_text.setText(subber);
                        }else if(sub>=40){
                            compare_text.setTextColor(Color.parseColor("#FF0000"));
                            compare_text.setText(subber);
                        }
                    }
                });

            }
        }).start();
        //set text

        //button->sodok list
        Button sodok_list=findViewById(R.id.sodok_shoplist);
        sodok_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_list=new Intent(data_page.this, shops_page.class);
                goto_list.putExtra("xinbun", xinbun);
                startActivity(goto_list);
            }
        });
        //button->sodok list

        //button->xincheng page
        xincheng=findViewById(R.id.xincheng);
        if(xinbun.equals("user")){
            xincheng.setText("Health is the best\nSafe our health");
            xincheng.setTextSize(10);
        }
        xincheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xinbun.equals("user")){
                    Toast.makeText(data_page.this, "Normal user can not use this", Toast.LENGTH_SHORT).show();
                }else{
                    Intent add_market=new Intent(data_page.this, add_market_page.class);
                    add_market.putExtra("xinbun", xinbun);
                    startActivity(add_market);
                }
            }
        });
        //button->xincheng page


    }

    public void getXmlData() throws XmlPullParserException{
        try{
            URL url=new URL("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey=6tWlCap7UZ3UvSzptgJb057qsqPQblinkHrCYKttYRT3VmA%2FDY1m%2B90OPaNkeliJX6Bu6qFVVVyeb%2F1YmQl%2B2Q%3D%3D&pageNo=1&numOfRows=5&startCreateDt="+today+"&endCreateDt="+today);
            URL url1=new URL("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey=6tWlCap7UZ3UvSzptgJb057qsqPQblinkHrCYKttYRT3VmA%2FDY1m%2B90OPaNkeliJX6Bu6qFVVVyeb%2F1YmQl%2B2Q%3D%3D&pageNo=1&numOfRows=5&startCreateDt="+yesterday+"&endCreateDt="+yesterday);

            URLConnection connection=url.openConnection();
            URLConnection connection1=url1.openConnection();
            connection.setReadTimeout(3000);
            connection1.setReadTimeout(3000);
            InputStream is=url.openStream();
            InputStream is1=url1.openStream();

            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xpp=factory.newPullParser();
            XmlPullParser xpp1=factory.newPullParser();

            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            xpp1.setInput(new InputStreamReader(is1, "UTF-8"));

            String tag, tag1;

            xpp.next();
            xpp1.next();

            int eventType=xpp.getEventType();
            int eventType1=xpp1.getEventType();

            while(eventType!=XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        tag=xpp.getName();
                        if(tag.equals("item"));
                        else if(tag.equals("decideCnt")){
                            xpp.next();
                            to_num=xpp.getText();
                        }
                        else if(tag.equals("deathCnt")){
                            xpp.next();
                            death_num=xpp.getText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag=xpp.getName();
                        if(tag.equals("item")){
                            break;
                        }
                }
                eventType=xpp.next();
            }

            while(eventType1!=XmlPullParser.END_DOCUMENT){
                switch(eventType1){
                    case XmlPullParser.START_TAG:
                        tag1=xpp1.getName();
                        if(tag1.equals("item"));
                        else if(tag1.equals("decideCnt")){
                            xpp1.next();
                            yes_num=xpp1.getText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag1=xpp1.getName();
                        if(tag1.equals("item")){
                            break;
                        }
                }
                eventType1=xpp1.next();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}



