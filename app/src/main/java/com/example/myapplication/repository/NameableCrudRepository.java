package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.NameableCrudDao;

public abstract class NameableCrudRepository<Dao extends NameableCrudDao,T> extends CrudRepository<Dao,T> {

NameableCrudRepository(Application application) {
        super(application);
    }
}
