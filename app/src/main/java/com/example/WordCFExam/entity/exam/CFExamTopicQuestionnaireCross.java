package com.example.WordCFExam.entity.exam;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.WordCFExam.entity.TextLabelable;

import java.io.Serializable;

public class CFExamTopicQuestionnaireCross implements Serializable,TextLabelable {

    @Embedded
    public CFExamTopicQuestionnaire cfExamQuestionnaire;

    @Relation(
            parentColumn = "topicID",
            entityColumn = "topicID"
    )
    public Topic topic;

    public CFExamTopicQuestionnaire getCfExamQuestionnaire() {
        return cfExamQuestionnaire;
    }

    public void setCfExamQuestionnaire(CFExamTopicQuestionnaire cfExamQuestionnaire) {
        this.cfExamQuestionnaire = cfExamQuestionnaire;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public String getLabelText() {
        return String.format("%s",
                getTopic().getTopicQuestion());
    }
}
