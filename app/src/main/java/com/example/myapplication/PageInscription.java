package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PageInscription extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_inscription);

        Button bouttonSinscrire = findViewById(R.id.bouttonSinscrire);

        EditText nom = findViewById(R.id.editTextInsNom);
        EditText prenom = findViewById(R.id.editTextInsPrenom);
        EditText universite = findViewById(R.id.editTextInsUniversite); // univ, cursus, mail
        EditText cursus = findViewById(R.id.editTextInsCursus);
        EditText mail = findViewById(R.id.editTextInsEmail);
        EditText[] liste = {nom,prenom,universite,cursus,mail}; // on met tous les champs dans une liste qu'on parcourera

        bouttonSinscrire.setOnClickListener(new View.OnClickListener() {
            Boolean sontVides = true;
            @Override
            public void onClick(View view) {
                for(int i=0;i<5;i++) { // On vérifie que tous les champs ne sont pas vides
                    if(TextUtils.isEmpty(liste[i].getText())) {
                        Toast.makeText(PageInscription.this, "Remplissez tous les champs !", Toast.LENGTH_SHORT).show();
                        sontVides = false;
                        break;
                    }
                }
                if(sontVides==true) {
                    Intent i = new Intent(PageInscription.this, PageConnexion.class);
                    startActivity(i);
                }
            }
        });
    }


}
