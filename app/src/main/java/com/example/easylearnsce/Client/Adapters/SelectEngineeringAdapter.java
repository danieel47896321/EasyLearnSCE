package com.example.easylearnsce.Client.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Client.Class.Tag;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.R;
import com.example.easylearnsce.Client.User.GenericEngineering;

import java.util.ArrayList;

public class SelectEngineeringAdapter extends RecyclerView.Adapter<SelectEngineeringAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Tag> tags;
    private User user;
    public SelectEngineeringAdapter(Context context, ArrayList<Tag> tags, User user) {
        this.context = context;
        this.tags = tags;
        this.user = user;
    }
    public SelectEngineeringAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tag_view,parent,false);
        return new SelectEngineeringAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull SelectEngineeringAdapter.MyViewHolder holder, int position) {
        holder.TagName.setText(tags.get(position).getTagName());
        holder.TagImage.setImageResource(tags.get(position).getPhoto());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GenericEngineering.class);
                intent.putExtra("title",holder.TagName.getText());
                intent.putExtra("user", user);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView TagName;
        ImageView TagImage;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TagName = itemView.findViewById(R.id.TagName);
            TagImage = itemView.findViewById(R.id.TagImage);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
    public int getItemCount() { return tags.size(); }
}
