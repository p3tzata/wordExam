package com.example.myapplication.repository.exam;

import android.app.Application;

import com.example.myapplication.dao.TopicDao;
import com.example.myapplication.dao.TopicTypeDao;
import com.example.myapplication.entity.exam.Topic;
import com.example.myapplication.entity.exam.TopicType;
import com.example.myapplication.repository.BaseNameCrudRepository;

public class TopicTypeRepository extends BaseNameCrudRepository<TopicTypeDao, TopicType> {

    public TopicTypeRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().topicTypeDao());
    }
}
