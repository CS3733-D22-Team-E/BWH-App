package edu.wpi.energetic_easter_bunnies.database;

public class Employee {
  private final String employeeID;
  private final String name;
  private String position;
  private int salary;
  private String location;
  private boolean available;

  public Employee(
      String employeeID,
      String name,
      String position,
      int salary,
      String location,
      boolean available) {
    this.employeeID = employeeID;
    this.name = name;
    this.position = position;
    this.salary = salary;
    this.location = location;
    this.available = available;
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

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
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
}
