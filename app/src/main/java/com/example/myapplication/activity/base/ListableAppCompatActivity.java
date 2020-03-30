package com.example.myapplication.activity.base;

import android.view.View;

public interface ListableAppCompatActivity<T>  {


    void recyclerViewOnClickHandler(View v, T selectedItem);

    void onSearchBarGetItemsExecutorHandler(String contains);

}
