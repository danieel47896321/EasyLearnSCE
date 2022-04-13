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
import com.example.easylearnsce.Client.Guest.About;
import com.example.easylearnsce.Client.Guest.Contact;
import com.example.easylearnsce.Client.Guest.CreateAccount;
import com.example.easylearnsce.Client.Guest.ResetPassword;
import com.example.easylearnsce.Client.Guest.SignIn;
import com.example.easylearnsce.R;

import java.util.ArrayList;

public class EasyLearnAdapter extends RecyclerView.Adapter<EasyLearnAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Tag> tags;
    private Intent intent;
    public EasyLearnAdapter(Context context, ArrayList<Tag> tags) {
        this.context = context;
        this.tags = tags;
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
    public EasyLearnAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tag_view,parent,false);
        return new EasyLearnAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull EasyLearnAdapter.MyViewHolder holder, int position) {
        holder.TagName.setText(tags.get(position).getTagName());
        holder.TagImage.setImageResource(tags.get(position).getPhoto());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.PickColor));
                if(holder.TagName.getText().equals(context.getResources().getString(R.string.ResetPassword)))
                    intent = new Intent(context, ResetPassword.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.SignIn)))
                    intent = new Intent(context, SignIn.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.CreateAccount)))
                    intent = new Intent(context, CreateAccount.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.Contact)))
                    intent = new Intent(context, Contact.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.About)))
                    intent = new Intent(context, About.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }
    public int getItemCount() { return tags.size(); }
}