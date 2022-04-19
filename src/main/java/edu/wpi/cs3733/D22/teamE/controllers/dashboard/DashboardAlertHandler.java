package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import edu.wpi.cs3733.D22.teamE.database.Location;
import edu.wpi.cs3733.D22.teamE.database.MedicalEquipment;
import edu.wpi.cs3733.D22.teamE.database.daos.MedicalEquipmentDAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DashboardAlertHandler extends DashboardHandler {

    ArrayList<MedicalEquipment> allEquipment;
    ArrayList<MedicalEquipment> infusionPumpsSentToCleaning;
    HashMap<Location, ArrayList<MedicalEquipment>> dirtyAreaInfusionPumps;
    ArrayList<Location> dirtyFusionPumpAlertLocs;
    HashMap<Location, ArrayList<MedicalEquipment>> cleanAreaInfusionPumps;
    ArrayList<Location> cleanFusionPumpAlertLocs;
    Integer infusionPumpAlertCounter;

    public DashboardAlertHandler(MedicalEquipmentDAOImpl medicalEquipmentDAO, DashboardController dashboardController) {
        super(medicalEquipmentDAO, dashboardController);
        this.allEquipment = (ArrayList<MedicalEquipment>) this.medicalEquipmentDAO.getAll();
    }

    @Override
    public void update() {

    }

    @Override
    public void initialize() {
        infusionPumpsSentToCleaning = new ArrayList<>();
        dirtyAreaInfusionPumps = new HashMap<>();
        cleanAreaInfusionPumps = new HashMap<>();
        infusionPumpAlertCounter = 0;
        dirtyFusionPumpAlertLocs = new ArrayList<>();
        cleanFusionPumpAlertLocs = new ArrayList<>();

        for (Location curLoc: dashboardController.locationDAO.getAll()) {
            if (curLoc.getNodeType().equals("DIRT")) {
                dirtyAreaInfusionPumps.put(curLoc, new ArrayList<>());
            } else if (curLoc.getNodeType().equals("STOR")) {
                cleanAreaInfusionPumps.put(curLoc, new ArrayList<>());
            }
        }

        for (MedicalEquipment curMedEq: allEquipment) {
            Location equipmentLocation = dashboardController.locationDAO.get(curMedEq.getCurrentLocation());
            if (dirtyAreaInfusionPumps.containsKey(equipmentLocation)) {
                ArrayList<MedicalEquipment> equipmentAtLoc = dirtyAreaInfusionPumps.get(equipmentLocation);
                equipmentAtLoc.add(curMedEq);
                dirtyAreaInfusionPumps.put(equipmentLocation, equipmentAtLoc);
            }
            if (cleanAreaInfusionPumps.containsKey(equipmentLocation)) {
                ArrayList<MedicalEquipment> equipmentAtLoc = cleanAreaInfusionPumps.get(equipmentLocation);
                equipmentAtLoc.add(curMedEq);
                cleanAreaInfusionPumps.put(equipmentLocation, equipmentAtLoc);
            }
        }

        for (Location curLoc: dirtyAreaInfusionPumps.keySet()) {
            ArrayList<MedicalEquipment> dirtyPumpsInArea = dirtyAreaInfusionPumps.get(curLoc);
            if (dirtyPumpsInArea.size() >= 10) {
                dirtyFusionPumpAlertLocs.add(curLoc);
                infusionPumpAlertCounter++;
            }
        }

        for (Location curLoc: cleanAreaInfusionPumps.keySet()) {
            ArrayList<MedicalEquipment> cleanPumpsInArea = cleanAreaInfusionPumps.get(curLoc);
            if (cleanPumpsInArea.size() < 5) {
                cleanFusionPumpAlertLocs.add(curLoc);
                infusionPumpAlertCounter++;
            }
        }
    }
}
