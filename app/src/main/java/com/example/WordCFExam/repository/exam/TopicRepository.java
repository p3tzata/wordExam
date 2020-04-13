package com.example.WordCFExam.repository.exam;

import android.app.Application;

import com.example.WordCFExam.dao.TopicDao;
import com.example.WordCFExam.entity.exam.Topic;
import com.example.WordCFExam.repository.BaseNameCrudRepository;

public class TopicRepository extends BaseNameCrudRepository<TopicDao, Topic> {

    public TopicRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().topicDao());
    }
}
