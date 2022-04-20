package edu.wpi.cs3733.D22.teamE;

import java.util.Objects;
import javafx.scene.Parent;

public class themeControl {
  private static final String darkModeURL =
      Objects.requireNonNull(Main.class.getResource("view/styles/darkMode.css")).toExternalForm();
  private static final String lightModeURL =
      Objects.requireNonNull(Main.class.getResource("view/styles/lightMode.css")).toExternalForm();

  public static void setLightMode(Parent root) {
    root.getStylesheets().remove(darkModeURL);
    root.getStylesheets().add(lightModeURL);
  }

  public static void setDarkMode(Parent root) {
    root.getStylesheets().remove(lightModeURL);
    root.getStylesheets().add(darkModeURL);
  }
}
