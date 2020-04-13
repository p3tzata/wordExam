package com.example.WordCFExam.service;

import android.app.Application;

import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.repository.ProfileRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;

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
