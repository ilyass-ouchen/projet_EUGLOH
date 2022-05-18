package com.example.myapplication;

public class Evenements {
    private String titre;
    private String date;
    private String localisation;
    private String groupeCible;
    private String host;
    private String dateLimite;
    private String description;

    // Constructeur de la classe Evenements
    public Evenements(String titre, String date, String localisation, String groupeCible, String host, String dateLimite, String description) {
        this.titre = titre;
        this.date = date;
        this.localisation = localisation;
        this.groupeCible = groupeCible;
        this.host = host;
        this.dateLimite = dateLimite;
        this.description = description;
    }

    // Accesseurs Get
    public String getTitre() {
        return titre;
    }

    public String getDate() {
        return date;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getGroupeCible() {
        return groupeCible;
    }

    public String getHost() {
        return host;
    }

    public String getDateLimite() {
        return dateLimite;
    }

    public String getDescription() {
        return description;
    }
}
