package com.example.WordCFExam.repository;

import android.app.Application;

import com.example.WordCFExam.dao.TopicTypeDao;
import com.example.WordCFExam.entity.TopicType;

import java.util.List;

public class TopicTypeRepository extends BaseNameCrudRepository<TopicTypeDao, TopicType> {

    public TopicTypeRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().topicTypeDao());
    }


    public List<TopicType> findAllOrderAlphabeticByParent(Long parentID, Long parentTopicTypeID, String contains) {

        return super.getDao().findAllOrderAlphabeticByParent(parentID,parentTopicTypeID,contains);


    }




}
