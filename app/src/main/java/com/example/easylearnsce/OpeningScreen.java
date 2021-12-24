package com.example.easylearnsce;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easylearnsce.Class.Chat;
import com.example.easylearnsce.Class.Loading;
import com.example.easylearnsce.Class.MessageAdapter;
import com.example.easylearnsce.Class.PopUpMSG;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Guest.CreateAccount;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.Guest.SignIn;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class OpeningScreen extends AppCompatActivity {
    private Loading loading;
    TextView textView3;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private byte encryptionKey[] = {9,115,51,86,105,4,-31,-23};
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init(){ HomePage(); }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void HomePage() {
       if(firebaseAuth.getCurrentUser() != null ) {
           if(firebaseAuth.getCurrentUser().isEmailVerified()) {
               loading = new Loading(OpeningScreen.this);
               databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       User user = snapshot.getValue(User.class);
                       Home(user);
                   }
                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                   }
               });
           }
           else
               EasyLearnSCE();

       }
        else
            EasyLearnSCE();
       // databaseReference.child("PublicKey").setValue(new String(encryptionKey));
        /*databaseReference.child("PublicKey").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String test = snapshot.getValue().toString();
                byte[] a = test.getBytes();
                textView3.setText(a+"\n"+encryptionKey);

                ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
       // String test = new String(encryptionKey, StandardCharsets.UTF_8);
        //textView3.setText(test.getBytes(StandardCharsets.UTF_8)+"\n"+encryptionKey.toString());
    }
    private void Home(User user){
        Intent intent = new Intent(OpeningScreen.this, Home.class);
        intent.putExtra("user", user);
        loading.stop();
        startActivity(intent);
        finish();
    }
    private void EasyLearnSCE(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(OpeningScreen.this, EasyLearnSCE.class));
                finish();
            }
        }, 300);
    }
}