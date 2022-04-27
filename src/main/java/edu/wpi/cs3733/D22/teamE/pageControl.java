package edu.wpi.cs3733.D22.teamE;

import static edu.wpi.cs3733.D22.teamE.database.CSVManager.saveAllCSVs;

import edu.wpi.cs3733.D22.teamE.database.CSVManager;
import edu.wpi.cs3733.D22.teamE.database.CSVManager.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class pageControl {

  public static boolean loadPage(String url, Stage stage) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("view/" + url));
      Parent root = loader.load();
      StackPane p = new StackPane();
      p.getChildren().add(root);

      stage.getScene().setRoot(p);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean loadPage(String url) {
    try {
      Stage newStage = new Stage();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("view/" + url));
      Parent root = loader.load();
      newStage.setScene(new Scene(root));
      newStage.show();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static Node getPageRoot(String url) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("view/" + url));
      return loader.load();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Node getPageRoot(String url, Object controller) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("view/" + url));
      loader.setController(controller);
      return loader.load();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void loadTop(String url, Stage stage) {
    Parent root = stage.getScene().getRoot();
    BorderPane basePage = (BorderPane) root.lookup("#rootBorderPane");
    basePage.setTop(getPageRoot(url));
  }

  public static void loadCenter(String url, Stage stage) {
    Parent root = stage.getScene().getRoot();
    BorderPane basePage = (BorderPane) root.lookup("#rootBorderPane");
    basePage.setCenter(getPageRoot(url));
  }

  public static void loadLeft(String url, Stage stage) {
    Parent root = stage.getScene().getRoot();
    BorderPane basePage = (BorderPane) root.lookup("#rootBorderPane");
    basePage.setLeft(getPageRoot(url));
  }

  public static void exitApp() {
    // TODO: uncomment this, was resulting in error
    /*
  public static void exitApp() throws SQLException, IOException {
    

    
     */
    saveAllCSVs();
    CallAPI.getInstance()
        .getExternalTransportAPI()
        .exportExternalTransportsToCSV(new File("CSVsaveFiles/TransportExt.csv"));
    System.exit(0);
  }

  private static void saveAllCSVs() throws SQLException, IOException {
    CSVManager.saveLocationCSV("TowerLocations.csv");
    CSVManager.saveEmployeeCSV("Employees.csv");
    CSVManager.saveMedEquipCSV("MedEquip.csv");
    CSVManager.saveLabRequestCSV("LabRequests.csv");
    CSVManager.saveMedEquipRequestCSV("MedEquipRequests.csv");
    CSVManager.saveServiceRequestCSV("ServiceRequests.csv");
    CSVManager.saveMedicineRequestCSV("MedicineRequests.csv");
    CSVManager.saveSanitationRequestCSV("SanitationRequests.csv");
    CSVManager.saveMealDeliveryCSV("MealRequests.csv");
    CSVManager.saveLanguageInterpreterRequestCSV("LangInterpRequests.csv");
    CSVManager.saveFacilitiesRequestCSV("FacilitiesRequests.csv");
    CSVManager.saveSecurityRequestCSV("SecurityRequests.csv");
    CSVManager.saveGiftDeliveryRequestCSV("GiftRequests.csv");
    CSVManager.saveAccountCSV("Accounts.csv");

    // CSVManager.saveEdgesCSV();
  }
}
