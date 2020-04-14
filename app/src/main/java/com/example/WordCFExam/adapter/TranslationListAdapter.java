package com.example.WordCFExam.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.wordActivity.ListWordEditableActivity;
import com.example.WordCFExam.activity.wordActivity.ListWordListableActivity;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.utitliy.MenuUtility;

import java.util.List;

public class TranslationListAdapter extends RecyclerView.Adapter<TranslationListAdapter.TranslationViewHolder>  {



    private final LayoutInflater mInflater;
    private Context context;
    private List<TranslationAndLanguages> mTranslations; // Cached copy of words


    public TranslationListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }




    @Override
    public TranslationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_dictionary_item, parent, false);
        return new TranslationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TranslationViewHolder holder, int position) {
        if (mTranslations != null) {
            TranslationAndLanguages current = mTranslations.get(position);

            holder.fromForeignItemView.setText(current.getForeignLanguage().getLanguageName() + " to " +
                    current.getNativeLanguage().getLanguageName());

            holder.toForeignItemView.setText(current.getNativeLanguage().getLanguageName() + " to " +
                    current.getForeignLanguage().getLanguageName());

        } else {
            // Covers the case of data not being ready yet.
            holder.fromForeignItemView.setText("No Word");
        }
    }

    public void setWords(List<TranslationAndLanguages> words){
        mTranslations = words;

        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTranslations != null)
            return mTranslations.size();
        else return 0;
    }





    class TranslationViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        private final TextView fromForeignItemView;
        private final TextView toForeignItemView;

        private TranslationViewHolder(View itemView) {

            super(itemView);
            fromForeignItemView = itemView.findViewById(R.id.tx_fromForeign);
            toForeignItemView = itemView.findViewById(R.id.tx_toForeign);
            fromForeignItemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent activity2Intent = null;
                    TranslationAndLanguages translationAndLanguages = mTranslations.get(getAdapterPosition());
                    Long fromLanguageID=translationAndLanguages.getForeignLanguage().getLanguageID();
                    Long toLanguageID=translationAndLanguages.getNativeLanguage().getLanguageID();
                    if (MenuUtility.isEditMode(context) && fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
                        activity2Intent = new Intent(context, ListWordEditableActivity.class);
                    } else {
                        activity2Intent = new Intent(context, ListWordListableActivity.class);
                    }


                    activity2Intent.putExtra("translationAndLanguages",translationAndLanguages);
                    activity2Intent.putExtra("translationFromLanguageID",fromLanguageID);
                    activity2Intent.putExtra("translationToLanguageID",toLanguageID);
                    context.startActivity(activity2Intent);

                    /*
                    TranslationAndLanguages translationAndLanguages = mTranslations.get(getAdapterPosition());
                    Intent activity2Intent = new Intent(context, ListAllWordActivity.class);
                    activity2Intent.putExtra("translationAndLanguages",translationAndLanguages);
                    activity2Intent.putExtra("translationFromLanguageID",translationAndLanguages.getForeignLanguage().getLanguageID());
                    context.startActivity(activity2Intent);
                    */

                }
            });

            toForeignItemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent activity2Intent = null;
                    TranslationAndLanguages translationAndLanguages = mTranslations.get(getAdapterPosition());
                    Long fromLanguageID=translationAndLanguages.getNativeLanguage().getLanguageID();
                    Long toLanguageID=translationAndLanguages.getForeignLanguage().getLanguageID();
                    if (MenuUtility.isEditMode(context) && fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
                        activity2Intent = new Intent(context, ListWordEditableActivity.class);
                    } else {
                        activity2Intent = new Intent(context, ListWordListableActivity.class);
                    }

                    activity2Intent.putExtra("translationAndLanguages",translationAndLanguages);
                    activity2Intent.putExtra("translationFromLanguageID",fromLanguageID);
                    activity2Intent.putExtra("translationToLanguageID",toLanguageID);
                    context.startActivity(activity2Intent);

                    /*
                    TranslationAndLanguages translationAndLanguages = mTranslations.get(getAdapterPosition());
                    Intent activity2Intent = new Intent(context, ListAllWordActivity.class);
                    activity2Intent.putExtra("translationAndLanguages",translationAndLanguages);
                    activity2Intent.putExtra("translationFromLanguageID",translationAndLanguages.getNativeLanguage().getLanguageID());
                    context.startActivity(activity2Intent);

                     */
                }
            });



        }



    }


}
