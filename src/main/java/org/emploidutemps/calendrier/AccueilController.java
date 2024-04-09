package org.emploidutemps.calendrier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class AccueilController {

    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.D) {
            // Ajoutez le code de déconnexion ici
            System.out.println("Déconnexion...");
        }
    }
    public void Connection(ActionEvent event, String FXML )throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(FXML));

        Scene scene = new Scene(root, 500, 500);

        scene.setOnKeyPressed(this::handleKeyPress);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(FXML);
        stage.setScene(scene);
        stage.show();

    }
    public void ConnextionAction(ActionEvent event) throws IOException {

        Connection(event, "connexion.fxml");

    }

    public void JourEDT(ActionEvent event) throws IOException {

    }

    private static int[] getPositionForCourse(LocalDateTime courseDateTime) {
        int column = (courseDateTime.getDayOfWeek()).getValue();
        int row = ((courseDateTime.getHour() - 8) * 60 + courseDateTime.getMinute()) / 30 + 1;
        return new int[]{column, row+1};
    }

    private static int calculateNumberOfRows(LocalDateTime startDate, LocalDateTime endDate) {
        long diffInMinutes = Duration.between(startDate, endDate).toMinutes();
        int numberOfRows = (int) (diffInMinutes / 30);
        return numberOfRows;
    }

    public void NextWeek(ActionEvent event) throws IOException {
        LocalDate NextWeek = SessionManager.getInstance().getSelectionDate().plusWeeks(1);;
        SessionManager.getInstance().setSelectionDate(NextWeek);
        if(SessionManager.getInstance().getLookingFor() == 0) {
            SemaineEDT(event);
        } else if(SessionManager.getInstance().getLookingFor() == 1) {
            FormationEDT_SHOW(event);
        }
    }

    public void LastWeek(ActionEvent event) throws IOException {
        LocalDate LastWeek = SessionManager.getInstance().getSelectionDate().minusWeeks(1);;
        SessionManager.getInstance().setSelectionDate(LastWeek);
        if(SessionManager.getInstance().getLookingFor() == 0) {
            SemaineEDT(event);
        } else if(SessionManager.getInstance().getLookingFor() == 1) {
            FormationEDT_SHOW(event);
        }
    }

    @FXML
    public void SemaineEDT(ActionEvent event) throws IOException {
        if (SessionManager.getInstance().isUtilisateurConnecte()) {
            SessionManager.getInstance().setLookingFor(0);
            Parent root = FXMLLoader.load(getClass().getResource("EDT-Semaine.fxml"));
            Scene scene = new Scene(root, 1000, 810);

            Scene currentScene;
            if (event.getSource() instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) event.getSource();
                ContextMenu menu = menuItem.getParentPopup();
                currentScene = menu.getOwnerNode().getScene();
            } else if (event.getSource() instanceof Node) {
                Node node = (Node) event.getSource();
                currentScene = node.getScene();
            } else {
                // Gérer le cas où l'origine de l'événement n'est ni un MenuItem ni un Node
                return;
            }

            Stage stage = (Stage) currentScene.getWindow();

            //Obtenir les dates de la semaine
            LocalDate today = SessionManager.getInstance().getSelectionDate();

            LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM");
            Label lundi = (Label) scene.lookup("#lundi");
            lundi.setText("Lundi " + monday.format(formatter));
            Label mardi = (Label) scene.lookup("#mardi");
            mardi.setText("Mardi " + monday.plusDays(1).format(formatter));
            Label mercredi = (Label) scene.lookup("#mercredi");
            mercredi.setText("Mercredi " + monday.plusDays(2).format(formatter));
            Label jeudi = (Label) scene.lookup("#jeudi");
            jeudi.setText("Jeudi " + monday.plusDays(3).format(formatter));
            Label vendredi = (Label) scene.lookup("#vendredi");
            vendredi.setText("Vendredi " + monday.plusDays(4).format(formatter));

            LocalDateTime mondayAt8AM = monday.atTime(8, 0);
            LocalDate friday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
            LocalDateTime fridayAt8PM = friday.atTime(20, 0);
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String startDate = formatter.format(mondayAt8AM);
            String endDate = formatter.format(fridayAt8PM);

            String query = "";
            if(SessionManager.getInstance().isProfesseur()) {
                query = "SELECT * FROM creneaux WHERE Enseignant = ? AND strftime('%Y-%m-%d %H:%M:%S', DTSTART) BETWEEN ? AND ?";
            } else {
                query = "SELECT * FROM creneaux WHERE Promotion = ? AND strftime('%Y-%m-%d %H:%M:%S', DTSTART) BETWEEN ? AND ?";
            }
            String JDBC_URL = "jdbc:sqlite:"+System.getProperty("user.dir") + "/BD";

            GridPane gridPane = (GridPane) scene.lookup("#grid");

            try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                if(SessionManager.getInstance().isProfesseur()) { preparedStatement.setString(1, SessionManager.getInstance().getNom()); }
                else { preparedStatement.setString(1, SessionManager.getInstance().getPromotion()); }
                preparedStatement.setString(2, startDate);
                preparedStatement.setString(3, endDate);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int uid = resultSet.getInt("UID");
                    String matiere = resultSet.getString("Matiere");
                    String enseignant = resultSet.getString("Enseignant");
                    String promotion = resultSet.getString("Promotion");
                    String salle = resultSet.getString("Salle");
                    String type = resultSet.getString("Type");
                    String dtstart = resultSet.getString("DTSTART");
                    String dtend = resultSet.getString("DTEND");
                    /*System.out.println("UID: " + uid + ", Type: " + type + ", Matiere: " + matiere + " Enseignant: " + enseignant
                            + " Promotion: " + promotion + " DTSTART: " + dtstart + " DTEND: " + dtend);*/

                    LocalDateTime course_startTime = LocalDateTime.parse(dtstart, formatter);
                    LocalDateTime course_endTime = LocalDateTime.parse(dtend, formatter);
                    int[] position = getPositionForCourse(course_startTime);
                    int column = position[0]; // Colonne
                    int row = position[1]; // Ligne
                    System.out.println("column: " + column);
                    System.out.println("row: " + row);
                    if(row == -1) continue;
                    int spanrow = calculateNumberOfRows(course_startTime, course_endTime);
                    System.out.println("spanrow: " + spanrow);

                    Label label = new Label("Matière: " + matiere + "\nEnseignant: " + enseignant + "\nPromotion: " + promotion
                            + "\nSalle: " + salle  + "\nType: " + type);
                    label.getStyleClass().add("date-label");
                    label.setMaxHeight(Double.MAX_VALUE);
                    label.setMaxWidth(Double.MAX_VALUE);
                    gridPane.add(label, column, row);
                    gridPane.setRowSpan(label, spanrow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            stage.setTitle("EDT-Semaine.fxml");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void MoisEDT(ActionEvent event) throws IOException{

    }

    public void FormationEDT(ActionEvent event) throws IOException {
        if (SessionManager.getInstance().isUtilisateurConnecte()) {
            Parent root = FXMLLoader.load(getClass().getResource("formation.fxml"));
            Scene scene = new Scene(root, 1000, 810);

            MenuItem menuItem = (MenuItem) event.getSource();
            ContextMenu menu = menuItem.getParentPopup();
            Node node = menu.getOwnerNode();
            Stage stage = (Stage) node.getScene().getWindow();

            stage.setTitle("EDT-Semaine.fxml");
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private TextField formationtextfield;
    @FXML
    public void SelectFormation(ActionEvent event) throws IOException {
        SessionManager.getInstance().setSelectionPromotion(formationtextfield.getText());
        SessionManager.getInstance().setLookingFor(1);
        FormationEDT_SHOW(event);
    }

    @FXML
    public void FormationEDT_SHOW(ActionEvent event) throws IOException {
        if (SessionManager.getInstance().isUtilisateurConnecte()) {
            Parent root = FXMLLoader.load(getClass().getResource("EDT-Semaine.fxml"));
            Scene scene = new Scene(root, 1000, 810);

            Scene currentScene;
            if (event.getSource() instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) event.getSource();
                ContextMenu menu = menuItem.getParentPopup();
                currentScene = menu.getOwnerNode().getScene();
            } else if (event.getSource() instanceof Node) {
                Node node = (Node) event.getSource();
                currentScene = node.getScene();
            } else {
                return;
            }

            Stage stage = (Stage) currentScene.getWindow();

            //Obtenir les dates de la semaine
            LocalDate today = SessionManager.getInstance().getSelectionDate();

            LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM");
            Label lundi = (Label) scene.lookup("#lundi");
            lundi.setText("Lundi " + monday.format(formatter));
            Label mardi = (Label) scene.lookup("#mardi");
            mardi.setText("Mardi " + monday.plusDays(1).format(formatter));
            Label mercredi = (Label) scene.lookup("#mercredi");
            mercredi.setText("Mercredi " + monday.plusDays(2).format(formatter));
            Label jeudi = (Label) scene.lookup("#jeudi");
            jeudi.setText("Jeudi " + monday.plusDays(3).format(formatter));
            Label vendredi = (Label) scene.lookup("#vendredi");
            vendredi.setText("Vendredi " + monday.plusDays(4).format(formatter));

            LocalDateTime mondayAt8AM = monday.atTime(8, 0);
            LocalDate friday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
            LocalDateTime fridayAt8PM = friday.atTime(20, 0);
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String startDate = formatter.format(mondayAt8AM);
            String endDate = formatter.format(fridayAt8PM);

            String query = "SELECT * FROM creneaux WHERE Promotion = ? AND strftime('%Y-%m-%d %H:%M:%S', DTSTART) BETWEEN ? AND ?";
            String JDBC_URL = "jdbc:sqlite:"+System.getProperty("user.dir") + "/BD";

            GridPane gridPane = (GridPane) scene.lookup("#grid");

            try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, SessionManager.getInstance().getSelectionPromotion());
                preparedStatement.setString(2, startDate);
                preparedStatement.setString(3, endDate);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int uid = resultSet.getInt("UID");
                    String matiere = resultSet.getString("Matiere");
                    String enseignant = resultSet.getString("Enseignant");
                    String promotion = resultSet.getString("Promotion");
                    String salle = resultSet.getString("Salle");
                    String type = resultSet.getString("Type");
                    String dtstart = resultSet.getString("DTSTART");
                    String dtend = resultSet.getString("DTEND");
                    /*System.out.println("UID: " + uid + ", Type: " + type + ", Matiere: " + matiere + " Enseignant: " + enseignant
                            + " Promotion: " + promotion + " DTSTART: " + dtstart + " DTEND: " + dtend);*/

                    LocalDateTime course_startTime = LocalDateTime.parse(dtstart, formatter);
                    LocalDateTime course_endTime = LocalDateTime.parse(dtend, formatter);
                    int[] position = getPositionForCourse(course_startTime);
                    int column = position[0]; // Colonne
                    int row = position[1]; // Ligne
                    System.out.println("column: " + column);
                    System.out.println("row: " + row);
                    if(row == -1) continue;
                    int spanrow = calculateNumberOfRows(course_startTime, course_endTime);
                    System.out.println("spanrow: " + spanrow);

                    Label label = new Label("Matière: " + matiere + "\nEnseignant: " + enseignant + "\nPromotion: " + promotion
                            + "\nSalle: " + salle  + "\nType: " + type);
                    label.getStyleClass().add("date-label");
                    label.setMaxHeight(Double.MAX_VALUE);
                    label.setMaxWidth(Double.MAX_VALUE);
                    gridPane.add(label, column, row);
                    gridPane.setRowSpan(label, spanrow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            stage.setTitle("EDT-Semaine.fxml");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void EnseignementEDT(ActionEvent event) throws IOException {
        if (SessionManager.getInstance().isUtilisateurConnecte()) {
            String FXML = "EDT-Semaine.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(FXML));

            Scene scene = new Scene(root, 1000, 810);

            MenuItem menuItem = (MenuItem) event.getSource();
            ContextMenu menu = menuItem.getParentPopup();
            Node node = menu.getOwnerNode();
            Stage stage = (Stage) node.getScene().getWindow();

            stage.setTitle(FXML);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void SalleEDT(ActionEvent event) throws IOException {
        if (SessionManager.getInstance().isUtilisateurConnecte()) {
            String FXML = "EDT-Semaine.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(FXML));

            Scene scene = new Scene(root, 1000, 810);

            MenuItem menuItem = (MenuItem) event.getSource();
            ContextMenu menu = menuItem.getParentPopup();
            Node node = menu.getOwnerNode();
            Stage stage = (Stage) node.getScene().getWindow();

            stage.setTitle(FXML);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void Rerservation(ActionEvent event) throws IOException {
        if (SessionManager.getInstance().isUtilisateurConnecte() && SessionManager.getInstance().isProfesseur()) {
            String FXML = "ReservationSalle.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(FXML));

            Scene scene = new Scene(root, 1000, 810);

            MenuItem menuItem = (MenuItem) event.getSource();
            ContextMenu menu = menuItem.getParentPopup();
            Node node = menu.getOwnerNode();
            Stage stage = (Stage) node.getScene().getWindow();

            stage.setTitle(FXML);
            stage.setScene(scene);
            stage.show();
        }
    }
}
