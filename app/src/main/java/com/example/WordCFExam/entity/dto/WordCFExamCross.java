package com.example.WordCFExam.entity.dto;

import android.os.TestLooperManager;

import com.example.WordCFExam.entity.TextLabelable;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;

import java.io.Serializable;

public class WordCFExamCross implements TextLabelable, Serializable {

    private Word word;
    private CFExamProfilePoint cfExamProfilePoint;

    public WordCFExamCross(Word word, CFExamProfilePoint cfExamProfilePoint) {
        this.word = word;
        this.cfExamProfilePoint = cfExamProfilePoint;
    }

    public Word getWord() {
        return word;
    }

    public CFExamProfilePoint getCfExamProfilePoint() {
        return cfExamProfilePoint;
    }

    @Override
    public String getLabelText() {
        if (cfExamProfilePoint.getCFExamProfilePointID()>0L) {
            return String.format("%s [cf: %s]",word.getWordString(),cfExamProfilePoint.getLabelText());
        } else {
            return String.format("%s",word.getWordString());
        }
    }
}
