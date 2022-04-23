package edu.wpi.cs3733.D22.teamE.database.daos;

import java.sql.SQLException;

/** Singleton to create a DAOSystem Instance */
public enum DAOSystemSingleton {
    INSTANCE;

    private DAOSystem system;

    DAOSystemSingleton() {
        try {
            system = new DAOSystem();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get DAOSystem
     *
     * @return system
     */
    public DAOSystem getSystem() {
        return system;
    }
}