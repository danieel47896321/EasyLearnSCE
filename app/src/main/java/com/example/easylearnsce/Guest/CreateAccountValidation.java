package com.example.easylearnsce.Guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.easylearnsce.Class.GuestNavView;
import com.example.easylearnsce.Class.Loading;
import com.example.easylearnsce.Class.PopUpMSG;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class CreateAccountValidation extends AppCompatActivity {
    private ImageView BackIcon, MenuIcon;
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private NavigationView GuestNavView;
    private TextInputLayout TextInputLayoutCode;
    private TextView Title, RegisterValidationText;
    private Loading loading;
    private Intent intent;
    private User user = new User();
    private String Password, codeBySystem;
    private Button ButtonOk;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance() ;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_validation);
        init();
    }
    private void init(){
        setID();
        BackIcon();
        MenuIcon();
        EndIcon();
        NavView();
        SignOut();
        CreateAccountValidation();
    }
    private void setID(){
        intent = getIntent();
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        Title = findViewById(R.id.Title);
        GuestNavView = findViewById(R.id.GuestNavView);
        Title.setText(R.string.CreateAccountValidation);
        RegisterValidationText = findViewById(R.id.RegisterValidationText);
        TextInputLayoutCode = findViewById(R.id.TextInputLayoutCode);
        ButtonOk = findViewById(R.id.ButtonOk);
        user = (User)intent.getSerializableExtra("User");
        Password = (String)intent.getSerializableExtra("Password");
        RegisterValidationText.setText(RegisterValidationText.getText().toString()+"\n"+user.getEmail());
    }
    private void EndIcon() {
        TextInputLayoutCode.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Clear(TextInputLayoutCode); }
        });
    }
    private void Clear(TextInputLayout input){
        input.setHelperText("");
        input.getEditText().setText("");
    }
    private void SignOut(){
        if(firebaseAuth.getCurrentUser() != null)
            firebaseAuth.getInstance().signOut();
    }
    private void verifyCode(String codeByUser){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,codeByUser);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loading.stop();
                if(task.isSuccessful()){
                    firebaseAuth.createUserWithEmailAndPassword(user.getEmail(),Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            user.setUid(firebaseAuth.getUid());
                            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                            new PopUpMSG(CreateAccountValidation.this,getResources().getString(R.string.CreateAccountValidation),getResources().getString(R.string.CompleteCreateAccount),SignIn.class);
                        }
                    });
                }else{
                    TextInputLayoutCode.getEditText().setText("fail here");
                }
            }
        });
    }
    private void CreateAccountValidation(){
        ButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(TextInputLayoutCode.getEditText().getText().toString())) {
                    TextInputLayoutCode.setHelperText(getResources().getString(R.string.Required));
                } else {
                    loading = new Loading(CreateAccountValidation.this);
                    verifyCode(TextInputLayoutCode.getEditText().getText().toString());
                }
            }
        });
    }
    private void MenuIcon(){
        MenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { drawerLayout.open(); }
        });
    }
    private void NavView(){
        GuestNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new GuestNavView(CreateAccountValidation.this, item.getItemId());
                return false;
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { StartActivity(CreateAccount.class); }
        });
    }
    private void StartActivity(Class Destination){
        startActivity(new Intent(CreateAccountValidation.this, Destination));
        finish();
    }
}