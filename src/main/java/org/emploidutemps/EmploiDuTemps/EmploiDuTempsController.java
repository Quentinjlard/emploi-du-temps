package org.emploidutemps.EmploiDuTemps;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class EmploiDuTempsController {

    @FXML
    private ComboBox<String> modeComboBox;

    @FXML
    private VBox dayWeekMonthContent;

    public void initialize() {
        // Ajouter les éléments au ComboBox
        modeComboBox.getItems().addAll("Jour", "Semaine", "Mois");
        modeComboBox.getSelectionModel().selectFirst(); // Sélectionner le premier élément par défaut
    }

    @FXML
    private void onPreviousClicked() {
        // Code à exécuter lorsque le bouton précédent est cliqué
    }

    @FXML
    private void onBackClicked() {
        // Code à exécuter lorsque le bouton "Retour" est cliqué
    }
}
