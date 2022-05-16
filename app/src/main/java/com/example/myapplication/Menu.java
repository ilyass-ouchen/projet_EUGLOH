package com.example.myapplication;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Menu extends AppCompatActivity {
    Animation rotateOpen, rotateClose, fromBottom, toBottom ;
    FloatingActionButton fb1, fb2, fb3;
    Boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
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

        // Suppression des élements inutile des chaines de caractères (", [ , \, etc)
        nom = replaceString(nom);
        prenom = replaceString(prenom);

        // Affichage du message d'accueil
        LinearLayout linearLayout = findViewById(R.id.editTextContainer);
        TextView tvAccueil = new TextView(this);
        tvAccueil.setHint("Bienvenue sur EuglohApp " + nom.toUpperCase() + " " + prenom);
        tvAccueil.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvAccueil.setPadding(20, 20, 20, 20);
        tvAccueil.setTextSize(30);
        tvAccueil.setGravity(Gravity.CENTER);
        tvAccueil.setTypeface(null, Typeface.BOLD_ITALIC);

        if (linearLayout != null) {
            linearLayout.addView(tvAccueil);
        }

        // Evenement de clique sur le bouton de menu
        fb1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onAddButtonClicked();
            }});
    }

    public String replaceString(String data){
        data = data.replaceAll("\\s", "");
        data = data.replaceAll("\\\\n", "");
        data = data.replaceAll("\\[", "");
        data = data.replaceAll("\\]", "");
        data = data.replaceAll("\"", "");
        return data;
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