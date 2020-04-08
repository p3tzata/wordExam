package com.example.myapplication.repository.exam;

import android.app.Application;

import com.example.myapplication.dao.CFExamProfileDao;
import com.example.myapplication.dao.TopicDao;
import com.example.myapplication.entity.exam.CFExamProfile;
import com.example.myapplication.entity.exam.Topic;
import com.example.myapplication.repository.BaseNameCrudRepository;

public class TopicRepository extends BaseNameCrudRepository<TopicDao, Topic> {

    public TopicRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().topicDao());
    }
}
