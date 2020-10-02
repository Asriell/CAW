package fr.univlyon1.m1if.m1if03.classes;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
    private String nom;
    private String description;
    private String proprietaire;
    private List<String> membres;
    private GestionBillets billets;

    public Groupe(String nom, String description, String proprietaire, List<String> membres, GestionBillets billets) {
        this.nom = nom;
        this.description = description;
        this.proprietaire = proprietaire;
        this.membres = membres;
        this.billets = billets;
    }

    public Groupe() {
        this.nom = null;
        this.description = null;
        this.proprietaire = null;
        this.membres = new ArrayList<String> ();
        this.billets = new GestionBillets();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public List<String> getMembres() {
        return membres;
    }

    public void setMembres(List<String> membres) {
        this.membres = membres;
    }

    public GestionBillets getBillets() {
        return billets;
    }

    public void setBillets(GestionBillets billets) {
        this.billets = billets;
    }

    public void addBillet(Billet billet) {
        this.billets.add(billet);
    }

    public JSONObject serialize() {
        JSONObject json = new JSONObject();

        json.put("nom", this.getNom());
        json.put("description", this.getDescription());
        json.put("proprietaire", this.getProprietaire());

        for (String membres : this.getMembres()) {
            json.append("membres", membres);
        }

        for (Billet billet : this.getBillets().getListBillets()) {
            json.append("billets", billet.serialize());
        }

        return json;
    }

    public static Groupe unSerialize(String JSON) {
        JSONObject obj = new JSONObject(JSON);
        if (!obj.isNull("nom") && !obj.isNull("description") && !obj.isNull("proprietaire") && !obj.isNull("membres") && !obj.isNull("billets")) {
            List<Billet> billets = new ArrayList<>();
            List<String> membres = new ArrayList<>();

            for (Object ob : obj.getJSONArray("membres")) {
                String currentMembres = ob.toString();
                membres.add(currentMembres);
            }

            for (Object ob : obj.getJSONArray("billets")) {
                Billet currentBillet = Billet.unSerialize(ob.toString());
                billets.add(currentBillet);
            }

            return new Groupe(obj.getString("nom"), obj.getString("description"), obj.getString("proprietaire"), membres, new GestionBillets(billets));
        }
        return null;
    }
}
