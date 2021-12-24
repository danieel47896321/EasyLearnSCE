package com.example.easylearnsce.SelectFunc;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easylearnsce.Class.AllCourses;
import com.example.easylearnsce.Class.Course;
import com.example.easylearnsce.Class.CourseView;
import com.example.easylearnsce.Class.Select;
import com.example.easylearnsce.Class.SelectView;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserMenuAdapter;
import com.example.easylearnsce.Class.UserNavView;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.SelectEngineering;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class GenericEngineering extends AppCompatActivity {
    private ImageView BackIcon, MenuIcon, ClearYear, ClearSemester, ClearCourse;
    private DrawerLayout drawerLayout;
    private NavigationView UserNavigationView;
    private TextView title,user_fullname,user_email,TextViewSearch, TextViewSearchYear,TextViewSearchSemester,TextViewSearchCourse;
    private User user = new User();
    private RecyclerView viewList;
    private NavigationView user_navView;
    private List<Course> courses;
    private Intent intent;
    private Dialog dialog;
    private EditText EditTextSearch;
    private ListView ListViewSearch;
    private AllCourses allCourses = new AllCourses();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_engineering);
        init();
    }
    public void init(){
        setID();
        ChoseEngineering();
        BackIcon();
        MenuIcon();
        NavView();
        YearFiltter();
        SemesterFiltter();
        CourseFiltter();
    }
    public void setID(){
        intent = getIntent();
        courses = new ArrayList<>();
        title = findViewById(R.id.Title);
        viewList = findViewById(R.id.CoursesRV);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        ClearYear = findViewById(R.id.ClearYear);
        ClearSemester = findViewById(R.id.ClearSemester);
        ClearCourse = findViewById(R.id.ClearCourse);
        user_navView = findViewById(R.id.UserNavigationView);
        title.setText((String)intent.getSerializableExtra("title"));
        user = (User)intent.getSerializableExtra("user");
        user.setEngineering(title.getText().toString());
        user_fullname = user_navView.getHeaderView(0).findViewById(R.id.user_fullname);
        user_email = user_navView.getHeaderView(0).findViewById(R.id.user_email);
        user_fullname.setText(user.getFirstname()+" "+user.getLastname());
        user_email.setText(user.getEmail());
        UserNavigationView = findViewById(R.id.UserNavigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        TextViewSearchYear = findViewById(R.id.TextViewSearchYear);
        TextViewSearchSemester = findViewById(R.id.TextViewSearchSemester);
        TextViewSearchCourse = findViewById(R.id.TextViewSearchCourse);
        new UserMenuAdapter(user,GenericEngineering.this);

    }
    private void MenuItem(){
        Menu menu= UserNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemSelectEngineering);
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
                user.setEngineering("null");
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
    }
    private String Engineering(){
        if(title.getText().equals(getResources().getString(R.string.StructuralEngineering)))
            return "Structural Engineering";
        else if(title.getText().equals(getResources().getString(R.string.MechanicalEngineering)))
            return "Mechanical Engineering";
        else if(title.getText().equals(getResources().getString(R.string.ElectricalEngineering)))
            return "Electrical Engineering";
        else if(title.getText().equals(getResources().getString(R.string.SoftwareEngineering)))
            return "Software Engineering";
        else if(title.getText().equals(getResources().getString(R.string.IndustrialEngineering)))
            return "Industrial Engineering";
        else if(title.getText().equals(getResources().getString(R.string.ChemicalEngineering)))
            return "Chemical Engineering";
        else if(title.getText().equals(getResources().getString(R.string.PreEngineering)))
            return "Pre Engineering";
        return "other";
    }
    private Course[] CoursesEngineering(){
        if(title.getText().equals(getResources().getString(R.string.StructuralEngineering)))
            return allCourses.getStructural_Engineering();
        else if(title.getText().equals(getResources().getString(R.string.MechanicalEngineering)))
            return allCourses.getMechanical_Engineering();
        else if(title.getText().equals(getResources().getString(R.string.ElectricalEngineering)))
            return allCourses.getElectrical_Engineering();
        else if(title.getText().equals(getResources().getString(R.string.SoftwareEngineering)))
            return allCourses.getSoftware_Engineering();
        else if(title.getText().equals(getResources().getString(R.string.IndustrialEngineering)))
            return allCourses.getIndustrial_Engineering();
        else if(title.getText().equals(getResources().getString(R.string.ChemicalEngineering)))
            return allCourses.getChemical_Engineering();
        else if(title.getText().equals(getResources().getString(R.string.PreEngineering)))
            return allCourses.getPre_Engineering();
        return allCourses.getStructural_Engineering();
    }
    private void ChoseEngineering(){
        if(title.getText().equals(getResources().getString(R.string.StructuralEngineering)))
            SetTags(allCourses.getChemical_Engineering());
        else if(title.getText().equals(getResources().getString(R.string.MechanicalEngineering)))
            SetTags(allCourses.getMechanical_Engineering());
        else if(title.getText().equals(getResources().getString(R.string.ElectricalEngineering)))
            SetTags(allCourses.getElectrical_Engineering());
        else if(title.getText().equals(getResources().getString(R.string.SoftwareEngineering)))
            SetTags(allCourses.getSoftware_Engineering());
        else if(title.getText().equals(getResources().getString(R.string.IndustrialEngineering)))
            SetTags(allCourses.getIndustrial_Engineering());
        else if(title.getText().equals(getResources().getString(R.string.ChemicalEngineering)))
            SetTags(allCourses.getChemical_Engineering());
        else if(title.getText().equals(getResources().getString(R.string.PreEngineering)))
            SetTags(allCourses.getPre_Engineering());
    }
    private void SetTags(Course[] Courses){
        for(int i=0; i<Courses.length; i++)
            courses.add(Courses[i]);
        ShowTags(courses);
    }
    private void ShowTags(List<Course> selects){
        CourseView mySelects = new CourseView(this,selects);
        mySelects.setUser(user);
        viewList.setLayoutManager(new GridLayoutManager(this,1));
        viewList.setAdapter(mySelects);
    }
    private void StartActivity(Class Destination){
        intent = new Intent(GenericEngineering.this, Destination);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    private void setDialog(String[] array, String title,TextView textViewPick){
        dialog = new Dialog(GenericEngineering.this);
        dialog.setContentView(R.layout.dialog_search_spinner);
        dialog.getWindow().setLayout(1200,1500);
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
    private void YearFiltter(){
        TextViewSearchYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog(getResources().getStringArray(R.array.Year),getResources().getString(R.string.SelectYear),TextViewSearchYear);
                ShowTags(courses);
                ClearYear.setVisibility(View.VISIBLE);
            }
        });
        ClearYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearYear.setVisibility(View.INVISIBLE);
                if(!TextViewSearchYear.getText().toString().equals(""))
                    TextViewSearchYear.setText("");
            }
        });
    }
    private void SemesterFiltter(){
        TextViewSearchSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog(getResources().getStringArray(R.array.Semester),getResources().getString(R.string.SelectSemester),TextViewSearchSemester);
                ClearSemester.setVisibility(View.VISIBLE);
            }
        });
        ClearSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearSemester.setVisibility(View.INVISIBLE);
                if(!TextViewSearchSemester.getText().toString().equals(""))
                    TextViewSearchSemester.setText("");
            }
        });
    }
    private void CourseFiltter(){
        TextViewSearchCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courses_list[] = new String[CoursesEngineering().length];
                for(int i=0;i<CoursesEngineering().length;i++)
                    courses_list[i] = CoursesEngineering()[i].getCourse_Name();
                setDialog(courses_list,getResources().getString(R.string.SelectCourse),TextViewSearchCourse);
                ClearCourse.setVisibility(View.VISIBLE);
            }
        });
        ClearCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearCourse.setVisibility(View.INVISIBLE);
                if(!TextViewSearchCourse.getText().toString().equals(""))
                    TextViewSearchCourse.setText("");
            }
        });
    }
}