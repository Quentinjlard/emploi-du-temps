package org.emploidutemps.calendrier;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.emploidutemps.calendrier.Parser.Parse;


public class ConnexionApplication extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ConnexionApplication.class.getResource("acceuil.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Acceuil");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        //Refaire base de donnée et ajouter les cours parsés
        String JDBC_URL = "jdbc:sqlite:"+System.getProperty("user.dir") + "/BD";
        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
                String query = "DROP TABLE IF EXISTS Utilisateurs";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                query = "CREATE TABLE IF NOT EXISTS Utilisateurs (" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "NomUtilisateur TEXT NOT NULL," + "MotDePasse TEXT NOT NULL);";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();

                query = "INSERT INTO Utilisateurs (NomUtilisateur, MotDePasse) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(query);
                insertStatement.setString(1, (String) "jean.dupont");
                insertStatement.setString(2, (String) "motdepasse1");
                insertStatement.executeUpdate();

                query = "DROP TABLE IF EXISTS creneaux";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                query = "CREATE TABLE IF NOT EXISTS creneaux (" + "UID INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "Type text," + "Matiere text," + "Salle text,"
                        + "Enseignant text," + "Promotion text," + "DTSTART DATETIME NOT NULL,"
                        + "Memo text," + "DTEND DATETIME NOT NULL);";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();

                //Ajout des informations
                JSONArray test = Parse(System.getProperty("user.dir")+"/EDT/Enseignant/EDT-Cecillon-Noe.txt");
                for (int i = 0; i < test.size(); i++) {
                    JSONObject jsonObject = (JSONObject) test.get(i);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    try {
                        String dateString = (String) jsonObject.get("DTSTART");
                        java.util.Date utilDate = dateFormat.parse(dateString);
                        Timestamp date_start = new Timestamp(utilDate.getTime());

                        dateString = (String) jsonObject.get("DTEND");
                        utilDate = dateFormat.parse(dateString);
                        Timestamp date_end = new Timestamp(utilDate.getTime());

                        query = "INSERT INTO creneaux (Type, Matiere, Salle, Enseignant, Promotion, DTSTART, Memo, DTEND) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        insertStatement = connection.prepareStatement(query);
                        insertStatement.setString(1, (String) jsonObject.get("Type"));
                        insertStatement.setString(2, (String) jsonObject.get("Matiere"));
                        insertStatement.setString(3, (String) jsonObject.get("Salle"));
                        insertStatement.setString(4, (String) jsonObject.get("Enseignant"));
                        insertStatement.setString(5, (String) jsonObject.get("Promotion"));
                        insertStatement.setTimestamp(6, date_start);
                        insertStatement.setString(7, (String) jsonObject.get("Memo"));
                        insertStatement.setTimestamp(8, date_end);
                        insertStatement.executeUpdate();
                    } catch (ParseException e) { throw new RuntimeException(e); }
                    break;
                }
            }
        } catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }
        launch();
    }
}