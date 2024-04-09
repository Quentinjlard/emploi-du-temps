package org.emploidutemps.calendrier;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private static String formatDateTime(String dateTimeString) {
        if (dateTimeString.length() == 8) dateTimeString += "T000000Z";
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, inputFormatter).plusHours(2);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(outputFormatter);
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
                query = "CREATE TABLE IF NOT EXISTS Utilisateurs (" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "NomUtilisateur TEXT NOT NULL,"
                        + "MotDePasse TEXT NOT NULL," + "Promotion TEXT," + "Nom TEXT NOT NULL," + "Professeur BOOLEAN NOT NULL);";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();

                query = "INSERT INTO Utilisateurs (NomUtilisateur, MotDePasse, Promotion, Nom, Professeur) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(query);
                insertStatement.setString(1, (String) "cecillon.noe");
                insertStatement.setString(2, (String) "motdepasse1");
                insertStatement.setString(3, (String) "");
                insertStatement.setString(4, (String) "CECILLON Noe");
                insertStatement.setBoolean(5, true);
                insertStatement.executeUpdate();

                query = "INSERT INTO Utilisateurs (NomUtilisateur, MotDePasse, Promotion, Nom, Professeur) VALUES (?, ?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(query);
                insertStatement.setString(1, (String) "test");
                insertStatement.setString(2, (String) "test");
                insertStatement.setString(3, (String) "M1 INTELLIGENCE ARTIFICIELLE (IA)");
                insertStatement.setString(4, (String) "TETART Serena");
                insertStatement.setBoolean(5, false);
                insertStatement.executeUpdate();

                query = "DROP TABLE IF EXISTS creneaux";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                query = "CREATE TABLE IF NOT EXISTS creneaux (" + "UID INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "Type text," + "Matiere text," + "Salle text,"
                        + "Enseignant text," + "Promotion text," + "DTSTART text NOT NULL,"
                        + "Memo text," + "DTEND text NOT NULL);";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();

                //Ajout des informations
                File[] files = new File(System.getProperty("user.dir")+"/EDT/").listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (!file.isDirectory()) {
                            JSONArray test = Parse(file.getAbsolutePath());
                            for (int i = 0; i < test.size(); i++) {
                                JSONObject jsonObject = (JSONObject) test.get(i);
                                String dtstart = formatDateTime((String) jsonObject.get("DTSTART"));
                                String dtend = formatDateTime((String) jsonObject.get("DTEND"));
                                query = "INSERT INTO creneaux (Type, Matiere, Salle, Enseignant, Promotion, DTSTART, Memo, DTEND) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                                insertStatement = connection.prepareStatement(query);
                                insertStatement.setString(1, (String) jsonObject.get("Type"));
                                insertStatement.setString(2, (String) jsonObject.get("Matiere"));
                                insertStatement.setString(3, (String) jsonObject.get("Salle"));
                                String enseignant = null;
                                if((String) jsonObject.get("Enseignant") != null) {
                                    enseignant = ((String) jsonObject.get("Enseignant")).replaceAll("\\s+", " ").trim();
                                }
                                insertStatement.setString(4, enseignant);
                                insertStatement.setString(5, (String) jsonObject.get("Promotion"));
                                insertStatement.setString(6, dtstart);
                                insertStatement.setString(7, (String) jsonObject.get("Memo"));
                                insertStatement.setString(8, dtend);
                                insertStatement.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }
        launch();
    }
}