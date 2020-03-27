package com.example.myapplication.repository;

import android.app.Application;
import android.content.Context;

import com.example.myapplication.dao.ProfileDao;
import com.example.myapplication.entity.Profile;

import java.util.List;

public class ProfileRepository extends BaseNameCrudRepository<ProfileDao,Profile> {

    public ProfileRepository(Context application) {
        super(application);
        super.setDao(super.getAppDatabase().profileDao());
    }
    public List<Profile> findAllOrderAlphabetic(){
        return super.getDao().findAllOrderAlphabetic();
    }

}
