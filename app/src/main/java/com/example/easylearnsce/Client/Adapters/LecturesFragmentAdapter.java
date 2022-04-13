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

import com.example.easylearnsce.Client.Class.Lecture;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.R;
import com.example.easylearnsce.Client.User.YouTubePlayer;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class LecturesFragmentAdapter extends RecyclerView.Adapter<LecturesFragmentAdapter.MyViewHolder>{
    private Context context;
    private List<Lecture> Select;
    private User user;
    private String CourseID;
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public LecturesFragmentAdapter(Context context, List<Lecture> select, User user, String CourseID) {
        this.context = context;
        this.Select = select;
        this.user = user;
        this.CourseID = CourseID;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView LectureName;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LectureName = itemView.findViewById(R.id.LectureName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
    public LecturesFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row,parent,false);
        return new LecturesFragmentAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull LecturesFragmentAdapter.MyViewHolder holder, int position) {
        holder.LectureName.setText(Select.get(position).getLectureName());
        holder.LectureName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setLecture(Select.get(position).getLectureName());
                intent = new Intent(context, YouTubePlayer.class);
                intent.putExtra("user", user);
                intent.putExtra("Video", Select.get(position).getUrl());
                intent.putExtra("CourseID", CourseID);
                intent.putExtra("Lecture", Select.get(position));
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }
    public int getItemCount() { return Select.size(); }
}
