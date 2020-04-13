package com.example.WordCFExam.activity.base;

import android.view.View;

public interface ListableAppCompatActivity<T>  {

    void onCreateCustom();

    void recyclerViewOnClickHandler(View v, T selectedItem);

    void onSearchBarGetItemsExecutorHandler(String contains);

}
