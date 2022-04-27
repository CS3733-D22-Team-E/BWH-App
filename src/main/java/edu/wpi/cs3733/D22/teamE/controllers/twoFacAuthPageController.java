package edu.wpi.cs3733.D22.teamE.controllers;

import static edu.wpi.cs3733.D22.teamE.controllers.loginPageController.compareCodes;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class twoFacAuthPageController implements Initializable {

  private @FXML JFXTextField authenticationField;

  @FXML Label invalidWarning;

  DAOSystem db;

  @FXML
  public void twoFAButton(ActionEvent event) {
    if (compareCodes(getAuthenticationCode())) {
      pageControl.loadPage("BasePage.fxml", (Stage) authenticationField.getScene().getWindow());

    } else {
      invalidWarning.setVisible(true);
    }
  }

  private String getAuthenticationCode() {
    return authenticationField.getText();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    RequiredFieldValidator validator = new RequiredFieldValidator();
    validator.setMessage("Input Required");
    try {
      db = new DAOSystem();
      authenticationField.getValidators().add(validator);
      authenticationField
          .focusedProperty()
          .addListener(
              (o, oldVal, newVal) -> {
                if (!newVal) authenticationField.validate();
              });

      invalidWarning.setVisible(false);
      authenticationField.setOnKeyReleased(
          event -> {
            if (event.getCode() == KeyCode.ENTER) {
              if (authenticationField.getText().isEmpty()
                  || authenticationField.getText().isBlank()) authenticationField.requestFocus();
              else twoFAButton(new ActionEvent());
            }
          });

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
