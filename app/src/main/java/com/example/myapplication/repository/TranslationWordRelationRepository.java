package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.TranslationWordRelationDao;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;


public class TranslationWordRelationRepository extends CrudRepository<TranslationWordRelationDao, TranslationWordRelation> {

    public TranslationWordRelationRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().translationWordRelationDao());
    }


    public ForeignWithNativeWords translateFromForeign(Long foreignWordID){
        return super.getDao().translateFromForeign(foreignWordID);
    }



}
