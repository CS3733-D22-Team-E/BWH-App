package edu.wpi.cs3733.D22.teamE.entity;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamZ.api.entity.ExternalTransportRequest;
import edu.wpi.cs3733.D22.teamZ.api.entity.RequestStatus;
import edu.wpi.cs3733.D22.teamZ.api.entity.TransportMethod;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.time.LocalDate;
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
    if ((targetRequest instanceof RequestInterface)) {
      if (returnObject == null) return getAsPage((RequestInterface) targetRequest);
      else if (returnObject instanceof JFXButton && displayObject instanceof JFXAlert)
        return getAsPage(
            (RequestInterface) targetRequest, (JFXButton) returnObject, (JFXAlert) displayObject);
      else throw new RuntimeException("Invalid Return Type");
    } else throw new RuntimeException("Not a Valid Request");
  }

  private static Node getAsPage(RequestInterface req)
      throws InvocationTargetException, IllegalAccessException {
    VBox box = new VBox();
    box.setSpacing(2);
    for (Method m : req.getClass().getMethods()) {
      if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
        if (!m.getName().contains("Class")
            && !m.getName().contains("RequestType")
            && !m.getName().contains("Coord")
            && !m.getName().contains("ServiceRequestID")
            && !m.getReturnType().isInstance(new FloralServiceRequest())) {
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

  private static Node getAsPage(RequestInterface req, JFXButton button, JFXAlert alert)
      throws InvocationTargetException, IllegalAccessException {
    VBox box = new VBox();
    box.setSpacing(2);
    List<Object> defVal = new ArrayList<>();
    List<Method> returnMethods = new ArrayList<>();
    List<Node> returnFields = new ArrayList<>();
    for (Method m : req.getClass().getMethods()) {
      if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
        if (!m.getName().contains("Class")
            && !m.getName().contains("RequestType")
            && !m.getName().contains("Coord")
            && !m.getName().contains("ServiceRequestID")
            && !m.getReturnType().isInstance(new FloralServiceRequest())
            && !m.getReturnType()
                .isInstance(
                    new ExternalTransportRequest(
                        "",
                        RequestStatus.DONE,
                        "",
                        "",
                        "",
                        "",
                        LocalDate.now(),
                        TransportMethod.PATIENTCAR))) {
          if (req instanceof ExternalTransportAdapter
              && (m.getName().contains("RequestDate") || m.getName().contains("DeliveryDate"))) {

          } else {
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
              if (r instanceof String) {
                defVal.add(r);
                JFXTextField textField = new JFXTextField();
                textField.setText(content);
                innerBox.getChildren().add(textField);
                box.getChildren().add(innerBox);
                returnMethods.add(req.getClass().getMethod("set" + label, String.class));
                returnFields.add(textField);
              } else if (r instanceof LocalDate) {
                defVal.add(r);
                JFXDatePicker datePicker = new JFXDatePicker();
                datePicker.setValue((LocalDate) r);
                innerBox.getChildren().add(datePicker);
                box.getChildren().add(innerBox);
                returnMethods.add(req.getClass().getMethod("set" + label, LocalDate.class));
                returnFields.add(datePicker);
              } else {
                defVal.add(content);
                JFXTextField textField = new JFXTextField();
                textField.setText(content);
                innerBox.getChildren().add(textField);
                box.getChildren().add(innerBox);
                returnMethods.add(req.getClass().getMethod("set" + label, String.class));
                returnFields.add(textField);
              }
            } catch (NoSuchMethodException e) {
              e.printStackTrace();
            }
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
              Node returnNode = returnFields.get(i);
              if (returnNode instanceof JFXTextField) {
                final Object r = returnMethod.invoke(req, ((JFXTextField) returnNode).getText());
              } else if (returnNode instanceof JFXDatePicker) {
                final Object r = returnMethod.invoke(req, ((JFXDatePicker) returnNode).getValue());
              }
            } catch (IllegalAccessException | InvocationTargetException e) {
              Throwable cause = e.getCause();
              PopUp.createWarning(cause.getMessage(), alert.getOwner());
              Node returnNode = returnFields.get(i);
              if (returnNode instanceof JFXTextField) {
                ((JFXTextField) returnNode).setText((String) defVal.get(i));
              } else if (returnNode instanceof JFXDatePicker) {
                ((JFXDatePicker) returnNode).setValue((LocalDate) defVal.get(i));
              }
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
