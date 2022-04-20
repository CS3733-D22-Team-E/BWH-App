package edu.wpi.cs3733.D22.teamE;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class pageControlFacade {

  public static boolean isLightMode = true;

  public static boolean loadPage(String url, Stage stage) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("view/" + url));
      Parent root = loader.load();
      StackPane p = new StackPane();
      p.getChildren().add(root);
      // theme setting
      if (isLightMode) {
        themeControl.setLightMode(root);
      } else {
        themeControl.setDarkMode(root);
      }
      stage.getScene().setRoot(p);
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

  public static void exitApp() {
    System.exit(0);
  }
}
