package edu.wpi.cs3733.D22.teamE.customUI;

import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import javafx.scene.image.Image;

public class EntityView extends NodeImageView<EntityInterface> {

  protected customImageViewTesting myLoc;

  public EntityView(Image i, EntityInterface node, customImageViewTesting myLoc) {
    super(i, node);

    myLoc.setScaleX(this.getScaleX() * 1.2);
    myLoc.setScaleY(this.getScaleY() * 1.2);

    this.scaleXProperty()
        .addListener((observableValue, number, t1) -> myLoc.setScaleX(t1.doubleValue() * 1.2));
    this.scaleYProperty()
        .addListener((observableValue, number, t1) -> myLoc.setScaleY(t1.doubleValue() * 1.2));

    this.myLoc = myLoc;

    this.setOnMouseClicked(
        event -> {
          if (!drag) {
            if (event.isShiftDown()) {
              PopUp.createReq(node, this.getScene().getWindow(), false, null);
              popup.hide();
            }
          }
        });

    this.setOnMouseReleased(
        event -> {
          try {
            if (drag) {
              double deltaX =
                  ((this.getStartX() - event.getX()) / getModifier()) + getWidthOffset();
              double deltaY =
                  ((this.getStartY() - event.getY()) / getModifier()) + getHeightOffset();
              int newX = (int) (node.getXCoord() - deltaX);
              int newY = (int) (node.getYCoord() - deltaY);
              newX = (int) event.getScreenX();
              newY = (int) event.getScreenY();
              System.out.println(newX + " : " + newY);
              Location l =
                  DAOSystemSingleton.INSTANCE
                      .getSystem()
                      .getClosestLocation(newX, newY, node.getFloorID());
              System.out.println(l.getXCoord() + " : " + l.getYCoord() + " : " + l.getFloor());
              node.setLocation(l);
              System.out.println(node.getLocation());
              DAOSystemSingleton.INSTANCE.getSystem().updateEntity(node);
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
    if (node instanceof Equipment) {
      if (node instanceof MedicalEquipment)
        DAOSystemSingleton.INSTANCE.getSystem().deleteMedEquip((MedicalEquipment) node);
    } else if (node instanceof RequestInterface)
      DAOSystemSingleton.INSTANCE.getSystem().deleteServiceRequest((RequestInterface) node);
  }
}
