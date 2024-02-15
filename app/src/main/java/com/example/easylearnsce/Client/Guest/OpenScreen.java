package com.example.easylearnsce.Client.Guest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.easylearnsce.Client.Class.Loading;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.User.Home;
import com.example.easylearnsce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenScreen extends AppCompatActivity {
    private Loading loading;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen);
        init();
    }
    private void init(){ HomePage(); }
    private void HomePage() {
       if(firebaseAuth.getCurrentUser() != null ) {
           if(firebaseAuth.getCurrentUser().isEmailVerified()) {
               loading = new Loading(OpenScreen.this);
               databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       User user = snapshot.getValue(User.class);
                       Home(user);
                   }
                   @Override
                   public void onCancelled(@NonNull DatabaseError error) { }
               });
           }
           else
               EasyLearnSCE();
       }
        else
            EasyLearnSCE();
    }
    private void Home(User user){
        Intent intent = new Intent(OpenScreen.this, Home.class);
        intent.putExtra("user", user);
        try {loading.stop();
        }catch (Exception e){};
        startActivity(intent);
        finish();
    }
    private void EasyLearnSCE(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(OpenScreen.this, EasyLearnSCE.class));
                finish();
            }
        }, 300);
    }
}