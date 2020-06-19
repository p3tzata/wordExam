package com.example.WordCFExam.service;

import android.app.Application;
import android.icu.text.UnicodeSetSpanner;

import com.example.WordCFExam.entity.Topic;
import com.example.WordCFExam.entity.TopicType;
import com.example.WordCFExam.repository.TopicTypeRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;
import com.example.WordCFExam.service.base.NameableCrudService;

import java.util.List;


public class TopicTypeService extends BaseNameCrudService<TopicTypeRepository, TopicType>
implements NameableCrudService<TopicType> {

    public TopicTypeService(Application application) {
        super(application,new TopicTypeRepository(application));
    }


    public List<TopicType> findAllOrderAlphabeticByParent(Long parentID,Long parentTopicTypeID, String contains) {
        return (List<TopicType>) super.getRepository().findAllOrderAlphabeticByParent(parentID,parentTopicTypeID,contains);
    }




}
