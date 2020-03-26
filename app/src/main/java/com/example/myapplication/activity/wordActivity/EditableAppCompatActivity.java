package com.example.myapplication.activity.wordActivity;

public interface EditableAppCompatActivity<T>  {

    public void callDeleteConfirmDialog(T selectedItem);

    public void callPopUpDialog(boolean isEditableMode,T selectedItem);

}
