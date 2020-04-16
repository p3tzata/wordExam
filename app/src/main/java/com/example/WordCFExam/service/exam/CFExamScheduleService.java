package com.example.WordCFExam.service.exam;

import android.app.Application;

import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamSchedule;
import com.example.WordCFExam.repository.exam.CFExamProfileRepository;
import com.example.WordCFExam.repository.exam.CFExamScheduleRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;
import com.example.WordCFExam.service.base.NameableCrudService;


public class CFExamScheduleService extends BaseNameCrudService<CFExamScheduleRepository, CFExamSchedule>
implements NameableCrudService<CFExamSchedule> {

    public CFExamScheduleService(Application application) {
        super(application,new CFExamScheduleRepository(application));
    }


}
