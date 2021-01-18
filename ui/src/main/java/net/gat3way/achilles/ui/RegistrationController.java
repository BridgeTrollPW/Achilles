package net.gat3way.achilles.ui;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import net.gat3way.achilles.crypt.VaultUtillity;
import net.gat3way.achilles.crypt.entity.Vault;

public class RegistrationController {
    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private void onBackToLoginButtonPressed() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void onRegistrationButtonPressed() throws IOException {
        if (new File("achilles.vault").exists()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(
                    "A vault with your personal accounts already exists! If you register a new account you will overwrite your current settings and loose all your local data.");
            alert.setContentText("Are you sure you want to erase and create a new profile?");
            alert.initModality(Modality.APPLICATION_MODAL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                App.setRoot("login");
                return;
            }
        }
        App.setRoot("loading");
        new Thread(() -> {
            // TODO Auto-generated method stub
            Vault vault = new Vault();
            vault.setProfileName(userTextField.getText());
            try {
                VaultUtillity.create(passwordTextField.getText(), vault);
            } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
                    | InvalidAlgorithmParameterException | IllegalBlockSizeException | IOException
                    | BadPaddingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("done");
            try {
                App.setRoot("login");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
    }
}