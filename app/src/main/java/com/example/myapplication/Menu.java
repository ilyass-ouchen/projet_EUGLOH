package com.example.myapplication;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Menu extends AppCompatActivity {
    Animation rotateOpen, rotateClose, fromBottom, toBottom ;
    FloatingActionButton fb1, fb2, fb3, fb4, fb5, fb6, fb7, deco;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
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

        fb1 = (FloatingActionButton) findViewById(R.id.boutonMenu);
        fb2 = (FloatingActionButton) findViewById(R.id.boutonEvents);
        fb3 = (FloatingActionButton) findViewById(R.id.boutonNews);
        fb4 = (FloatingActionButton) findViewById(R.id.boutonPropositionEvent);
        fb5 = (FloatingActionButton) findViewById(R.id.boutonPropositionNews);
        fb6 = (FloatingActionButton) findViewById(R.id.boutonVerifierEvent);
        fb7 = (FloatingActionButton) findViewById(R.id.boutonVerifierNews);
        deco = (FloatingActionButton) findViewById(R.id.boutonDeconnexion);

        tv1 = (TextView) findViewById(R.id.tvInfo1);
        tv2 = (TextView) findViewById(R.id.tvInfo2);
        tv3 = (TextView) findViewById(R.id.tvInfo3);
        tv4 = (TextView) findViewById(R.id.tvInfo4);
        tv5 = (TextView) findViewById(R.id.tvInfo5);
        tv6 = (TextView) findViewById(R.id.tvInfo6);
        tv7 = (TextView) findViewById(R.id.tvInfo7);

        // Recupération des données de l'utilisateurs envoyé par ConnexionCAS
        Bundle extras = getIntent().getExtras();
        String nom = extras.getString("Nom");
        String prenom = extras.getString("Prenom");
        String role = extras.getString("Role");
        String mail = extras.getString("Mail");
        String webView = extras.getString("webView");

        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    //Log.d(C.TAG, "Using clearCookies code for API >=" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
                    CookieManager.getInstance().removeAllCookies(null);
                    CookieManager.getInstance().flush();
                }
                else{
                    CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(getApplication().getApplicationContext());
                    cookieSyncMngr.startSync();
                    CookieManager cookieManager=CookieManager.getInstance();
                    cookieManager.removeAllCookie();
                    cookieManager.removeSessionCookie();
                    cookieSyncMngr.stopSync();
                    cookieSyncMngr.sync();
                }
                Intent i = new Intent(Menu.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

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
            fb4.startAnimation(fromBottom);
            fb5.startAnimation(fromBottom);
            fb6.startAnimation(fromBottom);
            fb7.startAnimation(fromBottom);
            deco.startAnimation(fromBottom);

            tv1.startAnimation(fromBottom);
            tv2.startAnimation(fromBottom);
            tv3.startAnimation(fromBottom);
            tv4.startAnimation(fromBottom);
            tv5.startAnimation(fromBottom);
            tv6.startAnimation(fromBottom);
            tv7.startAnimation(fromBottom);

            fb1.startAnimation(rotateOpen);
        }
        else{
            fb2.startAnimation(toBottom);
            fb3.startAnimation(toBottom);
            fb4.startAnimation(toBottom);
            fb5.startAnimation(toBottom);
            fb6.startAnimation(toBottom);
            fb7.startAnimation(toBottom);
            deco.startAnimation(toBottom);

            tv1.startAnimation(toBottom);
            tv2.startAnimation(toBottom);
            tv3.startAnimation(toBottom);
            tv4.startAnimation(toBottom);
            tv5.startAnimation(toBottom);
            tv6.startAnimation(toBottom);
            tv7.startAnimation(toBottom);

            fb1.startAnimation(rotateClose);
        }
    }

    private void setVisibility(Boolean clicked) {
        if(!clicked){
            fb2.setVisibility(View.VISIBLE);
            fb3.setVisibility(View.VISIBLE);
            fb4.setVisibility(View.VISIBLE);
            fb5.setVisibility(View.VISIBLE);
            fb6.setVisibility(View.VISIBLE);
            fb7.setVisibility(View.VISIBLE);
            deco.setVisibility(View.VISIBLE);

            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);
            tv6.setVisibility(View.VISIBLE);
            tv7.setVisibility(View.VISIBLE);
        }
        else{
            fb2.setVisibility(View.INVISIBLE);
            fb3.setVisibility(View.INVISIBLE);
            fb4.setVisibility(View.INVISIBLE);
            fb5.setVisibility(View.INVISIBLE);
            fb6.setVisibility(View.INVISIBLE);
            fb7.setVisibility(View.INVISIBLE);
            deco.setVisibility(View.INVISIBLE);

            tv1.setVisibility(View.INVISIBLE);
            tv2.setVisibility(View.INVISIBLE);
            tv3.setVisibility(View.INVISIBLE);
            tv4.setVisibility(View.INVISIBLE);
            tv5.setVisibility(View.INVISIBLE);
            tv6.setVisibility(View.INVISIBLE);
            tv7.setVisibility(View.INVISIBLE);
        }
    }

    private void setClickable(Boolean clicked){
        if(!clicked){
            fb2.setClickable(true);
            fb3.setClickable(true);
            fb4.setClickable(true);
            fb5.setClickable(true);
            fb6.setClickable(true);
            fb7.setClickable(true);
            deco.setClickable(true);
        }
        else{
            fb2.setClickable(false);
            fb3.setClickable(false);
            fb4.setClickable(false);
            fb5.setClickable(false);
            fb6.setClickable(false);
            fb7.setClickable(false);
            deco.setClickable(false);
        }
    }

}