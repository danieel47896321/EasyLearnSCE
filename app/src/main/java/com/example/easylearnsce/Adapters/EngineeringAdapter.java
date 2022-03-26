package com.example.easylearnsce.Adapters;

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

import com.example.easylearnsce.Class.Tag;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.example.easylearnsce.EngineeringFunc.GenericEngineering;

import java.util.List;

public class EngineeringAdapter extends RecyclerView.Adapter<EngineeringAdapter.MyViewHolder> {
    private Context context;
    private List<Tag> Select;
    private User user;
    public EngineeringAdapter(Context context, List<Tag> select) {
        this.context = context;
        this.Select = select;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView TagName;
        ImageView TagPhoto;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TagName = itemView.findViewById(R.id.TagName);
            TagPhoto = itemView.findViewById(R.id.TagPhoto);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
    public void setUser(User user){ this.user = user; }
    public EngineeringAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tag_view,parent,false);
        return new EngineeringAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull EngineeringAdapter.MyViewHolder holder, int position) {
        holder.TagName.setText(Select.get(position).getTagName());
        holder.TagPhoto.setImageResource(Select.get(position).getPhoto());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.PickColor));
                Intent intent = new Intent(context, GenericEngineering.class);
                intent.putExtra("Tag",holder.TagName.getText());
                intent.putExtra("user",user);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }
    public int getItemCount() { return Select.size(); }
}
