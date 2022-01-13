package com.example.easylearnsce.Class;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.easylearnsce.R;

    public class RemoveCourseDialog extends AppCompatDialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.remove_course,null);
            builder.setView(view).setTitle(getResources().getString(R.string.RemoveCourse)).setNegativeButton(getResources().getString(R.string.No),new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            }).setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return builder.create();
        }
    }
