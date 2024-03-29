package com.example.WordCFExam.service.base;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.WordCFExam.repository.BaseNameCrudRepository;

import java.util.List;

public abstract class BaseNameCrudService<R extends BaseNameCrudRepository,T> extends BaseCrudService<R,T>
implements NameableCrudService<T> {

    public BaseNameCrudService(@NonNull Application application, R repository) {
        super(application, repository);

    }

    public List<T> findAllOrderAlphabetic(Long parentID,String contains) {
        return (List<T>) super.getRepository().findAllOrderAlphabetic(parentID,contains);
    }



}
