package com.example.WordCFExam.entity.exam;

import androidx.room.Embedded;

import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.TextLabelable;

public class RandomExamHelpSentenceNativeCross implements TextLabelable {

    @Embedded
    private HelpSentence helpSentence;

    public RandomExamHelpSentenceNativeCross() {
    }

    public HelpSentence getHelpSentence() {
        return helpSentence;
    }

    public void setHelpSentence(HelpSentence helpSentence) {
        this.helpSentence = helpSentence;
    }

    @Override
    public String getLabelText() {
        return helpSentence.getSentenceTranslation();
    }
}
