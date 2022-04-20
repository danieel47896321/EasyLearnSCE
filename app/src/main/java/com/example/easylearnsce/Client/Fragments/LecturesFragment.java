package com.example.easylearnsce.Client.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easylearnsce.Client.Adapters.GenericEngineeringAdapter;
import com.example.easylearnsce.Client.Adapters.LecturesFragmentAdapter;
import com.example.easylearnsce.Client.Class.Course;
import com.example.easylearnsce.Client.Class.Lecture;
import com.example.easylearnsce.Client.Class.PopUpMSG;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.User.GenericEngineering;
import com.example.easylearnsce.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LecturesFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextInputLayout TextInputLayoutLecture;
    private Button ButtonRemoveCourse, ButtonCancel, ButtonAddVideo;
    private Dialog dialog;
    private TextInputLayout TextInputLayoutLinkToVideo;
    private ListView ListViewSearch;
    private EditText EditTextSearch, User_search;
    private TextView TextViewSearch ;
    private FloatingActionButton floatingActionButtonOpen;
    private ExtendedFloatingActionButton floatingActionButtonAdd, floatingActionButtonRemove;
    private Animation rotateOpen, rotateClose, toBottom, fromBottom;
    private Boolean isOpen = false;
    private Context context;
    private int LectureNumber = 0;
    private User user = new User();
    private Course course;
    private View view;
    private ArrayList<Lecture> lectures;
    public LecturesFragment() { }
    public void setUser(User user){
        this.user = user;
    }
    public void setCourse(Course course){
        this.course = course;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lectures, container, false);
        lectures = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        User_search = view.findViewById(R.id.User_search);
        floatingActionButtonOpen = view.findViewById(R.id.floatingActionButtonOpen);
        floatingActionButtonAdd = view.findViewById(R.id.floatingActionButtonAdd);
        floatingActionButtonRemove = view.findViewById(R.id.floatingActionButtonRemove);
        context = view.getContext();
        setAddAndRemove();
        setLectures();
        LectureSearch();
        return view;
    }
    private void setAddAndRemove(){
        if(user.getType().equals("Admin") || user.getType().equals("אדמין") || (user.getType().equals("Teacher") && user.getFullName().equals(course.getTeacherName())) || (user.getType().equals("מרצה") && user.getFullName().equals(course.getTeacherName()))){
            floatingActionButtonOpen.setVisibility(View.VISIBLE);
            floatingActionButtonOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isOpen = !isOpen;
                    rotateOpen = AnimationUtils.loadAnimation(context,R.anim.rotate_open);
                    rotateClose = AnimationUtils.loadAnimation(context,R.anim.rotate_close);
                    fromBottom = AnimationUtils.loadAnimation(context,R.anim.from_bottom);
                    toBottom = AnimationUtils.loadAnimation(context,R.anim.to_bottom);
                    if (isOpen) {
                        floatingActionButtonAdd.setVisibility(View.VISIBLE);
                        floatingActionButtonRemove.setVisibility(View.VISIBLE);
                        floatingActionButtonAdd.setAnimation(fromBottom);
                        floatingActionButtonRemove.setAnimation(fromBottom);
                        floatingActionButtonOpen.setAnimation(rotateOpen);
                        floatingActionButtonAdd.setClickable(true);
                        floatingActionButtonRemove.setClickable(true);

                        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddLectureDialog();
                            }
                        });
                        floatingActionButtonRemove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RemoveLectureDialog();
                            }
                        });
                    } else {
                        floatingActionButtonAdd.setVisibility(View.INVISIBLE);
                        floatingActionButtonRemove.setVisibility(View.INVISIBLE);
                        floatingActionButtonAdd.setAnimation(toBottom);
                        floatingActionButtonRemove.setAnimation(toBottom);
                        floatingActionButtonOpen.setAnimation(rotateClose);
                        floatingActionButtonAdd.setClickable(false);
                        floatingActionButtonRemove.setClickable(false);
                    }
                }
            });
        }
    }
    private void AddLectureDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_video,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutLinkToVideo = dialogView.findViewById(R.id.TextInputLayoutLinkToVideo);
        ButtonAddVideo = dialogView.findViewById(R.id.ButtonAddVideo);
        ButtonAddVideo.setText(this.getResources().getString(R.string.AddLecture));
        ButtonCancel = dialogView.findViewById(R.id.ButtonCancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lectures").child(getEngineeringName()).child(course.getId()).child((LectureNumber + 1)+"");
                    reference.setValue( new Lecture("Lecture",TextInputLayoutLinkToVideo.getEditText().getText().toString(),(LectureNumber + 1)+""));
                }
            }
        });
    }
    private void RemoveLectureDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_remove_lecture,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutLecture = dialogView.findViewById(R.id.TextInputLayoutLecture);
        ButtonRemoveCourse = dialogView.findViewById(R.id.ButtonRemoveCourse);
        ButtonRemoveCourse.setText(getResources().getString(R.string.RemoveLecture));
        ButtonCancel = dialogView.findViewById(R.id.ButtonCancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LecturePick();
        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alertDialog.cancel(); }
        });
        ButtonRemoveCourse.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(TextInputLayoutLecture.getEditText().getText().toString().equals(""))
                    TextInputLayoutLecture.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutLecture.setHelperText("");
                if(!TextInputLayoutLecture.getEditText().getText().toString().equals("")) {
                    alertDialog.cancel();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference().child("Lectures").child(getEngineeringName()).child(course.getId());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Lecture lecture = dataSnapshot.getValue(Lecture.class);
                                if(TextInputLayoutLecture.getEditText().getText().toString().equals(getResources().getString(R.string.Lecture) + " " +lecture.getNumber())){
                                    DatabaseReference reference1 = database.getReference().child("Lectures").child(getEngineeringName()).child(course.getId()).child(dataSnapshot.getKey());
                                    reference1.setValue(null);
                                    new PopUpMSG(getContext(), getResources().getString(R.string.RemoveLecture), getResources().getString(R.string.LectureSuccessfullyRemoved));
                                }
                            }
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
        DatabaseReference reference = database.getReference().child("Lectures").child(getEngineeringName()).child(course.getId());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Lecture> lectureArrayList = new ArrayList<>();
                lectureArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Lecture lecture = dataSnapshot.getValue(Lecture.class);
                    lectureArrayList.add(lecture);
                }
                TextInputLayoutLecture.getEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String lectures[] = new String[lectureArrayList.size()];
                        for(int i=0; i<lectureArrayList.size();i++)
                            lectures[i] = getResources().getString(R.string.Lecture) + " " +lectureArrayList.get(i).getNumber();
                        setDialog(lectures,getResources().getString(R.string.SelectLecture), TextInputLayoutLecture.getEditText());
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void setDialog(String[] array, String title,TextView textViewPick){
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
        DatabaseReference reference2 = database2.getReference("Lectures").child(getEngineeringName()).child(course.getId());
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lectures.clear();
                LectureNumber = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    Lecture lecture = data.getValue(Lecture.class);
                    if(("Lecture " + lecture.getNumber()).toLowerCase().contains(text.toLowerCase()) || ("הרצאה " + lecture.getNumber()).toLowerCase().contains(text.toLowerCase()))
                        lectures.add(lecture);
                    LectureNumber++;
                }
                LecturesFragmentAdapter lecturesFragmentAdapter = new LecturesFragmentAdapter(getContext(), lectures, user, course);
                recyclerView.setAdapter(lecturesFragmentAdapter);        }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void setLectures(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Lectures").child(getEngineeringName()).child(course.getId());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(User_search.getText().toString().equals("")) {
                    lectures.clear();
                    int index = 1;
                    LectureNumber = 0;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Lecture lecture = data.getValue(Lecture.class);
                        lectures.add(lecture);
                        if(("Lecture " + lecture.getNumber()).equals("Lecture " + index)) {
                            LectureNumber++;
                            index++;
                        }
                    }
                    LecturesFragmentAdapter lecturesFragmentAdapter = new LecturesFragmentAdapter(getContext(), lectures, user, course);
                    recyclerView.setAdapter(lecturesFragmentAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}