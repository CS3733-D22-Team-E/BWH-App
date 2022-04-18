package edu.wpi.cs3733.D22.teamE.entity;

import edu.wpi.cs3733.D22.teamE.controllers.dashboard.DashboardController;
import edu.wpi.cs3733.D22.teamE.controllers.dashboard.DashboardHandler;
import edu.wpi.cs3733.D22.teamE.database.Equipment;
import edu.wpi.cs3733.D22.teamE.database.MedicalEquipment;
import javafx.scene.control.Toggle;

import java.util.ArrayList;

public class DashboardPage {

  private Toggle filterSelection;
  private String equipmentSelection;
  private String floorSelection;
  private ArrayList<DashboardHandler> observers;
  private ArrayList<MedicalEquipment> filteredEquipment;

  public DashboardPage(Toggle filterSelection, String equipmentSelection, String floorSelection, ArrayList<MedicalEquipment> filteredEquipment) {
    this.filterSelection = filterSelection;
    this.equipmentSelection = equipmentSelection;
    this.floorSelection = floorSelection;
    this.filteredEquipment = filteredEquipment;
  }

  public void setFilterSelection(Toggle filterSelection) {
    this.filterSelection = filterSelection;
    notifyAllObservers();
  }

  public void setEquipmentSelection(String equipmentSelection) {
    this.equipmentSelection = equipmentSelection;
    notifyAllObservers();
  }

  public void setFloorSelection(String floorSelection) {
    this.floorSelection = floorSelection;
    notifyAllObservers();
  }

  public Toggle getFilterSelection() {
    return filterSelection;
  }

  public String getEquipmentSelection() {
    return equipmentSelection;
  }

  public String getFloorSelection() {
    return floorSelection;
  }

  public ArrayList<MedicalEquipment> getFilteredEquipment() {
    return filteredEquipment;
  }

  public void setFilteredEquipment(ArrayList<MedicalEquipment> filteredEquipment) {
    this.filteredEquipment = filteredEquipment;
  }

  public void attach(DashboardHandler observer) {
    observers.add(observer);
 }

 public void notifyAllObservers() {
    for (DashboardHandler observer: observers) {
        observer.update();
    }
 }

  public void setState(Toggle filterSelection, String equipmentSelection, String floorSelection, ArrayList<MedicalEquipment> filteredEquipment) {
    this.filterSelection = filterSelection;
    this.equipmentSelection = equipmentSelection;
    this.floorSelection = floorSelection;
    this.filteredEquipment = filteredEquipment;
    notifyAllObservers();
  }
}
