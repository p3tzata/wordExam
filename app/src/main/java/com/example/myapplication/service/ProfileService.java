package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.Profile;
import com.example.myapplication.repository.ProfileRepository;



public class ProfileService extends NameableCrudService<ProfileRepository, Profile> {

    public ProfileService(Application application) {
        super(application,new ProfileRepository(application));
    }

}
