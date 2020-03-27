package com.example.myapplication.adapter.configure;

import android.view.SurfaceControl;

import com.example.myapplication.activity.BaseEditableAppCompatActivity;
import com.example.myapplication.adapter.BaseEditableAdapter;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Translation;

//public class HelpSentenceEditableAdapterTEST extends RecyclerView.Adapter<HelpSentenceEditableAdapterTEST.ItemViewHolder>  {
public class TranslationEditableAdapter extends
        BaseEditableAdapter<BaseEditableAppCompatActivity, Translation,BaseEditableAdapter.ItemViewHolder> {

    public TranslationEditableAdapter(BaseEditableAppCompatActivity context) {
        super(context);
    }

    @Override
    protected void callOnDeleteMenuSelected(Translation selectedItem) {

        context.callDeleteConfirmDialog(selectedItem);
    }

    @Override
    protected void callOnUpdateMenuSelected(Translation selectedItem) {

        context.callPopUpDialog(true,selectedItem);
    }

}
