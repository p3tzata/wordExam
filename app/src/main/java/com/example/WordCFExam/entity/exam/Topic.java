package com.example.WordCFExam.entity.exam;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.WordCFExam.entity.TextLabelable;

import java.io.Serializable;

@Entity(
        indices = {
                @Index(value = "topicTypeID"),
                @Index(unique = true,value = {"topicTypeID","topicQuestion","topicQuestion"})},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = TopicType.class, parentColumns = "topicTypeID", childColumns = "topicTypeID")
}
)
public class Topic implements Serializable, TextLabelable {

    @PrimaryKey
    private Long topicID;

    @NonNull
    private Long topicTypeID;

    @NonNull
    private String topicQuestion;

    @NonNull
    private String topicAnswer;




    public Topic() {
    }


    public Long getTopicID() {
        return topicID;
    }

    public void setTopicID(Long topicID) {
        this.topicID = topicID;
    }

    @NonNull
    public Long getTopicTypeID() {
        return topicTypeID;
    }

    public void setTopicTypeID(@NonNull Long topicTypeID) {
        this.topicTypeID = topicTypeID;
    }

    @NonNull
    public String getTopicQuestion() {
        return topicQuestion;
    }

    public void setTopicQuestion(@NonNull String topicQuestion) {
        this.topicQuestion = topicQuestion;
    }

    @NonNull
    public String getTopicAnswer() {
        return topicAnswer;
    }

    public void setTopicAnswer(@NonNull String topicAnswer) {
        this.topicAnswer = topicAnswer;
    }

    @Override
    public String getLabelText() {
        return getTopicQuestion();
    }
}
