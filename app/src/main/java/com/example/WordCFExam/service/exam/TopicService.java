package com.example.WordCFExam.service.exam;

import android.app.Application;

import com.example.WordCFExam.entity.exam.Topic;
import com.example.WordCFExam.repository.exam.TopicRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;
import com.example.WordCFExam.service.base.NameableCrudService;


public class TopicService extends BaseNameCrudService<TopicRepository, Topic>
implements NameableCrudService<Topic> {

    public TopicService(Application application) {
        super(application,new TopicRepository(application));
    }


}
