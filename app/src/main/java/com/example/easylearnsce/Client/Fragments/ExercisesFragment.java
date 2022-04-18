package com.example.easylearnsce.Client.Fragments;

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

import com.example.easylearnsce.Client.Adapters.LecturesFragmentAdapter;
import com.example.easylearnsce.Client.Class.Lecture;
import com.example.easylearnsce.Client.Class.Loading;
import com.example.easylearnsce.Client.Class.PopUpMSG;
import com.example.easylearnsce.Client.Class.User;
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
    private TextInputLayout TextInputLayoutExercise,TextInputLayoutLinkToVideo;
    private Button ButtonRemoveCourse, ButtonCancel, ButtonAddVideo;
    private Dialog dialog;
    private ListView ListViewSearch;
    private EditText EditTextSearch, User_search;
    private TextView TextViewSearch;
    private Loading loading;
    private String CourseID;
    private int ExercisesNumber = 0;
    private User user;
    private View view;
    private ArrayList<Lecture> lectures;
    public ExercisesFragment() {
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setCourseID(String CourseID){
        this.CourseID = CourseID;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercises, container, false);
        lectures = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addLecture = view.findViewById(R.id.addLecture);
        User_search = view.findViewById(R.id.User_search);
        removeLecture = view.findViewById(R.id.removeLecture);
        if(user.getType().equals("Admin") || user.getType().equals("אדמין") || user.getType().equals("Teacher") || user.getType().equals("מרצה") ){
            addLecture.setVisibility(View.VISIBLE);
            removeLecture.setVisibility(View.VISIBLE);
            AddLecture();
            RemoveLecture();
        }
        setLectures();
        LectureSearch();
        return view;
    }
    private void AddLecture(){
        addLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { AddVideoDialog(); }
        });
    }
    private void AddVideoDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_video,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutLinkToVideo = dialogView.findViewById(R.id.TextInputLayoutLinkToVideo);
        ButtonAddVideo = dialogView.findViewById(R.id.ButtonAddVideo);
        ButtonCancel = dialogView.findViewById(R.id.ButtonCancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alertDialog.cancel(); }
        });
        ButtonAddVideo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(TextInputLayoutLinkToVideo.getEditText().getText().toString().equals(""))
                    TextInputLayoutLinkToVideo.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutLinkToVideo.setHelperText("");
                if(!(TextInputLayoutLinkToVideo.getEditText().getText().toString().equals(""))){
                    alertDialog.cancel();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Exercises").child(getEngineeringName()).child(CourseID).child((ExercisesNumber + 1)+"");
                    reference.setValue( new Lecture("Exercise",TextInputLayoutLinkToVideo.getEditText().getText().toString(),(ExercisesNumber + 1)+""));
                }
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
        ButtonRemoveCourse.setText(getResources().getString(R.string.Remove));
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
                    DatabaseReference reference = database.getReference().child("Exercises").child(getEngineeringName()).child(CourseID);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            loading = new Loading(getContext());
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Lecture lecture = dataSnapshot.getValue(Lecture.class);
                                if(TextInputLayoutExercise.getEditText().getText().toString().equals(getResources().getString(R.string.Exercise) + " " +lecture.getNumber())){
                                    DatabaseReference reference1 = database.getReference().child("Exercises").child(getEngineeringName()).child(CourseID).child(dataSnapshot.getKey());
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
        DatabaseReference reference = database.getReference().child("Exercises").child(getEngineeringName()).child(CourseID);
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
                            lectures[i] = getResources().getString(R.string.Exercise) + " " +lectureArrayList.get(i).getNumber();
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
    private String getEngineeringName(){
        if(user.getEngineering().equals("Structural Tag") || user.getEngineering().equals("הנדסת בניין"))
            return "Structural Engineering";
        else if(user.getEngineering().equals("Mechanical Engineering") || user.getEngineering().equals("הנדסת מכונות"))
            return "Mechanical Engineering";
        else if(user.getEngineering().equals("Electrical Engineering") || user.getEngineering().equals("הנדסת חשמל ואלקטרוניקה"))
            return "Electrical Engineering";
        else if(user.getEngineering().equals("Software Engineering") || user.getEngineering().equals("הנדסת תוכנה"))
            return "Software Engineering";
        else if(user.getEngineering().equals("Industrial Engineering") || user.getEngineering().equals("הנדסת תעשייה וניהול"))
            return "Industrial Engineering";
        else if(user.getEngineering().equals("Chemical Engineering") || user.getEngineering().equals("הנדסת כימיה"))
            return "Chemical Engineering";
        else if(user.getEngineering().equals("Programming Computer ") || user.getEngineering().equals("מדעי המחשב"))
            return "Programming Computer";
        else if(user.getEngineering().equals("Pre Engineering") || user.getEngineering().equals("מכינה"))
            return "Pre Engineering";
        return "Other";
    }
    private void LectureSearch(){
        User_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { UserSearch(s.toString()); }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    private void UserSearch(String text) {
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference reference2 = database2.getReference("Exercises").child(getEngineeringName()).child(CourseID);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lectures.clear();
                ExercisesNumber = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    Lecture lecture = data.getValue(Lecture.class);
                    if(lecture.getLectureName().toLowerCase().contains(text.toLowerCase()))
                        lectures.add(lecture);
                    ExercisesNumber++;
                }
                LecturesFragmentAdapter lecturesFragmentAdapter = new LecturesFragmentAdapter(getContext(), lectures, user, CourseID);
                recyclerView.setAdapter(lecturesFragmentAdapter);        }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void setLectures(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Exercises").child(getEngineeringName()).child(CourseID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(User_search.getText().toString().equals("")) {
                    lectures.clear();
                    int index = 1;
                    ExercisesNumber = 0;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Lecture lecture = data.getValue(Lecture.class);
                        lectures.add(lecture);
                        if(("Exercise " + lecture.getNumber()).equals("Exercise " + index)) {
                            ExercisesNumber++;
                            index++;
                        }
                    }
                    LecturesFragmentAdapter lecturesFragmentAdapter = new LecturesFragmentAdapter(getContext(), lectures, user, CourseID);
                    recyclerView.setAdapter(lecturesFragmentAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}