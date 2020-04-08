package com.example.myapplication.repository.exam;

import android.app.Application;

import com.example.myapplication.dao.CFExamProfileDao;
import com.example.myapplication.dao.LanguageDao;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.exam.CFExamProfile;
import com.example.myapplication.repository.BaseNameCrudRepository;

public class CFExamProfileRepository extends BaseNameCrudRepository<CFExamProfileDao, CFExamProfile> {

    public CFExamProfileRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().cfExamProfileDao());
    }
}
