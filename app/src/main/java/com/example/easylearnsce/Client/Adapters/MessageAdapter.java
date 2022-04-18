package com.example.easylearnsce.Client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easylearnsce.Client.Class.Chat;
import com.example.easylearnsce.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int Sender = 0;
    public static final int Receiver = 1;
    private Context context;
    private List<Chat> chats;
    private String imageUrl;
    public MessageAdapter(Context context, List<Chat> chats, String imageUrl){
        this.context = context;
        this.chats = chats;
        this.imageUrl = imageUrl;
    }
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == Sender) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_sender, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_receiver, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        holder.show_message.setText(chat.getMessage());
        if(chats.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            holder.sender_image.setVisibility(View.GONE);
        else {
            if(!imageUrl.equals("Image"))
                Glide.with(context).load(imageUrl).into(holder.sender_image);
            else
                holder.sender_image.setImageResource(R.drawable.openingscreen);
        }
    }
    @Override
    public int getItemViewType(int position) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(chats.get(position).getSender().equals(firebaseAuth.getCurrentUser().getUid()))
            return Sender;
        else
            return Receiver;
    }
    @Override
    public int getItemCount() { return chats.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView sender_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            sender_image = itemView.findViewById(R.id.sender_image);
        }
    }
}