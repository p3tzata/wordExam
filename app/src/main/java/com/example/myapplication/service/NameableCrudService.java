package com.example.myapplication.service;

import java.util.List;

public interface NameableCrudService<T> extends CrudService<T> {

    public List<T> findAllOrderAlphabetic(Object... objects);

}
