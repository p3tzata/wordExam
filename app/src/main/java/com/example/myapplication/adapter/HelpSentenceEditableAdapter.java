package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.wordActivity.UpdateWordHelpSentenceActivity;

import com.example.myapplication.entity.HelpSentence;


import java.util.List;

public class HelpSentenceEditableAdapter extends RecyclerView.Adapter<HelpSentenceEditableAdapter.ItemViewHolder>  {



    private final LayoutInflater mInflater;
    private Context context;
    private List<HelpSentence> mItems; // Cached copy of items


    public HelpSentenceEditableAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (mItems != null) {
            HelpSentence current = mItems.get(position);
            holder.wordItemView.setText(current.getSentenceString());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Items");
        }
    }

    public void setItems(List<HelpSentence> items){
        mItems = items;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mItems has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }





    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView wordItemView;

        private ItemViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.tx_word_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.inflate(R.menu.popup_crud_menu_update_delete);
            //adding click listener
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    HelpSentence selectedItem = mItems.get(getAdapterPosition());
                    switch (item.getItemId()) {
                        case R.id.menu_delete:

                            if (context instanceof UpdateWordHelpSentenceActivity) {
                                UpdateWordHelpSentenceActivity context = (UpdateWordHelpSentenceActivity) HelpSentenceEditableAdapter.this.context;
                                context.callDeleteConfirmDialog(selectedItem);
                            }



                    break;
                        case R.id.menu_update:

                            if (context instanceof UpdateWordHelpSentenceActivity) {
                                UpdateWordHelpSentenceActivity context = (UpdateWordHelpSentenceActivity) HelpSentenceEditableAdapter.this.context;
                                context.callLoginDialog(true,selectedItem);
                            }

                    }
                   return false;
                }
            });

            popupMenu.show();

        }
    }




}
