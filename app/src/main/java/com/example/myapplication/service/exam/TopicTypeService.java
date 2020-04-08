package com.example.myapplication.service.exam;

import android.app.Application;

import com.example.myapplication.entity.exam.Topic;
import com.example.myapplication.entity.exam.TopicType;
import com.example.myapplication.repository.exam.TopicRepository;
import com.example.myapplication.repository.exam.TopicTypeRepository;
import com.example.myapplication.service.base.BaseNameCrudService;
import com.example.myapplication.service.base.NameableCrudService;


public class TopicTypeService extends BaseNameCrudService<TopicTypeRepository, TopicType>
implements NameableCrudService<TopicType> {

    public TopicTypeService(Application application) {
        super(application,new TopicTypeRepository(application));
    }


}
