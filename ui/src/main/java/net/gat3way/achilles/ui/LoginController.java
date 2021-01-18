package net.gat3way.achilles.ui;

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

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import net.gat3way.achilles.crypt.VaultUtillity;
import net.gat3way.achilles.crypt.entity.Vault;

public class LoginController {

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private void onRegisterButtonPressed() throws IOException {
        App.setRoot("registration");
    }

    @FXML
    private void onLoginButtonPressed() throws IOException {
        Vault v;
        try {
            v = VaultUtillity.open(passwordTextField.getText());
            v.getProfileName();
        } catch (AEADBadTagException badTagException) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Decryption failure");
            alert.setHeaderText(
                    "The Application was not able to decrypt your personal vault due to bad user and password combination, please try again");
            //alert.setContentText("Are you sure you want to erase and create a new profile?");
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
            e.printStackTrace();
        }
        System.out.println("success");
    }
}
