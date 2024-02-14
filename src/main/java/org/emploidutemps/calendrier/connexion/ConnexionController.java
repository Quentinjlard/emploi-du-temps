package org.emploidutemps.calendrier.connexion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConnexionController {

    @FXML
    private Label connexionText;

    @FXML
    protected void onConnexionButtonClick() {
        connexionText.setText("OK!");
    }

}