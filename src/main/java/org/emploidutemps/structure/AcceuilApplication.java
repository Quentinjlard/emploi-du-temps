package org.emploidutemps.structure;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.emploidutemps.EmploiDuTemps.ConnexionApplication;

import java.io.IOException;

public class AcceuilApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ConnexionApplication.class.getResource("acceuil.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
    }
}
