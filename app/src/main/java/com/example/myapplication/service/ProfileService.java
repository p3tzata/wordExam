package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.Profile;
import com.example.myapplication.repository.ProfileRepository;

import java.util.List;


public class ProfileService extends BaseNameCrudService<ProfileRepository, Profile> {

    public ProfileService(Application application) {
        super(application,new ProfileRepository(application));
    }

    public List<Profile> findAllOrderAlphabetic(){
        return super.getRepository().findAllOrderAlphabetic();
    }

}
