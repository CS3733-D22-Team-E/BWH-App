package edu.wpi.cs3733.D22.teamE.entity;

import java.lang.reflect.InvocationTargetException;
import javafx.scene.Node;

public interface requestPage {
  Node getAsPage(Object returnObject, Object displayObject)
      throws InvocationTargetException, IllegalAccessException;

  @Override
  String toString();
}
