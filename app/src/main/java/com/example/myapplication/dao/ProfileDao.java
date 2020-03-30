package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.dao.base.NameableCrudDao;
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

    @Override
    @Query("SELECT * FROM profile p where :parentID=:parentID and p.profileName like '%'||:contains||'%' order by p.profileName")
    abstract public List<Profile> findAllOrderAlphabetic(Long parentID,String contains);

    @Query("SELECT * FROM profile p where p.profileID=:ID")
    abstract public Profile findByID(Long ID);



}
