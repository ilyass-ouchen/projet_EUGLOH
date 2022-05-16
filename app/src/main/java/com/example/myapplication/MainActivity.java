package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ImageButton btnConnexion = findViewById(R.id.boutonConnexion);
        Button btnCommencer = findViewById(R.id.boutonCommencer);

        // Clique sur le bouton de connexion
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ConnexionCAS.class);
                startActivity(i);
                finish();
            }
        });

        // Clique sur le bouton "Commencer"
        btnCommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(MainActivity.this, MainActivity.this);
                startActivity(i);*/
            }
        });
    }
}