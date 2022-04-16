package edu.wpi.cs3733.D22.teamE;

import com.jfoenix.controls.JFXSpinner;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.application.Preloader;
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

public class AppPreloader extends Preloader implements SharedScene {
  @FXML HBox spinnerBox;
  Parent preloaderParent;
  ProgressBar bar;
  JFXSpinner spinner;
  Group topGroup;
  Stage stage;
  boolean noLoadingProgress = true;

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
    stage.setResizable(false);
    stage.setScene(createPreloaderScene());
    stage.show();
  }

  @Override
  public void handleProgressNotification(ProgressNotification pn) {
    // application loading progress is rescaled to be first 50%
    // Even if there is nothing to load 0% and 100% events can be
    // delivered
    if (pn.getProgress() != 1.0 || !noLoadingProgress) {
      spinner.setProgress(pn.getProgress() / 2);
      // bar.setProgress(pn.getProgress() / 2);
      if (pn.getProgress() > 0) {
        noLoadingProgress = false;
      }
    }
  }

  @Override
  public void handleApplicationNotification(PreloaderNotification pn) {
    if (pn instanceof ProgressNotification) {
      // expect application to send us progress notifications
      // with progress ranging from 0 to 1.0
      double v = ((ProgressNotification) pn).getProgress();
      if (!noLoadingProgress) {
        // if we were receiving loading progress notifications
        // then progress is already at 50%.
        // Rescale application progress to start from 50%
        v = 0.5 + v / 2;
      }
      // bar.setProgress(v);
      spinner.setProgress(v);
    } // hide after get any state update from application
  }

  @Override
  public void handleStateChangeNotification(StateChangeNotification evt) {
    if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
      // its time to start fading into application ...
      SharedScene appScene = (SharedScene) evt.getApplication();
      fadeInTo(appScene.getParentNode());
    }
  }

  private void fadeInTo(Parent p) {
    // add application scene to the preloader group
    // (visualized "behind" preloader at this point)
    // Note: list is back to front
    topGroup.getChildren().add(0, p);
    p.setDisable(true);

    // setup fade transition for preloader part of scene
    // fade out over 5s
    FadeTransition ft = new FadeTransition(Duration.millis(5000), preloaderParent);
    ft.setFromValue(1.0);
    ft.setToValue(0.0);
    ft.setOnFinished(t -> topGroup.getChildren().remove(preloaderParent));
    FadeTransition newFT = new FadeTransition(Duration.millis(5000), p);
    newFT.setFromValue(0.0);
    newFT.setToValue(1.0);
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
