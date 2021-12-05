package edu.skku.map.final_pp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class twodays_adapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<twodays_item> items;
    StorageReference storageReference;
    String today, yesterday;
    ImageView img;

    public twodays_adapter(Context context, ArrayList<twodays_item> item)
    {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items=item;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.twodays_item, parent, false);
        }

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


        storageReference= FirebaseStorage.getInstance().getReference("market");

        twodays_item item=items.get(position);
        TextView v1=(TextView)convertView.findViewById(R.id.twodays_name);
        TextView v2=(TextView)convertView.findViewById(R.id.twodays_addr);
        TextView v3=(TextView)convertView.findViewById(R.id.twodays_date);
        TextView v4=(TextView)convertView.findViewById(R.id.twodays_phone);
        img=(ImageView)convertView.findViewById(R.id.zhengjian);

        v1.setText("가계 이름: "+item.getStore_name());
        v2.setText("주소: "+item.getAddr());
        v3.setText("소독 날짜: "+item.getDate());
        v4.setText("연락처: "+item.getPhone());

        if(item.getDate().equals(today)){
            final long TWO_MEGABYTE=1024*1024*4;
            storageReference.child(today).child(item.getStore_name()).getBytes(TWO_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if(bitmap==null){

                    }else{
                        img.setImageBitmap(bitmap);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else if(item.getDate().equals(yesterday)){
            final long TWO_MEGABYTE=1024*1024*4;
            storageReference.child(yesterday).child(item.getStore_name()).getBytes(TWO_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if(bitmap==null){

                    }else{
                        img.setImageBitmap(bitmap);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }





        return convertView;
    }
}
