package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.wordActivity.BaseEditableAppCompatActivity;
import com.example.myapplication.activity.wordActivity.UpdateWordHelpSentenceActivity;
import com.example.myapplication.entity.HelpSentence;

import java.util.List;

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
