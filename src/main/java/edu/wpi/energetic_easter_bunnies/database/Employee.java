package edu.wpi.energetic_easter_bunnies.database;

public class Employee {
  private final String employeeID;
  private final String name;
  private String position;
  private double salary;
  private String location;
  private boolean available;
  private int numID;

  public Employee() {
    this.employeeID = "";
    this.name = "";
    this.position = "";
    this.salary = 0.0;
    this.location = "";
    this.available = false;
    this.numID = 0;
  }

  public Employee(
      String employeeID,
      String name,
      String position,
      double salary,
      String location,
      boolean available,
      int numID) {
    this.employeeID = employeeID;
    this.name = name;
    this.position = position;
    this.salary = salary;
    this.location = location;
    this.available = available;
    this.numID = numID;
  }

  public String getEmployeeID() {
    return employeeID;
  }

  public String getName() {
    return name;
  }

  public String getPosition() {
    return position;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public int getNumID() {
    return numID;
  }

  public void setNumID(int numID) {
    this.numID = numID;
  }

  public boolean getAvailable() {
    return available;
  }
}
