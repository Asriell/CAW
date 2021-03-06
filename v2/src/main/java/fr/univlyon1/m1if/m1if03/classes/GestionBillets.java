package fr.univlyon1.m1if.m1if03.classes;

import java.util.ArrayList;
import java.util.List;

public class GestionBillets {
    private List<Billet> billets;
    public GestionBillets() {
        this.billets = new ArrayList<>();
    }
    public GestionBillets(List<Billet> billets) {
        this.billets = billets;
    }

    public void add(Billet billet) {
        this.billets.add(billet);
    }

    public Billet getBillet(int i) {
        return billets.get(i);
    }

    public List<Billet> getListBillets() {
        return this.billets;
    }

    public Billet getLastBillet() {
        if (billets.size() > 0)
            return this.getBillet(billets.size() -1);
        return null;
        //throw new IndexOutOfBoundsException("Erreur dans l'appel à la fonction getLastBillet");
    }
}
