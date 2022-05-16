package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ConnexionCAS extends AppCompatActivity {
    private WebView webView;
    String NomUtilisateur = "";
    String PrenomUtilisateur = "";
    String RoleUtilisateur = "";
    String MailUtilisateur = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_connexion_cas);

        webView = (WebView) findViewById(R.id.myWebView);

        // Suppression du Cache/Historique/Cookies de la webview
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.clearSslPreferences();

        // Ajout des autorisations pour réaliser des commandes JavaScript sur la WebView
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        // Parametrage de la WebView
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Si la connexion CAS réussi alors on redirige vers la page CAS contenant l'ensemble des informations de l'utilisateur
                if(url.contains("idp.universite-paris-saclay.fr")) {
                    webView.setVisibility(View.GONE);
                    webView.loadUrl("https://sso.universite-paris-saclay.fr/cas/login");
                }
                return false;
            }

            // Cette méthode récupere les infomartions de l'utilisateur connecté sur la page CAS : nom, prénom, role, mail
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(webView, url);
                if (url.equals("https://sso.universite-paris-saclay.fr/cas/login")) {
                    // Récupération du nom
                    webView.evaluateJavascript("document.getElementsByClassName(\"mdc-data-table__cell\")[19].textContent",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    if (html != null && html != "")
                                        redirectionAccueil(html, "", "", "");
                                }
                    });
                    // Récupération du prenom
                    webView.evaluateJavascript("document.getElementsByClassName(\"mdc-data-table__cell\")[15].textContent",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    if (html != null && html != "")
                                        redirectionAccueil("", html, "", "");
                                }
                    });
                    // Récupération du role
                    webView.evaluateJavascript("document.getElementsByClassName(\"mdc-data-table__cell\")[9].textContent",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    if (html != null && html != "")
                                        redirectionAccueil("", "", html, "");
                                }
                    });
                    // Récupération du mail
                    webView.evaluateJavascript("document.getElementsByClassName(\"mdc-data-table__cell\")[11].textContent",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    if (html != null && html != "")
                                        redirectionAccueil("", "", "", html);
                                }
                    });

                }
            }

        });
        // Chargement de la page d'authentification eCampus de Paris-Saclay
        webView.loadUrl("https://sso.universite-paris-saclay.fr/cas/login?service=https%3A%2F%2Fidp.universite-paris-saclay.fr%2Fidp%2FAuthn%2FExternal%3Fconversation%3De1s2&entityId=https%3A%2F%2Fecampus.paris-saclay.fr%2Fauth%2Fsaml2%2Fsp%2Fmetadata.php");
    }

    // Cette méthode recoit les informations de l'utilisateur connecté et les stockes dans les variables globale NomUtilisateur, PrenomUtilisateur, RoleUtilisateur et MailUtilisateur
    public void redirectionAccueil(String nom, String prenom, String role, String mail){
        if(nom != null && nom != "")
            NomUtilisateur = nom;
        else if(prenom != null && prenom != "")
            PrenomUtilisateur = prenom;
        else if(role != null && role != "")
            RoleUtilisateur = role;
        else if(mail != null && mail != "")
            MailUtilisateur = mail;

        // Redirection sur la page d'accueil de l'application
        if(NomUtilisateur != "" && PrenomUtilisateur != "" && RoleUtilisateur != "" && MailUtilisateur != ""){
            Intent i = new Intent(ConnexionCAS.this, Menu.class);
            i.putExtra("Nom", NomUtilisateur);
            i.putExtra("Prenom", PrenomUtilisateur);
            i.putExtra("Role", RoleUtilisateur);
            i.putExtra("Mail", MailUtilisateur);
            startActivity(i);
            finish();
        }
    }
}
