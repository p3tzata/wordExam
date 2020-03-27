package com.example.myapplication.repository;

import android.app.Application;
import android.content.Context;

import com.example.myapplication.dao.NameableCrudDao;

import java.util.List;

public abstract class BaseNameCrudRepository<Dao extends NameableCrudDao,T> extends BaseCrudRepository<Dao,T>
        implements NameableCrudRepository<T> {

    BaseNameCrudRepository(Context application) {
        super(application);
    }

    public List<T> findAllOrderAlphabetic(Object... objs) {
        if(objs.length==0){
            return super.getDao().findAllOrderAlphabetic();
        }
        return null;
   }



    }




