package org.emploidutemps.calendrier;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AccueilController {

    public void Connection(ActionEvent event, String FXML )throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 500, 500);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();
    }
    public void ConnextionAction(ActionEvent event) throws IOException {

        Connection(event, "connexion.fxml");

    }

    public void JourEDT(ActionEvent event) throws IOException {

        String FXML = "EDT-Jour.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 500, 500);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();

    }

    public void SeamineEDT(ActionEvent event) throws IOException {

        String FXML = "EDT-Semaine.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 500, 500);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();

    }

    public void MoisEDT(ActionEvent event) throws IOException{

        String FXML = "EDT-Mois.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 500, 500);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();

    }

    public void FormationEDT(ActionEvent event) {
    }

    public void EnseignementEDT(ActionEvent event) {
    }

    public void SalleEDT(ActionEvent event) {
    }
}
