package com.example.easylearnsce.Client.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easylearnsce.Client.Adapters.RequestsAdapter;
import com.example.easylearnsce.Client.Class.UserMenuInfo;
import com.example.easylearnsce.Client.Class.PopUpMSG;
import com.example.easylearnsce.Client.Class.Request;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.Class.UserNavigationView;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Requests extends AppCompatActivity {
    private TextView Title, TextViewSearch;
    private Dialog dialog;
    private LinearLayout RequestLinearLayout;
    private TextInputLayout TextInputLayoutFirstName, TextInputLayoutLastName ,TextInputLayoutEmail, TextInputLayoutRequests, TextInputLayoutDetails;
    private EditText EditTextSearch;
    private ListView ListViewSearch;
    private Button ButtonRequests;
    private DrawerLayout drawerLayout;
    private ImageView BackIcon, MenuIcon;
    private User user = new User();
    private RecyclerView recyclerView;
    private ArrayList<Request> Requests;
    private Request request;
    private NavigationView navigationView;
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        init();
    }
    private void init(){
        setID();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavigationView();
        CheckType();
    }
    private void setID(){
        intent = getIntent();
        Requests = new ArrayList<>();
        user = (User)intent.getSerializableExtra("user");
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        navigationView = findViewById(R.id.navigationView);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.Requests));
        drawerLayout = findViewById(R.id.drawerLayout);
        recyclerView = findViewById(R.id.recyclerView);
        new UserMenuInfo(user, Requests.this);
        TextInputLayoutFirstName = findViewById(R.id.TextInputLayoutFirstName);
        TextInputLayoutLastName = findViewById(R.id.TextInputLayoutLastName);
        TextInputLayoutEmail = findViewById(R.id.TextInputLayoutEmail);
        TextInputLayoutRequests = findViewById(R.id.TextInputLayoutRequests);
        TextInputLayoutDetails = findViewById(R.id.TextInputLayoutDetails);
        ButtonRequests = findViewById(R.id.ButtonRequests);
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemRequests);
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
                new UserNavigationView(Requests.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBackPressed(); }
        });
    }
    private void CheckType(){
        if(user.getType().equals("Admin") || user.getType().equals("אדמין"))
            Admin();
        else if(user.getType().equals("Teacher") || user.getType().equals("מרצה") || user.getType().equals("Student") || user.getType().equals("סטודנט"))
            TeacherAndStudent();
    }
    private void TeacherAndStudent(){
        TextInputLayoutFirstName.getEditText().setText(user.getFirstName());
        TextInputLayoutLastName.getEditText().setText(user.getLastName());
        TextInputLayoutEmail.getEditText().setText(user.getEmail());
        RequestPick();
        SendRequest();
    }
    private void RequestPick(){
        TextInputLayoutRequests.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { setDialog(getResources().getStringArray(R.array.Permissions),getResources().getString(R.string.SelectRequest),TextInputLayoutRequests.getEditText()); }
        });
    }
    private void setDialog(String[] array, String title,TextView textViewPick){
        dialog = new Dialog(Requests.this);
        dialog.setContentView(R.layout.dialog_search_spinner);
        dialog.getWindow().setLayout(1000,950);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditTextSearch = dialog.findViewById(R.id.EditTextSearch);
        ListViewSearch = dialog.findViewById(R.id.ListViewSearch);
        TextViewSearch = dialog.findViewById(R.id.TextViewSearch);
        TextViewSearch.setText(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Requests.this, R.layout.dropdown_item, array);
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
                if(adapterView.getItemAtPosition(i).toString().equals("Report Problem") || adapterView.getItemAtPosition(i).toString().equals("דיווח על תקלה") ||
                        adapterView.getItemAtPosition(i).toString().equals("Other") || adapterView.getItemAtPosition(i).toString().equals("אחר"))
                    TextInputLayoutDetails.setVisibility(View.VISIBLE);
                else
                    TextInputLayoutDetails.setVisibility(View.GONE);
            }
        });
    }
    private void SendRequest(){
        ButtonRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextInputLayoutRequests.getEditText().getText().toString().equals(""))
                    TextInputLayoutRequests.setHelperText(getResources().getString(R.string.Required));
                else{
                    TextInputLayoutRequests.setHelperText("");
                    if(TextInputLayoutRequests.getEditText().getText().toString().equals("Get Teacher Permission") || TextInputLayoutRequests.getEditText().getText().toString().equals("קבלת הרשאות מרצה")) {
                        if(user.getType().equals("Teacher") || user.getType().equals("מרצה"))
                            new PopUpMSG(Requests.this, getResources().getString(R.string.Requests), getResources().getString(R.string.RequestApproved));
                        else {
                            request = new Request(user.getUid(), user.getFirstName(), user.getLastName(), user.getEmail(), TextInputLayoutRequests.getEditText().getText().toString(), user.getType());
                            TeacherRequest(request);
                        }
                    }
                    else{
                        if(TextInputLayoutDetails.getEditText().getText().toString().equals(""))
                            TextInputLayoutDetails.setHelperText(getResources().getString(R.string.Required));
                        else {
                            request = new Request( user.getUid(), user.getFirstName(), user.getLastName(), user.getEmail(), TextInputLayoutRequests.getEditText().getText().toString(), user.getType(), TextInputLayoutDetails.getEditText().getText().toString());
                            OtherRequest(request);
                        }
                    }
                }
            }
        });
    }
    private void TeacherRequest(Request request){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests").child(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { UploadRequest(request); }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void OtherRequest(Request request){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests").child(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { UploadRequest(request); }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void UploadRequest(Request request){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests");
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child(request.getId()).setValue(request);
        new PopUpMSG(Requests.this, getResources().getString(R.string.Requests), getResources().getString(R.string.RequestSentSuccessfully), Home.class,user);
    }
    private void Admin(){
        TextInputLayoutFirstName.setVisibility(View.GONE);
        TextInputLayoutLastName.setVisibility(View.GONE);
        TextInputLayoutEmail.setVisibility(View.GONE);
        TextInputLayoutRequests.setVisibility(View.GONE);
        TextInputLayoutDetails.setVisibility(View.GONE);
        ButtonRequests.setVisibility(View.GONE);
        RequestLinearLayout = findViewById(R.id.RequestLinearLayout);
        RequestLinearLayout.setVisibility(View.VISIBLE);
        setTags();
    }
    private void setTags(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Requests.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Request request = dataSnapshot1.getValue(Request.class);
                        if(request.getStatus().equals("Pending Approval") || request.getStatus().equals("ממתין לתשובה"))
                            Requests.add(request);
                    }
                ShowRequests(Requests);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void ShowRequests(ArrayList<Request> requests){
        RequestsAdapter requestsAdapter = new RequestsAdapter(this,requests);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(requestsAdapter);
    }
    public void onBackPressed() {
        intent = new Intent(Requests.this, Home.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}