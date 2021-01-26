package net.gat3way.achilles.ui.frontend.startup.control;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import net.gat3way.achilles.crypt.VaultUtillity;
import net.gat3way.achilles.crypt.entity.Vault;
import net.gat3way.achilles.ui.App;

public class LoginController {
    private static final Logger LOG = LogManager.getLogger(LoginController.class);

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private void onRegisterButtonPressed() throws IOException {
        App.setRoot("registration");
    }

    @FXML
    private void onLoginButtonPressed() throws IOException {
        Vault vault;
        try {
            vault = VaultUtillity.open(passwordTextField.getText());
            vault.getProfileName();
        } catch (AEADBadTagException badTagException) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Decryption failure");
            alert.setHeaderText(
                    "The Application was not able to decrypt your personal vault due to bad user and password combination, please try again");
            alert.initModality(Modality.APPLICATION_MODAL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                App.setRoot("login");
                return;
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
                | ClassNotFoundException | InvalidKeySpecException | IOException e) {
            // TODO Auto-generated catch block
            LOG.error("Error ");
        }
        App.setRoot("frontend/launchpad/launchpad");
    }
}
