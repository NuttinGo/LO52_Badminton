package com.example.nico.lo52_badminton;

/**
 * Created by Nico on 29/09/2017.
 */

public class Tube {
    private int id_tube;
    private int id_marque;
    private int classement_fed_tube;
    private String nom_tube;
    private String validite_tube;
    private String nom_image_tube;

    // Constructeur
    public Tube(int id,int marque,int classement,String nom, String val,String image) {
        this.id_tube=id;
        this.id_marque=marque;
        this.classement_fed_tube=classement;
        this.nom_tube=nom;
        this.validite_tube=val;
        this.nom_image_tube=image;
    }

    public int getId_tube() {
        return id_tube;
    }

    public void setId_tube(int id) {
        this.id_tube = id;
    }

    public int getId_Marque() {
        return id_marque;
    }

    public void setId_Marque(int id) {
        this.id_marque = id;
    }

    public int getClassementFed_tube() {
        return classement_fed_tube;
    }

    public void setClassementFed_tube(int classement) {
        this.classement_fed_tube = classement;
    }

    public String getValidite_tube() {
        return validite_tube;
    }

    public void setValidite_tube(String val) {
        this.validite_tube = val;
    }
    public String getNom_tube() {
        return nom_tube;
    }

    public void setNom_Tube(String val) {
        this.nom_tube = val;
    }

    public String getNomImage_tube() {
        return nom_image_tube;
    }

    public void setNomImage_Tube(String val) {
        this.nom_image_tube = val;
    }

}
