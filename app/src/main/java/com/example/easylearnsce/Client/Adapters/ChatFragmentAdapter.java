package com.example.easylearnsce.Client.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easylearnsce.Client.Class.Chat;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.R;
import com.example.easylearnsce.Client.User.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class ChatFragmentAdapter extends RecyclerView.Adapter<ChatFragmentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<User> users;
    private String LastMsg;
    public ChatFragmentAdapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = users;
    }
    @NonNull
    @Override
    public ChatFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_select_view, parent, false);
        return new ChatFragmentAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ChatFragmentAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.UserName.setText(user.getFirstName()+" "+user.getLastName());
        if(!user.getImage().equals("Image"))
            Glide.with(context).load(user.getImage()).into(holder.UserImage);
        lastMsg(user.getUid(),holder.UserInfo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Message.class);
                intent.putExtra("user",user);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() { return users.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView UserName;
        public TextView UserInfo;
        public ImageView UserImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.UserName);
            UserInfo = itemView.findViewById(R.id.UserInfo);
            UserImage = itemView.findViewById(R.id.UserImage);
        }
    }
    private void lastMsg(String userID, TextView lastMsg){
        LastMsg = "";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot temp : snapshot.getChildren()){
                    Chat chat = temp.getValue(Chat.class);
                    if( (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userID)) ||  (chat.getReceiver().equals(userID) && chat.getSender().equals(firebaseUser.getUid())))
                        LastMsg = chat.getMessage();
                }
                lastMsg.setText(LastMsg);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
