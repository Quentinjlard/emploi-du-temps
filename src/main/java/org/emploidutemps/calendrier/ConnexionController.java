package org.emploidutemps.calendrier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnexionController {

    @FXML
    private Label connexionText;
    @FXML
    private TextField TextFiledLogin;
    @FXML
    private TextField TextFiledPassword;

    @FXML
    protected void onConnexionButtonClick(ActionEvent event) throws IOException {

        boolean authentification = AuthentificationManager.authentifierUtilisateur(TextFiledLogin.getText(), TextFiledPassword.getText());

        if ( authentification ) {
            connexionText.setText("Authentification réussie.");
            System.out.println("Authentification réussie.");

            SessionManager.getInstance().setUtilisateurConnecte(true);

            Parent root = FXMLLoader.load(getClass().getResource("acceuil.fxml"));

            Scene scene = new Scene(root, 1000, 800);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Acceuil");
            stage.setScene(scene);
            stage.show();

        } else {
            connexionText.setText("Nom d'utilisateur ou mot de passe incorrect.");
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    public void OnConnxionBack(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("acceuil.fxml"));

        Scene scene = new Scene(root, 1000, 800);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Acceuil");
        stage.setScene(scene);
        stage.show();

    }
}