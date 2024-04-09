package org.emploidutemps.calendrier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthentificationManager {

    private static final String JDBC_URL = "jdbc:sqlite:"+System.getProperty("user.dir") + "/BD";

    public static void main(String[] args) {
        String nomUtilisateurSaisi = "jean.dupont";
        String motDePasseSaisi = "motdepasse1";

        if (authentifierUtilisateur(nomUtilisateurSaisi, motDePasseSaisi)) {
            System.out.println("Authentification réussie.");
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    public static boolean authentifierUtilisateur(String nomUtilisateur, String motDePasse) {
        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
                String query = "SELECT * FROM Utilisateurs WHERE NomUtilisateur = ? AND MotDePasse = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, nomUtilisateur);
                    preparedStatement.setString(2, motDePasse);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if(resultSet.next()) {
                            boolean prof = resultSet.getBoolean("Professeur");
                            String promotion = resultSet.getString("Promotion");
                            String nom = resultSet.getString("Nom");
                            SessionManager.getInstance().setUtilisateurConnecte(true, prof, promotion, nom);
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
