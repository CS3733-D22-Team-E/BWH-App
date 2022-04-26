package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import edu.wpi.cs3733.D22.teamE.Main;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.customUI.ServiceRequestButtonListCell;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.ProfilePictureManager;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.entity.RequestInterface;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class profilePageController implements Initializable {

  @FXML Circle photoCirc; 
  @FXML Label nameLabel;
  @FXML Label statusLabel;
  @FXML Label idLabel;
  @FXML JFXComboBox database;
  @FXML JFXComboBox colorScheme;
  @FXML TextField newPassword;
  @FXML TextField confirmPassword;
  @FXML TableColumn floor;
  @FXML TableColumn status;
  @FXML TableColumn room;
  @FXML TableColumn requestType;

  private Account account;
  private Employee employee;
  private DAOSystem db;
  public JFXListView<RequestInterface> reqList;
  private URL url;
  private ResourceBundle rb;
  @FXML Button resetPassword;

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
      posLabel.setText(account.getPosition());
      salLabel.setText(String.valueOf(employee.getSalary()));
      int count = 0;
      List<RequestInterface> l = db.getAllServiceRequests();
      ArrayList<RequestInterface> myReq = new ArrayList<>();
      ObservableList<RequestInterface> data = FXCollections.observableArrayList();
      for (RequestInterface r : l) {
        count++;
        if (Objects.equals(employee.getEmployeeID(), "admin")) {
          myReq.add(r);
          data.add(r);
        } else if (Objects.equals(employee.getEmployeeID(), r.getStaffAssignee())) {
          myReq.add(r);
          data.add(r);
        }
      }
      reqList.setItems(data);
      reqList.setCellFactory(ServiceRequestButtonListCell.forListView(this));
      reqNumLabel.setText(String.valueOf(count));

    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void resetPassword(ActionEvent event) throws IOException {
    pageControl.loadPage("passwordSettingPage.fxml");
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
      initialize(url, rb);
    }
  }

  public void hover(MouseEvent e) {
    ((Node) e.getSource()).getScene().setCursor(Cursor.HAND);
  }

  public void leave(MouseEvent e) {
    ((Node) e.getSource()).getScene().setCursor(Cursor.DEFAULT);
  }
}
