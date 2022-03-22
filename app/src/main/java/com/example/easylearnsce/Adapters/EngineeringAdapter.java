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
        TextView textView;
        ImageView imageView;
        ConstraintLayout cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.engineering_name);
            imageView = itemView.findViewById(R.id.engineering_pic);
            cardView = itemView.findViewById(R.id.engineeringview_id);
        }
    }
    public void setUser(User user){ this.user = user; }
    public EngineeringAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.select_view,parent,false);
        return new EngineeringAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull EngineeringAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(Select.get(position).getTagName());
        holder.imageView.setImageResource(Select.get(position).getPhoto());
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.PickColor));
                return false;
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.PickColor));
                Intent intent = new Intent(context, GenericEngineering.class);
                intent.putExtra("Tag",holder.textView.getText());
                intent.putExtra("user",user);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }
    public int getItemCount() { return Select.size(); }
}
