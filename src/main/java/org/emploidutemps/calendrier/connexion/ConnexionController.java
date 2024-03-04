package org.emploidutemps.calendrier.connexion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.emploidutemps.calendrier.api.AuthentificationManager;

public class ConnexionController {

    @FXML
    private Label connexionText;
    @FXML
    private TextField TextFiledLogin;
    @FXML
    private TextField TextFiledPassword;

    @FXML
    protected void onConnexionButtonClick() {

        boolean authentification = AuthentificationManager.authentifierUtilisateur(TextFiledLogin.getText(),
                                                                                    TextFiledPassword.getText());

        if ( authentification ) {
            connexionText.setText("Authentification réussie.");
            System.out.println("Authentification réussie.");
        } else {
            connexionText.setText("Nom d'utilisateur ou mot de passe incorrect.");
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

}