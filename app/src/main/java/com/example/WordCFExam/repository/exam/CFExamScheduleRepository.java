package com.example.WordCFExam.repository.exam;

import android.app.Application;

import com.example.WordCFExam.dao.CFExamProfileDao;
import com.example.WordCFExam.dao.CFExamScheduleDao;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamSchedule;
import com.example.WordCFExam.repository.BaseNameCrudRepository;

public class CFExamScheduleRepository extends BaseNameCrudRepository<CFExamScheduleDao, CFExamSchedule> {

    public CFExamScheduleRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().cfExamScheduleDao());
    }
}
