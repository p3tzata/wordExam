package com.example.myapplication.service.base;

import java.util.List;

public interface NameableCrudService<T> extends CrudService<T> {

    public List<T> findAllOrderAlphabetic(Long parentID,String contains);

}
