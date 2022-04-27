package edu.wpi.energetic_easter_bunnies.database_testing;

import edu.wpi.cs3733.D22.teamE.controllers.mainController;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.Test;

public class MainControllerTesting {

  @Test
  public void testAPIButton() {
    mainController mainController = new mainController();
    mainController.apiButton(new ActionEvent());
  }
}
