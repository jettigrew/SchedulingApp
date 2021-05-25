package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.util.Optional;

public class AlertGenerator {
    public void createAlert(String alertType, String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.valueOf(alertType));
        alert.setTitle(alertType);
        alert.setContentText(alertMessage);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public boolean getConfirmationAlert(String contentText) {
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("CONFIRMATION");
        alert2.setContentText(contentText);
        alert2.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> input2 = alert2.showAndWait();
        return input2.isPresent() && input2.get() == ButtonType.OK;
    }
}
