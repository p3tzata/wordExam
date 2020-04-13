package com.example.WordCFExam.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WordCFExam.R;

import java.util.ArrayList;

public class ShowForeignWordRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<String> data = new ArrayList<>();
    private final int VIEW_TYPE_TEXTVIEW = 0;
    private final int VIEW_TYPE_ITEM_1 = 1;
    private final int VIEW_TYPE_ITEM_2 = 2;
    private final LayoutInflater inflater;
    private Context context;

    public ShowForeignWordRecycleAdapter(Context ctx, ArrayList<String> data){
        this.context = ctx;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_TEXTVIEW){
            View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
            return new TextViewHolder(view);
        }else if(viewType == VIEW_TYPE_ITEM_1){
            View view = inflater.inflate(R.layout.item_top_recycler, parent, false);
            return new Item1Holder(view);
        }else{
            View view = inflater.inflate(R.layout.item_top_recycler, parent, false);
            return new Item2Holder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TextViewHolder){
            ((TextViewHolder) holder).textView.setText("Header");
        }else if(holder instanceof Item1Holder){
            String current = data.get(position);
            ((Item1Holder) holder).itemTextView.setText(current);
        }else if(holder instanceof Item2Holder){
            ((Item2Holder) holder).itemTextView.setText("Footer");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TextViewHolder extends RecyclerView.ViewHolder {


        TextView textView;

        public TextViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
    class Item1Holder extends RecyclerView.ViewHolder {


        TextView itemTextView;

        public Item1Holder(View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.textView);
        }
    }
    class Item2Holder extends RecyclerView.ViewHolder {


        TextView itemTextView;

        public Item2Holder(View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.textView);
        }
    }
}