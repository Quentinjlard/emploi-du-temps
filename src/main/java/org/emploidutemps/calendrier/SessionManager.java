package org.emploidutemps.calendrier;

public class SessionManager {
    private static SessionManager instance;
    private boolean utilisateurConnecte;
    private boolean professeur;

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

    public void setUtilisateurConnecte(boolean connecte) {
        this.utilisateurConnecte = connecte;
        this.professeur = true;
    }

    public boolean isProfesseur() {
        return professeur;
    }

    public void setProfesseur(boolean prof) {
        this.professeur = prof;
    }
}