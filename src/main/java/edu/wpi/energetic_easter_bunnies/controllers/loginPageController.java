package edu.wpi.energetic_easter_bunnies.controllers;

import static edu.wpi.energetic_easter_bunnies.entity.loginPage.verifyUser;

import edu.wpi.energetic_easter_bunnies.Main;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginPageController {
  private @FXML TextField usernameField;

  private @FXML PasswordField passwordField;

  @FXML Label invalidWarning;

  @FXML
  public void submitLogin(ActionEvent event) throws IOException {
    if (verifyUser(getUsername(), getPassword())) {
      Node node = (Node) event.getSource();

      FXMLLoader loader = new FXMLLoader();

      URL url = Main.class.getResource("view/defaultPage.fxml");
      if (url != null) {
        loader.setLocation(url);
        Parent newRoot = loader.load();

        try {
          node.getScene().setRoot(newRoot);
        } catch (NullPointerException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Path Doesn't Exist");
      }
    } else {
      invalidWarning.setVisible(true);
    }
  }

  private String getPassword() {
    return passwordField.getText();
  }

  private String getUsername() {
    return usernameField.getText();
  }
}
