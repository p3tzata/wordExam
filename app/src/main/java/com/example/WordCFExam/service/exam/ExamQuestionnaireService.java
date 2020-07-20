package com.example.WordCFExam.service.exam;

public interface ExamQuestionnaireService<T> {

    boolean examProcessedOK(T item);
    boolean examProcessedFail(T item);
    boolean examProcessedFailTotal(T item);



}
