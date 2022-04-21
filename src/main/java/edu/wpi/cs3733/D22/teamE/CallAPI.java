package edu.wpi.cs3733.D22.teamE;

import javafx.application.Application;
import javafx.stage.Stage;

public class CallAPI {
  private static CallAPI singleton;

  private CallAPI() {}

  public static CallAPI getInstance() {
    if (singleton == null) singleton = new CallAPI();

    return singleton;
  }

  public void openAPI() {
    Application apiApp = new APIApp();
    Stage stage = new Stage();
    try {
      apiApp.start(stage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
