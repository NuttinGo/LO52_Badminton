package com.example.nico.lo52_badminton;

/**
 * Created by Nico on 29/09/2017.
 */

public class Marque {
    private int id_marque;
    private String nom_marque;

    // Constructeur
    public Marque(int id,String nom) {
        this.id_marque=id;
        this.nom_marque=nom;
    }

    public int getId_marque() {
        return id_marque;
    }

    public void setId_marque(int id) {
        this.id_marque = id;
    }

    public String getNom_marque() {
        return nom_marque;
    }

    public void setNom_marque(String nom) {
        this.nom_marque = nom;
    }
}
