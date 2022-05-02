package edu.wpi.cs3733.D22.teamE;

import arduino.*;
import com.fazecast.jSerialComm.SerialPort;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ardComm {
  private Arduino arduino = new Arduino("COM10", 9600);
  private static ardComm singleton;

  private ardComm() {}

  private HashMap<String, Arduino> connections = new HashMap<>();

  public static ardComm getInstance() {
    if (singleton == null) {
      singleton = new ardComm();
    }
    // singleton.checkArdConnection();
    return singleton;
  }

  public String readData() {
    System.out.println("In readData()");
    String data = "";
    boolean connection = ardComm.getInstance().getUno().openConnection();
    if (!connection) {
      System.err.println("Error: Unable to connect to serial port");
    } else {
      while (data.equals("")) {
        data = arduino.serialRead();
      }
    }
    arduino.closeConnection();
    System.out.println("The data is" + data);
    return data;
  }

  public boolean verifyID(String id) { // for testing rfid only
    if (!id.contains("93\n52\nCD\n1B")) {
      return false;
    }
    return true;
  }

  public List<String> getAvailableCOMs() {
    List<String> available = new ArrayList<>();
    SerialPort[] ports = SerialPort.getCommPorts();
    for (SerialPort s : ports) {
      if (s.openPort()) {
        available.add(s.getSystemPortName().trim());
      }
      s.closePort();
    }
    return available;
  }

  public void checkArdConnection() {
    List<String> ports = this.getAvailableCOMs();
    System.out.println(ports);
    for (String port : ports) {
      Thread search =
          new Thread(
              new Runnable() {
                @Override
                public void run() {
                  arduino = new Arduino(port, 9600);
                  if (arduino.openConnection() && ardComm.this.checkForBuffer(arduino)) {
                    System.out.println("Port " + port + " has correct connection");
                    arduino.closeConnection();
                    System.out.println("hi");
                    if (!connections.containsKey(port)) {
                      connections.put(port, arduino);
                    }
                  } else arduino.closeConnection();
                  System.out.println("Hi again");
                }
              });
      System.out.println("Searching " + port);
      search.start();
    }
  }

  public boolean checkForBuffer(Arduino arduino) {
    long startTime = System.currentTimeMillis();
    String aIn = "";
    while (!containsLetters(aIn)) {
      aIn = arduino.serialRead();
      //      System.out.println("Test signal: " + aIn);
      long currentTime = System.currentTimeMillis();
      if (currentTime >= startTime + 2000) {
        return false;
      }
    }
    System.out.println("Buffer received on " + arduino.getPortDescription());
    return true;
  }

  public boolean containsLetters(String p) {
    return !p.isEmpty();
  }

  public boolean hasConnections() {
    return !this.connections.isEmpty();
  }

  public Collection<Arduino> getConnections() {
    return this.connections.values();
  }

  public Arduino getUno() {
    return this.arduino;
  }
}
