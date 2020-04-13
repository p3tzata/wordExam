package com.example.WordCFExam.service.exam;

import android.app.Application;

import com.example.WordCFExam.entity.exam.TopicType;
import com.example.WordCFExam.repository.exam.TopicTypeRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;
import com.example.WordCFExam.service.base.NameableCrudService;


public class TopicTypeService extends BaseNameCrudService<TopicTypeRepository, TopicType>
implements NameableCrudService<TopicType> {

    public TopicTypeService(Application application) {
        super(application,new TopicTypeRepository(application));
    }


}
