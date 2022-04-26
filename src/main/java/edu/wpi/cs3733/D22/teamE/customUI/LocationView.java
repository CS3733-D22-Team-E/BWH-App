package edu.wpi.cs3733.D22.teamE.customUI;

import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.Location;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import javafx.scene.image.Image;

public class LocationView extends NodeImageView<Location> {

  public LocationView(Image i, Location node) {
    super(i, node);

    this.setName(node.getLongName());

    this.setOnMouseReleased(
        event -> {
          try {
            if (drag) {
              double deltaX =
                  ((this.getStartX() - event.getX()) / getModifier()) + getWidthOffset();
              double deltaY =
                  ((this.getStartY() - event.getY()) / getModifier()) + getHeightOffset();
              DAOSystemSingleton.INSTANCE
                  .getSystem()
                  .updateCoord(
                      node, (int) (node.getXCoord() - deltaX), (int) (node.getYCoord() - deltaY));
              this.setClicked(false);
              controller.fetchDB();
              drag = false;
            }
          } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
          }
        });
  }

  @Override
  public void deleteFromDB() {
    DAOSystemSingleton.INSTANCE.getSystem().deleteLocation(node);
  }
}
