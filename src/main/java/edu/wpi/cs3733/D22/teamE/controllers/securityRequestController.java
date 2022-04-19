package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.database.daos.SecurityRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.daos.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.securityRequest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class securityRequestController extends serviceRequestPageController{

    @FXML
    JFXComboBox<String> securityRequestType;
    @FXML JFXComboBox<String> timeFrameComboBox;

    @FXML
    TableView<securityRequest> requestsTable;

    @FXML
    TableColumn<securityRequest, String> tableSecurityRequestType;
    @FXML TableColumn<securityRequest, String> tableStaffAssignee;
    @FXML TableColumn<securityRequest, String> tableLocNodeID;
    @FXML TableColumn<securityRequest, String> tableTimeFrame;
    @FXML TableColumn<securityRequest, String> tableRequestStatus;
    @FXML TableColumn<securityRequest, String> tableOtherNotes;
    ObservableList<securityRequest> tableList;

    LocationDAOImpl locationDB;
    SecurityRequestDAOImpl securityRequestDB;

    /** Constructor */
    public securityRequestController() {}

    /**
     * Initializes the
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            super.initialize(location, resources);
            securityRequestType.getItems().addAll("Blood Sample", "Urine Sample", "X-Ray", "CAT Scan", "MRI");
            timeFrameComboBox.getItems().addAll("ASAP", "<1 day", "<1 week");

            securityRequestDB = new SecurityRequestDAOImpl();
            populateSecurityRequestTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateSecurityRequestTable() {
        ObservableList<securityRequest> securityRequests = populateSecurityRequestList();
        tableSecurityRequestType.setCellValueFactory(
                new PropertyValueFactory<securityRequest, String>("securityRequestType"));
        tableStaffAssignee.setCellValueFactory(
                new PropertyValueFactory<securityRequest, String>("staffAssignee"));
        tableLocNodeID.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<securityRequest, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<securityRequest, String> param) {
                        securityRequest curSecRequest = param.getValue();
                        return new SimpleStringProperty(roomIDToRoomName.get(curSecRequest.getRoomID()));
                    }
                });
        tableTimeFrame.setCellValueFactory(new PropertyValueFactory<securityRequest, String>("timeFrame"));
        tableRequestStatus.setCellValueFactory(
                new PropertyValueFactory<securityRequest, String>("requestStatus"));
        tableOtherNotes.setCellValueFactory(new PropertyValueFactory<securityRequest, String>("otherNotes"));

        requestsTable.setItems(securityRequests);
    }

    protected ObservableList<securityRequest> populateSecurityRequestList() {
        List<securityRequest> list = securityRequestDB.getAll();
        tableList = FXCollections.observableArrayList();
        for (securityRequest l : list) {
            tableList.add(l);
        }
        return tableList;
    }

    @Override
    public void submitButton(ActionEvent event) throws SQLException {
        try {
            securityRequest securityReq = new securityRequest();
            securityReq.setSecurityRequestType(securityRequestType.getValue());
            securityReq.setTimeFrame(timeFrameComboBox.getValue());
            securityReq.setFloorID(floor.getValue());
            securityReq.setRoomID(roomNameToRoomID.get(room.getValue()));
            securityReq.setOtherNotes(notes.getText());
            securityReq.setRequestStatus(requestStatus.getText());
            securityReq.setStaffAssignee(staffAssignee.getText());
            securitySendToDB(securityReq);

        } catch (RuntimeException error) {
            System.out.println("Error : Some Value is NULL");
            PopUp.createWarning("Warning : A required value was not filled", null);
        }
    }

    private void securitySendToDB(securityRequest securityReq) throws SQLException {
        securityRequestDB.update(securityReq);
        tableList.add(securityReq);
    }

    @FXML
    private void resetButton(ActionEvent event) {
        floor.getSelectionModel().clearSelection();
        room.getSelectionModel().clearSelection();
        securityRequestType.getSelectionModel().clearSelection();
        timeFrameComboBox.getSelectionModel().clearSelection();
        requestStatus.clear();
        staffAssignee.clear();
        notes.clear();
    }

}
