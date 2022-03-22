package com.example.easylearnsce.Guest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easylearnsce.Class.GuestLagnuage;
import com.example.easylearnsce.Class.GuestNavigationView;
import com.example.easylearnsce.Class.Loading;
import com.example.easylearnsce.Class.PopUpMSG;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;


public class CreateAccount extends AppCompatActivity {
    private ImageView BackIcon, MenuIcon;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextInputLayout TextInputLayoutFirstName, TextInputLayoutLastName ,TextInputLayoutEmail, TextInputLayoutPassword, TextInputLayoutPasswordConfirm, TextInputLayoutGender, TextInputLayoutAge, TextInputLayoutCity;
    private TextView Title, SignIn, TextViewSearchCity, TextViewSearchAge, TextViewSearchGender,TextViewSearch, TextViewSearchLanguage;
    private Dialog dialog;
    private Calendar calendar = Calendar.getInstance();
    private int Year = calendar.get(Calendar.YEAR), Month = calendar.get(Calendar.MONTH), Day = calendar.get(Calendar.DAY_OF_MONTH), UserYear, UserMonth, UserDay;
    private ListView ListViewSearch;
    private EditText EditTextSearch;
    private Button ButtonNext, NextButtonFinish;
    private Loading loading;
    private GuestLagnuage guestLagnuage;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance() ;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users");
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        init();
    }
    private void init(){
        setID();
        setLanguage();
        MenuItem();
        BackIcon();
        MenuIcon();
        EndIcon();
        NavigationView();
        SignOut();
        AlreadyHaveAccount();
        CreateAccountCheck();
    }
    private void setID(){
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        Title = findViewById(R.id.Title);
        navigationView = findViewById(R.id.navigationView);
        Title.setText(R.string.CreateAccount);
        SignIn = findViewById(R.id.SignIn);
        TextInputLayoutFirstName = findViewById(R.id.TextInputLayoutFirstName);
        TextInputLayoutLastName = findViewById(R.id.TextInputLayoutLastName);
        TextInputLayoutEmail = findViewById(R.id.TextInputLayoutEmail);
        TextInputLayoutPassword = findViewById(R.id.TextInputLayoutPassword);
        TextInputLayoutPasswordConfirm = findViewById(R.id.TextInputLayoutPasswordConfirm);
        ButtonNext = findViewById(R.id.ButtonNext);
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        guestLagnuage = new GuestLagnuage(CreateAccount.this);
    }
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { guestLagnuage.setDialog(); }
        });
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemCreateAccount);
        menuItem.setCheckable(false);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);
    }
    private void EndIcon() {
        TextInputLayoutFirstName.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Clear(TextInputLayoutFirstName); }
        });
        TextInputLayoutLastName.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Clear(TextInputLayoutLastName); }
        });
        TextInputLayoutEmail.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Clear(TextInputLayoutEmail); }
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
                new GuestNavigationView(CreateAccount.this, item.getItemId());
                return false;
            }
        });
    }
    private void AlreadyHaveAccount(){
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { StartActivity(SignIn.class); }
        });
    }
    private boolean isEmailValid(CharSequence email) { return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(); }
    private void CheckEmailExists(){
        firebaseAuth.fetchSignInMethodsForEmail(TextInputLayoutEmail.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                loading.stop();
                if(!task.getResult().getSignInMethods().isEmpty())
                    TextInputLayoutEmail.setHelperText(getResources().getString(R.string.EmailExist));
                else {
                    TextInputLayoutEmail.setHelperText("");
                    fillInfo();
                }
            }
        });
    }
    private boolean CheckEmail(){
        if(TextInputLayoutEmail.getEditText().getText().length()<1) {
            TextInputLayoutEmail.setHelperText(getResources().getString(R.string.Required));
            return false;
        }
        else if(!isEmailValid(TextInputLayoutEmail.getEditText().getText().toString())) {
            TextInputLayoutEmail.setHelperText(getResources().getString(R.string.InvalidEmail));
            return false;
        }
        else
            TextInputLayoutEmail.setHelperText("");
        return true;
    }
    private boolean CheckFirstName(){
        if(TextInputLayoutFirstName.getEditText().getText().length()<1) {
            TextInputLayoutFirstName.setHelperText(getResources().getString(R.string.Required));
            return false;
        }
        else
            TextInputLayoutFirstName.setHelperText("");
        return true;
    }
    private boolean CheckLastName(){
        if(TextInputLayoutLastName.getEditText().getText().length()<1) {
            TextInputLayoutLastName.setHelperText(getResources().getString(R.string.Required));
            return false;
        }
        else
            TextInputLayoutLastName.setHelperText("");
        return true;
    }
    private boolean CheckPassword(){
        if(TextInputLayoutPassword.getEditText().getText().length()<1) {
            TextInputLayoutPassword.setHelperText(getResources().getString(R.string.Required));
            return false;
        }
        else if(TextInputLayoutPassword.getEditText().getText().length()<6) {
            TextInputLayoutPassword.setHelperText(getResources().getString(R.string.Must6Chars));
            return false;
        }
        else
            TextInputLayoutPassword.setHelperText("");
        return true;
    }
    private boolean CheckConfirmPassword(){
        if(TextInputLayoutPasswordConfirm.getEditText().getText().length()<1) {
            TextInputLayoutPasswordConfirm.setHelperText(getResources().getString(R.string.Required));
            return false;
        }
        else if(TextInputLayoutPasswordConfirm.getEditText().getText().length()<6){
            TextInputLayoutPasswordConfirm.setHelperText(getResources().getString(R.string.Must6Chars));
            return false;
        }
        else
            TextInputLayoutPasswordConfirm.setHelperText("");
        return true;
    }
    private void CheckValues(){
        if( CheckFirstName() && CheckLastName() && CheckEmail() && CheckPassword() && CheckConfirmPassword()){
            if(!(TextInputLayoutPassword.getEditText().getText().toString().equals(TextInputLayoutPasswordConfirm.getEditText().getText().toString())))
                TextInputLayoutPasswordConfirm.setHelperText(getResources().getString(R.string.DifferentPassword));
            else{
                loading = new Loading(CreateAccount.this);
                CheckEmailExists();
            }
        }
    }
    private void CreateAccountCheck(){
        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckFirstName();
                CheckLastName();
                CheckEmail();
                CheckPassword();
                CheckConfirmPassword();
                CheckValues();
            }
        });
    }
    private void fillInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_createaccount,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutGender = dialogView.findViewById(R.id.TextInputLayoutGender);
        TextInputLayoutCity = dialogView.findViewById(R.id.TextInputLayoutCity);
        TextInputLayoutAge = dialogView.findViewById(R.id.TextInputLayoutAge);
        TextViewSearchCity = dialogView.findViewById(R.id.TextViewSearchCity);
        TextViewSearchAge = dialogView.findViewById(R.id.TextViewSearchAge);
        TextViewSearchGender = dialogView.findViewById(R.id.TextViewSearchGender);
        NextButtonFinish = dialogView.findViewById(R.id.NextButtonFinish);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        CityPick();
        AgePick();
        GenderPick();
        NextButtonFinish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(TextInputLayoutGender.getEditText().getText().toString().equals(""))
                    TextInputLayoutGender.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutGender.setHelperText("");
                if(TextInputLayoutAge.getEditText().getText().toString().equals(""))
                    TextInputLayoutAge.setHelperText(getResources().getString(R.string.Required));
                else if(Period.between(LocalDate.of(Integer.valueOf(user.getYear()), Integer.valueOf(user.getMonth()), Integer.valueOf(user.getDay())),LocalDate.of(Year, Month+1, Day)).getYears() < 18)
                    TextInputLayoutAge.setHelperText(getResources().getString(R.string.RequiredAge18OrMore));
                else
                    TextInputLayoutAge.setHelperText("");
                if(TextInputLayoutCity.getEditText().getText().toString().equals(""))
                    TextInputLayoutCity.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutCity.setHelperText("");
                if(!(TextInputLayoutCity.getEditText().getText().toString().equals("")) && !(TextInputLayoutAge.getEditText().getText().toString().equals("")) && !(TextInputLayoutGender.getEditText().getText().toString().equals(""))){
                    alertDialog.cancel();
                    CreateAccount();
                }
            }
        });
    }
    private void CreateAccount(){
        user = new User(TextInputLayoutFirstName.getEditText().getText().toString(), TextInputLayoutLastName.getEditText().getText().toString(), TextInputLayoutEmail.getEditText().getText().toString());
        user.setCity(TextInputLayoutCity.getEditText().getText().toString());
        user.setGender(TextInputLayoutGender.getEditText().getText().toString());
        user.setDay(UserDay+"");
        user.setMonth(UserMonth+"");
        user.setYear(UserYear+"");
        user.setBirthDay(UserDay + "/" + UserMonth + "/" + UserYear);
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(),TextInputLayoutPassword.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            user.setUid(firebaseAuth.getUid());
                            user.setFullName(user.getFirstName()+" "+user.getLastName());
                            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                            new PopUpMSG(CreateAccount.this, getResources().getString(R.string.CreateAccount), getResources().getString(R.string.CompleteCreateAccount), SignIn.class);
                        }
                    });
                }
            }
        });
    }
    private void CityPick(){
        TextInputLayoutCity.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { setDialog(getResources().getStringArray(R.array.City),getResources().getString(R.string.SelectCity),TextInputLayoutCity.getEditText()); }
        });
    }
    private void AgePick(){
        TextInputLayoutAge.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAccount.this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        UserMonth = month;
                        UserYear = year;
                        UserDay = dayOfMonth;
                        String Date = dayOfMonth + "/" + month + "/" + year;
                        TextInputLayoutAge.getEditText().setText(Date);
                    }
                },Year, Month, Day);
                datePickerDialog.show();
            }
        });
    }
    private void GenderPick(){
        TextInputLayoutGender.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog(getResources().getStringArray(R.array.Gender),getResources().getString(R.string.SelectGender),TextInputLayoutGender.getEditText());
            }
        });
    }
    private void setDialog(String[] array, String title,TextView textViewPick){
        dialog = new Dialog(CreateAccount.this);
        dialog.setContentView(R.layout.dialog_search_spinner);
        dialog.getWindow().setLayout(1000,950);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditTextSearch = dialog.findViewById(R.id.EditTextSearch);
        ListViewSearch = dialog.findViewById(R.id.ListViewSearch);
        TextViewSearch = dialog.findViewById(R.id.TextViewSearch);
        TextViewSearch.setText(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateAccount.this, R.layout.dropdown_item, array);
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
            public void onClick(View v) { StartActivity(EasyLearnSCE.class); }
        });
    }
    private void StartActivity(Class Destination){
        startActivity(new Intent(CreateAccount.this, Destination));
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateAccount.this, EasyLearnSCE.class));
        finish();
    }
}