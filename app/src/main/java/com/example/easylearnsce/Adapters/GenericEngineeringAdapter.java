package com.example.easylearnsce.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Class.Course;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.example.easylearnsce.EngineeringFunc.GenericCourse;

import java.util.List;

public class GenericEngineeringAdapter extends RecyclerView.Adapter<GenericEngineeringAdapter.MyViewHolder> {
    private Context context;
    private List<Course> courses;
    private User user;
    private String Engineering = "Tag";
    public GenericEngineeringAdapter(Context context, List<Course> course, String Engineering) {
        this.context = context;
        this.courses = course;
        this.Engineering = Engineering;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView course_name;
        TextView course_teacher_name;
        TextView course_year;
        TextView course_semester;
        TextView course_engineering;
        ConstraintLayout course_card_view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            course_name = itemView.findViewById(R.id.course_name);
            course_teacher_name = itemView.findViewById(R.id.course_teacher_name);
            course_year = itemView.findViewById(R.id.course_year);
            course_semester = itemView.findViewById(R.id.course_semester);
            course_engineering = itemView.findViewById(R.id.course_engineering);
            course_card_view = itemView.findViewById(R.id.course_view);
        }
    }
    public GenericEngineeringAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.course_item,parent,false);
        return new GenericEngineeringAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull GenericEngineeringAdapter.MyViewHolder holder, int position) {
        holder.course_name.setText(courses.get(position).getCourse_Name());
        holder.course_teacher_name.setText(courses.get(position).getCourse_Teacher_name());
        holder.course_year.setText(courses.get(position).getCourse_Year());
        holder.course_semester.setText(courses.get(position).getCourse_Semester());
        holder.course_engineering.setText(courses.get(position).getCourse_Engineering());
        holder.course_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.course_card_view.setBackgroundColor(context.getResources().getColor(R.color.PickColor));
                Intent intent;
                intent = new Intent(context, GenericCourse.class);
                intent.putExtra("Course",holder.course_name.getText().toString());
                intent.putExtra("Tag",Engineering);
                intent.putExtra("user",user);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }
    public void setUser(User user){ this.user = user; }
    public int getItemCount() { return courses.size(); }
}