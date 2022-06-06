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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easylearnsce.Client.Adapters.ExercisesFragmentAdapter;
import com.example.easylearnsce.Client.Adapters.LecturesFragmentAdapter;
import com.example.easylearnsce.Client.Class.Course;
import com.example.easylearnsce.Client.Class.Lecture;
import com.example.easylearnsce.Client.Class.Loading;
import com.example.easylearnsce.Client.Class.PopUpMSG;
import com.example.easylearnsce.Client.Class.User;
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

public class ExercisesFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextInputLayout TextInputLayoutExercise,TextInputLayoutLinkToVideo;
    private Button ButtonRemoveCourse, ButtonCancel, ButtonAddVideo;
    private Dialog dialog;
    private ListView ListViewSearch;
    private EditText EditTextSearch, User_search;
    private TextView TextViewSearch;
    private Loading loading;
    private FloatingActionButton floatingActionButtonOpen;
    private ExtendedFloatingActionButton floatingActionButtonAdd, floatingActionButtonRemove;
    private Animation rotateOpen, rotateClose, toBottom, fromBottom;
    private Boolean isOpen = false;
    private Context context;
    private Course course;
    private int ExercisesNumber = 0;
    private User user;
    private View view;
    private ArrayList<Lecture> lectures;
    public ExercisesFragment() {
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setCourse(Course course){
        this.course = course;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercises, container, false);
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
        setExercises();
        ExerciseSearch();
        return view;
    }
    private void setExercises(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Courses").child(getEngineeringName()).child(course.getId()).child("exercises");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(User_search.getText().toString().equals("")) {
                    course.getExercises().clear();
                    ExercisesNumber = 1;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Lecture lecture = data.getValue(Lecture.class);
                        course.getExercises().add(lecture);
                        if(lecture.getLectureName().equals("Exercise " + ExercisesNumber))
                            ExercisesNumber++;
                    }
                    ExercisesFragmentAdapter exercisesFragmentAdapter = new ExercisesFragmentAdapter(getContext(), user, course);
                    recyclerView.setAdapter(exercisesFragmentAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void ExerciseSearch(){
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
        DatabaseReference reference2 = database2.getReference("Courses").child(getEngineeringName()).child(course.getId()).child("exercises");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!User_search.getText().toString().equals("")) {
                    course.getExercises().clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Lecture lecture = data.getValue(Lecture.class);
                        if ((getResources().getString(R.string.Exercise) + " " + lecture.getNumber()).toLowerCase().contains(text.toLowerCase()))
                            course.getExercises().add(lecture);
                    }
                    ExercisesFragmentAdapter exercisesFragmentAdapter = new ExercisesFragmentAdapter(getContext(), user, course);
                    recyclerView.setAdapter(exercisesFragmentAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void setAddAndRemove(){
        if(user.getType().equals("Admin") || user.getType().equals("אדמין") || (user.getType().equals("Teacher") && user.getFullName().equals(course.getTeacherName())) || (user.getType().equals("מרצה") && user.getFullName().equals(course.getTeacherName()) )){
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
                                AddExerciseDialog();
                            }
                        });
                        floatingActionButtonRemove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RemoveExerciseDialog();
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
    private void AddExerciseDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_video,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutLinkToVideo = dialogView.findViewById(R.id.TextInputLayoutLinkToVideo);
        ButtonAddVideo = dialogView.findViewById(R.id.ButtonAddVideo);
        ButtonAddVideo.setText(this.getResources().getString(R.string.AddExercise));
        ButtonAddVideo.setTextAppearance(dialogView.getContext(), R.style.Button_Style);
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
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses").child(getEngineeringName()).child(course.getId()).child("exercises").child(ExercisesNumber + "");
                    reference.setValue(new Lecture("Exercise " + ExercisesNumber, TextInputLayoutLinkToVideo.getEditText().getText().toString(), ExercisesNumber, "Exercise"));
                }
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
        ButtonRemoveCourse.setText(getResources().getString(R.string.RemoveExercise));
        ButtonCancel = dialogView.findViewById(R.id.ButtonCancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ExercisePick();
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
                    int index = 0;
                    for(int i=0;i<course.getExercises().size();i++)
                        if( ("Exercise " + course.getExercises().get(i).getNumber()).equals(TextInputLayoutExercise.getEditText().getText().toString()) ||  ("תרגול " + course.getExercises().get(i).getNumber()).equals(TextInputLayoutExercise.getEditText().getText().toString()))
                            index = course.getExercises().get(i).getNumber();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses").child(getEngineeringName()).child(course.getId()).child("exercises").child(index + "");
                    reference.setValue(null);
                    new PopUpMSG(getContext(), getResources().getString(R.string.RemoveExercise), getResources().getString(R.string.ExerciseSuccessfullyRemoved));
                }
            }
        });
    }
    private void ExercisePick(){
        TextInputLayoutExercise.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lectures[] = new String[course.getExercises().size()];
                for(int i=0; i<course.getExercises().size();i++)
                    lectures[i] = getResources().getString(R.string.Exercise) + " " +course.getExercises().get(i).getNumber();
                setDialog(lectures,getResources().getString(R.string.SelectExercise), TextInputLayoutExercise.getEditText());
            }
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
}