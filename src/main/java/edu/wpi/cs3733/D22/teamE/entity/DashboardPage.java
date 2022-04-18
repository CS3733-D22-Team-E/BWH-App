package edu.wpi.cs3733.D22.teamE.entity;

public class DashboardPage {

    private String filterSelection;
    private String equipmentSelection;
    private String floorSelection;

    public DashboardPage(String filterSelection, String equipmentSelection, String floorSelection) {
        this.filterSelection = filterSelection;
        this.equipmentSelection = equipmentSelection;
        this.floorSelection = floorSelection;
    }

    public String getFilterSelection() {
        return filterSelection;
    }

    public void setFilterSelection(String filterSelection) {
        this.filterSelection = filterSelection;
    }

    public String getEquipmentSelection() {
        return equipmentSelection;
    }

    public void setEquipmentSelection(String equipmentSelection) {
        this.equipmentSelection = equipmentSelection;
    }

    public String getFloorSelection() {
        return floorSelection;
    }

    public void setFloorSelection(String floorSelection) {
        this.floorSelection = floorSelection;
    }
}
