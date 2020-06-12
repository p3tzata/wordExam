package com.example.WordCFExam.entity.dto;

import com.example.WordCFExam.entity.TextLabelable;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.Topic;

import java.io.Serializable;

public class TopicCFExamCross implements TextLabelable, Serializable {

    private Topic topic;
    private CFExamProfilePoint cfExamProfilePoint;

    public TopicCFExamCross(Topic topic, CFExamProfilePoint cfExamProfilePoint) {
        this.topic = topic;
        this.cfExamProfilePoint = cfExamProfilePoint;
    }

    public Topic getTopic() {
        return topic;
    }

    public CFExamProfilePoint getCfExamProfilePoint() {
        return cfExamProfilePoint;
    }

    @Override
    public String getLabelText() {
        if (cfExamProfilePoint.getCFExamProfilePointID()>0L) {
            return String.format("%s [cf: %s]",topic.getLabelText(),cfExamProfilePoint.getLabelText());
        } else {
            return String.format("%s",topic.getLabelText());
        }
    }
}
