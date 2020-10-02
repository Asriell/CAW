package fr.univlyon1.m1if.m1if03.classes;

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

}
