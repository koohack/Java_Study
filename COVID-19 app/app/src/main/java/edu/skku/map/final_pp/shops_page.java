package edu.skku.map.final_pp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class shops_page extends AppCompatActivity {

    ListView listView;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String shop1, shop2, shop3, shop4, shop5, bebe, xinbun;
    ArrayList<shops_item_getter> memo, memo1, memo2, memo3, memo4;
    shop_adapter adapter, adapter1, adapter2, adapter3, adapter4;
    Intent gett;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_page);

        gett=getIntent();
        xinbun=gett.getStringExtra("xinbun");
        ImageView back_to_data = findViewById(R.id.goback_data_page);
        back_to_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goback = new Intent(shops_page.this, data_page.class);
                //here for xinbun string
                goback.putExtra("xinbun", xinbun);
                startActivity(goback);
            }
        });
        memo = new ArrayList<shops_item_getter>();
        listView = findViewById(R.id.shops_page_listview);


        shops_item_getter i1 = new shops_item_getter("Hansclean", "1688-2573", "서울특별시 강남구 테헤란로82길 15,4호 (대치동, 디아이타워)", "admin@hans-clean.com");
        shops_item_getter i2 = new shops_item_getter("CESCO", "1588-1119", "서울특별시 강동구 상일로 10길 46번지 세스코 터치센터", "xxxxxx@xxxxx.com");
        shops_item_getter i3 = new shops_item_getter("닥터세바", "1688-2887", "서울특별시 영등포구 버드나루로9 천일빌딩 5층", "yyyyyy@yyyyy.com");
        shops_item_getter i4 = new shops_item_getter("마스터 방역", "1833-8257", "서울 동작구 서달로2길 29 상가동 203호", "zzzzzz@zzzzz.com");
        shops_item_getter i5 = new shops_item_getter("스카이쉴드", "1899-5118", "서울 강동구 상암로 224 삼익파크상가 210호", "skyeshield@naver.com");

        memo.clear();
        memo.add(i1);

        memo.add(i2);

        memo.add(i3);

        memo.add(i4);

        memo.add(i5);
        adapter4 = new shop_adapter(shops_page.this, memo);
        listView.setAdapter(adapter4);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bebe = memo.get(position).getPhone();
                //bebe = bebe.replaceAll("-", "");
                int permissionresult=checkSelfPermission(Manifest.permission.CALL_PHONE);

                if(permissionresult==PackageManager.PERMISSION_DENIED){

                    if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                        AlertDialog.Builder builder=new AlertDialog.Builder(shops_page.this);
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
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bebe)));
                }
            }
        });



    }

}
