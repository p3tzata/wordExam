package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.ProfileDao;
import com.example.myapplication.entity.Profile;

public class ProfileRepository extends NameableCrudRepository<ProfileDao,Profile> {

    public ProfileRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().profileDao());
    }
}
