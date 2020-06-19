package com.example.WordCFExam.repository;

import android.app.Application;
import android.database.Cursor;

import com.example.WordCFExam.dao.TopicDao;
import com.example.WordCFExam.entity.dto.TopicCFExamCross;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicRepository extends BaseNameCrudRepository<TopicDao, Topic> {

    public TopicRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().topicDao());
    }

    public List<TopicCFExamCross> findByTopicStringContainsAndParentIDCFExamCross(Long parentID, String topicStringContain){
        List<TopicCFExamCross> listWord = new ArrayList<>();

        Cursor cursor = super.getDao().findByTopicStringContainsAndParentIDCFExamCross(parentID, topicStringContain);
        if (cursor != null && cursor.moveToFirst()){ //make sure you got results, and move to first row
            do{
                Long topicID  = (Long) cursor.getLong(cursor.getColumnIndexOrThrow("topicID"));
                String topicQuestion  = (String) cursor.getString(cursor.getColumnIndexOrThrow("topicQuestion"));
                String topicAnswer =  (String) cursor.getString(cursor.getColumnIndexOrThrow("topicAnswer"));
                Long topicTypeID = (Long) cursor.getLong(cursor.getColumnIndexOrThrow("topicTypeID"));


                Integer isLoopRepeat= cursor.getInt(cursor.getColumnIndexOrThrow("isLoopRepeat"));
                Long CFExamProfileID =  cursor.getLong(cursor.getColumnIndexOrThrow("CFExamProfileID"));
                Long CFExamProfilePointID =  cursor.getLong(cursor.getColumnIndexOrThrow("CFExamProfilePointID"));
                Long lastOfPeriodInMinute =  cursor.getLong(cursor.getColumnIndexOrThrow("lastOfPeriodInMinute"));
                String name =  cursor.getString(cursor.getColumnIndexOrThrow("name"));

                Topic topic = (Topic) new Topic() {{
                    setTopicID(topicID);
                    setTopicQuestion(topicQuestion);
                    setTopicAnswer(topicAnswer);
                    setTopicTypeID(topicTypeID);

                }};
                CFExamProfilePoint cfExamProfilePoint=null;
                if (CFExamProfilePointID!=null) {
                    cfExamProfilePoint = new CFExamProfilePoint() {{
                        setCFExamProfileID(CFExamProfileID);
                        setCFExamProfilePointID(CFExamProfilePointID);
                        setIsLoopRepeat(isLoopRepeat == 1 ? true : false);
                        setLastOfPeriodInMinute(lastOfPeriodInMinute);
                        setName(name);
                    }};
                }


                listWord.add(new TopicCFExamCross(topic, cfExamProfilePoint ));




            } while (cursor.moveToNext()); //move to next row in the query result

        }

        return listWord;

    }



}
