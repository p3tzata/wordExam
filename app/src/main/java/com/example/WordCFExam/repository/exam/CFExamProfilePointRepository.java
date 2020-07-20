package com.example.WordCFExam.repository.exam;

import android.app.Application;

import com.example.WordCFExam.dao.CFExamProfilePointDao;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.CFExamProfilePointCross;
import com.example.WordCFExam.repository.BaseNameCrudRepository;

import java.util.List;

public class CFExamProfilePointRepository extends BaseNameCrudRepository<CFExamProfilePointDao, CFExamProfilePoint> {

    public CFExamProfilePointRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().cfExamProfilePointDao());
    }

    public List<CFExamProfilePoint> findByGreatLastOfPeriod(CFExamProfilePoint item) {
        return super.getDao().findByGreatLastOfPeriod(item.getCFExamProfileID(),item.getCFExamProfilePointID(),item.getLastOfPeriodInMinute());

    }

    public List<CFExamProfilePoint> findAllByOrderByLastOfPeriod(CFExamProfilePoint currentPoint) {
       return findAllByOrderByLastOfPeriod(currentPoint.getCFExamProfileID());

    }

    public List<CFExamProfilePoint> findAllByOrderByLastOfPeriod(Long profilePointID) {
        return getDao().findAllByOrderByLastOfPeriod(profilePointID);
    }


    public List<CFExamProfilePoint> findPreviousLastOfPeriod(Long currentProfileID,Long currentProfilePointID,Long lastOfPeriodInMinute){
        return getDao().findPreviousLastOfPeriod(currentProfileID,currentProfilePointID,lastOfPeriodInMinute);
    }

    public CFExamProfilePointCross findCrossByID(Long ID){
        return getDao().findCrossByID(ID);
    }


}
