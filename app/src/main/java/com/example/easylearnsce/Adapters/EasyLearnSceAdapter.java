package com.example.easylearnsce.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Class.SelectView;
import com.example.easylearnsce.Class.Tag;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Guest.About;
import com.example.easylearnsce.Guest.Contact;
import com.example.easylearnsce.Guest.CreateAccount;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.Guest.ResetPassword;
import com.example.easylearnsce.Guest.SignIn;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.ChangePassword;
import com.example.easylearnsce.User.EasyLearnChat;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.Profile;
import com.example.easylearnsce.User.SelectEngineering;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class EasyLearnSceAdapter extends RecyclerView.Adapter<EasyLearnSceAdapter.MyViewHolder> {
    private Context context;
    private List<Tag> Select;
    private User user;
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public EasyLearnSceAdapter(Context context, List<Tag> select) {
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
    public EasyLearnSceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.select_view,parent,false);
        return new EasyLearnSceAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull EasyLearnSceAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(Select.get(position).getTagName());
        holder.imageView.setImageResource(Select.get(position).getPhoto());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.PickColor));
                //EasyLearnSCE
                if(holder.textView.getText().equals(context.getResources().getString(R.string.ResetPassword)))
                    intent = new Intent(context, ResetPassword.class);
                else if(holder.textView.getText().equals(context.getResources().getString(R.string.SignIn)))
                    intent = new Intent(context, SignIn.class);
                else if(holder.textView.getText().equals(context.getResources().getString(R.string.CreateAccount)))
                    intent = new Intent(context, CreateAccount.class);
                else if(holder.textView.getText().equals(context.getResources().getString(R.string.Contact)))
                    intent = new Intent(context, Contact.class);
                else if(holder.textView.getText().equals(context.getResources().getString(R.string.About)))
                    intent = new Intent(context, About.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }
    public int getItemCount() { return Select.size(); }
}