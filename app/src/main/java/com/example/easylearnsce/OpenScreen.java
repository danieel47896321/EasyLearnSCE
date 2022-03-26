package com.example.easylearnsce;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import com.example.easylearnsce.Class.Loading;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.User.Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class OpenScreen extends AppCompatActivity {
    private Loading loading;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init(){ HomePage(); }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        loading.stop();
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