package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageNews extends AppCompatActivity {
    private WebView webViewNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_news);

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
                                                    //Log.d("test1", html);
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
                                                    html = html.replaceAll(" ", "");
                                                    //Log.d("test1", html);
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
                                                    //Log.d("test1", html);
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
    }
}