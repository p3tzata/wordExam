package com.example.WordCFExam.service.exam;

import android.app.Application;

import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaireCross;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.repository.exam.CFExamTopicQuestionnaireRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class CFExamTopicQuestionnaireService extends
        BaseExamQuestionnaireService<CFExamTopicQuestionnaireRepository, CFExamTopicQuestionnaire> {

    private CFExamProfilePointService cfExamProfilePointService;


    public CFExamTopicQuestionnaireService(Application application) {
        super(application,new CFExamTopicQuestionnaireRepository(application));
        cfExamProfilePointService= FactoryUtil.createCFExamProfilePointService(getApplication());
    }



    @Override
    public boolean examProcessedOK(CFExamTopicQuestionnaire item) {

        CFExamProfilePoint currentPoint = cfExamProfilePointService.findByID(item.getCurrentCFExamProfilePointID());

        if (currentPoint.getIsLoopRepeat()) {
            item.setEntryPointDateTime(Calendar.getInstance().getTime());
            item.setPostponeInMinute(null);
            super.update(item);
        } else {
            CFExamProfilePoint nextProfilePoint = cfExamProfilePointService.findNextProfilePoint(item.getCurrentCFExamProfilePointID());
            if (nextProfilePoint==null) {
                super.delete(item);
            } else {
                item.setCurrentCFExamProfilePointID(nextProfilePoint.getCFExamProfilePointID());
                item.setEntryPointDateTime(Calendar.getInstance().getTime());
                item.setPostponeInMinute(null);
                super.update(item);
            }
        }
        return true;
    }


    @Override
    public boolean examProcessedFail(CFExamTopicQuestionnaire item) {

        CFExamProfilePoint currentPoint = cfExamProfilePointService.findByID(item.getCurrentCFExamProfilePointID());
        CFExamProfilePoint firstProfilePoint = cfExamProfilePointService.findFirstProfilePoint(currentPoint);
        if (firstProfilePoint!=null) {
            item.setCurrentCFExamProfilePointID(firstProfilePoint.getCFExamProfilePointID());
            item.setPostponeInMinute(null);
            item.setEntryPointDateTime(Calendar.getInstance().getTime());
            super.update(item);
            return true;
        }
        return false;


    }

    @Override
    public boolean examProcessedFailTotal(CFExamTopicQuestionnaire item) {

        CFExamProfilePoint currentPoint = cfExamProfilePointService.findByID(item.getCurrentCFExamProfilePointID());
        CFExamProfilePoint firstProfilePoint = cfExamProfilePointService.findFirstProfilePoint(currentPoint);
        if (firstProfilePoint!=null) {
            item.setCurrentCFExamProfilePointID(firstProfilePoint.getCFExamProfilePointID());
            item.setPostponeInMinute(null);
            item.setEntryPointDateTime(Calendar.getInstance().getTime());
            super.update(item);
            return true;
        }
        return false;


    }


    public CFExamTopicQuestionnaire findByTopicID(Long topicID){
        return getRepository().findByTopicID(topicID);
    }

    public boolean create(Long topicID, CFExamProfile cfExamProfile) {


        CFExamProfilePoint firstProfilePoint = cfExamProfilePointService.findFirstProfilePoint(cfExamProfile.getCFExamProfileID());
        CFExamTopicQuestionnaire cfExamTopicQuestionnaire = new CFExamTopicQuestionnaire();
        cfExamTopicQuestionnaire.setCurrentCFExamProfilePointID(firstProfilePoint.getCFExamProfilePointID());
        cfExamTopicQuestionnaire.setTopicID(topicID);
        cfExamTopicQuestionnaire.setEntryPointDateTime(Calendar.getInstance().getTime());
        Long insert = this.insert(cfExamTopicQuestionnaire);
        if (insert>0L) {
            return true;
        } else {
            return false;
        }



    }



    public List<Profile> findAllProfileNeedProceed(){
        DateFormat dateFormat = new SimpleDateFormat("HH");
        String formattedEntryPointDate = dateFormat.format(Calendar.getInstance().getTime());
        int currentHour=Integer.valueOf(formattedEntryPointDate);
        return getRepository().findAllProfileNeedProceed(System.currentTimeMillis(),currentHour);
    }

    public List<CFExamTopicQuestionnaireCross> findAllNeedProceed(Long profileID){

        return getRepository().findAllNeedProceed(profileID,System.currentTimeMillis());
    }











}
