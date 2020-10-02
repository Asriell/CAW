package fr.univlyon1.m1if.m1if03.classes;

import java.util.ArrayList;
import java.util.List;

public class GestionComm {
    private List<Commentaire> commentaires;
    public GestionComm() {
        this.commentaires = new ArrayList<>();
    }
    public void add(Commentaire commentaire) {
        this.commentaires.add(commentaire);
    }
  //  public Commentaire getCommentaire(Billet billet) { return commentaires.get(i); }

}
