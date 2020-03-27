package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;

import java.util.List;

@Dao
public abstract class ProfileDao implements NameableCrudDao<Profile> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(Profile entity);

    @Update
    public abstract Integer update(Profile entity);

    @Delete
    public abstract Integer delete(Profile entity);

    @Query("SELECT * FROM profile p order by p.profileName")
    abstract public List<Profile> findAllOrderAlphabetic();

    @Query("SELECT * FROM profile p where p.profileID=:ID")
    abstract public Profile findByID(Long ID);



}
