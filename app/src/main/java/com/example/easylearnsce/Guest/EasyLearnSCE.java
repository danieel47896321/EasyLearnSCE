package com.example.easylearnsce.Guest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
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

import com.example.easylearnsce.Class.GuestNavView;
import com.example.easylearnsce.Class.Language;
import com.example.easylearnsce.Class.Select;
import com.example.easylearnsce.Class.SelectView;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.example.easylearnsce.SelectFunc.GenericEngineering;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class EasyLearnSCE extends AppCompatActivity {
    private RecyclerView Tags;
    private DrawerLayout drawerLayout;
    private List<Select> Selects;
    private NavigationView GuestNavView;
    private TextView Title, TextViewSearchLanguage, TextViewSearch;
    private Dialog dialog;
    private EditText EditTextSearch;
    private ListView ListViewSearch;
    private ImageView BackIcon, MenuIcon;
    private AutoCompleteTextView AutoCompleteTextViewLanguage;
    private final int SIZE = 5;
    private String EasyLearnSCETagsName[] = new String[SIZE];
    private int TagsPhoto[] = {R.drawable.login,R.drawable.register,R.drawable.forgotpassword,R.drawable.about,R.drawable.contact};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easylearnsce);
        init();
    }
    private void init(){
        Selects = new ArrayList<>();
        setID();
        setTags();
        setLanguage();
        MenuItem();
        MenuIcon();
        NavView();
    }
    private void setID(){
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        BackIcon.setVisibility(View.INVISIBLE);
        drawerLayout = findViewById(R.id.drawerLayout);
        Title = findViewById(R.id.Title);
        Title.setText("");
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        GuestNavView = findViewById(R.id.GuestNavView);
        Tags = findViewById(R.id.Tags);
        EasyLearnSCETagsName = getResources().getStringArray(R.array.EasyLearnSCETagsName);
    }
    private void MenuItem(){
        Menu menu= GuestNavView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemEasyLearnSCE);
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
        GuestNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new GuestNavView(EasyLearnSCE.this, item.getItemId());
                return false;
            }
        });
    }
    private void setTags(){
        for(int i=0; i<SIZE; i++)
            Selects.add(new Select(EasyLearnSCETagsName[i], TagsPhoto[i]));
        ShowTags(Selects);
    }
    private void ShowTags(List<Select> selects){
        SelectView Select = new SelectView(this,selects);
        Tags.setLayoutManager(new GridLayoutManager(this,1));
        Tags.setAdapter(Select);
    }
    private void setDialog(){
        dialog = new Dialog(EasyLearnSCE.this);
        dialog.setContentView(R.layout.dialog_search_spinner);
        dialog.getWindow().setLayout(800,800);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditTextSearch = dialog.findViewById(R.id.EditTextSearch);
        ListViewSearch = dialog.findViewById(R.id.ListViewSearch);
        TextViewSearch = dialog.findViewById(R.id.TextViewSearch);
        TextViewSearch.setText(getResources().getString(R.string.SelectLanguage));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EasyLearnSCE.this, R.layout.dropdown_item, getResources().getStringArray(R.array.Lagnuage));
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
                new Language(EasyLearnSCE.this,adapterView.getItemAtPosition(i).toString());
            }
        });
    }
}