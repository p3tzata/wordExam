package com.example.myapplication.repository;

import android.content.Context;

import com.example.myapplication.dao.base.NameableCrudDao;

import java.util.List;

public abstract class BaseNameCrudRepository<Dao extends NameableCrudDao,T> extends BaseCrudRepository<Dao,T>
        implements NameableCrudRepository<T> {

    BaseNameCrudRepository(Context application) {
        super(application);
    }

    public List<T> findAllOrderAlphabetic(Long parentID,String contains) {

        return super.getDao().findAllOrderAlphabetic(parentID,contains);


   }



    }




