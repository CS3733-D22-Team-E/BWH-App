package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.PopUp;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class helpPageController {

  private String mapHelpMessage =
      "The Interactive Map allows user to view the location of the different service requests, medical equipment,a dn important locations in the hospital.  Additionally, you are able to move locations and medical equipment around.  There is also an option to add locations or medical equipment. If there is an issue with the icons showing up in the correct spot or at all, then there is an issue with the backend, and nothing can be done without contacting (508) 740-3904.";
  private String serviceHelpMessage =
      "If you are having an issue with a service request page, make sure that you have filled in all of the required fields on the page, even the other notes section (if need be type out 'NA').  If you are having further issues, please call (617) 732-5927.";

  public void mapHelp(ActionEvent event) {
    PopUp.createWarning(mapHelpMessage, ((Node) event.getSource()).getScene().getWindow());
  }

  public void serviceHelp(ActionEvent event) {
    PopUp.createWarning(serviceHelpMessage, ((Node) event.getSource()).getScene().getWindow());
  }
}
