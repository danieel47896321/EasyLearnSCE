package com.example.easylearnsce.Fragments;

import android.os.Bundle;

import com.example.easylearnsce.R;
import com.google.android.youtube.player.YouTubeBaseActivity;

public class GenericYouTubePlayer extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_you_tube_player);
    }
}