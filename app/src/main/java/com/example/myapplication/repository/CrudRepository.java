package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.CrudDao;
import com.example.myapplication.database.DatabaseClient;
import com.example.myapplication.database.WordRoomDatabase;

public abstract class CrudRepository<Dao extends CrudDao,T> {

    WordRoomDatabase appDatabase;
    private Dao daoObject;
    CrudRepository(Application application) {
        appDatabase = DatabaseClient.getInstance(application).getAppDatabase();
    }

    protected void setDao(Dao dao) {
        this.daoObject = dao;
    }

    protected WordRoomDatabase getAppDatabase(){
        return appDatabase;
    }
    protected Dao getDao(){
        return daoObject;
    }



    public T findByID(Long ID) {
        return (T) daoObject.findByID(ID);
    }

    public void update(T entity) {
        daoObject.update(entity);
    }

    public Long inset(T entity) {
       return daoObject.insert(entity);
    }

    public void delete(T entity) {
        daoObject.delete(entity);
    }










}
