package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

// Classe permettant la proposition de news par les enseignants
public class PropositionNews extends AppCompatActivity {
    EditText titre;
    EditText date;
    EditText description;
    Button btnProposerNews;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Animation rotateOpen, rotateClose, fromBottom, toBottom, fromRight, toRight ;
    FloatingActionButton fb1, fb2, fb3, fb4, fb5, fb6, fb7, deco;
    Boolean clicked = false;
    UtilisateurConnecte utilisateurConnecte;

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

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        fromRight = AnimationUtils.loadAnimation(this, R.anim.from_right_anim);
        toRight = AnimationUtils.loadAnimation(this, R.anim.to_right_anim);

        fb1 = (FloatingActionButton) findViewById(R.id.boutonMenu);
        fb2 = (FloatingActionButton) findViewById(R.id.boutonEvents);
        fb3 = (FloatingActionButton) findViewById(R.id.boutonNews);
        fb4 = (FloatingActionButton) findViewById(R.id.boutonPropositionEvent);
        fb5 = (FloatingActionButton) findViewById(R.id.boutonPropositionNews);
        fb6 = (FloatingActionButton) findViewById(R.id.boutonVerifierEvent);
        fb7 = (FloatingActionButton) findViewById(R.id.boutonVerifierNews);
        deco = (FloatingActionButton) findViewById(R.id.boutonDeconnexion);

        utilisateurConnecte = (UtilisateurConnecte) getIntent().getSerializableExtra("utilisateurConnecte");

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

        // MENU
        fb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PropositionNews.this, PageEvenements.class);
                i.putExtra("utilisateurConnecte", utilisateurConnecte);
                startActivity(i);
                finish();
            }
        });

        fb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PropositionNews.this, PageNews.class);
                i.putExtra("utilisateurConnecte", utilisateurConnecte);
                startActivity(i);
                finish();
            }
        });
        if(utilisateurConnecte.getRole() == Role.Enseignant || utilisateurConnecte.getRole() == Role.Administrateur) {
            fb4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(PropositionNews.this, PropositionEvenements.class);
                    i.putExtra("utilisateurConnecte", utilisateurConnecte);
                    startActivity(i);
                    finish();
                }
            });

            fb5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(PropositionNews.this, "Vous êtes déjà sur la page de proposition de news !", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(utilisateurConnecte.getRole() == Role.Administrateur) {
            fb6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(PropositionNews.this, ValidationEvents.class);
                    i.putExtra("utilisateurConnecte", utilisateurConnecte);
                    startActivity(i);
                    finish();
                }
            });

            fb7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(PropositionNews.this, ValidationNews.class);
                    i.putExtra("utilisateurConnecte", utilisateurConnecte);
                    startActivity(i);
                    finish();
                }
            });
        }
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
                Intent i = new Intent(PropositionNews.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

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
            if(utilisateurConnecte.getRole() == Role.Administrateur || utilisateurConnecte.getRole() == Role.Enseignant) {
                fb4.startAnimation(fromRight);
                fb5.startAnimation(fromRight);
            }
            if(utilisateurConnecte.getRole() == Role.Administrateur) {
                fb6.startAnimation(fromRight);
                fb7.startAnimation(fromRight);
            }
            deco.startAnimation(fromBottom);

            fb1.startAnimation(rotateOpen);
        }
        else{
            fb2.startAnimation(toBottom);
            fb3.startAnimation(toBottom);
            if(utilisateurConnecte.getRole() == Role.Administrateur || utilisateurConnecte.getRole() == Role.Enseignant) {
                fb4.startAnimation(toRight);
                fb5.startAnimation(toRight);
            }
            if(utilisateurConnecte.getRole() == Role.Administrateur) {
                fb6.startAnimation(toRight);
                fb7.startAnimation(toRight);
            }
            deco.startAnimation(toBottom);
            fb1.startAnimation(rotateClose);
        }
    }

    private void setVisibility(Boolean clicked) {
        if(!clicked){
            fb2.setVisibility(View.VISIBLE);
            fb3.setVisibility(View.VISIBLE);
            if(utilisateurConnecte.getRole() == Role.Administrateur || utilisateurConnecte.getRole() == Role.Enseignant) {
                fb4.setVisibility(View.VISIBLE);
                fb5.setVisibility(View.VISIBLE);
            }
            if(utilisateurConnecte.getRole() == Role.Administrateur) {
                fb6.setVisibility(View.VISIBLE);
                fb7.setVisibility(View.VISIBLE);
            }
            deco.setVisibility(View.VISIBLE);
        }
        else{
            fb2.setVisibility(View.INVISIBLE);
            fb3.setVisibility(View.INVISIBLE);
            fb4.setVisibility(View.INVISIBLE);
            fb5.setVisibility(View.INVISIBLE);
            fb6.setVisibility(View.INVISIBLE);
            fb7.setVisibility(View.INVISIBLE);
            deco.setVisibility(View.INVISIBLE);

        }
    }

    private void setClickable(Boolean clicked){
        if(!clicked){
            fb2.setClickable(true);
            fb3.setClickable(true);
            if(utilisateurConnecte.getRole() == Role.Administrateur || utilisateurConnecte.getRole() == Role.Enseignant) {
                fb4.setClickable(true);
                fb5.setClickable(true);
            }
            if(utilisateurConnecte.getRole() == Role.Administrateur) {
                fb6.setClickable(true);
                fb7.setClickable(true);
            }
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