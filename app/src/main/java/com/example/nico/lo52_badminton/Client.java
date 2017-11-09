package com.example.nico.lo52_badminton;

/**
 * Created by Nico on 29/09/2017.
 */

public class Client {

    private int id_client;
    private String nom_client;

    // Constructeur
    public Client(String nom) {
        this.id_client=0;
        this.nom_client=nom;
    }

    public Client(int id,String nom) {
        this.id_client=id;
        this.nom_client=nom;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id) {
        this.id_client = id;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom) {
        this.nom_client = nom;
    }

} 
