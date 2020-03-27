package com.example.myapplication.adapter.configure;

import com.example.myapplication.activity.BaseEditableAppCompatActivity;
import com.example.myapplication.adapter.BaseEditableAdapter;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;


public class LanguageEditableAdapter extends BaseEditableAdapter<BaseEditableAppCompatActivity, Language,BaseEditableAdapter.ItemViewHolder> {

    public LanguageEditableAdapter(BaseEditableAppCompatActivity context) {
        super(context);
    }

    @Override
    protected void callOnDeleteMenuSelected(Language selectedItem) {

        context.callDeleteConfirmDialog(selectedItem);
    }

    @Override
    protected void callOnUpdateMenuSelected(Language selectedItem) {

        context.callPopUpDialog(true,selectedItem);
    }

}
