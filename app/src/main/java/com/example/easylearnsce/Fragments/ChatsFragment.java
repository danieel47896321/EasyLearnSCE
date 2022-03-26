package com.example.easylearnsce.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.easylearnsce.Class.Chat;
import com.example.easylearnsce.Adapters.ChatAdapter;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ChatAdapter userAdapter;
    private List<User> mUsers;
    private DatabaseReference reference;
    private List<String> usersList;
    private FirebaseUser firebaseUser;
    private EditText User_search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getSender().equals(firebaseUser.getUid()))
                        usersList.add(chat.getReceiver());
                    if(chat.getReceiver().equals(firebaseUser.getUid()))
                        usersList.add(chat.getSender());
                }
                readChats();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        User_search = view.findViewById(R.id.User_search);
        User_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UserSearch(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        return view;
    }
    private void UserSearch(String text) {
        Query query = FirebaseDatabase.getInstance().getReference().child("Users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user.getFullName().toLowerCase().contains(text.toLowerCase())) {
                        assert user != null;
                        for (String id : usersList) {
                            if (user.getUid().equals(id)) {
                                if (mUsers.contains(user)) { }
                                else
                                    mUsers.add(user);
                            }
                        }
                    }
                }
                userAdapter = new ChatAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void readChats(){
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(User_search.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        assert user != null;
                        for (String id : usersList) {
                            if (user.getUid().equals(id)) {
                                if (mUsers.contains(user)) { }
                                else
                                    mUsers.add(user);
                            }
                        }
                    }
                    userAdapter = new ChatAdapter(getContext(), mUsers);
                    recyclerView.setAdapter(userAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}