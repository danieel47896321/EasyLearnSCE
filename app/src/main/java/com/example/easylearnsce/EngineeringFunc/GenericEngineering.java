package com.example.easylearnsce.EngineeringFunc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easylearnsce.Class.Course;
import com.example.easylearnsce.Adapters.GenericEngineeringAdapter;
import com.example.easylearnsce.Class.Loading;
import com.example.easylearnsce.Class.PopUpMSG;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserMenuInfo;
import com.example.easylearnsce.Class.UserNavigationView;
import com.example.easylearnsce.Guest.CreateAccount;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.SelectEngineering;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenericEngineering extends AppCompatActivity {
    private ImageView BackIcon, MenuIcon, addCourse, removeCourse;
    private TextInputLayout TextInputLayoutCourse, TextInputLayoutTeacherName ,TextInputLayoutDepartment, TextInputLayoutYear, TextInputLayoutSemester;
    private Button ButtonAddCourse, ButtonRemoveCourse, ButtonCancel;
    private DrawerLayout drawerLayout;
    private Dialog dialog;
    private Loading loading;
    private ListView ListViewSearch;
    private EditText EditTextSearch;
    private TextView Title, User_search, TextViewSearch;
    private User user = new User();
    private String Engineering = "Tag";
    private RecyclerView viewList;
    private NavigationView navigationView;
    private ArrayList<Course> courses;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_engineering);
        init();
    }
    public void init(){
        setID();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavigationView();
        addCourse();
        removeCourseDialog();
        setTags();
    }
    public void setID(){
        intent = getIntent();
        courses = new ArrayList<>();
        Title = findViewById(R.id.Title);
        addCourse = findViewById(R.id.addCourse);
        removeCourse = findViewById(R.id.removeCourse);
        User_search = findViewById(R.id.User_search);
        viewList = findViewById(R.id.CoursesRV);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        navigationView = findViewById(R.id.navigationView);
        Engineering = (String)intent.getSerializableExtra("title");
        Title.setText(Engineering);
        user = (User)intent.getSerializableExtra("user");
        user.setEngineering(Title.getText().toString());
        drawerLayout = findViewById(R.id.drawerLayout);
        new UserMenuInfo(user,GenericEngineering.this);
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.ItemEngineering);
        item.setTitle(Title.getText());
        item.setVisible(true);
        item.setCheckable(false);
        item.setChecked(true);
        item.setEnabled(false);
        if(Engineering.equals("Structural Engineering") || Engineering.equals("הנדסת בניין"))
            item.setIcon(R.drawable.structural);
        else if(Engineering.equals("Mechanical Engineering") || Engineering.equals("הנדסת מכונות"))
            item.setIcon(R.drawable.mechanical);
        else if(Engineering.equals("Electrical Engineering") || Engineering.equals("הנדסת חשמל ואלקטרוניקה"))
            item.setIcon(R.drawable.electrical);
        else if(Engineering.equals("Software Engineering") || Engineering.equals("הנדסת תוכנה"))
            item.setIcon(R.drawable.software);
        else if(Engineering.equals("Industrial Engineering") || Engineering.equals("הנדסת תעשייה וניהול"))
            item.setIcon(R.drawable.industrial);
        else if(Engineering.equals("Chemical Engineering") || Engineering.equals("הנדסת כימיה"))
            item.setIcon(R.drawable.chemical);
        else if(Engineering.equals("Programming Computer") || Engineering.equals("מדעי המחשב"))
            item.setIcon(R.drawable.software);
        else if(Engineering.equals("Pre Engineering") || Engineering.equals("מכינה"))
            item.setIcon(R.drawable.mehina);
    }
    private void addCourse(){
        if(user.getType().equals("Admin") || user.getType().equals("Teacher")) {
            addCourse.setVisibility(View.VISIBLE);
            addCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddCourseDialog();
                }
            });
        }
    }
    private void AddCourseDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GenericEngineering.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_course,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutCourse = dialogView.findViewById(R.id.TextInputLayoutCourse);
        TextInputLayoutTeacherName = dialogView.findViewById(R.id.TextInputLayoutTeacherName);
        TextInputLayoutDepartment = dialogView.findViewById(R.id.TextInputLayoutDepartment);
        TextInputLayoutYear = dialogView.findViewById(R.id.TextInputLayoutYear);
        TextInputLayoutSemester = dialogView.findViewById(R.id.TextInputLayoutSemester);
        ButtonAddCourse = dialogView.findViewById(R.id.ButtonAddCourse);
        ButtonCancel = dialogView.findViewById(R.id.ButtonCancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        EndIcon();
        YearPick();
        SemesterPick();
        if(user.getType().equals("Teacher") || user.getType().equals("מרצה")){
            TextInputLayoutTeacherName.getEditText().setText(user.getFullName());
            TextInputLayoutTeacherName.getEditText().setClickable(false);
            TextInputLayoutTeacherName.getEditText().setFocusable(false);
            TextInputLayoutDepartment.getEditText().setText(Engineering);
            TextInputLayoutDepartment.getEditText().setClickable(false);
            TextInputLayoutDepartment.getEditText().setFocusable(false);
        }
        else
            DepartmentPick();
        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alertDialog.cancel(); }
        });
        ButtonAddCourse.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(TextInputLayoutCourse.getEditText().getText().toString().equals(""))
                    TextInputLayoutCourse.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutCourse.setHelperText("");
                if(TextInputLayoutTeacherName.getEditText().getText().toString().equals(""))
                    TextInputLayoutTeacherName.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutTeacherName.setHelperText("");
                if(TextInputLayoutDepartment.getEditText().getText().toString().equals(""))
                    TextInputLayoutDepartment.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutDepartment.setHelperText("");
                if(TextInputLayoutYear.getEditText().getText().toString().equals(""))
                    TextInputLayoutYear.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutYear.setHelperText("");
                if(TextInputLayoutSemester.getEditText().getText().toString().equals(""))
                    TextInputLayoutSemester.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutSemester.setHelperText("");
                if(!(TextInputLayoutCourse.getEditText().getText().toString().equals("")) && !(TextInputLayoutTeacherName.getEditText().getText().toString().equals("")) && !(TextInputLayoutDepartment.getEditText().getText().toString().equals(""))
                        && !(TextInputLayoutYear.getEditText().getText().toString().equals(""))  && !(TextInputLayoutSemester.getEditText().getText().toString().equals(""))) {
                    alertDialog.cancel();
                    if (user.getType().equals("Admin") || user.getType().equals("אדמין")) {
                        loading = new Loading(GenericEngineering.this);
                        AddCourse(new Course(TextInputLayoutCourse.getEditText().getText().toString(), TextInputLayoutTeacherName.getEditText().getText().toString(),
                                TextInputLayoutDepartment.getEditText().getText().toString(), TextInputLayoutYear.getEditText().getText().toString(), TextInputLayoutSemester.getEditText().getText().toString()));
                    }
                    else{
                        if(user.getPermission().equals("High")){
                            AddCourse(new Course(TextInputLayoutCourse.getEditText().getText().toString(), user.getFullName(), Engineering,
                                    TextInputLayoutYear.getEditText().getText().toString(), TextInputLayoutSemester.getEditText().getText().toString()));
                        }
                        else{
                            new PopUpMSG(GenericEngineering.this,getResources().getString(R.string.AddCourse), "ss");
                        }
                    }
                }
            }
        });
    }
    private void AddCourse(Course course){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Courses").child(getEngineeringName()).child((int)(Math.random()*1000000000)+"");
        reference.setValue(course);
        loading.stop();
    }
    private void removeCourseDialog(){
        if(user.getType().equals("Admin") || user.getType().equals("Teacher")) {
            removeCourse.setVisibility(View.VISIBLE);
            removeCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    private void DepartmentPick(){
        TextInputLayoutDepartment.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { setDialog(getResources().getStringArray(R.array.Department),getResources().getString(R.string.SelectDepartment),TextInputLayoutDepartment.getEditText()); }
        });
    }
    private void YearPick(){
        TextInputLayoutYear.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { setDialog(getResources().getStringArray(R.array.Year),getResources().getString(R.string.SelectYear),TextInputLayoutYear.getEditText()); }
        });
    }
    private void SemesterPick(){
        TextInputLayoutSemester.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { setDialog(getResources().getStringArray(R.array.Semester),getResources().getString(R.string.SelectSemester),TextInputLayoutSemester.getEditText()); }
        });
    }
    private void EndIcon() {
        TextInputLayoutCourse.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Clear(TextInputLayoutCourse); }
        });
        TextInputLayoutTeacherName.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Clear(TextInputLayoutTeacherName); }
        });
    }
    private void setDialog(String[] array, String title,TextView textViewPick){
        dialog = new Dialog(GenericEngineering.this);
        dialog.setContentView(R.layout.dialog_search_spinner);
        dialog.getWindow().setLayout(1000,950);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditTextSearch = dialog.findViewById(R.id.EditTextSearch);
        ListViewSearch = dialog.findViewById(R.id.ListViewSearch);
        TextViewSearch = dialog.findViewById(R.id.TextViewSearch);
        TextViewSearch.setText(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(GenericEngineering.this, R.layout.dropdown_item, array);
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
    private void Clear(TextInputLayout input){
        input.setHelperText("");
        input.getEditText().setText("");
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
                new UserNavigationView(GenericEngineering.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(GenericEngineering.this, SelectEngineering.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
    }
    private String getEngineeringName(){
        if(Engineering.equals("Structural Tag") || Engineering.equals("הנדסת בניין"))
            return "Structural Engineering";
        else if(Engineering.equals("Mechanical Engineering") || Engineering.equals("הנדסת מכונות"))
            return "Mechanical Engineering";
        else if(Engineering.equals("Electrical Engineering") || Engineering.equals("הנדסת חשמל ואלקטרוניקה"))
            return "Electrical Engineering";
        else if(Engineering.equals("Software Engineering") || Engineering.equals("הנדסת תוכנה"))
            return "Software Engineering";
        else if(Engineering.equals("Industrial Engineering") || Engineering.equals("הנדסת תעשייה וניהול"))
            return "Industrial Engineering";
        else if(Engineering.equals("Chemical Engineering") || Engineering.equals("הנדסת כימיה"))
            return "Chemical Engineering";
        else if(Engineering.equals("Programming Computer ") || Engineering.equals("מדעי המחשב"))
            return "Programming Computer";
        else if(Engineering.equals("Pre Engineering") || Engineering.equals("מכינה"))
            return "Pre Engineering";
        return "Other";
    }
    private void setTags(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Courses").child(getEngineeringName());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    Course course = data.getValue(Course.class);
                    courses.add(course);
                }
                ShowTags(courses);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void ShowTags(List<Course> selects){
        GenericEngineeringAdapter mySelects = new GenericEngineeringAdapter(this,selects,Engineering);
        mySelects.setUser(user);
        viewList.setLayoutManager(new GridLayoutManager(this,1));
        viewList.setAdapter(mySelects);
    }
    @Override
    public void onBackPressed() {
        intent = new Intent(GenericEngineering.this, SelectEngineering.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}