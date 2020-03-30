package com.example.myapplication.service.base;

public interface CrudService<T>  {

    //public R getRepository();

     Long insert(T entity);

     Integer update(T entity);

    Integer delete(T entity);
    T findByID(Long ID);


}
