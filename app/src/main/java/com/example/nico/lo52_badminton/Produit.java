package com.example.nico.lo52_badminton;

/**
 * Created by Nico on 29/09/2017.
 */

public class Produit {
    private int id_distributeur;
    private int id_tube;
    private float prix_produit;
    private int stock_produit;

    // Constructeur
    public Produit(int id_d,int id_t,float p,int s) {
        this.id_distributeur=id_d;
        this.id_tube=id_t;
        this.prix_produit=p;
        this.stock_produit=s;
    }

    public int getId_distributeur() {
        return id_distributeur;
    }

    public void setId_distributeur(int id) {
        this.id_distributeur = id;
    }

    public int getId_tube() {
        return id_tube;
    }

    public void setId_tube(int id) {
        this.id_tube = id;
    }

    public int getStock_produit() {
        return stock_produit;
    }

    public void setStock_produit(int s) {
        this.stock_produit = s;
    }

    public float getPrix_produit() {
        return prix_produit;
    }

    public void setPrix_produit(float p) {
        this.prix_produit = p;
    }
}
