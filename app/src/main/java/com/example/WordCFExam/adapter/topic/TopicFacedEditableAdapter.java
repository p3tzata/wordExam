package com.example.WordCFExam.adapter.topic;

import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivityFaced;
import com.example.WordCFExam.adapter.BaseRecycleAdapterFaced;
import com.example.WordCFExam.entity.dto.TopicCFExamCross;
import com.example.WordCFExam.entity.Topic;

public class TopicFacedEditableAdapter extends
        BaseRecycleAdapterFaced<BaseEditableAppCompatActivityFaced, TopicCFExamCross, Topic, BaseRecycleAdapterFaced.ItemViewHolder> {

    public TopicFacedEditableAdapter(BaseEditableAppCompatActivityFaced context) {
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
