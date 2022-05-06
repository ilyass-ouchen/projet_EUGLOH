package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PageConnexion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_connexion); // faire le layout de connexion

        EditText log = findViewById(R.id.editTextConnexionNom);
        EditText mdp = findViewById(R.id.editTextConnexionMdp);
        Button btnIns = findViewById(R.id.bouttonSinscrire);
        btnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String l = log.getText().toString();
                String m = mdp.getText().toString();
                if(l.equals("admin") && m.equals("admin")) {
                    Intent i = new Intent(PageConnexion.this, Menu.class);
                    startActivity(i);
                }
            }
        });
    }
}

// Dans cette classe on vérifie que login et mdp sont bons dans json