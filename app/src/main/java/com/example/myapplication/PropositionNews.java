package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
// Classe permettant la proposition de news par les enseignants
public class PropositionNews extends AppCompatActivity {
    EditText titre;
    EditText date;
    EditText description;
    Button btnProposerNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_proposition_news);

        titre = findViewById(R.id.editTextTitreNews);
        date = findViewById(R.id.editTextDateNews);
        description = findViewById(R.id.editTextDescriptionNews);
        btnProposerNews = findViewById(R.id.buttonProposerNews);

        /*
        btnProposerNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        */
    }
}