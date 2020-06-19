package com.example.WordCFExam.service;

import android.app.Application;

import com.example.WordCFExam.entity.dto.TopicCFExamCross;
import com.example.WordCFExam.entity.Topic;
import com.example.WordCFExam.repository.TopicRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;
import com.example.WordCFExam.service.base.NameableCrudService;

import java.util.List;


public class TopicService extends BaseNameCrudService<TopicRepository, Topic>
implements NameableCrudService<Topic> {

    public TopicService(Application application) {
        super(application,new TopicRepository(application));
    }

    public List<TopicCFExamCross> findByTopicStringContainsAndParentIDCFExamCross(Long parentID, String topicStringContain){
        return super.getRepository().findByTopicStringContainsAndParentIDCFExamCross(parentID, topicStringContain);
    }



}
