package com.example.myapplication.service.base;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myapplication.repository.BaseCrudRepository;

public abstract class BaseCrudService<R extends BaseCrudRepository,T> extends AndroidViewModel implements CrudService<T>{

    private R repository;

    public BaseCrudService(@NonNull Application application, R repository) {
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

    public Integer delete(T entity) {return repository.delete(entity);}

    public T findByID(Long ID) {
        return (T) repository.findByID(ID);
    }



}
