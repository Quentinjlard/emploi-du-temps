package org.emploidutemps.calendrier;

import java.time.LocalDate;

public class SessionManager {
    private static SessionManager instance;
    private boolean utilisateurConnecte;
    private boolean professeur;
    private String promotion;
    private String nom;
    private LocalDate selectionDate;
    private String selectionPromotion;
    private int lookingFor;

    private SessionManager() {
        // Constructeur privé pour empêcher l'instanciation directe
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public boolean isUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(boolean Connecte, boolean Professeur, String Promotion, String Nom) {
        this.utilisateurConnecte = Connecte;
        this.promotion = Promotion;
        this.professeur = Professeur;
        this.nom = Nom;
        this.selectionDate = LocalDate.now();
        this.lookingFor = 0;
    }

    public boolean isProfesseur() { return professeur; }

    public String getPromotion() { return promotion; }

    public String getNom() { return nom; }

    public void setSelectionDate(LocalDate newDate) { this.selectionDate = newDate; }
    public LocalDate getSelectionDate() { return selectionDate; }
    public void setSelectionPromotion(String selectpromo) { this.selectionPromotion = selectpromo; }
    public String getSelectionPromotion() { return selectionPromotion; }
    public void setLookingFor(int look) { this.lookingFor = look; }
    public int getLookingFor() { return lookingFor; }
}