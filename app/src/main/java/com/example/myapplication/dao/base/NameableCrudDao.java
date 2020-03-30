package com.example.myapplication.dao.base;

import com.example.myapplication.dao.base.CrudDao;

import java.util.List;

public interface NameableCrudDao<Type> extends CrudDao<Type> {

   List<Type> findAllOrderAlphabetic(Long parentID,String contains);

}
