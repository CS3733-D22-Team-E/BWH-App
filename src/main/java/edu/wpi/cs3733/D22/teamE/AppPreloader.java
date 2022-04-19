package edu.wpi.cs3733.D22.teamE;

import com.jfoenix.controls.JFXSpinner;
import java.util.ArrayList;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AppPreloader extends Application implements SharedScene {
  @FXML VBox spinnerBox;
  @FXML StackPane spCont;
  Parent preloaderParent;
  ProgressBar bar;
  ArrayList<JFXSpinner> spinners = new ArrayList<>();
  Group topGroup;
  Stage stage;
  boolean noLoadingProgress = true;

  public int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  private Scene createPreloaderScene() {
    ArrayList<String> spinnerClasses = new ArrayList<>();
    ArrayList<String> spinnerClasses2 = new ArrayList<>();
    spinnerClasses.add("materialDesign-purple");
    spinnerClasses2.add("first-spinner");
    spinnerClasses.add("materialDesign-blue");
    spinnerClasses2.add("second-spinner");
    spinnerClasses.add("materialDesign-cyan");
    spinnerClasses2.add("third-spinner");
    spinnerClasses.add("materialDesign-green");
    spinnerClasses2.add("fourth-spinner");
    spinnerClasses.add("materialDesign-yellow");
    spinnerClasses2.add("fifth-spinner");
    spinnerClasses.add("materialDesign-orange");
    spinnerClasses2.add("sixth-spinner");
    spinnerClasses.add("materialDesign-red");
    spinnerClasses2.add("seventh-spinner");
    preloaderParent = (Parent) pageControlFacade.getPageRoot("loadingPage.fxml", this);
    spinnerBox.setAlignment(Pos.CENTER);
    assert preloaderParent != null;
    spinnerBox
        .getStylesheets()
        .add(Main.class.getResource("view/styles/spinner.css").toExternalForm());
    spinnerBox.setAlignment(Pos.CENTER);
    spinnerBox.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
    spCont.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
    for (int i = 0; i < spinnerClasses.size(); i++) {
      String class1 = spinnerClasses.get(i);
      String class2 = spinnerClasses2.get(i);
      JFXSpinner spinner = new JFXSpinner();
      // spinner.setRadius(20 * (i + 1));
      // spinner.getStylesheets().add(preloaderParent.getStylesheets().get(0));
      spinner.getStyleClass().addAll(class1, class2);
      spinner.setStartingAngle(-40 - (i * 30));
      spinners.add(spinner);
      // spinner.setPrefSize();
      // spinnerBox.getChildren().add(spinner);
      spCont.getChildren().add(spinner);
    }
    // spinnerBox.getChildren().add(spCont);
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
