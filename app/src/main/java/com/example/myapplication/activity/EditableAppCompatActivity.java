package com.example.myapplication.activity;

import android.view.View;

public interface EditableAppCompatActivity<T> extends ListableAppCompatActivity<T>  {

     void handlerDeleteClick(T selectedItem);

     void handlerCreateUpdateClick(boolean isEditableMode,T selectedItem);

     void callShowCrudMenu(View v, T selectedItem);

}
