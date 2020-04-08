package com.example.myapplication.service.exam;

import android.app.Application;

import com.example.myapplication.entity.exam.CFExamProfile;
import com.example.myapplication.entity.exam.CFExamProfilePoint;
import com.example.myapplication.repository.exam.CFExamProfilePointRepository;
import com.example.myapplication.repository.exam.CFExamProfileRepository;
import com.example.myapplication.service.base.BaseNameCrudService;
import com.example.myapplication.service.base.NameableCrudService;

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
        List<CFExamProfilePoint> allByOrderByLastOfPeriod = getRepository().findAllByOrderByLastOfPeriod(currentPoint);
        if (allByOrderByLastOfPeriod!=null) {
            return allByOrderByLastOfPeriod.get(0);
        } else {
            return null;
        }

    }
}
