package com.example.easylearnsce.EngineeringFunc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.easylearnsce.Adapters.GenericEngineeringAdapter;
import com.example.easylearnsce.Class.RemoveCourseDialog;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserMenuInfo;
import com.example.easylearnsce.Class.UserNavigationView;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.SelectEngineering;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GenericEngineering extends AppCompatActivity {
    private ImageView BackIcon, MenuIcon, addCourse, removeCourse;
    private DrawerLayout drawerLayout;
    private NavigationView UserNavigationView;
    private TextView title, User_search;
    private User user = new User();
    private String Engineering = "Tag";
    private RecyclerView viewList;
    private NavigationView navigationView;
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
        NavigationView();
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
        navigationView = findViewById(R.id.navigationView);
        Engineering = (String)intent.getSerializableExtra("Tag");
        user = (User)intent.getSerializableExtra("user");
        user.setEngineering(title.getText().toString());
        UserNavigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        new UserMenuInfo(user,GenericEngineering.this);
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
        if(Engineering.equals("Structural Tag") || Engineering.equals("הנדסת בניין")) {
            menuItem = menu.findItem(R.id.StructuralEngineering);
            title.setText(getResources().getString(R.string.StructuralEngineering));
        }
        else if(Engineering.equals("Mechanical Tag") || Engineering.equals("הנדסת מכונות")) {
            menuItem = menu.findItem(R.id.MechanicalEngineering);
            title.setText(getResources().getString(R.string.MechanicalEngineering));
        }
        else if(Engineering.equals("Electrical Tag") || Engineering.equals("הנדסת חשמל ואלקטרוניקה")){
            menuItem = menu.findItem(R.id.ElectricalEngineering);
            title.setText(getResources().getString(R.string.ElectricalEngineering));
        }
        else if(Engineering.equals("Software Tag") || Engineering.equals("הנדסת תוכנה")){
            menuItem = menu.findItem(R.id.SoftwareEngineering);
            title.setText(getResources().getString(R.string.SoftwareEngineering));
        }
        else if(Engineering.equals("Industrial Tag") || Engineering.equals("הנדסת תעשייה וניהול")){
            menuItem = menu.findItem(R.id.IndustrialEngineering);
            title.setText(getResources().getString(R.string.IndustrialEngineering));
        }
        else if(Engineering.equals("Chemical Tag") || Engineering.equals("הנדסת כימיה")){
            menuItem = menu.findItem(R.id.ChemicalEngineering);
            title.setText(getResources().getString(R.string.ChemicalEngineering));
        }
        else if(Engineering.equals("Programming Tag") || Engineering.equals("מדעי המחשב")){
            menuItem = menu.findItem(R.id.ProgrammingComputer);
            title.setText(getResources().getString(R.string.ProgrammingComputer));
        }
        else if(Engineering.equals("Pre Tag") || Engineering.equals("מכינה")){
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
    private void NavigationView(){
        UserNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
    private void ChoseEngineering(){
        if(title.getText().equals("Structural Tag") || title.getText().equals("הנדסת בניין"))
            SetTags(allCourses.getChemical_Engineering());
        else if(title.getText().equals("Mechanical Tag") || title.getText().equals("הנדסת מכונות"))
            SetTags(allCourses.getMechanical_Engineering());
        else if(title.getText().equals("Electrical Tag") || title.getText().equals("הנדסת חשמל ואלקטרוניקה"))
            SetTags(allCourses.getElectrical_Engineering());
        else if(title.getText().equals("Software Tag") || title.getText().equals("הנדסת תוכנה"))
            SetTags(allCourses.getSoftware_Engineering());
        else if(title.getText().equals("Industrial Tag") || title.getText().equals("הנדסת תעשייה וניהול"))
            SetTags(allCourses.getIndustrial_Engineering());
        else if(title.getText().equals("Chemical Tag") || title.getText().equals("הנדסת כימיה"))
            SetTags(allCourses.getChemical_Engineering());
        else if(title.getText().equals("Programming Computer ") || title.getText().equals("מדעי המחשב"))
            SetTags(allCourses.getPrograming_computer());
        else if(title.getText().equals("Pre Tag") || title.getText().equals("מכינה"))
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
        if(Engineering.equals("Structural Tag") || Engineering.equals("הנדסת בניין"))
            return "Structural Tag";
        else if(Engineering.equals("Mechanical Tag") || Engineering.equals("הנדסת מכונות"))
            return "Mechanical Tag";
        else if(Engineering.equals("Electrical Tag") || Engineering.equals("הנדסת חשמל ואלקטרוניקה"))
            return "Electrical Tag";
        else if(Engineering.equals("Software Tag") || Engineering.equals("הנדסת תוכנה"))
            return "Software Tag";
        else if(Engineering.equals("Industrial Tag") || Engineering.equals("הנדסת תעשייה וניהול"))
            return "Industrial Tag";
        else if(Engineering.equals("Chemical Tag") || Engineering.equals("הנדסת כימיה"))
            return "Chemical Tag";
        else if(Engineering.equals("Programming Computer ") || Engineering.equals("מדעי המחשב"))
            return "Programming Tag";
        else if(Engineering.equals("Pre Tag") || Engineering.equals("מכינה"))
            return "Pre Tag";
        return "Other";
    }
    private void SetTags(Course[] Courses){
        for(int i=0; i<Courses.length; i++)
            courses.add(Courses[i]);
        ShowTags(courses);
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