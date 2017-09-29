package com.example.nico.lo52_badminton;

/**
 * Created by Nico on 29/09/2017.
 */

public class Distributeur {
    private int id_distributeur;
    private String nom_distributeur;

    // Constructeur
    public Distributeur(int id,String nom) {
        this.id_distributeur=id;
        this.nom_distributeur=nom;
    }

    public int getId_distributeur() {
        return id_distributeur;
    }

    public void setId_distributeur(int id) {
        this.id_distributeur = id;
    }

    public String getNom_distributeur() {
        return nom_distributeur;
    }

    public void setNom_distributeur(String nom) {
        this.nom_distributeur = nom;
    }
}
