package edu.skku.map.final_pp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class sign_up_page extends AppCompatActivity {

    EditText user_id, password, name;
    String User_ID="", Password="", Name="";
    CheckBox checkBox;
    private DatabaseReference databaseReference, databaseReference1;
    TextView tt;
    int stack=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        user_id=findViewById(R.id.User_ID_edt);
        password=findViewById(R.id.Password_edt);
        name=findViewById(R.id.Name_edt);
        checkBox=findViewById(R.id.checkBox);

        databaseReference= FirebaseDatabase.getInstance().getReference("all_User");
        databaseReference1= FirebaseDatabase.getInstance().getReference();
        tt=findViewById(R.id.just_text1);

        User_ID=user_id.getText().toString();
        Password=password.getText().toString();
        Name=name.getText().toString();


        final Button sign_up_button=findViewById(R.id.sign_up_button);
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_ID=user_id.getText().toString();
                Password=password.getText().toString();
                Name=name.getText().toString();

                if((User_ID.length() * Password.length() * Name.length()) == 0){
                    Toast.makeText(sign_up_page.this, "Please fill the all content", Toast.LENGTH_SHORT).show();
                }else{
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child(User_ID).exists()){
                                    if(stack==0){
                                        Toast.makeText(sign_up_page.this, "Please use other user name", Toast.LENGTH_SHORT).show();
                                        stack=1;
                                    }
                                }else{
                                    stack=1;
                                    postFirebaseDatabase(true);
                                    Intent signupIntent=new Intent(sign_up_page.this, MainActivity.class);
                                    startActivity(signupIntent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                }
            }//onclick listener
        });


    }

    public void postFirebaseDatabase(boolean add){
        Map<String, Object> childupdatas=new HashMap<>();
        Map<String, Object> postvalues=null;
        final String t1=User_ID, t2=Password, t3=Name;
        if(add){
            if(checkBox.isChecked()){
                Firebase_sign_up post1=new Firebase_sign_up(t1, t2, t3, "owner");
                postvalues=post1.toMap();
            }else{
                Firebase_sign_up post12=new Firebase_sign_up(t1, t2, t3, "user");
                postvalues=post12.toMap();
            }
        }
        childupdatas.put("/all_User/"+User_ID, postvalues);
        databaseReference1.updateChildren(childupdatas);
        clearET();
    }

    public void clearET(){
        User_ID="";
        Password="";
        Name="";
    }



}
