package com.example.myapplication;

public class News {
    private String titre;
    private String date;
    private String description;

    // Constructeur de la classe News
    public News(String titre, String date, String description) {
        this.titre = titre;
        this.date = date;
        this.description = description;
    }

    // Accesseurs Get
    public String getTitre() {
        return titre;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
