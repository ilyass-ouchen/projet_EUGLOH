package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Menu  extends AppCompatActivity {

    Animation rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
    Animation rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
    Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
    Animation toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

    FloatingActionButton fb1 = findViewById(R.id.fb1);
    FloatingActionButton fb2 = findViewById(R.id.fb2);
    FloatingActionButton fb3 = findViewById(R.id.fb3);

    Boolean clicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        fb1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onAddButtonClicked();
            }});
    }
    public void onAddButtonClicked(){
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        if(!clicked){
            clicked = true;
        }
        else{
            clicked = false;
        }
    }
    private void setAnimation(Boolean clicked) {
        if(!clicked){
            fb2.startAnimation(fromBottom);
            fb3.startAnimation(fromBottom);
            fb1.startAnimation(rotateOpen);
        }
        else{
            fb2.startAnimation(toBottom);
            fb3.startAnimation(toBottom);
            fb1.startAnimation(rotateClose);
        }
    }

    private void setVisibility(Boolean clicked) {
        if(!clicked){
            fb2.setVisibility(View.VISIBLE);
            fb3.setVisibility(View.VISIBLE);
        }
        else{
            fb2.setVisibility(View.INVISIBLE);
            fb3.setVisibility(View.INVISIBLE);
        }
    }
    private void setClickable(Boolean clicked){
        if(!clicked){
            fb2.setClickable(true);
            fb3.setClickable(true);
        }
        else{
            fb2.setClickable(false);
            fb3.setClickable(false);
        }
    }
}
