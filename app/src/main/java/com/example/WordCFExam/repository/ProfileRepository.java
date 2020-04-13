package com.example.WordCFExam.repository;

import android.content.Context;

import com.example.WordCFExam.dao.ProfileDao;
import com.example.WordCFExam.entity.Profile;

import java.util.List;

public class ProfileRepository extends BaseNameCrudRepository<ProfileDao,Profile> {

    public ProfileRepository(Context application) {
        super(application);
        super.setDao(super.getAppDatabase().profileDao());
    }

    @Override
    public List<Profile> findAllOrderAlphabetic(Long parentID, String contains) {
        return super.getDao().findAllOrderAlphabetic(parentID, contains);
    }
}
