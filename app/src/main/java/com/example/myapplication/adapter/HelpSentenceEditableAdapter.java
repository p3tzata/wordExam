package com.example.myapplication.adapter;

import com.example.myapplication.activity.BaseEditableAppCompatActivity;
import com.example.myapplication.entity.HelpSentence;

//public class HelpSentenceEditableAdapterTEST extends RecyclerView.Adapter<HelpSentenceEditableAdapterTEST.ItemViewHolder>  {
public class HelpSentenceEditableAdapter extends BaseEditableAdapter<BaseEditableAppCompatActivity,HelpSentence,BaseEditableAdapter.ItemViewHolder>  {

    public HelpSentenceEditableAdapter(BaseEditableAppCompatActivity context) {
        super(context);
    }

    @Override
    protected void callOnDeleteMenuSelected(HelpSentence selectedItem) {
        /*if (context instanceof UpdateWordHelpSentenceActivity) {
            UpdateWordHelpSentenceActivity context = (UpdateWordHelpSentenceActivity) super.context;
            context.callDeleteConfirmDialog(selectedItem);
        }

         */
        context.callDeleteConfirmDialog(selectedItem);
    }

    @Override
    protected void callOnUpdateMenuSelected(HelpSentence selectedItem) {
        /*if (context instanceof UpdateWordHelpSentenceActivity) {
            UpdateWordHelpSentenceActivity context = (UpdateWordHelpSentenceActivity) super.context;
            context.callPopUpDialog(true,selectedItem);
        }*/
        context.callPopUpDialog(true,selectedItem);
    }

}
