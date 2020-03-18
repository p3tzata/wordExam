package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.UpdateWordActivity;
import com.example.myapplication.entity.WordOld;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>  {



    private final LayoutInflater mInflater;
    private Context context;
    private List<WordOld> mWordOlds; // Cached copy of words
    private List<WordOld> mWordsFull; // Cached copy of words

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
        if (mWordOlds != null) {
            WordOld current = mWordOlds.get(position);
            holder.wordItemView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    public void setWords(List<WordOld> wordOlds){
        mWordOlds = wordOlds;
        mWordsFull = new ArrayList<>(wordOlds);
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWordOlds != null)
            return mWordOlds.size();
        else return 0;
    }





    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            WordOld wordOld = mWordOlds.get(getAdapterPosition());

            Intent intent = new Intent(context, UpdateWordActivity.class);
            intent.putExtra("word_id", wordOld.getId());

            context.startActivity(intent);

        }
    }


}
