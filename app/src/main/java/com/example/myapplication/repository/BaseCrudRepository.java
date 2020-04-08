package com.example.myapplication.repository;

import android.content.Context;

import com.example.myapplication.dao.base.CrudDao;
import com.example.myapplication.database.DatabaseClient;
import com.example.myapplication.database.WordRoomDatabase;

public abstract class BaseCrudRepository<Dao extends CrudDao,T> {

    WordRoomDatabase appDatabase;
    private Dao daoObject;
    protected BaseCrudRepository(Context application) {
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

    public Integer update(T entity) {
        return daoObject.update(entity);
    }

    public Long inset(T entity) {
       return daoObject.insert(entity);
    }

    public Integer delete(T entity) {
        return daoObject.delete(entity);
    }










}
