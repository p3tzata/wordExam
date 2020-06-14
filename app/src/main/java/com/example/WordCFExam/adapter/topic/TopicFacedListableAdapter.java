package com.example.WordCFExam.adapter.topic;

import com.example.WordCFExam.activity.base.BaseListableAppCompatActivityFaced;
import com.example.WordCFExam.adapter.BaseRecycleAdapterFaced;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.TopicCFExamCross;
import com.example.WordCFExam.entity.dto.WordCFExamCross;
import com.example.WordCFExam.entity.exam.Topic;

public class TopicFacedListableAdapter extends
        BaseRecycleAdapterFaced<BaseListableAppCompatActivityFaced, TopicCFExamCross,Topic, BaseRecycleAdapterFaced.ItemViewHolder> {

    public TopicFacedListableAdapter(BaseListableAppCompatActivityFaced context) {
        super(context);
    }

    @Override
    public Topic castViewTypeToEntityType(TopicCFExamCross selectedItem) {



        Topic topic = new Topic();
        topic.setTopicID(selectedItem.getTopic().getTopicID());
        topic.setTopicQuestion(selectedItem.getTopic().getTopicQuestion());
        topic.setTopicTypeID(selectedItem.getTopic().getTopicTypeID());
        topic.setTopicAnswer(selectedItem.getTopic().getTopicAnswer());
        topic.setTopicTypeID(selectedItem.getTopic().getTopicTypeID());

        return topic;
    }

}
