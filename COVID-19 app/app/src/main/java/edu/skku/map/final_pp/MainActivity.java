package edu.skku.map.final_pp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String User_ID="", Password="";
    EditText userid, password;
    int password_checker;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userid=findViewById(R.id.ID_edt);
        password=findViewById(R.id.Password_edt);
        databaseReference=FirebaseDatabase.getInstance().getReference("all_User");


        TextView sign_up=findViewById(R.id.sign_up_page);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_sign_up=new Intent(MainActivity.this, sign_up_page.class);
                startActivity(to_sign_up);
            }
        });

        final Button login_button=findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_ID=userid.getText().toString();
                Password=password.getText().toString();
                if(User_ID.length()==0){
                    Toast.makeText(MainActivity.this, "Wrong Username", Toast.LENGTH_SHORT).show();
                }
                if(User_ID.length()!=0 && Password.length()==0){
                    Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }else if(User_ID.length()!=0 && Password.length()!=0){

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(User_ID).exists()){
                                String ppp=dataSnapshot.child(User_ID).child("Password").getValue(String.class);
                                if(ppp.equals(Password)){
                                    password_checker=1;
                                }else{
                                    password_checker=0;
                                }

                                if(password_checker==1){
                                    Intent login_succ=new Intent(MainActivity.this, data_page.class);
                                    login_succ.putExtra("User_ID", User_ID);
                                    login_succ.putExtra("xinbun", dataSnapshot.child(User_ID).child("xinbun").getValue(String.class));
                                    startActivity(login_succ);
                                }else{
                                    Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(MainActivity.this, "Wrong Username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });//data reference
                }
            }//onclick
        });//onclick


    }
}
