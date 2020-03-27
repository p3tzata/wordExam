package com.example.myapplication.activity;

import android.view.View;

public interface ListableAppCompatActivity<T>  {

    void recyclerViewOnClickHandler(View v, T selectedItem);

}
