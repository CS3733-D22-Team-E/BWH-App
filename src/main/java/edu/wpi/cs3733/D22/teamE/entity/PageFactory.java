package edu.wpi.cs3733.D22.teamE.entity;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamZ.api.entity.ExternalTransportRequest;
import edu.wpi.cs3733.D22.teamZ.api.entity.RequestStatus;
import edu.wpi.cs3733.D22.teamZ.api.entity.TransportMethod;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PageFactory {

  public static Node getAsPage(Object target, Object returnObject, Object displayObject)
      throws InvocationTargetException, IllegalAccessException {
    if ((target instanceof EntityInterface)) {
      if (returnObject == null) return getAsPage((EntityInterface) target);
      else if (returnObject instanceof JFXButton && displayObject instanceof JFXAlert)
        return getAsPage(
            (EntityInterface) target, (JFXButton) returnObject, (JFXAlert) displayObject);
      else throw new RuntimeException("Invalid Parameter");
    } else if (target instanceof Employee) {
      if (displayObject instanceof JFXAlert && returnObject instanceof JFXButton)
        return getAsPage((Employee) target, (JFXButton) returnObject, (JFXAlert) displayObject);
      else throw new RuntimeException("Invalid Parameter");
    } else throw new RuntimeException("Invalid Parameter");
  }

  private static Node getAsPage(EntityInterface req)
      throws InvocationTargetException, IllegalAccessException {
    VBox box = new VBox();
    box.setSpacing(2);
    for (Method m : req.getClass().getMethods()) {
      if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
        if (!m.getName().contains("Class")
            && !m.getName().contains("RequestType")
            && !m.getName().contains("Coord")
            && !m.getName().contains("ServiceRequestID")
            && !m.getName().contains("Location")
            && !m.getName().contains("NumID")
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

  private static Node getAsPage(EntityInterface req, JFXButton button, JFXAlert alert)
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
            && !m.getName().contains("Location")
            && !m.getName().contains("NumID")
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
                        TransportMethod.PATIENTCAR))
            && !(req instanceof ExternalTransportAdapter
                && (m.getName().contains("RequestDate") || m.getName().contains("DeliveryDate")))) {
          try {
            final Object r = m.invoke(req);
            String label = m.getName();
            String content = r.toString();
            label = label.trim();
            label = label.replace("get", "");
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
          if (req instanceof RequestInterface)
            DAOSystemSingleton.INSTANCE.getSystem().update((RequestInterface) req);
          else if (req instanceof MedicalEquipment)
            DAOSystemSingleton.INSTANCE.getSystem().update((MedicalEquipment) req);
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

  private static Node getAsPage(Employee e, JFXButton button, JFXAlert alert)
      throws InvocationTargetException, IllegalAccessException {
    VBox box = new VBox();
    box.setSpacing(2);
    List<Object> defVal = new ArrayList<>();
    List<Method> returnMethods = new ArrayList<>();
    List<Node> returnFields = new ArrayList<>();
    for (Method m : e.getClass().getMethods()) {
      if (m.getName().startsWith("get")
          && m.getParameterTypes().length == 0
          && !m.getName().contains("Class")
          && !m.getName().equals("getEmployee")
          && !m.getName().contains("NumID")) {
        try {
          final Object r = m.invoke(e);
          String label = m.getName();
          String content = r.toString();
          label = label.trim();
          label = label.replace("get", "");
          content = content.trim();
          HBox innerBox = new HBox();
          Label l = new Label(label + " : ");
          innerBox.getChildren().add(l);
          if (m.getName().contains("EmployeeID") || m.getName().contains("Name") || true) {
            Label newL = new Label(content);
            newL.setText(content);
            innerBox.getChildren().add(newL);
            box.getChildren().add(innerBox);
          } else {
            if (r instanceof String) {
              defVal.add(r);
              JFXTextField textField = new JFXTextField();
              textField.setText(content);
              innerBox.getChildren().add(textField);
              box.getChildren().add(innerBox);
              returnMethods.add(e.getClass().getMethod("set" + label, String.class));
              returnFields.add(textField);
            } else if (r instanceof LocalDate) {
              defVal.add(r);
              JFXDatePicker datePicker = new JFXDatePicker();
              datePicker.setValue((LocalDate) r);
              innerBox.getChildren().add(datePicker);
              box.getChildren().add(innerBox);
              returnMethods.add(e.getClass().getMethod("set" + label, LocalDate.class));
              returnFields.add(datePicker);
            } else {
              defVal.add(content);
              JFXTextField textField = new JFXTextField();
              textField.setText(content);
              innerBox.getChildren().add(textField);
              box.getChildren().add(innerBox);
              returnMethods.add(e.getClass().getMethod("set" + label, String.class));
              returnFields.add(textField);
            }
          }
        } catch (NoSuchMethodException err) {
          err.printStackTrace();
        }
      } else System.out.println(m.getName());
    }
    button.setOnAction(
        event -> {
          for (int i = 0; i < returnMethods.size(); i++) {
            Method returnMethod = returnMethods.get(i);
            try {
              System.out.println(returnMethod.getName());
              Node returnNode = returnFields.get(i);
              if (returnNode instanceof JFXTextField) {
                final Object r = returnMethod.invoke(e, ((JFXTextField) returnNode).getText());
              } else if (returnNode instanceof JFXDatePicker) {
                final Object r = returnMethod.invoke(e, ((JFXDatePicker) returnNode).getValue());
              }
            } catch (IllegalAccessException | InvocationTargetException err) {
              Throwable cause = err.getCause();
              PopUp.createWarning(cause.getMessage(), alert.getOwner());
              Node returnNode = returnFields.get(i);
              if (returnNode instanceof JFXTextField) {
                ((JFXTextField) returnNode).setText((String) defVal.get(i));
              } else if (returnNode instanceof JFXDatePicker) {
                ((JFXDatePicker) returnNode).setValue((LocalDate) defVal.get(i));
              }
            }
          }
          DAOSystemSingleton.INSTANCE.getSystem().update(e);
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
