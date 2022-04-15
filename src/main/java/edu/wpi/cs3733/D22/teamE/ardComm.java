package edu.wpi.cs3733.D22.teamE;

import arduino.*;

public class ardComm {
    Arduino uno;

    public String readData(){
        String data = "";
        uno = new Arduino("COM4", 9600);
        boolean connection = uno.openConnection();
        if (!connection) {
            System.err.println("Error: Unable to connect to serial port");
        } else {
            while (data.equals("")){
                data = uno.serialRead();
            }
        }
        System.out.println(data);
        return data;
    }
}
