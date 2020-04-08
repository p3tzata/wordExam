package com.example.myapplication.service.exam;

import android.app.Application;

import com.example.myapplication.entity.exam.CFExamProfile;
import com.example.myapplication.entity.exam.Topic;
import com.example.myapplication.repository.exam.CFExamProfileRepository;
import com.example.myapplication.repository.exam.TopicRepository;
import com.example.myapplication.service.base.BaseNameCrudService;
import com.example.myapplication.service.base.NameableCrudService;


public class TopicService extends BaseNameCrudService<TopicRepository, Topic>
implements NameableCrudService<Topic> {

    public TopicService(Application application) {
        super(application,new TopicRepository(application));
    }


}
