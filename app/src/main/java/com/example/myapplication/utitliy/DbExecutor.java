package com.example.myapplication.utitliy;

public interface DbExecutor<T> {

    public T doInBackground();

    public void onPostExecute(T item);

}
