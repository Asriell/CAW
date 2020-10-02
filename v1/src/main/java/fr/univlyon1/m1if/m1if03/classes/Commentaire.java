package fr.univlyon1.m1if.m1if03.classes;

public class Commentaire {
    private String titre, contenu, auteur;
    private Billet billet;

    public Commentaire() {
        this.titre = "Rien";
        this.contenu = "Vide";
        this.auteur = "Personne";
        this.billet = new Billet();
    }

    public Commentaire(String titre, String contenu, String auteur, Billet billet) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.billet = billet;
    }

    public String getTitre() { return titre; }

    public void setTitre(String titre) { this.titre = titre; }

    public String getContenu() { return contenu; }

    public void setContenu(String contenu) { this.contenu = contenu; }

    public String getAuteur() { return auteur; }

    public void setAuteur(String auteur) { this.auteur = auteur; }

    public Billet getBillet() { return billet; }

    public void setBillet(Billet billet) { this.billet = billet; }
}
