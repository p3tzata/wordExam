package com.example.WordCFExam.activity.base;

import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public interface EditableAppCompatActivity<T> extends ListableAppCompatActivity<T>  {


     void handlerDeleteClick(T selectedItem);

     void handlerCreateUpdateClick(boolean isEditableMode,T selectedItem);

     void handlerViewClick(T selectedItem);

     void callShowCrudMenu(View v, T selectedItem);

     void callShowNewItemButton(FloatingActionButton fab_newItem);

     void updateItem(T item);

     void deleteItem(T item);

      void createItem(T item);


}
