package edu.skku.map.final_pp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class add_market_page extends AppCompatActivity {

    String today, yesterday, store_name, addr, date, phone, xinbun;
    ImageView zhengming;
    Uri updata, updata_post;
    EditText Store_name, Addr, Date, Phone;
    Button update;
    StorageReference storageReference;
    DatabaseReference databaseReference, ggang;
    Bitmap bitmap;
    private final int PICK_IMAGE=777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_market_page);

        Intent gege=getIntent();
        xinbun=gege.getStringExtra("xinbun");
        updata=null;
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

        //view setter
        storageReference=FirebaseStorage.getInstance().getReference("market");
        databaseReference= FirebaseDatabase.getInstance().getReference("market");
        ggang=FirebaseDatabase.getInstance().getReference();
        zhengming=findViewById(R.id.zhengming_tu);
        Store_name=findViewById(R.id.store_name_edt);
        Addr=findViewById(R.id.adress_edt);
        Date=findViewById(R.id.disinfection_date_edt);
        Phone=findViewById(R.id.phone_number_edt);
        update=findViewById(R.id.add_market_button);
        //view setter

        //add market
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent add_market=new Intent(add_market_page.this, data_page.class);
                store_name=Store_name.getText().toString();
                addr=Addr.getText().toString();
                date=Date.getText().toString();
                phone=Phone.getText().toString();

                if((store_name.length() * addr.length() * date.length() * phone.length())==0){
                    Toast.makeText(add_market_page.this, "Please full the all content", Toast.LENGTH_SHORT).show();
                }else{
                    postFirebaseDatabase(true);
                    if(updata!=null){
                        UploadTask uploadTask=storageReference.child(date).child(store_name).putFile(updata);
                    }
                    Store_name.setText("");
                    Addr.setText("");
                    Date.setText("");
                    Phone.setText("");
                    add_market.putExtra("xinbun", xinbun);
                    startActivity(add_market);
                }
            }
        });
        //add market


        //add zhengming tu
        zhengming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
        //add zhengming tu

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE){
            if(data.getData()!=null){
                zhengming.setImageURI(data.getData());
                updata=data.getData();
            }
        }

    }

    public void postFirebaseDatabase(boolean add){
        Map<String, Object> childupdatas=new HashMap<>();
        Map<String, Object> postvalues=null;
        if(add){
            make_data post=new make_data(store_name, addr, date, phone);
            postvalues=post.toMap();
        }
        childupdatas.put("/market/"+date+"/"+store_name, postvalues);
        ggang.updateChildren(childupdatas);
    }



}
