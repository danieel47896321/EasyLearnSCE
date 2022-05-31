package com.example.easylearnsce.Client.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Client.Class.Course;
import com.example.easylearnsce.Client.Class.Lecture;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.R;
import com.example.easylearnsce.Client.User.GenericYouTubePlayer;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class LecturesFragmentAdapter extends RecyclerView.Adapter<LecturesFragmentAdapter.MyViewHolder>{
    private Context context;
    private User user;
    private Course course;
    private Intent intent;
    public LecturesFragmentAdapter(Context context, User user, Course course) {
        this.context = context;
        this.user = user;
        this.course = course;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView LectureName;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LectureName = itemView.findViewById(R.id.LectureName);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
    public LecturesFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.lecture_and_exercise,parent,false);
        return new LecturesFragmentAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull LecturesFragmentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.LectureName.setText(context.getResources().getString(R.string.Lecture) + " " +course.getLectures().get(position).getNumber());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setLecture(course.getLectures().get(position).getLectureName());
                intent = new Intent(context, GenericYouTubePlayer.class);
                intent.putExtra("user", user);
                intent.putExtra("Lecture", course.getLectures().get(position));
                intent.putExtra("Type", "Lecture");
                intent.putExtra("Course", course);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }
    public int getItemCount() { return course.getLectures().size(); }
}
