package com.example.nico.lo52_badminton;

/**
 * Created by Nico on 05/10/2017.
 */

public class Achat {
    private int id_client;
    private int id_produit;
    private int quantite_achat;
    //Booleén true si >0 et false sinon
    private int achat_effectue;
    private String date_achat;

    // Constructeur
    public Achat(int id_c,int id_p,int q,int b,String s) {
        this.id_client=id_c;
        this.id_produit=id_p;
        this.quantite_achat=q;
        this.achat_effectue=b;
        this.date_achat=s;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id) {
        this.id_client = id;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id) {
        this.id_produit = id;
    }

    public int getQuantite_achat() {
        return quantite_achat;
    }

    public void setQuantite_achat(int quantite) {
        this.quantite_achat = quantite;
    }

    public int getAchat_effectue() {
        return achat_effectue;
    }

    public void setAchat_effectue(int b) {
        this.achat_effectue = b;
    }

    public String getDate_achat() {
        return date_achat;
    }

    public void setDate_achat(String d) {
        this.date_achat = d;
    }
}
