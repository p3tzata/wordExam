package com.example.WordCFExam.utitliy;

public interface DbExecutor<T> {

    public T doInBackground();

    public void onPostExecute(T item);

}
