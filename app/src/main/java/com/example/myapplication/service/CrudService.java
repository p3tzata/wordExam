package com.example.myapplication.service;

public interface CrudService<T>  {

    //public R getRepository();

    public Long insert(T entity);

    public Integer update(T entity);

    public Integer delete(T entity);

    public T findByID(Long ID);


}
