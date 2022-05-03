package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class PageConnexion extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_connexion);

        webView = (WebView) findViewById(R.id.myWebView);

        // Suppression du Cache/Historique/Cookies de la webview
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.clearSslPreferences();

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Si redirection = authentification réussie
                if (url.contains("idp.universite-paris-saclay.fr")) {
                    webView.destroy();
                    Intent i = new Intent(PageConnexion.this, MainActivity.class);
                    startActivity(i);
                }
                return false;
            }
        });

        // Chargement de la page d'authentification CAS de Paris-Saclay
        webView.loadUrl("https://sso.universite-paris-saclay.fr/cas/login?service=https%3A%2F%2Fidp.universite-paris-saclay.fr%2Fidp%2FAuthn%2FExternal%3Fconversation%3De1s2&entityId=https%3A%2F%2Fecampus.paris-saclay.fr%2Fauth%2Fsaml2%2Fsp%2Fmetadata.php");

    }
}
