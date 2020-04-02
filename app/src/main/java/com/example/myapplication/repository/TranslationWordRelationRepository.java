package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.TranslationWordRelationDao;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;
import com.example.myapplication.entity.dto.NativeWithForeignWords;

import java.util.List;


public class TranslationWordRelationRepository extends BaseCrudRepository<TranslationWordRelationDao, TranslationWordRelation> {

    public TranslationWordRelationRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().translationWordRelationDao());
    }


    public ForeignWithNativeWords translateFromForeign(Long foreignWordID){
        return super.getDao().translateFromForeign(foreignWordID);
    }

    /*
    public NativeWithForeignWords translateFromNative(Long nativeWordID){
        return super.getDao().translateFromNative(nativeWordID);
    }

     */


    public List<Word> translateFromNative(Long nativeWordID,Long toLanguageID){
        return super.getDao().translateFromNative(nativeWordID,toLanguageID);
    }

    public List<Word> translateFromForeign(Long foreignWordID,Long toLanguageID){
        return super.getDao().translateFromForeign(foreignWordID, toLanguageID);
    }



    public TranslationWordRelation findByForeignWordIDAndNativeWordID(Long foreignWordID, Long nativeWordID){
        return super.getDao().findByForeignWordIDAndNativeWordID(foreignWordID,nativeWordID);
    }

    public List<TranslationWordRelation> findByNativeWordID(Long nativeWordID){
        return super.getDao().findByNativeWordID(nativeWordID);
    }


}
