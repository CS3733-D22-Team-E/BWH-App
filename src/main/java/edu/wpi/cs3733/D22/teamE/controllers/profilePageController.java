package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXListView;
import edu.wpi.cs3733.D22.teamE.Main;
import edu.wpi.cs3733.D22.teamE.customUI.ServiceRequestButtonListCell;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.entity.serviceRequest;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class profilePageController extends containsSideMenu implements Initializable {

  public Circle photoCirc;
  public Label nameLabel;
  public Label posLabel;
  public Label salLabel;
  public Label reqNumLabel;
  private Account account;
  private Employee employee;
  private DAOSystem db;
  public JFXListView<serviceRequest> reqList;
  @FXML Button resetPassword;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    try {
      db = new DAOSystem();
      account = AccountsManager.getInstance().getAccount();
      employee = AccountsManager.getInstance().getEmployee();
      URL url = Main.class.getResource("view/icons/prof.png");
      assert url != null;
      InputStream is = url.openStream();
      Image image = new Image(is);
      photoCirc.setFill(new ImagePattern(image));
      nameLabel.setText(account.getFirstName() + " " + account.getLastName());
      posLabel.setText(account.getPosition());
      salLabel.setText(String.valueOf(employee.getSalary()));
      int count = 0;
      List<serviceRequest> l = db.getAllServiceRequests();
      ArrayList<serviceRequest> myReq = new ArrayList<>();
      ObservableList<serviceRequest> data = FXCollections.observableArrayList();
      for (serviceRequest r : l) {
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
}
