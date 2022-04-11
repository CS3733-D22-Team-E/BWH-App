package edu.wpi.energetic_easter_bunnies.entity;

import edu.wpi.energetic_easter_bunnies.database.daos.MedicalEquipmentServiceRequestDAOImpl;

import java.sql.SQLException;

public class requestFacade {

    MedicalEquipmentServiceRequestDAOImpl medicalEquipmentServiceRequestDAO;

    public requestFacade() {
        try {
            medicalEquipmentServiceRequestDAO = new MedicalEquipmentServiceRequestDAOImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // todo : this facade implements all communication between DB and Front end - handles all requests
  // likely holds a list of all service requests
  // responsible for loading all requests from DB
  // responsible for sending all requests to DB
  // this allows for a single class where update requests that then handles the logic of which table
  // to put it when we save

}
