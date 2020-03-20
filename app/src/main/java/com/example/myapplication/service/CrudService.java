package com.example.myapplication.service;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myapplication.repository.CrudRepository;

public abstract class CrudService<R extends CrudRepository,T> extends AndroidViewModel {

    private R repository;

    public CrudService(@NonNull Application application, R repository) {
        super(application);
        this.repository=repository;
    }

    public R getRepository() {
        return repository;
    }

    public Long insert(T entity) {
        return repository.inset(entity);
    }

    public Integer update(T entity) {return repository.update(entity);}

    public T findByID(Long ID) {
        return (T) repository.findByID(ID);
    }





}
