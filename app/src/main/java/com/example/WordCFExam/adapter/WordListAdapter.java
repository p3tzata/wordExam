package com.example.WordCFExam.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.wordActivity.ShowForeignWordActivity;
import com.example.WordCFExam.activity.wordActivity.ShowNativeWordActivity;
import com.example.WordCFExam.activity.wordActivity.UpdateWordMenuActivity;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.utitliy.MenuUtility;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>  {



    private final LayoutInflater mInflater;
    private Context context;
    private List<Word> mWords; // Cached copy of words
    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;

    public WordListAdapter(Context context) {
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
            Intent activity2Intent=null;
            Word word = mWords.get(getAdapterPosition());
            if (MenuUtility.isEditMode(context) && fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
                activity2Intent = new Intent(context, UpdateWordMenuActivity.class);
            } else {

                if (WordListAdapter.this.translationAndLanguages.getForeignLanguage().getLanguageID().equals(
                        WordListAdapter.this.fromLanguageID)) {
                    activity2Intent = new Intent(context, ShowForeignWordActivity.class);
                    activity2Intent.putExtra("translationToLanguageID", translationAndLanguages.getNativeLanguage().getLanguageID());
                } else {
                    activity2Intent = new Intent(context, ShowNativeWordActivity.class);
                    activity2Intent.putExtra("translationToLanguageID", translationAndLanguages.getForeignLanguage().getLanguageID());
                }

            }
            activity2Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity2Intent.putExtra("translationAndLanguages", WordListAdapter.this.translationAndLanguages);
            activity2Intent.putExtra("translationFromLanguageID", WordListAdapter.this.fromLanguageID);
            activity2Intent.putExtra("word", word);
            context.startActivity(activity2Intent);



        }
    }


}
