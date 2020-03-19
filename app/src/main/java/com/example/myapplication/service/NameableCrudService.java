package com.example.myapplication.service;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myapplication.repository.NameableCrudRepository;

import java.util.List;

public abstract class NameableCrudService<R extends NameableCrudRepository,T> extends CrudService<R,T> {

    public NameableCrudService(@NonNull Application application, R repository) {
        super(application, repository);

    }

    public List<T> findAllOrderAlphabetic() {
        return (List<T>) super.getRepository().findAllOrderAlphabetic();
    }



}
