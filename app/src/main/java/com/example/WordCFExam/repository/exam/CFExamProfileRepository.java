package com.example.WordCFExam.repository.exam;

import android.app.Application;

import com.example.WordCFExam.dao.CFExamProfileDao;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.repository.BaseNameCrudRepository;

public class CFExamProfileRepository extends BaseNameCrudRepository<CFExamProfileDao, CFExamProfile> {

    public CFExamProfileRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().cfExamProfileDao());
    }
}
