package com.example.easylearnsce.Client.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Client.Class.Loading;
import com.example.easylearnsce.Client.Class.PopUpMSG;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.Class.UserMenuInfo;
import com.example.easylearnsce.Client.Class.UserNavigationView;
import com.example.easylearnsce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    private TextInputLayout TextInputLayoutCurrentPassword, TextInputLayoutNewPassword, TextInputLayoutPasswordConfirm;
    private Button ButtonSaveChanges;
    private DrawerLayout drawerLayout;
    private Loading loading;
    private Intent intent;
    private User user;
    private TextView Title;
    private ImageView BackIcon, MenuIcon;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }
    private void init(){
        setID();
        SaveChanges();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavigationView();
    }
    private void setID(){
        TextInputLayoutCurrentPassword = findViewById(R.id.TextInputLayoutCurrentPassword);
        TextInputLayoutNewPassword = findViewById(R.id.TextInputLayoutNewPassword);
        TextInputLayoutPasswordConfirm = findViewById(R.id.TextInputLayoutPasswordConfirm);
        ButtonSaveChanges = findViewById(R.id.ButtonSaveChanges);
        navigationView = findViewById(R.id.navigationView);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.ChangePassword));
        drawerLayout = findViewById(R.id.drawerLayout);
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        new UserMenuInfo(user,ChangePassword.this);
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemChangePassword);
        menuItem.setCheckable(false);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);
    }
    private void MenuIcon(){
        MenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { drawerLayout.open(); }
        });
    }
    private void NavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new UserNavigationView(ChangePassword.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void CheckValues(){
        if(TextInputLayoutCurrentPassword.getEditText().getText().length()<1)
            TextInputLayoutCurrentPassword.setHelperText(getResources().getString(R.string.Required));
        else if(TextInputLayoutCurrentPassword.getEditText().getText().length()<6)
            TextInputLayoutCurrentPassword.setHelperText(getResources().getString(R.string.Must6Chars));
        else
            TextInputLayoutCurrentPassword.setHelperText("");
        if(TextInputLayoutNewPassword.getEditText().getText().length()<1)
            TextInputLayoutNewPassword.setHelperText(getResources().getString(R.string.Required));
        else if(TextInputLayoutNewPassword.getEditText().getText().length()<6)
            TextInputLayoutNewPassword.setHelperText(getResources().getString(R.string.Must6Chars));
        else
            TextInputLayoutNewPassword.setHelperText("");
        if(TextInputLayoutPasswordConfirm.getEditText().getText().length()<1)
            TextInputLayoutPasswordConfirm.setHelperText(getResources().getString(R.string.Required));
        else if(TextInputLayoutPasswordConfirm.getEditText().getText().length()<6)
            TextInputLayoutPasswordConfirm.setHelperText(getResources().getString(R.string.Must6Chars));
        else
            TextInputLayoutPasswordConfirm.setHelperText("");
    }
    private void SaveChanges(){
        ButtonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValues();
                if(TextInputLayoutCurrentPassword.getEditText().getText().length() > 5 && TextInputLayoutNewPassword.getEditText().getText().length() > 5 && TextInputLayoutPasswordConfirm.getEditText().getText().length() > 5){
                    if(TextInputLayoutNewPassword.getEditText().getText().toString().equals(TextInputLayoutPasswordConfirm.getEditText().getText().toString())) {
                        loading = new Loading(ChangePassword.this);
                        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), TextInputLayoutCurrentPassword.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loading.stop();
                                if (task.isSuccessful()) {
                                    currentUser.updatePassword(TextInputLayoutNewPassword.getEditText().getText().toString());
                                    new PopUpMSG(ChangePassword.this,getResources().getString(R.string.ChangePassword),getResources().getString(R.string.PasswordChanged), Home.class,user);
                                } else
                                    TextInputLayoutCurrentPassword.setHelperText(getResources().getString(R.string.WrongPassword));
                            }
                        });
                    }
                    else
                        TextInputLayoutPasswordConfirm.setHelperText(getResources().getString(R.string.DifferentPassword));
                }
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { StartActivity(Home.class); }
        });
    }
    private void StartActivity(Class Destination){
        intent = new Intent(ChangePassword.this, Destination);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        intent = new Intent(ChangePassword.this, Home.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
