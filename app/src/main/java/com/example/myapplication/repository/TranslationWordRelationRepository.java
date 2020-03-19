package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.TranslationWordRelationDao;
import com.example.myapplication.entity.TranslationWordRelation;

public class TranslationWordRelationRepository extends CrudRepository<TranslationWordRelationDao, TranslationWordRelation> {

    public TranslationWordRelationRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().translationWordRelationDao());
    }




}
