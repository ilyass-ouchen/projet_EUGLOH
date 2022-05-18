package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageEvenements extends AppCompatActivity {
    private WebView webViewEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_evenements);

        webViewEvents = (WebView) findViewById(R.id.webViewEvents);
        webViewEvents.getSettings().setJavaScriptEnabled(true);
        webViewEvents.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        // Récupération des évenements présent sur le site Eugloh
        webViewEvents.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(webViewEvents, url);
                // Recupération du nombre d'évenement du site Eugloh
                webViewEvents.evaluateJavascript("document.getElementsByClassName(\"mb-8\").length",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                int i;
                                if(html != null && html != "") {
                                    for (i = 0; i <Integer.valueOf(html); i++){
                                        int iterationEvents = i;
                                        // Récupération du nombre d'informations pour l'evenement courant
                                        webViewEvents.evaluateJavascript("document.getElementsByClassName(\"mb-8\")[" + i + "].childNodes[1].childElementCount",
                                                new ValueCallback<String>() {
                                                    @Override
                                                    public void onReceiveValue(String html) {
                                                        if (html != null && html != ""){
                                                            storeEvents(webViewEvents, iterationEvents, Integer.valueOf(html));
                                                        }
                                                    }
                                                }
                                        );
                                    }
                                }
                            }
                        }
                );
            }
        });
        // Chargement de la page des events du site Eugloh
        webViewEvents.loadUrl("https://www.eugloh.eu/study-and-mobility/events");
    }

    // Fonction qui recupère les evenements du site et les affiche sur l'application
    public void storeEvents(WebView wv, int iterationEvents, int numberDataEvent){
        // Recupération du titre de l'evenement courant
        wv.evaluateJavascript("document.getElementsByClassName(\"h4 mb-3\")[" + iterationEvents + "].textContent",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String html) {
                        if (html != null && html != ""){
                            html = html.replaceAll("\"", "");
                            Log.d("Resultat", html);
                        }
                    }
                }
        );

        // Récupération des informations de l'evenement courant
        int j;
        for(j = 0; j < numberDataEvent/2; j++){
            wv.evaluateJavascript("document.getElementsByClassName(\"mb-8\")[" + iterationEvents + "].childNodes[1].getElementsByClassName(\"cell medium-2\")[" + j + "].textContent + \" : \" + document.getElementsByClassName(\"mb-8\")[" + iterationEvents + "].childNodes[1].getElementsByClassName(\"cell medium-10\")[" + j + "].textContent",
                    new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String html) {
                            if (html != null && html != ""){
                                html = html.replaceAll("\\\\n", "");
                                html = html.replaceAll("\"", "");
                                html = html.replaceAll("  ", "");
                                Log.d("Resultat", html);
                            }
                        }
                    }
            );
        }
    }

}