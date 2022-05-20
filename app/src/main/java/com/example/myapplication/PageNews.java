package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PageNews extends AppCompatActivity {
    private WebView webViewNews;
    String titre = "";
    String description = "";
    String date = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<News> newsArrayList;
    MyAdapterNews myAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_news);
        getSupportActionBar().hide();

        // Suppression de tout les evenements stocké dans FireStore
        db.collection("News").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        db.collection("News").document(document.getId())
                                .delete();
                    }
                } else {
                    Log.d(TAG, "Une erreur s'est produite: ", task.getException());
                }
            }
        });

        // Ajout des news validé par l'administrateur à la collection News
        db.collection("AcceptedNews").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        News news = new News(document.getString("titre"),
                                document.getString("date"), document.getString("description"));
                        db.collection("News")
                                .add(news);
                    }
                } else {
                    Log.d(TAG, "Une erreur s'est produite: ", task.getException());
                }
            }
        });

        webViewNews = (WebView) findViewById(R.id.webViewNews);
        webViewNews.getSettings().setJavaScriptEnabled(true);
        webViewNews.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webViewNews.setWebViewClient(new WebViewClient() {

            // Récupération des news présente sur le site Eugloh
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Récupération du nombre de news du site Eugloh
                view.evaluateJavascript("document.getElementsByClassName(\"mb-8\").length",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                int i;
                                // Itération sur chaque news du site
                                for (i = 1; i < Integer.parseInt(html); i++) {
                                    // Récupération du titre de la news
                                    view.evaluateJavascript("document.getElementsByClassName(\"mb-8\")[" + i + "].childNodes[1].children[1].getElementsByClassName(\"h4 mb-3\")[0].textContent",
                                            new ValueCallback<String>() {
                                                @Override
                                                public void onReceiveValue(String html) {
                                                    html = html.replaceAll("\"", "");
                                                    storeNews(html,"","");
                                                }
                                            }
                                    );
                                    // Récupération de la date de la news
                                    view.evaluateJavascript("document.getElementsByClassName(\"mb-8\")[" + i + "].childNodes[1].children[1].getElementsByTagName(\"time\")[0].textContent",
                                            new ValueCallback<String>() {
                                                @Override
                                                public void onReceiveValue(String html) {
                                                    html = html.replaceAll("\\\\n", "");
                                                    html = html.replaceAll("\"", "");
                                                    html = html.replaceAll("  ", "");
                                                    storeNews("",html,"");
                                                }
                                            }
                                    );
                                    // Récupération des liens qui mènent aux descriptions des news du site Eugloh
                                    view.evaluateJavascript("document.getElementsByClassName(\"mb-8\")[" + i + "].childNodes[1].children[1].getElementsByClassName(\"h4 mb-3\")[0].getElementsByTagName(\"a\")[0].getAttribute(\"href\")",
                                            new ValueCallback<String>() {
                                                @Override
                                                public void onReceiveValue(String html) {
                                                    html = html.replaceAll("\"", "");
                                                    html = "https://www.eugloh.eu" + html;
                                                    storeNews("","",html);
                                                }
                                            }
                                    );
                                }
                            }
                        }
                );

            }
        });
        // Chargement de la page des news du site Eugloh
        webViewNews.loadUrl("https://www.eugloh.eu/news/eugloh-news");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Chargement des données ...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerViewNews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsArrayList = new ArrayList<News>();
        myAdapter = new MyAdapterNews(PageNews.this, newsArrayList);

        recyclerView.setAdapter(myAdapter);
        EventChangeListener();
    }

    public void storeNews(String titreR,String dateR, String descriptionR){
        if (titreR != "") {
            titre = titreR;
        }
        if (dateR != "") {
            date = dateR;
        }
        if (descriptionR != "") {
            description = descriptionR;
        }
        if(titre != "" && date != "" && description != ""){
            News news = new News(titre, date, description);
            db.collection("News")
                    .add(news);
            titre = "";
            date = "";
            description = "";
        }
    }

    private void EventChangeListener() {
        db.collection("News")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("FireStore error : ", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                newsArrayList.add(dc.getDocument().toObject(News.class));
                            }
                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
}