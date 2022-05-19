package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

// Classe permettant la proposition d'evenement par les enseignants
public class PropositionEvenements extends AppCompatActivity {
    EditText titre;
    EditText date;
    EditText localisation;
    EditText groupecible;
    EditText hebergeur;
    EditText datelimite;
    EditText description;
    Button btnProposerEvent;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_proposition_evenements);

        titre = findViewById(R.id.editTextTitreEvents);
        date = findViewById(R.id.editTextDateEvents);
        localisation = findViewById(R.id.editTextLocalisationEvents);
        groupecible = findViewById(R.id.editTextGroupeEvents);
        hebergeur = findViewById(R.id.editTextHostEvents);
        datelimite = findViewById(R.id.editTextDateLimiteEvents);
        description = findViewById(R.id.editTextDescriptionEvents);
        btnProposerEvent = findViewById(R.id.buttonProposerEvent);

        btnProposerEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sTitre = titre.getText().toString();
                String sDate = date.getText().toString();
                String sLocalisation = localisation.getText().toString();
                String sGroupeCible = groupecible.getText().toString();
                String sHost = hebergeur.getText().toString();
                String sDatelimite = datelimite.getText().toString();
                String sDescription = description.getText().toString();

                Evenements evenements = new Evenements(sTitre, sDate, sLocalisation, sGroupeCible, sHost, sDatelimite, sDescription);
                // Ajout de l'evenement à FireStore
                db.collection("ProposedEvents")
                        .add(evenements)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Suppression du contenu des champs de saisie
                                titre.getText().clear();
                                date.getText().clear();
                                localisation.getText().clear();
                                groupecible.getText().clear();
                                hebergeur.getText().clear();
                                datelimite.getText().clear();
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
                                localisation.getText().clear();
                                groupecible.getText().clear();
                                hebergeur.getText().clear();
                                datelimite.getText().clear();
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