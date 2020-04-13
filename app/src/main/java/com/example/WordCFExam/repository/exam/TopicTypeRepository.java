package com.example.WordCFExam.repository.exam;

import android.app.Application;

import com.example.WordCFExam.dao.TopicTypeDao;
import com.example.WordCFExam.entity.exam.TopicType;
import com.example.WordCFExam.repository.BaseNameCrudRepository;

public class TopicTypeRepository extends BaseNameCrudRepository<TopicTypeDao, TopicType> {

    public TopicTypeRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().topicTypeDao());
    }
}
