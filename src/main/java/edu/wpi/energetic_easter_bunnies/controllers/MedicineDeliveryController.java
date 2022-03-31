package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.medicineDelivery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class MedicineDeliveryController extends ServiceRequestPageController {

    @FXML MenuButton floor;
    @FXML MenuButton room;
    @FXML MenuButton medicine;
    @FXML TextField amount;
    @FXML TextField date;
    @FXML TextField time;
    @FXML CheckBox mon;
    @FXML CheckBox tues;
    @FXML CheckBox wed;
    @FXML CheckBox thurs;
    @FXML CheckBox fri;
    @FXML CheckBox sat;
    @FXML CheckBox sun;

    medicineDelivery medicineDeliveryRequest = new medicineDelivery();

    public MedicineDeliveryController() {}

    @FXML
    public void submitButton(ActionEvent event) {
        try {
            // medicalEquipmentRequest.setLocation(building.getValue() + floor.getValue());
            medicineDelivery.setFloorID(String.valueOf(floor.getItems()));
            medicineDelivery.setAmount(amount.getText());
            medicineDelivery.setMedicine(String.valueOf(medicine.getItems()));
            medicineDelivery.setDeliveryDate(date.getText());
            medicineDelivery.setDeliveryTime(time.getText());
            medicineDelivery.setMon(mon.isSelected());
            medicineDelivery.setTues(tues.isSelected());
            medicineDelivery.setWed(wed.isSelected());
            medicineDelivery.setThurs(thurs.isSelected());
            medicineDelivery.setFri(fri.isSelected());
            medicineDelivery.setSat(sat.isSelected());
            medicineDelivery.setSun(sun.isSelected());

        } catch (NullPointerException error) {
            System.out.println("Error : Some Value is NULL");
            PopUpWarning.createWarning("Warning : A required value was not filled");
        }
    }

}
