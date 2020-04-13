package com.example.WordCFExam.service.exam;

import android.app.Application;

import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.CFExamProfilePointCross;
import com.example.WordCFExam.repository.exam.CFExamProfilePointRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;
import com.example.WordCFExam.service.base.NameableCrudService;

import java.util.List;


public class CFExamProfilePointService extends BaseNameCrudService<CFExamProfilePointRepository, CFExamProfilePoint>
implements NameableCrudService<CFExamProfilePoint> {

    public CFExamProfilePointService(Application application) {
        super(application,new CFExamProfilePointRepository(application));
    }


    public CFExamProfilePoint findNextProfilePoint(Long cfExamProfilePointID) {
        CFExamProfilePoint cfExamProfilePoint = getRepository().findByID(cfExamProfilePointID);
        List<CFExamProfilePoint> lastOfPeriod = getRepository().findByGreatLastOfPeriod(cfExamProfilePoint);
        if (lastOfPeriod!=null) {
            return lastOfPeriod.get(0);
        } else {
            return null;
        }

    }

    public CFExamProfilePoint findFirstProfilePoint(CFExamProfilePoint currentPoint) {

        return findFirstProfilePoint(currentPoint.getCFExamProfileID());
    }

    public CFExamProfilePoint findFirstProfilePoint(Long profilePointID) {
        List<CFExamProfilePoint> allByOrderByLastOfPeriod = getRepository().findAllByOrderByLastOfPeriod(profilePointID);
        if (allByOrderByLastOfPeriod!=null) {
            return allByOrderByLastOfPeriod.get(0);
        } else {
            return null;
        }
    }

    public CFExamProfilePointCross findCrossByID(Long profilePointID){
        return getRepository().findCrossByID(profilePointID);
    }








}
