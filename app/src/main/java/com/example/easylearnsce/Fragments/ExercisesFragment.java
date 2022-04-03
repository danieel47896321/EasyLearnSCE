package com.example.easylearnsce.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easylearnsce.Adapters.LecturesFragmentAdapter;
import com.example.easylearnsce.Class.Lecture;
import com.example.easylearnsce.Class.Loading;
import com.example.easylearnsce.Class.PopUpMSG;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExercisesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageView addLecture, removeLecture;
    private TextInputLayout TextInputLayoutExercise;
    private Button ButtonRemoveCourse, ButtonCancel;
    private Dialog dialog;
    private ListView ListViewSearch;
    private EditText EditTextSearch;
    private TextView TextViewSearch;
    private Loading loading;
    private int ExercisesNumber = 0;
    private User user;
    public void setUser(User user){
        this.user = user;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addLecture = view.findViewById(R.id.addLecture);
        removeLecture = view.findViewById(R.id.removeLecture);
        if(user.getType().equals("Admin") || user.getType().equals("אדמין") || user.getType().equals("Teacher") || user.getType().equals("מרצה") ){
            addLecture.setVisibility(View.VISIBLE);
            removeLecture.setVisibility(View.VISIBLE);
            AddLecture();
            RemoveLecture();
        }
        setLectures();
        return view;
    }
    private void AddLecture(){
        addLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Exercises").child(user.getEngineering()).child(user.getCourse()).child((ExercisesNumber + 1)+"");
                reference.setValue( new Lecture("תרגול " + (ExercisesNumber +1),"ss"));
            }
        });
    }
    private void RemoveLecture(){
        removeLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveExerciseDialog();
            }
        });
    }
    private void RemoveExerciseDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_remove_exercise,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutExercise = dialogView.findViewById(R.id.TextInputLayoutExercise);
        ButtonRemoveCourse = dialogView.findViewById(R.id.ButtonRemoveCourse);
        ButtonCancel = dialogView.findViewById(R.id.ButtonCancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        LecturePick();
        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alertDialog.cancel(); }
        });
        ButtonRemoveCourse.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(TextInputLayoutExercise.getEditText().getText().toString().equals(""))
                    TextInputLayoutExercise.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutExercise.setHelperText("");
                if(!TextInputLayoutExercise.getEditText().getText().toString().equals("")) {
                    alertDialog.cancel();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference().child("Exercises").child(user.getEngineering()).child(user.getCourse());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            loading = new Loading(getContext());
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Lecture lecture = dataSnapshot.getValue(Lecture.class);
                                if(TextInputLayoutExercise.getEditText().getText().toString().equals(lecture.getLectureName())){
                                    DatabaseReference reference1 = database.getReference().child("Exercises").child(user.getEngineering()).child(user.getCourse()).child(dataSnapshot.getKey());
                                    reference1.setValue(null);
                                    new PopUpMSG(getContext(), getResources().getString(R.string.RemoveExercise), getResources().getString(R.string.ExerciseSuccessfullyRemoved));
                                }
                            }
                            loading.stop();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                }
            }
        });
    }
    private void LecturePick(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Exercises").child(user.getEngineering()).child(user.getCourse());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Lecture> lectureArrayList = new ArrayList<>();
                lectureArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Lecture lecture = dataSnapshot.getValue(Lecture.class);
                    lectureArrayList.add(lecture);
                }
                TextInputLayoutExercise.getEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String lectures[] = new String[lectureArrayList.size()];
                        for(int i=0; i<lectureArrayList.size();i++)
                            lectures[i] = lectureArrayList.get(i).getLectureName();
                        setDialog(lectures,getResources().getString(R.string.SelectExercise), TextInputLayoutExercise.getEditText());
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void setDialog(String[] array, String title, TextView textViewPick){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_search_spinner);
        dialog.getWindow().setLayout(1000,950);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditTextSearch = dialog.findViewById(R.id.EditTextSearch);
        ListViewSearch = dialog.findViewById(R.id.ListViewSearch);
        TextViewSearch = dialog.findViewById(R.id.TextViewSearch);
        TextViewSearch.setText(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, array);
        ListViewSearch.setAdapter(adapter);
        EditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        ListViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                textViewPick.setText(adapterView.getItemAtPosition(i).toString());
            }
        });
    }
    private void setLectures(){
        ArrayList<Lecture> lectures = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Exercises").child(user.getEngineering()).child(user.getCourse());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lectures.clear();
                ExercisesNumber = 0;
                for(DataSnapshot data : snapshot.getChildren()) {
                    Lecture  lecture = data.getValue(Lecture.class);
                    lectures.add(lecture);
                    ExercisesNumber++;
                }
                LecturesFragmentAdapter lecturesFragmentAdapter= new LecturesFragmentAdapter(getContext(), lectures);
                recyclerView.setAdapter(lecturesFragmentAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}