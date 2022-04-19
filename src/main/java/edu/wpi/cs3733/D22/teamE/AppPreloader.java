package edu.wpi.cs3733.D22.teamE;

import com.jfoenix.controls.JFXSpinner;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AppPreloader extends Application implements SharedScene {
  @FXML HBox spinnerBox;
  Parent preloaderParent;
  ProgressBar bar;
  JFXSpinner spinner;
  Group topGroup;
  Stage stage;
  boolean noLoadingProgress = true;

  public int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  private Scene createPreloaderScene() {
    // bar = new ProgressBar(0);
    // BorderPane p = new BorderPane();
    // p.setCenter(bar);
    preloaderParent = (Parent) pageControlFacade.getPageRoot("loadingPage.fxml", this);
    spinner = new JFXSpinner(0);
    spinnerBox.setAlignment(Pos.CENTER);
    ((VBox) spinnerBox.getParent()).setAlignment(Pos.CENTER);
    double prefWidthVBOX = ((VBox) spinnerBox.getParent()).getPrefWidth();
    double prefHeightVBOX = ((VBox) spinnerBox.getParent()).getPrefHeight();
    spinner.setPrefSize(prefWidthVBOX / 3, prefHeightVBOX / 4);
    spinnerBox.getChildren().add(spinner);
    topGroup = new Group(preloaderParent);
    return new Scene(Objects.requireNonNull(topGroup));
  }

  public void start(Stage stage) throws Exception {
    this.stage = stage;
    stage.setResizable(true);
    stage.setScene(createPreloaderScene());
    stage.show();
  }

  public void fadeInTo(Parent p) {
    // add application scene to the preloader group
    // (visualized "behind" preloader at this point)
    // Note: list is back to front
    topGroup.getChildren().add(0, p);
    p.setDisable(true);

    // setup fade transition for preloader part of scene
    // fade out over 5s
    FadeTransition ft = new FadeTransition(Duration.millis(5000), preloaderParent);
    ft.setRate(2);
    ft.setFromValue(1.0);
    ft.setToValue(0.0);
    ft.setOnFinished(t -> topGroup.getChildren().remove(preloaderParent));
    FadeTransition newFT = new FadeTransition(Duration.millis(5000), p);
    newFT.setFromValue(0.0);
    newFT.setToValue(1.0);
    newFT.setRate(1.25);
    newFT.setOnFinished(
        event -> {
          p.setDisable(false);
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          pageControlFacade.loadPage("loginPage.fxml", (Stage) p.getScene().getWindow());
        });
    ft.play();
    newFT.play();
  }

  @Override
  public Parent getParentNode() {
    return preloaderParent;
  }
}
