package com.example.easylearnsce.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Class.Course;
import com.example.easylearnsce.Class.Loading;
import com.example.easylearnsce.Class.PopUpMSG;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.GenericCourse;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class GenericEngineeringAdapter extends RecyclerView.Adapter<GenericEngineeringAdapter.MyViewHolder> {
    private Context context;
    private List<Course> courses;
    private User User;
    private Loading loading;
    private Dialog dialog;
    private ListView ListViewSearch;
    private EditText EditTextSearch;
    private TextView TextViewSearch;
    private String Engineering;
    private TextInputLayout TextInputLayoutCourse, TextInputLayoutTeacherName ,TextInputLayoutDepartment, TextInputLayoutYear, TextInputLayoutSemester;
    private Button ButtonAddCourse, ButtonCancel;
    public GenericEngineeringAdapter(Context context, List<Course> course, String Engineering, User user) {
        this.context = context;
        this.courses = course;
        this.Engineering = Engineering;
        this.User = user;
    }
    public GenericEngineeringAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.course_item,parent,false);
        return new GenericEngineeringAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull GenericEngineeringAdapter.MyViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.CourseName.setText(context.getResources().getString(R.string.Course) + ": " + course.getCourseName());
        holder.TeacherName.setText(context.getResources().getString(R.string.Teacher) + ": " +  course.getTeacherName());
        holder.Year.setText(context.getResources().getString(R.string.Year) + ": " + course.getCourseYear());
        holder.Semester.setText(context.getResources().getString(R.string.Semester) + ": " + course.getCourseSemester());
        holder.Engineering.setText(context.getResources().getString(R.string.Department) + ": " + course.getCourseEngineering());
        if(User.getType().equals("Admin") || User.getType().equals("אדמין")) {
            holder.Edit.setVisibility(View.VISIBLE);
            holder.Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditCourseDialog(course);
                }
            });
        }
        holder.course_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.course_card_view.setBackgroundColor(context.getResources().getColor(R.color.PickColor));
                Intent intent;
                intent = new Intent(context, GenericCourse.class);
                intent.putExtra("Course", course.getCourseName());
                intent.putExtra("CourseID", course.getId());
                intent.putExtra("user", User);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }
    private void EditCourseDialog(Course course){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_course,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutCourse = dialogView.findViewById(R.id.TextInputLayoutCourse);
        TextInputLayoutCourse.getEditText().setText(course.getCourseName());
        TextInputLayoutTeacherName = dialogView.findViewById(R.id.TextInputLayoutTeacherName);
        TextInputLayoutTeacherName.getEditText().setText(course.getTeacherName());
        TextInputLayoutDepartment = dialogView.findViewById(R.id.TextInputLayoutDepartment);
        TextInputLayoutDepartment.getEditText().setText(course.getCourseEngineering());
        TextInputLayoutYear = dialogView.findViewById(R.id.TextInputLayoutYear);
        TextInputLayoutYear.getEditText().setText(course.getCourseYear());
        TextInputLayoutSemester = dialogView.findViewById(R.id.TextInputLayoutSemester);
        TextInputLayoutSemester.getEditText().setText(course.getCourseSemester());
        ButtonAddCourse = dialogView.findViewById(R.id.ButtonAddCourse);
        ButtonCancel = dialogView.findViewById(R.id.ButtonCancel);
        TextInputLayoutDepartment.getEditText().setText(Engineering);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        EndIcon();
        YearPick();
        SemesterPick();
        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alertDialog.cancel(); }
        });
        ButtonAddCourse.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(TextInputLayoutCourse.getEditText().getText().toString().equals(""))
                    TextInputLayoutCourse.setHelperText(context.getResources().getString(R.string.Required));
                else
                    TextInputLayoutCourse.setHelperText("");
                if(TextInputLayoutTeacherName.getEditText().getText().toString().equals(""))
                    TextInputLayoutTeacherName.setHelperText(context.getResources().getString(R.string.Required));
                else
                    TextInputLayoutTeacherName.setHelperText("");
                if(TextInputLayoutDepartment.getEditText().getText().toString().equals(""))
                    TextInputLayoutDepartment.setHelperText(context.getResources().getString(R.string.Required));
                else
                    TextInputLayoutDepartment.setHelperText("");
                if(TextInputLayoutYear.getEditText().getText().toString().equals(""))
                    TextInputLayoutYear.setHelperText(context.getResources().getString(R.string.Required));
                else
                    TextInputLayoutYear.setHelperText("");
                if(TextInputLayoutSemester.getEditText().getText().toString().equals(""))
                    TextInputLayoutSemester.setHelperText(context.getResources().getString(R.string.Required));
                else
                    TextInputLayoutSemester.setHelperText("");
                if(!(TextInputLayoutCourse.getEditText().getText().toString().equals("")) && !(TextInputLayoutTeacherName.getEditText().getText().toString().equals("")) && !(TextInputLayoutDepartment.getEditText().getText().toString().equals(""))
                        && !(TextInputLayoutYear.getEditText().getText().toString().equals(""))  && !(TextInputLayoutSemester.getEditText().getText().toString().equals(""))) {
                    alertDialog.cancel();
                    loading = new Loading(context);
                    Course course1 = new Course(TextInputLayoutCourse.getEditText().getText().toString(), TextInputLayoutTeacherName.getEditText().getText().toString(), TextInputLayoutYear.getEditText().getText().toString(), TextInputLayoutSemester.getEditText().getText().toString(), Engineering);
                    course1.setId(course.getId());
                    EditCourse(course1);
                }
            }
        });
    }
    private void EditCourse(Course course){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Courses").child(getEngineeringName()).child(course.getId());
        reference.setValue(course);
        loading.stop();
        new PopUpMSG(context, context.getResources().getString(R.string.EditCourse), context.getResources().getString(R.string.EditSaved));
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
    private void YearPick(){
        TextInputLayoutYear.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { setDialog(context.getResources().getStringArray(R.array.Year),context.getResources().getString(R.string.SelectYear),TextInputLayoutYear.getEditText()); }
        });
    }
    private void SemesterPick(){

        TextInputLayoutSemester.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { setDialog(context.getResources().getStringArray(R.array.Semester),context.getResources().getString(R.string.SelectSemester),TextInputLayoutSemester.getEditText()); }
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
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_search_spinner);
        dialog.getWindow().setLayout(1000,950);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditTextSearch = dialog.findViewById(R.id.EditTextSearch);
        ListViewSearch = dialog.findViewById(R.id.ListViewSearch);
        TextViewSearch = dialog.findViewById(R.id.TextViewSearch);
        TextViewSearch.setText(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.dropdown_item, array);
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
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView CourseName;
        TextView TeacherName;
        TextView Year;
        TextView Semester;
        TextView Engineering;
        TextView Edit;
        ConstraintLayout course_card_view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            CourseName = itemView.findViewById(R.id.CourseName);
            TeacherName = itemView.findViewById(R.id.TeacherName);
            Year = itemView.findViewById(R.id.Year);
            Semester = itemView.findViewById(R.id.Semester);
            Engineering = itemView.findViewById(R.id.Engineering);
            Edit = itemView.findViewById(R.id.Edit);
            course_card_view = itemView.findViewById(R.id.course_view);
        }
    }
    public int getItemCount() { return courses.size(); }
}