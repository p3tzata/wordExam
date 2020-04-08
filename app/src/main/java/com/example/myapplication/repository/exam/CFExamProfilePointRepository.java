package com.example.myapplication.repository.exam;

import android.app.Application;

import com.example.myapplication.dao.CFExamProfileDao;
import com.example.myapplication.dao.CFExamProfilePointDao;
import com.example.myapplication.entity.exam.CFExamProfile;
import com.example.myapplication.entity.exam.CFExamProfilePoint;
import com.example.myapplication.repository.BaseNameCrudRepository;

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
        return getDao().findAllByOrderByLastOfPeriod(currentPoint.getCFExamProfileID());

    }
}
