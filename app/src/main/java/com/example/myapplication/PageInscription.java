package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageInscription extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_inscription);

        Button bouttonSinscrire = findViewById(R.id.bouttonSinscrire);

        EditText nom = findViewById(R.id.editTextInsNom);
        EditText prenom = findViewById(R.id.editTextInsPrenom);
        EditText universite = findViewById(R.id.editTextInsUniversite); // univ, cursus, mail
        EditText cursus = findViewById(R.id.editTextInsDate);
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
                if(sontVides) {
                    try {
                        JSONObject jo = new JSONObject();

                        // putting data to JSONObject
                        jo.put("firstName", "John");
                        jo.put("lastName", "Smith");
                        jo.put("age", 25);

                        // for address data, first create LinkedHashMap
                        Map m = new LinkedHashMap(4);
                        m.put("streetAddress", "21 2nd Street");
                        m.put("city", "New York");
                        m.put("state", "NY");
                        m.put("postalCode", 10021);

                        // putting address to JSONObject
                        jo.put("address", m);

                        // for phone numbers, first create JSONArray
                        JSONArray ja = new JSONArray();

                        m = new LinkedHashMap(2);
                        m.put("type", "home");
                        m.put("number", "212 555-1234");

                        // adding map to list
                        ja.put(m); //add

                        m = new LinkedHashMap(2);
                        m.put("type", "fax");
                        m.put("number", "212 555-1234");

                        // adding map to list
                        ja.put(m); //add

                        // putting phoneNumbers to JSONObject
                        jo.put("phoneNumbers", ja);

                        // writing JSON to file:"JSONExample.json" in cwd
                        PrintWriter pw = new PrintWriter("app/test.json");
                        pw.write(jo.toString());

                        pw.flush();
                        pw.close();
                    }
                    catch (JSONException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent(PageInscription.this, PageConnexion.class);
                    startActivity(i);
                }
            }
        });
    }


}