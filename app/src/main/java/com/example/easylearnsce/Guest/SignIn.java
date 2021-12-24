package com.example.easylearnsce.Guest;

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
import android.widget.Toast;

import com.example.easylearnsce.Class.GuestNavView;
import com.example.easylearnsce.Class.Loading;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserImage;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SignIn extends AppCompatActivity {
    private TextView Title;
    private DrawerLayout drawerLayout;
    private ImageView BackIcon, MenuIcon;
    private NavigationView GuestNavView;
    private TextView ResetPassword, CreateAccount;
    private TextInputLayout TextInputLayoutEmail, TextInputLayoutPassword;
    private Button ButtonSignIn, Google;
    private GoogleSignInClient googleSignInClient;
    private static final int RcSignIn = 101;
    private Loading loading;
    private Intent intent;
    private User user = new User();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
    }
    private void init(){
        setID();
        MenuItem();
        BackIcon();
        MenuIcon();
        EndIcon();
        NavView();
        SignInCheck();
        ResetPassword();
        CreateAccount();
        Google();
    }
    private void setID(){
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        Title = findViewById(R.id.Title);
        GuestNavView = findViewById(R.id.GuestNavView);
        Title.setText(R.string.SignIn);
        CreateAccount = findViewById(R.id.CreateAccount);
        Google = findViewById(R.id.Google);
        ResetPassword = findViewById(R.id.ResetPassword);
        TextInputLayoutEmail = findViewById(R.id.TextInputLayoutEmail);
        TextInputLayoutPassword = findViewById(R.id.TextInputLayoutPassword);
        ButtonSignIn = findViewById(R.id.ButtonSignIn);
    }
    private void MenuItem(){
        Menu menu= GuestNavView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemSignIn);
        menuItem.setCheckable(false);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);
    }
    private void EndIcon() {
        TextInputLayoutEmail.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayoutEmail.setHelperText("");
                TextInputLayoutEmail.getEditText().setText("");
            }
        });
    }
    private void ResetPassword(){
        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { StartActivity(ResetPassword.class); }
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
                new GuestNavView(SignIn.this, item.getItemId());
                return false;
            }
        });
    }
    private void CreateAccount(){
        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { StartActivity(CreateAccount.class); }
        });
    }
    private boolean isEmailValid(CharSequence email) { return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(); }
    private void CheckValues(){
        if(TextInputLayoutEmail.getEditText().getText().length()<1)
            TextInputLayoutEmail.setHelperText(getResources().getString(R.string.Required));
        else if(!isEmailValid(TextInputLayoutEmail.getEditText().getText().toString()))
            TextInputLayoutEmail.setHelperText(getResources().getString(R.string.InvalidEmail));
        else
            TextInputLayoutEmail.setHelperText("");
        if(TextInputLayoutPassword.getEditText().getText().length()<1)
            TextInputLayoutPassword.setHelperText(getResources().getString(R.string.Required));
        else if(TextInputLayoutPassword.getEditText().getText().length()<6)
            TextInputLayoutPassword.setHelperText(getResources().getString(R.string.Must6Chars));
        else
            TextInputLayoutPassword.setHelperText("");
    }
    private void SignInCheck(){
        ButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.getInstance().signOut();
                CheckValues();
                if(TextInputLayoutEmail.getEditText().getText().length() > 0 && isEmailValid(TextInputLayoutEmail.getEditText().getText().toString()) && TextInputLayoutPassword.getEditText().getText().length() > 0)
                    SignIn();
            }
        });
    }
    private void CheckEmailExists(){
        firebaseAuth.fetchSignInMethodsForEmail(TextInputLayoutEmail.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if(task.getResult().getSignInMethods().isEmpty())
                    TextInputLayoutEmail.setHelperText(getResources().getString(R.string.EmailNotExist));
                else {
                    TextInputLayoutEmail.setHelperText("");
                    if(TextInputLayoutPassword.getEditText().getText().length()<1)
                        TextInputLayoutPassword.setHelperText(getResources().getString(R.string.Required));
                    else if(TextInputLayoutPassword.getEditText().getText().length()<6)
                        TextInputLayoutPassword.setHelperText(getResources().getString(R.string.Must6Chars));
                    else
                        TextInputLayoutPassword.setHelperText(getResources().getString(R.string.WrongPassword));
                }
                loading.stop();
            }
        });
    }
    private void Google(){
        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.About)).requestEmail().build();
                googleSignInClient = GoogleSignIn.getClient(SignIn.this, gso);
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RcSignIn);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RcSignIn) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                loading = new Loading(SignIn.this);
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) { loading.stop(); }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = firebaseAuth.getCurrentUser();
                            // Sign in success, update UI with the signed-in user's information
                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                int firstSpace = firebaseUser.getDisplayName().indexOf(" ");
                                user.setEmail(firebaseUser.getEmail());
                                user.setFirstname(firebaseUser.getDisplayName().substring(0,firstSpace));
                                user.setLastname(firebaseUser.getDisplayName().substring(firstSpace+1));
                                user.setUid(firebaseAuth.getCurrentUser().getUid());
                                databaseReference.child(firebaseUser.getUid()).setValue(user);
                                loading.stop();
                                intent = new Intent(SignIn.this, Home.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                                finish();
                            }
                            getUser();
                        }
                        else
                            finish();
                    }
                });
    }
    private void SignIn(){
        firebaseAuth.signInWithEmailAndPassword(TextInputLayoutEmail.getEditText().getText().toString(), TextInputLayoutPassword.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(firebaseAuth.getCurrentUser().isEmailVerified()) {
                        loading = new Loading(SignIn.this);
                        getUser();
                    }
                    else {
                        Toast.makeText(SignIn.this, R.string.CheckEmailVerify, Toast.LENGTH_LONG).show();
                    }
                } else
                    CheckEmailExists();
            }
        });
    }
    private void getUser(){
        if(firebaseAuth.getCurrentUser() != null) {
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
    }
    private void Home(User user){
        Intent intent = new Intent(SignIn.this, Home.class);
        intent.putExtra("user", user);
        loading.stop();
        startActivity(intent);
        finish();
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { StartActivity(EasyLearnSCE.class); }
        });
    }
    private void StartActivity(Class Destination){
        startActivity(new Intent(com.example.easylearnsce.Guest.SignIn.this, Destination));
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignIn.this, EasyLearnSCE.class));
        finish();
    }
}