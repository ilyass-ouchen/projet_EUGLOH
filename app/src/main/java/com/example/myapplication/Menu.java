package com.example.myapplication;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Menu extends AppCompatActivity {
    Animation rotateOpen, rotateClose, fromBottom, toBottom ;
    FloatingActionButton fb1, fb2, fb3;
    Boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        fb1 = (FloatingActionButton) findViewById(R.id.fb1);
        fb2 = (FloatingActionButton) findViewById(R.id.fb2);
        fb3 = (FloatingActionButton) findViewById(R.id.fb3);

        // Recupération des données de l'utilisateurs envoyé par ConnexionCAS
        Bundle extras = getIntent().getExtras();
        String nom = extras.getString("Nom");
        String prenom = extras.getString("Prenom");
        String role = extras.getString("Role");
        String mail = extras.getString("Mail");

        Log.d("nom", nom);
        Log.d("prenom", prenom);
        Log.d("role", role);
        Log.d("mail", mail);

        // Evenement de clique sur le bouton de menu
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