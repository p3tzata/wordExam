package com.example.myapplication.service;

import android.app.Application;

import androidx.room.Query;

import com.example.myapplication.entity.Profile;
import com.example.myapplication.repository.ProfileRepository;
import com.example.myapplication.service.base.BaseNameCrudService;

import java.util.List;


public class ProfileService extends BaseNameCrudService<ProfileRepository, Profile> {

    public ProfileService(Application application) {
        super(application,new ProfileRepository(application));
    }


    @Override
    public List<Profile> findAllOrderAlphabetic(Long parentID,String contains){
        return super.getRepository().findAllOrderAlphabetic(parentID,contains);
    }

}
