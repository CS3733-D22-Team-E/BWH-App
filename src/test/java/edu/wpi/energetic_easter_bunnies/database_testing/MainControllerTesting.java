package edu.wpi.energetic_easter_bunnies.database_testing;

import edu.wpi.cs3733.D22.teamE.controllers.mainController;
import org.junit.jupiter.api.Test;

public class MainControllerTesting {

  @Test
  public void testFloralAPIButton() {
    mainController mainController = new mainController();
    // mainController.apiButton(new ActionEvent());
  }

  @Test
  public void testExternalTransportButton() {
    mainController mainController = new mainController();
    // mainController.extTransportButton(new ActionEvent());
  }

  @Test
  public void testSanitationButton() {
    mainController mainController = new mainController();
    // mainController.setSanitationButton(new ActionEvent());
  }
}
