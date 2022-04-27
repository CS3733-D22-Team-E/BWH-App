package edu.wpi.cs3733.D22.teamE;

import java.io.IOException;
import javafx.event.ActionEvent;

public interface pageButtons {

  void statusButton(ActionEvent event) throws IOException;

  void exitButton(ActionEvent event) throws IOException;

  void mapButton(ActionEvent event) throws IOException;

  void aboutButton(ActionEvent event) throws IOException;

  void helpButton(ActionEvent event) throws IOException;

  void dashboardButton(ActionEvent event) throws IOException;
}
