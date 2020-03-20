package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Profile;

import java.util.List;


public interface CrudDao<Type> {

    Type findByID(Long ID);
    Long insert(Type entity);
    Integer update(Type entity);
    void delete(Type entity);


}
