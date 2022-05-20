package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

// Classe permettant la proposition de news par les enseignants
public class PropositionNews extends AppCompatActivity {
    EditText titre;
    EditText date;
    EditText description;
    Button btnProposerNews;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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


        btnProposerNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sTitre = titre.getText().toString();
                String sDate = date.getText().toString();
                String sDescription = description.getText().toString();
                News news = new News(sTitre, sDate, sDescription);

                // Ajout de la news à FireStore
                db.collection("ProposedNews")
                        .add(news)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                titre.getText().clear();
                                date.getText().clear();
                                description.getText().clear();

                                Context context = getApplicationContext();
                                CharSequence text = "Proposition réalisée avec succés !";
                                int duration = Toast.LENGTH_SHORT;

                                // Affichage d'un message de succés
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Suppression du contenu des champs de saisie
                                titre.getText().clear();
                                date.getText().clear();
                                description.getText().clear();

                                // Affichage d'un message d'erreur
                                Context context = getApplicationContext();
                                CharSequence text = "Une erreur s'est produite, veuillez réessayer";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        });
            }
        });

    }
}