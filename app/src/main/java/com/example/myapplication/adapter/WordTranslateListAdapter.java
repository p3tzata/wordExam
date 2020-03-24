package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.wordActivity.ShowForeignWordActivity;
import com.example.myapplication.activity.wordActivity.ShowNativeWordActivity;
import com.example.myapplication.activity.wordActivity.UpdateWordMenuActivity;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.utitliy.MenuUtility;

import java.util.List;

import static android.content.Context.WINDOW_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class WordTranslateListAdapter extends RecyclerView.Adapter<WordTranslateListAdapter.WordViewHolder>  {



    private final LayoutInflater mInflater;
    private Context context;

    private List<Word> mWords; // Cached copy of words
    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;
    private Object WindowManager;

    public WordTranslateListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;

    }




    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWordString());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    public void setWords(List<Word> words, TranslationAndLanguages translationAndLanguages, Long fromLanguageID){
        mWords = words;
        this.translationAndLanguages=translationAndLanguages;
        this.fromLanguageID=fromLanguageID;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }





    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.tx_word_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.inflate(R.menu.delete_menu);
            //adding click listener
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_delete:

                            Word word = mWords.get(getAdapterPosition());
                            String debug=null;


                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Delete Confirm") .setTitle(word.getWordString());

                            //Setting message manually and performing action on button click
                            builder.setMessage("Are you sure ?")
                                    //.setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //finish();
                                            Toast.makeText(context,"you choose yes action for alertbox :" + word.getWordString(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                                            Toast.makeText(context,"you choose no action for alertbox",
                                                    Toast.LENGTH_SHORT).show();
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
