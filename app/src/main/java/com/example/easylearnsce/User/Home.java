package com.example.easylearnsce.User;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easylearnsce.Class.Language;
import com.example.easylearnsce.Class.Select;
import com.example.easylearnsce.Class.SelectView;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserImage;
import com.example.easylearnsce.Class.UserMenuAdapter;
import com.example.easylearnsce.Class.UserNavView;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {
    private TextView Title, TextViewSearchLanguage, TextViewSearch;
    private Dialog dialog;
    private EditText EditTextSearch;
    private ListView ListViewSearch;
    private DrawerLayout drawerLayout;
    private ImageView BackIcon, MenuIcon;
    private User user = new User();
    private RecyclerView viewList;
    private List<Select> selects;
    private NavigationView UserNavigationView;
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final int SIZE = 5;
    private String HomeTagsName[] = new String[SIZE] ;
    private int EngineeringsPhotos[] = {R.drawable.engineering, R.drawable.message, R.drawable.person, R.drawable.forgotpassword ,R.drawable.signout};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }
    private void init(){
        setID();
        setLanguage();
        setTags();
        MenuItem();
        SignOutIcon();
        MenuIcon();
        NavView();
    }
    private void setID(){
        intent = getIntent();
        selects = new ArrayList<>();
        user = (User)intent.getSerializableExtra("user");
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        BackIcon.setImageResource(R.drawable.signout);
        UserNavigationView = findViewById(R.id.UserNavigationView);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.Home));
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        drawerLayout = findViewById(R.id.drawerLayout);
        viewList = findViewById(R.id.MainScreenRV);
        HomeTagsName = getResources().getStringArray(R.array.HomeTagsName);
        new UserMenuAdapter(user,Home.this);
    }
    private void MenuItem(){
        Menu menu= UserNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemHome);
        menuItem.setCheckable(false);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);
    }
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { setDialog(); }
        });
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
                new UserNavView(Home.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void SignOutIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.signOut();
                startActivity(new Intent(Home.this, EasyLearnSCE.class));
                finish();
            }
        });
    }
    private void setTags(){
        for(int i=0; i<SIZE; i++)
            selects.add(new Select(HomeTagsName[i], EngineeringsPhotos[i]));
        ShowTags(selects);
    }
    private void ShowTags(List<Select> selects){
        SelectView Select = new SelectView(this,selects);
        Select.setUser(user);
        viewList.setLayoutManager(new GridLayoutManager(this,1));
        viewList.setAdapter(Select);
    }
    private void StartActivity(Class Destination){
        intent = new Intent(Home.this, Destination);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    private void setDialog(){
        dialog = new Dialog(Home.this);
        dialog.setContentView(R.layout.dialog_search_spinner);
        dialog.getWindow().setLayout(800,800);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditTextSearch = dialog.findViewById(R.id.EditTextSearch);
        ListViewSearch = dialog.findViewById(R.id.ListViewSearch);
        TextViewSearch = dialog.findViewById(R.id.TextViewSearch);
        TextViewSearch.setText(getResources().getString(R.string.SelectLanguage));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Home.this, R.layout.dropdown_item, getResources().getStringArray(R.array.Lagnuage));
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
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                Locale locale;
                if(adapterView.getItemAtPosition(i).toString().equals(getResources().getString(R.string.English)))
                    locale = new Locale("en");
                else
                    locale = new Locale("he");
                Locale.setDefault(locale);
                Resources resources = Home.this.getResources();
                Configuration configuration = resources.getConfiguration();
                configuration.setLocale(locale);
                resources.updateConfiguration(configuration,resources.getDisplayMetrics());
                StartActivity(Home.class);
            }
        });
    }
    public void onBackPressed() {

    }
}