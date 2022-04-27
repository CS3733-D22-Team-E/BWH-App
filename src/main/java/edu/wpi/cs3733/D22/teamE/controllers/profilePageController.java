package edu.wpi.cs3733.D22.teamE.controllers;

import static edu.wpi.cs3733.D22.teamE.pageControl.isLightMode;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import edu.wpi.cs3733.D22.teamE.Main;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.RSAEncryption;
import edu.wpi.cs3733.D22.teamE.customUI.ServiceRequestButtonListCell;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.ProfilePictureManager;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.entity.EntityInterface;
import edu.wpi.cs3733.D22.teamE.entity.RequestInterface;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.entity.passwordSettingRequest;
import edu.wpi.cs3733.D22.teamE.themeControl;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class profilePageController implements Initializable {

  @FXML VBox mainPane;
  @FXML Circle photoCirc;
  @FXML Label nameLabel;
  @FXML Label statusLabel;
  @FXML Label idLabel;
  @FXML JFXComboBox database;
  // @FXML JFXComboBox colorScheme;
  @FXML TextField newPassword;
  @FXML TextField confirmPassword;
  @FXML TableColumn floor;
  @FXML TableColumn status;
  @FXML TableColumn room;
  @FXML TableColumn requestType;
  @FXML ImageView imgMode;

  private Account account;
  private Employee employee;
  private DAOSystem db;
  public JFXListView<EntityInterface> reqList;
  @FXML Button resetPassword;
  private URL url;
  private ResourceBundle rb;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      db = new DAOSystem();
      account = AccountsManager.getInstance().getAccount();
      employee = AccountsManager.getInstance().getEmployee();
      Image image;
      try {
        InputStream is = ProfilePictureManager.getPersonalPicture(employee);
        image = new Image(is);
      } catch (Exception e) {
        URL url = Main.class.getResource("view/icons/profilepic.png");
        assert url != null;
        InputStream is = url.openStream();
        image = new Image(is);
      }
      EventHandler<MouseEvent> circleEH = e -> changePic(e);
      EventHandler<MouseEvent> circleEnter = e -> hover(e);
      EventHandler<MouseEvent> circleLeave = e -> leave(e);
      photoCirc.addEventHandler(MouseEvent.MOUSE_CLICKED, circleEH);
      photoCirc.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, circleEnter);
      photoCirc.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, circleLeave);
      Tooltip t = new Tooltip("Click to Change Profile Picture");
      t.setShowDelay(Duration.seconds(0.1));
      Tooltip.install(photoCirc, t);
      photoCirc.setFill(new ImagePattern(image));
      nameLabel.setText(account.getFirstName() + " " + account.getLastName());
      statusLabel.setText(account.getPosition());
      idLabel.setText(employee.getEmployeeID());
      database.getItems().addAll("Embeded", "Client");
      EventHandler<Event> databaseChange = e -> changeDBConnection(e);
      database.setOnAction(databaseChange);
      // colorScheme.getSelectionModel().selectFirst();
      // colorScheme.getItems().addAll("Bright", "Dark");

      int count = 0;
      List<EntityInterface> l = new ArrayList<>();
      l.addAll(DAOSystemSingleton.INSTANCE.getSystem().getAllServiceRequests());
      ObservableList<EntityInterface> data = FXCollections.observableArrayList();
      for (EntityInterface e : l) {
        if (e instanceof RequestInterface) {
          RequestInterface r = (RequestInterface) e;
          if (Objects.equals(employee.getEmployeeID(), "admin")) {
            data.add(r);
            count++;
          } else if (Objects.equals(employee.getEmployeeID(), r.getStaffAssignee())) {
            data.add(r);
            count++;
          }
        }
      }
      reqList.setItems(data);
      reqList.setCellFactory(ServiceRequestButtonListCell.forListView(this));

    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void changePassword(ActionEvent event) {
    try {
      passwordSettingRequest passwordSettingRequest = new passwordSettingRequest();
      passwordSettingRequest.setNewPassword(newPassword.getText());
      passwordSettingRequest.setConfirmNewPassword(confirmPassword.getText());

      submitPasswordChange(passwordSettingRequest);
    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning(
          "Warning : A required value was not filled", newPassword.getScene().getWindow());
    }
  }

  private void submitPasswordChange(passwordSettingRequest r) {

    String password = r.getNewPassword();
    String confirmation = r.getConfirmNewPassword();
    if (!password.equals(confirmation)) {
      PopUp.createWarning(
          "Warning : Confirmation Doesn't Match Password!", newPassword.getScene().getWindow());
    } else {
      try {
        Account account = AccountsManager.getInstance().getAccount();
        account.setPasswordHash(RSAEncryption.generatePasswordHASH(r.getNewPassword()));
        db.update(account);
      } catch (Exception e) {
        e.printStackTrace();
        PopUp.createWarning(
            "Error: password change not successful", newPassword.getScene().getWindow());
      }
      PopUp.createWarning(
          "Congrats! you have reset your password successfully",
          newPassword.getScene().getWindow());
      newPassword.setText("");
      confirmPassword.setText("");
    }
  }

  public void changePic(MouseEvent e) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose New Profile Picture");
    fileChooser
        .getExtensionFilters()
        .addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    File selectedFile = fileChooser.showOpenDialog(stage);
    if (selectedFile != null) {
      try {
        byte[] bytes = Files.readAllBytes(selectedFile.toPath());
        if (bytes.length >= 65535) {
          PopUp.createWarning("Error: File can't be larger than 64 kb", stage);
          return;
        }
        ProfilePictureManager.savePersonalPicture(employee, bytes);
      } catch (IOException ioException) {
        ioException.printStackTrace();
        PopUp.createWarning("Error: Couldn't read file", stage);
      } catch (SQLException sqlException) {
        sqlException.printStackTrace();
        PopUp.createWarning("Error: Employee not found", stage);
      }
      Image image;
      try {
        InputStream is = ProfilePictureManager.getPersonalPicture(employee);
        image = new Image(is);
        photoCirc.setFill(new ImagePattern(image));
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

  public void hover(MouseEvent e) {
    ((Node) e.getSource()).getScene().setCursor(Cursor.HAND);
  }

  public void leave(MouseEvent e) {
    ((Node) e.getSource()).getScene().setCursor(Cursor.DEFAULT);
  }

  public void changeDBConnection(Event e) {
    System.out.println("Test");
  }

  // public void changeColorScheme(Event e) {
  // colorScheme
  // colorScheme
  //  .getSelectionModel()
  //  .selectedItemProperty()
  //  .addListener(
  //      new ChangeListener<String>() {
  //       @Override
  //       public void changed(
  //           ObservableValue<? extends String> observableValue,
  //           String oldValue,
  //           String newValue) {
  //         if(newValue == "Dark"){}
  //       }})
  ;
  // }

  public void changeMode(ActionEvent event) throws FileNotFoundException {
    if (isLightMode) {
      themeControl.setDarkMode(mainPane);
      Image newImg =
          new Image(
              Objects.requireNonNull(Main.class.getResourceAsStream("view/icons/ic-light.png")));
      imgMode.setImage(newImg);
      isLightMode = false;
    } else {
      themeControl.setLightMode(mainPane);
      Image newImg =
          new Image(
              Objects.requireNonNull(Main.class.getResourceAsStream("view/icons/ic-dark.png")));
      imgMode.setImage(newImg);
      isLightMode = true;
    }
  }
}
