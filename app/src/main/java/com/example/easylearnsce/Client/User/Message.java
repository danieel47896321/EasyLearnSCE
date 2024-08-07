package com.example.easylearnsce.Client.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.easylearnsce.Client.Class.Chat;
import com.example.easylearnsce.Client.Adapters.MessageAdapter;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import de.hdodenhof.circleimageview.CircleImageView;

public class Message extends AppCompatActivity {
    private TextView username, TextSend;
    private DatabaseReference reference;
    private CircleImageView profileImage;
    private ImageView BackIcon, ButtonSend;
    private Intent intent;
    private MessageAdapter messageAdapter;
    private List<Chat> chats;
    private RecyclerView recyclerView;
    private User user = new User();
    private SecretKeySpec secretKeySpec;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init();
        BackIcon();
        TextSend();
    }
    private void init(){
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        TextSend = findViewById(R.id.TextSend);
        ButtonSend = findViewById(R.id.ButtonSend);
        profileImage = findViewById(R.id.ProfileImage);
        BackIcon = findViewById(R.id.BackIcon);
        username = findViewById(R.id.userName);
        if(!user.getImage().equals("Image")) {
            Glide.with(Message.this).asBitmap().load(user.getImage()).into(new CustomTarget<Bitmap>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) { profileImage.setImageBitmap(resource); }
                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) { }
            });
        }
        username.setText(user.getFirstName()+" "+ user.getLastName());
        readMessage(firebaseAuth.getCurrentUser().getUid(), user.getUid(),user.getImage());
    }
    private void TextSend(){
        ButtonSend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(!TextSend.getText().toString().equals(""))
                    sendMessage(firebaseAuth.getCurrentUser().getUid(), user.getUid(), TextSend.getText().toString());
                TextSend.setText("");
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        try{
            String currentDateandTime = new SimpleDateFormat("HH:mm dd-MM-yyyy").format(new Date());
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sender", sender);
            hashMap.put("receiver", receiver);
            hashMap.put("message", EncryptMessage(message));
            databaseReference.child("Chats").push().setValue(hashMap);
        }
        catch (Exception ignored){}
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String EncryptMessage(String msg){
        byte[] encryptedMsgByte = new byte[0];
        try { encryptedMsgByte = msg.getBytes("UTF-8"); }
        catch (UnsupportedEncodingException e) {e.printStackTrace();}
        String EncodedMsg = Base64.getEncoder().encodeToString(encryptedMsgByte);
        return EncodedMsg;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String DecryptMessage(String EncryptMessage){
        byte[] decodedByte = Base64.getDecoder().decode(EncryptMessage);
        String decryptedMsg = "";
        try { decryptedMsg = new String(decodedByte, "UTF-8"); }
        catch (UnsupportedEncodingException e) {e.printStackTrace();}
        return decryptedMsg;
    }
    private void readMessage(String sender, String receiver, String imageUrl){
        chats = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(sender) && chat.getSender().equals(receiver) || chat.getReceiver().equals(receiver) && chat.getSender().equals(sender)) {
                        chat.setMessage(DecryptMessage(chat.getMessage()));
                        chats.add(chat);
                    }
                    messageAdapter = new MessageAdapter(Message.this, chats, imageUrl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }
}