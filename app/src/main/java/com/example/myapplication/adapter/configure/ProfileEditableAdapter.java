package com.example.myapplication.adapter.configure;

import com.example.myapplication.activity.BaseEditableAppCompatActivity;
import com.example.myapplication.adapter.BaseEditableAdapter;
import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Profile;

//public class HelpSentenceEditableAdapterTEST extends RecyclerView.Adapter<HelpSentenceEditableAdapterTEST.ItemViewHolder>  {
public class ProfileEditableAdapter extends BaseEditableAdapter<BaseEditableAppCompatActivity, Profile,BaseEditableAdapter.ItemViewHolder> {

    public ProfileEditableAdapter(BaseEditableAppCompatActivity context) {
        super(context);
    }

    @Override
    protected void callOnDeleteMenuSelected(Profile selectedItem) {

        context.callDeleteConfirmDialog(selectedItem);
    }

    @Override
    protected void callOnUpdateMenuSelected(Profile selectedItem) {

        context.callPopUpDialog(true,selectedItem);
    }

}
