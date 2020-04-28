package com.example.WordCFExam.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseListableAppCompatActivityNonFaced;
import com.example.WordCFExam.entity.TextLabelable;

import java.util.List;

public abstract class BaseRecycleAdapterNonFaced<C extends BaseListableAppCompatActivityNonFaced,I extends TextLabelable,V extends BaseRecycleAdapterNonFaced.ItemViewHolder>  extends RecyclerView.Adapter<BaseRecycleAdapterNonFaced<C,I, V>.ItemViewHolder>

{

   // abstract public void callOnClick(View v,I selectedItem);

    private final LayoutInflater mInflater;
    protected C context;
    protected List<I> mItems;

    public BaseRecycleAdapterNonFaced(C context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseRecycleAdapterNonFaced.ItemViewHolder holder, int position) {
        if (mItems != null) {
            I current = mItems.get(position);
            holder.wordItemView.setText(current.getLabelText());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Items");
        }
    }

    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }

    public void setItems(List<I> items){
        mItems = items;
        notifyDataSetChanged();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView;

        protected ItemViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.tx_word_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            I selectedItem = mItems.get(getAdapterPosition());
            //callOnClick(v,selectedItem);
            context.recyclerViewOnClickHandler(v,selectedItem);


        }
    }










}
