package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.database.daos.GiftRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.giftDeliveryRequest;
import edu.wpi.cs3733.D22.teamE.entity.mealDeliveryRequest;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * This is the controller class for the gift delivery service request. Inherits from the
 * serviceDeliveryController super class.
 */
public class giftDeliveryController extends serviceRequestPageController implements Initializable {
    @FXML JFXComboBox<String> giftOptionsDropDown;
    @FXML DatePicker deliveryDateTime; //TODO: Make requestDate just get local time?
    @FXML CheckBox isUrgent;
    @FXML TextField staffAssignee;
    @FXML TextField requestStatus;
    @FXML TextField patientName;
    @FXML TextArea otherNotesTxt; //TODO: Rename to gift message text
    @FXML TableView<giftDeliveryRequest> giftDeliveryTable;

    @FXML TableColumn<giftDeliveryRequest, String> tableGiftType;
    @FXML TableColumn<giftDeliveryRequest, String> tablePatientName;
    @FXML TableColumn<giftDeliveryRequest, String> tableRoomNumber;
    @FXML TableColumn<giftDeliveryRequest, String> tableFloorNumber;
    @FXML TableColumn<giftDeliveryRequest, LocalDate> tableDeliveryDate;
    @FXML TableColumn<giftDeliveryRequest, LocalDate> tableRequestDate;
    @FXML TableColumn<giftDeliveryRequest, String> tableStaffAssignee;
    @FXML TableColumn<giftDeliveryRequest, String> tableRequestStatus;
    @FXML TableColumn<giftDeliveryRequest, Boolean> tableUrgent;
    @FXML TableColumn<giftDeliveryRequest, String> tableOtherNotes;

    GiftRequestDAOImpl giftRequestDAO;
    ObservableList<giftDeliveryRequest> tableList;

    giftDeliveryRequest request = new giftDeliveryRequest(); //object to store inputted page data

    public giftDeliveryController() {}

    @Override
    public void submitButton(ActionEvent event) throws SQLException {

    }
}
