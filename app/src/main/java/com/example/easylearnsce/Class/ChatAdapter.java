package com.example.easylearnsce.Class;

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
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.Message;
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
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List<User> users;
    private String LastMsg;
    private Cipher cipher,decipher;
    private SecretKeySpec secretKeySpec;
    public ChatAdapter(Context context, List<User> users ){
        this.context = context;
        this.users = users;
    }
    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_select_view, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.FragmentUserName.setText(user.getFirstName()+" "+user.getLastName());
        //holder.FragmentUserInfo.setText(user.getGender()+", "+user.getAge()+", "+user.getCity());
        if(!user.getImage().equals("Image"))
            Glide.with(context).load(user.getImage()).into(holder.FragmentUserImage);
        lastMsg(user.getUid(),holder.FragmentUserInfo);
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
        public TextView FragmentUserName;
        public TextView FragmentUserInfo;
        public ImageView FragmentUserImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            FragmentUserName = itemView.findViewById(R.id.FragmentUserName);
            FragmentUserInfo = itemView.findViewById(R.id.FragmentUserInfo);
            FragmentUserImage = itemView.findViewById(R.id.FragmentUserImage);
        }
    }
    private void lastMsg(String userID, TextView lastMsg){
        LastMsg = "";
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
                String PublicKey = snapshot.child("PublicKey").getValue().toString();
                String PrivateKey = ", -68, 88, 17, 20, 3, -105, 119, -53]";
                String Key = PublicKey.subSequence(0,PublicKey.length()-1) + PrivateKey;
                String[] byteValues = Key.substring(1, Key.length() - 1).split(",");
                byte[] secretKey = new byte[byteValues.length];
                for (int i=0; i<secretKey.length; i++)
                    secretKey[i] = Byte.parseByte(byteValues[i].trim());
                secretKeySpec = new SecretKeySpec(secretKey,"AES");
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        secretKeySpec = new SecretKeySpec(secretKey,"AES");
                        for(DataSnapshot temp : snapshot.getChildren()){
                            Chat chat = temp.getValue(Chat.class);
                            if( (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userID)) ||  (chat.getReceiver().equals(userID) && chat.getSender().equals(firebaseUser.getUid()))){
                                try { LastMsg = AESDecryptionMethod(chat.getMessage());
                                } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
                            }
                        }
                        lastMsg.setText(LastMsg);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

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
}
