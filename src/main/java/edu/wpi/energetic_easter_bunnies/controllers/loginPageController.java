package edu.wpi.energetic_easter_bunnies.controllers;

import static edu.wpi.energetic_easter_bunnies.RSAEncryption.validatePassword;

import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.database.daos.AccountDAOImpl;
import edu.wpi.energetic_easter_bunnies.database.daos.DAO;
import edu.wpi.energetic_easter_bunnies.entity.accounts.Account;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class loginPageController {
  private @FXML TextField usernameField;

  private @FXML PasswordField passwordField;

  @FXML Label invalidWarning;

  @FXML
  public void submitLogin(ActionEvent event) throws IOException {
    if (verifyUser()) {
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

  private boolean verifyUser() {
    /*try {
      DAO<Account> accountDAO = new AccountDAOImpl();
      Account account = accountDAO.get(getUsername());
      if (!validatePassword(getPassword(), account.getPasswordHash())) {
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } catch (NullPointerException e) {
      e.printStackTrace();
      return false;
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
      return false;
    } catch (BadPaddingException e) {
      e.printStackTrace();
      return false;
    }*/
    return true;
  }
}
