package com.example.easylearnsce.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.easylearnsce.Class.Loading;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserMenuAdapter;
import com.example.easylearnsce.Class.UserNavView;
import com.example.easylearnsce.R;
import com.example.easylearnsce.SelectFunc.GenericEngineering;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    private TextView Title,TextViewSearchCity, TextViewSearchAge, TextViewSearchGender,TextViewSearch;
    private TextInputLayout TextInputLayoutFirstName, TextInputLayoutLastName ,TextInputLayoutEmail;
    private Button Confirm;
    private DrawerLayout drawerLayout;
    private ImageView BackIcon, MenuIcon,addImage;
    private UserMenuAdapter userMenuAdapter;
    private Loading loading;
    private Intent intent;
    private View UserProfileImage, UserImage;
    private NavigationView UserNavigationView;
    private Dialog dialog;
    private EditText EditTextSearch;
    private ListView ListViewSearch;
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private Uri uri = null;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    private User user, newUser = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }
    void init(){
        setID();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavView();
        Confirm();
        AddImage();
        ShowInfo();
    }
    public void setID(){
        intent = getIntent();
        UserProfileImage = findViewById(R.id.UserProfileImage);
        TextInputLayoutFirstName = findViewById(R.id.TextInputLayoutFirstName);
        TextInputLayoutLastName = findViewById(R.id.TextInputLayoutLastName);
        TextInputLayoutEmail = findViewById(R.id.TextInputLayoutEmail);
        TextViewSearchCity = findViewById(R.id.TextViewSearchCity);
        TextViewSearchAge = findViewById(R.id.TextViewSearchAge);
        TextViewSearchGender = findViewById(R.id.TextViewSearchGender);
        addImage = findViewById(R.id.addImage);
        Confirm = findViewById(R.id.confirm);
        UserNavigationView = findViewById(R.id.UserNavigationView);
        UserImage = UserNavigationView.getHeaderView(0).findViewById(R.id.UserImage);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.Profile));
        user = (User)intent.getSerializableExtra("user");
        drawerLayout = findViewById(R.id.drawerLayout);
        userMenuAdapter = new UserMenuAdapter(user,Profile.this);
    }
    private void MenuItem(){
        Menu menu= UserNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemProfile);
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
    private void NavView(){
        UserNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new UserNavView(Profile.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void ShowInfo(){
        TextInputLayoutFirstName.getEditText().setText(user.getFirstname());
        TextInputLayoutLastName.getEditText().setText(user.getLastname());
        TextInputLayoutEmail.getEditText().setText(user.getEmail());
        TextViewSearchCity.setText(user.getCity());
        TextViewSearchAge.setText(user.getAge());
        TextViewSearchGender.setText(user.getGender());
        Glide.with(Profile.this).asBitmap().load(user.getImage()).into(new CustomTarget<Bitmap>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) { UserProfileImage.setBackground(new BitmapDrawable(getResources(), resource)); }
            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) { }
        });
        CityPick();
        AgePick();
        GenderPick();
    }
    private void Confirm(){
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckInput()){
                    loading = new Loading(Profile.this);
                    newUser.setEmail(user.getEmail());
                    newUser.setType(user.getType());
                    newUser.setUid(user.getUid());
                    newUser.setFirstname(TextInputLayoutFirstName.getEditText().getText().toString());
                    newUser.setLastname(TextInputLayoutLastName.getEditText().getText().toString());
                    newUser.setGender(TextViewSearchGender.getText().toString());
                    newUser.setAge(TextViewSearchAge.getText().toString());
                    newUser.setCity(TextViewSearchCity.getText().toString());
                    if(uri != null)
                        UploadImage();
                    else {
                        newUser.setImage(user.getImage());
                        updateData();
                    }
                }
            }
        });
    }
    private void updateData(){
        databaseReference.child(user.getUid()).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loading.stop();
                user = newUser;
                StartActivity(Home.class);
            }
        });
    }
    private boolean CheckInput(){
        if(TextInputLayoutFirstName.getEditText().getText().length() < 1 || TextInputLayoutLastName.getEditText().getText().length() < 1 ) {
            if(TextInputLayoutFirstName.getEditText().getText().length() < 1 )
                TextInputLayoutFirstName.setHelperText(getResources().getString(R.string.Required));
            else
                TextInputLayoutFirstName.setHelperText("");
            if(TextInputLayoutLastName.getEditText().getText().length() < 1 )
                TextInputLayoutLastName.setHelperText(getResources().getString(R.string.Required));
            else
                TextInputLayoutLastName.setHelperText("");
            return false;
        }
        else if(TextViewSearchCity.getText().toString().equals("")){
            return false;
        }
        else if(TextViewSearchAge.getText().toString().equals("")){
            return false;
        }
        else if(TextViewSearchGender.getText().toString().equals("")){
            return false;
        }
        return true;
    }
    private void CityPick(){
        TextViewSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog(getResources().getStringArray(R.array.City),getResources().getString(R.string.SelectCity),TextViewSearchCity);
            }
        });
    }
    private void AgePick(){
        String age[] = new String[103];
        for(int i=0; i<103 ; i++)
            age[i] = ""+(i+18);
        TextViewSearchAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog(age ,getResources().getString(R.string.SelectAge),TextViewSearchAge);
            }
        });
    }
    private void GenderPick(){
        TextViewSearchGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog(getResources().getStringArray(R.array.Gender),getResources().getString(R.string.SelectGender),TextViewSearchGender);
            }
        });
    }
    private void AddImage(){
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { chooseProfilePicture(); }
        });
    }
    private void chooseProfilePicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_profile_picture,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        ImageView Cammera = dialogView.findViewById(R.id.Cammera);
        ImageView Gallery = dialogView.findViewById(R.id.Gallery);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Cammera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAndRequestPermissions())
                    CammeraPicture();
                alertDialog.cancel();
            }
        });
        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryPicture();
                alertDialog.cancel();
            }
        });
    }
    private void GalleryPicture(){
        Intent photo = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photo.setType("image/*");
        startActivityForResult(photo, 1);
    }
    private void CammeraPicture(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicture.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePicture, 2);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    uri = data.getData();
                    Bitmap bitmap = null;
                    try { bitmap = MediaStore.Images.Media.getBitmap(Profile.this.getContentResolver(), uri);
                    } catch (IOException e) { e.printStackTrace(); }
                    UserProfileImage.setBackground(new BitmapDrawable(getResources(), bitmap));
                    UserImage.setBackground(new BitmapDrawable(getResources(), bitmap));
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    UserProfileImage.setBackground(new BitmapDrawable(getResources(), bitmap));
                    UserImage.setBackground(new BitmapDrawable(getResources(), bitmap));
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    try {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 500, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, user.getUid(), null);
                        uri = Uri.parse(path);
                    }catch (Exception e) { e.printStackTrace(); }
                }
                break;
        }
    }
    private Boolean checkAndRequestPermissions(){
        if(Build.VERSION.SDK_INT >= 23 ){
            int cameraPermission = ActivityCompat.checkSelfPermission(Profile.this, Manifest.permission.CAMERA);
            if(cameraPermission == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(Profile.this,new String[]{Manifest.permission.CAMERA},20);
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            CammeraPicture();
        else{ }
    }
    private void UploadImage(){
        storageReference = firebaseStorage.getReference();
        StorageReference ImageRefrence = storageReference.child(firebaseAuth.getCurrentUser().getUid()+".jpg");
        ImageRefrence.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        newUser.setImage(uri.toString());
                        updateData();
                    }
                });
            }
        });
    }
    private void setDialog(String[] array, String title,TextView textViewPick){
        dialog = new Dialog(Profile.this);
        dialog.setContentView(R.layout.dialog_search_spinner);
        dialog.getWindow().setLayout(1000,950);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditTextSearch = dialog.findViewById(R.id.EditTextSearch);
        ListViewSearch = dialog.findViewById(R.id.ListViewSearch);
        TextViewSearch = dialog.findViewById(R.id.TextViewSearch);
        TextViewSearch.setText(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Profile.this, R.layout.dropdown_item, array);
        ListViewSearch.setAdapter(adapter);
        EditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        ListViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                textViewPick.setText(adapterView.getItemAtPosition(i).toString());
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
        intent = new Intent(Profile.this, Destination);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}