package org.emploidutemps.calendrier;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class CalendrierController {
    public void SemainePrecedante(ActionEvent event) {
    }

    public void SemaineSuivante(ActionEvent event) {
    }

    public void forwardOneWeek(ActionEvent event) {
    }

    public void backOneWeek(ActionEvent event) {
    }

    public void JourEDT(ActionEvent event) throws IOException {
        String FXML = "EDT-Jour.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 1000, 800);

        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu menu = menuItem.getParentPopup();
        Node node = menu.getOwnerNode();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setResizable(false);

        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();
    }


    public void SeamineEDT(ActionEvent event) throws IOException {

        String FXML = "EDT-Semaine.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 1000, 800);

        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu menu = menuItem.getParentPopup();
        Node node = menu.getOwnerNode();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();

    }

    public void MoisEDT(ActionEvent event) throws IOException{

        String FXML = "EDT-Mois.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 800, 600);

        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu menu = menuItem.getParentPopup();
        Node node = menu.getOwnerNode();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();

    }

    public void FormationEDT(ActionEvent event) throws IOException {
        String FXML = "EDT-Semaine.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 800, 600);

        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu menu = menuItem.getParentPopup();
        Node node = menu.getOwnerNode();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();
    }

    public void EnseignementEDT(ActionEvent event) throws IOException {
        String FXML = "EDT-Semaine.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 800, 600);

        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu menu = menuItem.getParentPopup();
        Node node = menu.getOwnerNode();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();
    }

    public void SalleEDT(ActionEvent event) throws IOException {
        String FXML = "EDT-Semaine.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 800, 600);

        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu menu = menuItem.getParentPopup();
        Node node = menu.getOwnerNode();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();
    }

    public void Rerservation(ActionEvent event) throws IOException {
        String FXML = "ReservationSalle.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 800, 600);

        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu menu = menuItem.getParentPopup();
        Node node = menu.getOwnerNode();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();
    }
}
