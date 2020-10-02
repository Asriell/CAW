package fr.univlyon1.m1if.m1if03.classes;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Billet {
    private String titre, contenu, auteur, groupe;
    private List<Commentaire> commentaires;


    public Billet() {
        this.titre = "Rien";
        this.contenu = "Vide";
        this.auteur = "Personne";
        this.groupe = "Aucun";
        this.commentaires = null;
    }

    public Billet(String titre, String contenu, String auteur, String groupe, List<Commentaire> comm) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.groupe = groupe;
        this.commentaires = comm;

    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getGroupe() { return groupe; }

    public void setGroupe(String groupe) { this.groupe = groupe;}

    public void addCommentaire(Commentaire c) {
        if (this.commentaires == null ) {
            this.commentaires = new ArrayList <>();
        }
        this.commentaires.add(c);
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public JSONObject serialize() {
        JSONObject json = new JSONObject();

        json.put("titre", this.titre);
        json.put("contenu", this.getContenu());
        json.put("auteur", this.getAuteur());
        json.put("groupe", this.getGroupe());

        for (Commentaire com : this.getCommentaires()) {
            json.append("commentaires", com.serialize());
        }

        return json;
    }

    public static Billet unSerialize(String JSON) {
        JSONObject obj = new JSONObject(JSON);
        if (!obj.isNull("titre") && !obj.isNull("contenu") && !obj.isNull("auteur") && !obj.isNull("groupe")) {

            List<Commentaire> comm = new ArrayList<>();;

            if(!obj.isNull("commentaires")) {
                for (Object ob : obj.getJSONArray("commentaires")) {
                    Commentaire currentComment = Commentaire.unSerialize(ob.toString());
                    comm.add(currentComment);
                }
            }

            return new Billet(obj.getString("titre"), obj.getString("contenu"), obj.getString("auteur"), obj.getString("groupe"), comm);
        }
        return null;
    }

}
