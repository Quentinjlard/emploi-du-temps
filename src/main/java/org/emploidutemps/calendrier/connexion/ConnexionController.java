package org.emploidutemps.calendrier.connexion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConnexionController {

    @FXML
    private Label connexionText;
    @FXML
    private TextField TextFiledLogin;
    @FXML
    private TextField TextFiledPassword;

    @FXML
    protected void onConnexionButtonClick() {

        String id = TextFiledLogin.getText();
        String pswd = TextFiledPassword.getText();
        connexionText.setText(id + " " + pswd);
    }

}