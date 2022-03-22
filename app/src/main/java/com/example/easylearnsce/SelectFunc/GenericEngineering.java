package com.example.easylearnsce.SelectFunc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.AddCourseDialog;
import com.example.easylearnsce.Class.AllCourses;
import com.example.easylearnsce.Class.Course;
import com.example.easylearnsce.Class.CourseView;
import com.example.easylearnsce.Class.Engineering;
import com.example.easylearnsce.Class.RemoveCourseDialog;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserLanguage;
import com.example.easylearnsce.Class.UserMenuAdapter;
import com.example.easylearnsce.Class.UserNavView;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.SelectEngineering;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GenericEngineering extends AppCompatActivity {
    private ImageView BackIcon, MenuIcon, addCourse, removeCourse;
    private DrawerLayout drawerLayout;
    private NavigationView UserNavigationView;
    private UserLanguage lagnuage;
    private TextView title, TextViewSearchLanguage, User_search;
    private User user = new User();
    private String Engineering = "Engineering";
    private RecyclerView viewList;
    private NavigationView user_navView;
    private List<Course> courses;
    private Intent intent;
    private AllCourses allCourses = new AllCourses();
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
        setLanguage();
        NavView();
        setCourses();
        addCourse();
        removeCourse();
    }
    public void setID(){
        intent = getIntent();
        courses = new ArrayList<>();
        title = findViewById(R.id.Title);
        addCourse = findViewById(R.id.addCourse);
        removeCourse = findViewById(R.id.removeCourse);
        User_search = findViewById(R.id.User_search);
        viewList = findViewById(R.id.CoursesRV);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        user_navView = findViewById(R.id.UserNavigationView);
        Engineering = (String)intent.getSerializableExtra("Engineering");
        user = (User)intent.getSerializableExtra("user");
        user.setEngineering(title.getText().toString());
        UserNavigationView = findViewById(R.id.UserNavigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        new UserMenuAdapter(user,GenericEngineering.this);
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        lagnuage = new UserLanguage(GenericEngineering.this, user);
    }
    private void MenuItem(){
        Menu menu= UserNavigationView.getMenu();
        MenuItem Item1 = menu.findItem(R.id.StructuralEngineering);
        MenuItem Item2 = menu.findItem(R.id.MechanicalEngineering);
        MenuItem Item3 = menu.findItem(R.id.ElectricalEngineering);
        MenuItem Item4 = menu.findItem(R.id.SoftwareEngineering);
        MenuItem Item5 = menu.findItem(R.id.IndustrialEngineering);
        MenuItem Item6 = menu.findItem(R.id.ChemicalEngineering);
        MenuItem Item7 = menu.findItem(R.id.ProgrammingComputer);
        MenuItem Item8 = menu.findItem(R.id.PreEngineering);
        Item1.setVisible(true);
        Item2.setVisible(true);
        Item3.setVisible(true);
        Item4.setVisible(true);
        Item5.setVisible(true);
        Item6.setVisible(true);
        Item7.setVisible(true);
        Item8.setVisible(true);
        MenuItem menuItem = menu.findItem(R.id.StructuralEngineering);
        if(Engineering.equals("Structural Engineering") || Engineering.equals("הנדסת בניין")) {
            menuItem = menu.findItem(R.id.StructuralEngineering);
            title.setText(getResources().getString(R.string.StructuralEngineering));
        }
        else if(Engineering.equals("Mechanical Engineering") || Engineering.equals("הנדסת מכונות")) {
            menuItem = menu.findItem(R.id.MechanicalEngineering);
            title.setText(getResources().getString(R.string.MechanicalEngineering));
        }
        else if(Engineering.equals("Electrical Engineering") || Engineering.equals("הנדסת חשמל ואלקטרוניקה")){
            menuItem = menu.findItem(R.id.ElectricalEngineering);
            title.setText(getResources().getString(R.string.ElectricalEngineering));
        }
        else if(Engineering.equals("Software Engineering") || Engineering.equals("הנדסת תוכנה")){
            menuItem = menu.findItem(R.id.SoftwareEngineering);
            title.setText(getResources().getString(R.string.SoftwareEngineering));
        }
        else if(Engineering.equals("Industrial Engineering") || Engineering.equals("הנדסת תעשייה וניהול")){
            menuItem = menu.findItem(R.id.IndustrialEngineering);
            title.setText(getResources().getString(R.string.IndustrialEngineering));
        }
        else if(Engineering.equals("Chemical Engineering") || Engineering.equals("הנדסת כימיה")){
            menuItem = menu.findItem(R.id.ChemicalEngineering);
            title.setText(getResources().getString(R.string.ChemicalEngineering));
        }
        else if(Engineering.equals("Programming Engineering") || Engineering.equals("מדעי המחשב")){
            menuItem = menu.findItem(R.id.ProgrammingComputer);
            title.setText(getResources().getString(R.string.ProgrammingComputer));
        }
        else if(Engineering.equals("Pre Engineering") || Engineering.equals("מכינה")){
            menuItem = menu.findItem(R.id.PreEngineering);
            title.setText(getResources().getString(R.string.PreEngineering));
        }
        menuItem.setCheckable(false);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);
        ChoseEngineering();
    }
    private void addCourse(){
        if(user.getType().equals("Admin") || user.getType().equals("Teacher")) {
            addCourse.setVisibility(View.VISIBLE);
            addCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddCourseDialog addCourseDialog = new AddCourseDialog();
                    addCourseDialog.show(getSupportFragmentManager(), "Add Course");
                }
            });
        }
    }
    private void removeCourse(){
        if(user.getType().equals("Admin") || user.getType().equals("Teacher")) {
            removeCourse.setVisibility(View.VISIBLE);
            removeCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RemoveCourseDialog addCourseDialog = new RemoveCourseDialog();
                    addCourseDialog.show(getSupportFragmentManager(), "Remove Course");
                }
            });
        }
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
                new UserNavView(GenericEngineering.this, item.getItemId(), user);
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
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lagnuage.setEngineering(Engineering);
                lagnuage.setDialog();
            }
        });
    }
    private void ChoseEngineering(){
        if(title.getText().equals("Structural Engineering") || title.getText().equals("הנדסת בניין"))
            SetTags(allCourses.getChemical_Engineering());
        else if(title.getText().equals("Mechanical Engineering") || title.getText().equals("הנדסת מכונות"))
            SetTags(allCourses.getMechanical_Engineering());
        else if(title.getText().equals("Electrical Engineering") || title.getText().equals("הנדסת חשמל ואלקטרוניקה"))
            SetTags(allCourses.getElectrical_Engineering());
        else if(title.getText().equals("Software Engineering") || title.getText().equals("הנדסת תוכנה"))
            SetTags(allCourses.getSoftware_Engineering());
        else if(title.getText().equals("Industrial Engineering") || title.getText().equals("הנדסת תעשייה וניהול"))
            SetTags(allCourses.getIndustrial_Engineering());
        else if(title.getText().equals("Chemical Engineering") || title.getText().equals("הנדסת כימיה"))
            SetTags(allCourses.getChemical_Engineering());
        else if(title.getText().equals("Programming Computer ") || title.getText().equals("מדעי המחשב"))
            SetTags(allCourses.getPrograming_computer());
        else if(title.getText().equals("Pre Engineering") || title.getText().equals("מכינה"))
            SetTags(allCourses.getPre_Engineering());
    }
    private void setCourses(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Courses").child(getEngineeringName()).child("Hebrew");;
        if (getResources().getConfiguration().locale.getDisplayLanguage().equals("Hebrew") || getResources().getConfiguration().locale.getDisplayLanguage().equals("עברית"))
            reference = database.getReference().child("Courses").child(getEngineeringName()).child("Hebrew");
        else if (getResources().getConfiguration().locale.getDisplayLanguage().equals("English") || getResources().getConfiguration().locale.getDisplayLanguage().equals("אנגלית"))
            reference = database.getReference().child("Courses").child(getEngineeringName()).child("English");
        reference.setValue(courses);
    }
    private String getEngineeringName(){
        if(Engineering.equals("Structural Engineering") || Engineering.equals("הנדסת בניין"))
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
            return "Programming Engineering";
        else if(Engineering.equals("Pre Engineering") || Engineering.equals("מכינה"))
            return "Pre Engineering";
        return "Other";
    }
    private void SetTags(Course[] Courses){
        for(int i=0; i<Courses.length; i++)
            courses.add(Courses[i]);
        ShowTags(courses);
    }
    private void ShowTags(List<Course> selects){
        CourseView mySelects = new CourseView(this,selects,Engineering);
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