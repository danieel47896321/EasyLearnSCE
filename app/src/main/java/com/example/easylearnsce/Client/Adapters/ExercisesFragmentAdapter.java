package com.example.easylearnsce.Client.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Client.Class.Course;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.User.GenericYouTubePlayer;
import com.example.easylearnsce.R;

public class ExercisesFragmentAdapter extends RecyclerView.Adapter<ExercisesFragmentAdapter.MyViewHolder>{
    private Context context;
    private User user;
    private Course course;
    private Intent intent;
    public ExercisesFragmentAdapter(Context context, User user, Course course) {
        this.context = context;
        this.user = user;
        this.course = course;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ExerciseName;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ExerciseName = itemView.findViewById(R.id.LectureName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
    public ExercisesFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.lecture_and_exercise,parent,false);
        return new ExercisesFragmentAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull ExercisesFragmentAdapter.MyViewHolder holder, int position) {
        holder.ExerciseName.setText(context.getResources().getString(R.string.Exercise) + " " +course.getExercises().get(position).getNumber());
        holder.ExerciseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ExerciseName.setBackgroundColor(context.getResources().getColor(R.color.grey));
                user.setLecture(course.getExercises().get(position).getLectureName());
                intent = new Intent(context, GenericYouTubePlayer.class);
                intent.putExtra("user", user);
                intent.putExtra("Lecture", course.getExercises().get(position));
                intent.putExtra("Type", "Exercise");
                intent.putExtra("Course", course);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }
    public int getItemCount() { return course.getExercises().size(); }
}