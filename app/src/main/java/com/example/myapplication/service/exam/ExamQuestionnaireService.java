package com.example.myapplication.service.exam;

import java.util.List;

public interface ExamQuestionnaireService<T> {

    boolean examProcessedOK(T item);
    boolean examProcessedFail(T item);



}
