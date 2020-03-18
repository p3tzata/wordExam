package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.TranslationWordRelation;

import java.util.List;

@Dao
public abstract class TranslationWordRelationDao implements CrudDao<TranslationWordRelation> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract  public  Long insert(Language entity);

    @Update
    abstract public  void update(Language entity);

    @Delete
    abstract public  void delete(Language entity);

    @Query("SELECT * FROM translationwordrelation l where l.translationWordRelationID=:ID")
    abstract public TranslationWordRelation findByID(Long ID);

    @Query("SELECT * FROM translationwordrelation t where t.wordID=:ID")
    abstract public TranslationWordRelation findTranslationWordRelationByDstWordID (Long ID);




}
