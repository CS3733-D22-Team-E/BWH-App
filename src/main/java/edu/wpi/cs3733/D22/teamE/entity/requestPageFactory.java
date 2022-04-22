package edu.wpi.cs3733.D22.teamE.entity;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class requestPageFactory {

  private static DAOSystem db;

  static {
    try {
      db = new DAOSystem();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Node getAsPage(Object targetRequest, Object returnObject, Object displayObject)
      throws InvocationTargetException, IllegalAccessException {
    if ((targetRequest instanceof serviceRequest)) {
      if (returnObject == null) return getAsPage((serviceRequest) targetRequest);
      else if (returnObject instanceof JFXButton && displayObject instanceof JFXAlert)
        return getAsPage(
            (serviceRequest) targetRequest, (JFXButton) returnObject, (JFXAlert) displayObject);
      else throw new RuntimeException("Invalid Return Type");
    } else throw new RuntimeException("Not a Valid Request");
  }

  private static Node getAsPage(serviceRequest req)
      throws InvocationTargetException, IllegalAccessException {
    VBox box = new VBox();
    box.setSpacing(2);
    for (Method m : req.getClass().getMethods()) {
      if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
        if (!m.getName().contains("Class")
            && !m.getName().contains("RequestType")
            && !m.getName().contains("Coord")
            && !m.getName().contains("ServiceRequestID")) {
          final Object r = m.invoke(req);
          String label = m.getName();
          String content = r.toString();
          label = label.trim();
          label = label.replace("get", "");
          content = content.trim();
          HBox innerBox = new HBox();
          Label l = new Label(label + " : " + content);
          innerBox.getChildren().add(l);
          box.getChildren().add(innerBox);
        } else System.out.println(m.getName());
      }
    }
    ScrollPane p = new ScrollPane();
    p.setFitToWidth(true);
    p.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    p.setPadding(new Insets(20));
    p.setContent(box);
    return p;
  }

  private static Node getAsPage(serviceRequest req, JFXButton button, JFXAlert alert)
      throws InvocationTargetException, IllegalAccessException {
    VBox box = new VBox();
    box.setSpacing(2);
    List<String> defVal = new ArrayList<>();
    List<Method> returnMethods = new ArrayList<>();
    List<JFXTextField> returnFields = new ArrayList<>();
    for (Method m : req.getClass().getMethods()) {
      if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
        if (!m.getName().contains("Class")
            && !m.getName().contains("RequestType")
            && !m.getName().contains("Coord")
            && !m.getName().contains("ServiceRequestID")) {
          final Object r = m.invoke(req);
          String label = m.getName();
          String content = r.toString();
          label = label.trim();
          label = label.replace("get", "");
          try {
            content = content.trim();
            HBox innerBox = new HBox();
            Label l = new Label(label + " : ");
            innerBox.getChildren().add(l);
            JFXTextField textField = new JFXTextField();
            textField.setText(content);
            innerBox.getChildren().add(textField);
            box.getChildren().add(innerBox);
            returnMethods.add(req.getClass().getMethod("set" + label, String.class));
            defVal.add(content);
            returnFields.add(textField);
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          }
        } else System.out.println(m.getName());
      }
    }
    button.setOnAction(
        event -> {
          for (int i = 0; i < returnMethods.size(); i++) {
            Method returnMethod = returnMethods.get(i);
            try {
              System.out.println(returnMethod.getName());
              final Object r = returnMethod.invoke(req, returnFields.get(i).getText());
            } catch (IllegalAccessException | InvocationTargetException e) {
              Throwable cause = e.getCause();
              PopUp.createWarning(cause.getMessage(), alert.getOwner());
              returnFields.get(i).setText(defVal.get(i));
            }
          }
          db.updateServiceRequest(req);
        });
    ScrollPane p = new ScrollPane();
    p.setFitToWidth(true);
    p.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    // p.setPadding(new Insets(50));
    box.setPadding(new Insets(20));
    box.setSpacing(10);
    p.setContent(box);
    return p;
  }
}
