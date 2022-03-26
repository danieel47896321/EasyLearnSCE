package com.example.easylearnsce.User;

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
import com.example.easylearnsce.Class.Chat;
import com.example.easylearnsce.Adapters.MessageAdapter;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
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
    private String PublicKey, PrivateKey, Key;
    private byte secretKey[];
    private Cipher cipher,decipher;
    private SecretKeySpec secretKeySpec;
    private User user = new User();
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
        recyclerView = findViewById(R.id.MessageRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        TextSend = findViewById(R.id.TextSend);
        ButtonSend = findViewById(R.id.ButtonSend);
        profileImage = findViewById(R.id.ProfileImage);
        BackIcon = findViewById(R.id.BackIcon);
        username = findViewById(R.id.userName);
        try{
            cipher = Cipher.getInstance("AES");
            decipher = Cipher.getInstance("AES");
        }
        catch (NoSuchAlgorithmException e){}
        catch (NoSuchPaddingException e){}
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Key");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PublicKey = snapshot.child("PublicKey").getValue().toString();
                PrivateKey = ", -68, 88, 17, 20, 3, -105, 119, -53]";
                Key = PublicKey.subSequence(0,PublicKey.length()-1) + PrivateKey;
                String[] byteValues = Key.substring(1, Key.length() - 1).split(",");
                secretKey = new byte[byteValues.length];
                for (int i=0; i<secretKey.length; i++)
                    secretKey[i] = Byte.parseByte(byteValues[i].trim());
                secretKeySpec = new SecretKeySpec(secretKey,"AES");
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
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    private String AESEncryptionMethod(String msg){
        byte[] stringByte = msg.getBytes();
        byte[] encryptedByte = new byte[stringByte.length];
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encryptedByte = cipher.doFinal(stringByte);
        }catch (InvalidKeyException e){
        } catch (BadPaddingException e) { e.printStackTrace();
        } catch (IllegalBlockSizeException e) { e.printStackTrace(); }
        String returnString = null;
        try { returnString = new String(encryptedByte,"ISO-8859-1");
        } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
        return returnString;
    }
    private String AESDecryptionMethod(String msg) throws UnsupportedEncodingException {
        byte[] EncryptedByte = msg.getBytes("ISO-8859-1");
        String decryptedString = msg;
        byte[] decryption;
        try {
            decipher.init(cipher.DECRYPT_MODE,secretKeySpec);
            decryption = decipher.doFinal(EncryptedByte);
            decryptedString = new String(decryption);
        } catch (InvalidKeyException e) { e.printStackTrace();
        } catch (BadPaddingException e) { e.printStackTrace();
        } catch (IllegalBlockSizeException e) { e.printStackTrace(); }
        return decryptedString;
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
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sender", sender);
            hashMap.put("receiver", receiver);
            hashMap.put("message", AESEncryptionMethod(message));
            databaseReference.child("Chats").push().setValue(hashMap);
        }
        catch (Exception ignored){}
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
                    if(chat.getReceiver().equals(sender) && chat.getSender().equals(receiver) || chat.getReceiver().equals(receiver) && chat.getSender().equals(sender)){
                        try {
                            chat.setMessage(AESDecryptionMethod(chat.getMessage()));
                            chats.add(chat);

                        } catch (Exception e) { e.printStackTrace(); }
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