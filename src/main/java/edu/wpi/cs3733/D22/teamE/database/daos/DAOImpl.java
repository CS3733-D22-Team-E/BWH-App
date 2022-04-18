package edu.wpi.cs3733.D22.teamE.database.daos;

public abstract class DAOImpl<T> implements DAO<T> {

    @Override
    public void update(T t){
        delete(t);
        add(t);
    }
}
