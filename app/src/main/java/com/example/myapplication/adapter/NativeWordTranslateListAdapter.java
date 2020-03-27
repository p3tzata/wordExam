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
import com.example.myapplication.activity.wordActivity.UpdateWordTranslationActivity;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.service.TranslationWordRelationService;

import java.util.List;

public class NativeWordTranslateListAdapter extends RecyclerView.Adapter<NativeWordTranslateListAdapter.ItemViewHolder>  {



    private final LayoutInflater mInflater;
    private Context context;


    private List<Word> mItems; // Cached copy of words
    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;

    private TranslationWordRelationService translationWordRelationService;

    public NativeWordTranslateListAdapter(Context context) {
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
            Word current = mItems.get(position);
            holder.wordItemView.setText(current.getWordString());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    public void setItems(List<Word> words, TranslationAndLanguages translationAndLanguages, Long fromLanguageID){
        mItems = words;
        this.translationAndLanguages=translationAndLanguages;
        this.fromLanguageID=fromLanguageID;
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
            popupMenu.inflate(R.menu.menu_delete);
            //adding click listener
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_delete:

                            Word nativeWord = mItems.get(getAdapterPosition());
                            String debug=null;


                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage(nativeWord.getWordString());

                            //Setting message manually and performing action on button click
                            builder
                                    //.setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //finish();

                                            if (context instanceof UpdateWordTranslationActivity) {
                                                UpdateWordTranslationActivity context = (UpdateWordTranslationActivity) NativeWordTranslateListAdapter.this.context;
                                                context.deleteRelation(nativeWord);
                                            }


                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                       dialog.cancel();
                                        }
                                    });

                            //dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)

                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            alert.setTitle("Are you sure for deleting");
                            alert.show();
                            //Setting the title manually
                            /*


                            */
                    break;
                    }
                   return false;
                }
            });

            popupMenu.show();

        }
    }




}
