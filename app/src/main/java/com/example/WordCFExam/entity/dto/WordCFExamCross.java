package com.example.WordCFExam.entity.dto;

import android.os.TestLooperManager;

import com.example.WordCFExam.entity.TextLabelable;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;

public class WordCFExamCross implements TextLabelable, Serializable {

    private Word word;
    private CFExamProfilePoint cfExamProfilePoint;
    private CFExamWordQuestionnaire cfExamWordQuestionnaire;

    public WordCFExamCross(Word word, CFExamProfilePoint cfExamProfilePoint) {
        this.word = word;
        this.cfExamProfilePoint = cfExamProfilePoint;
    }

    public WordCFExamCross(Word word, CFExamProfilePoint cfExamProfilePoint, CFExamWordQuestionnaire cfExamWordQuestionnaire) {

        this.word = word;
        this.cfExamProfilePoint = cfExamProfilePoint;
        this.cfExamWordQuestionnaire=cfExamWordQuestionnaire;

    }

    public Word getWord() {
        return word;
    }

    public CFExamProfilePoint getCfExamProfilePoint() {
        return cfExamProfilePoint;
    }

    @Override
    public String getLabelText() {
        if (cfExamProfilePoint.getCFExamProfilePointID()>0L && cfExamWordQuestionnaire!=null ) {

            Long EntryPoint= cfExamWordQuestionnaire.getEntryPointDateTime().getTime();
            Long PostponeTimestamp = Long.valueOf(cfExamWordQuestionnaire.getPostponeInMinute())*60*1000;
            Long lastOfPeriodTimeStamp= cfExamProfilePoint.getLastOfPeriodInMinute()*60*1000;

            if (PostponeTimestamp>0) {
                lastOfPeriodTimeStamp=0L;
            }

            Long nextRun = EntryPoint+PostponeTimestamp+lastOfPeriodTimeStamp;
            Date nextRunDate = new Date(nextRun);
           // String debugStop3a=null;
            DateFormat dateFormat = new SimpleDateFormat("dd MMM");
            String nextDate = dateFormat.format(nextRunDate);
            return String.format("%s [cf: %s, next: %s]",word.getWordString(),cfExamProfilePoint.getLabelText(),nextDate);

        } else if  (cfExamProfilePoint.getCFExamProfilePointID()>0L) {
            return String.format("%s [cf: %s]",word.getWordString(),cfExamProfilePoint.getLabelText());
        } else {
            return String.format("%s",word.getWordString());
        }
    }
}
