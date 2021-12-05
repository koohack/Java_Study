package edu.skku.map.final_pp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class shop_adapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<shops_item_getter> items;
    StorageReference storageReference;
    ImageView img;

    public shop_adapter(Context context, ArrayList<shops_item_getter> items){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items=items;
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
            convertView=inflater.inflate(R.layout.shop_item, parent, false);
        }

        shops_item_getter item=items.get(position);

        img=(ImageView)convertView.findViewById(R.id.bang_img);
        TextView v1=(TextView)convertView.findViewById(R.id.shop_name);
        TextView v2=(TextView)convertView.findViewById(R.id.shop_phone);
        TextView v3=(TextView)convertView.findViewById(R.id.shop_addr);
        TextView v4=(TextView)convertView.findViewById(R.id.shop_email);
        storageReference= FirebaseStorage.getInstance().getReference("shop");

        final long TWO_MEGABYTE=1024*1024*4;
        storageReference.child(item.getName()).child(item.getName()).child(item.getName()+".PNG").getBytes(TWO_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                /*
                if(bitmap==null){
                    img.getLayoutParams().height=0;
                    img.requestLayout();
                }
                 */
                img.setImageBitmap(bitmap);
            }
        });


        v1.setText("업체 명칭: "+item.getName());
        v2.setText("전화번호: "+item.getPhone());
        v3.setText("상세주소: "+item.getAddr());
        v4.setText("이메일: "+item.getE_mail());








        return convertView;
    }
}
