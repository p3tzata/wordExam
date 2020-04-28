package com.example.WordCFExam.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseListableAppCompatActivityFaced;
import com.example.WordCFExam.entity.TextLabelable;

import java.util.List;

public abstract class BaseRecycleAdapterFaced<C extends BaseListableAppCompatActivityFaced,V extends TextLabelable,T extends TextLabelable,A extends BaseRecycleAdapterFaced.ItemViewHolder>  extends RecyclerView.Adapter<BaseRecycleAdapterFaced<C,V,T, A>.ItemViewHolder>
{

   // abstract public void callOnClick(View v,I selectedItem);

    private final LayoutInflater mInflater;
    protected C context;
    protected List<V> mItems;

    public BaseRecycleAdapterFaced(C context) {
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
    public void onBindViewHolder(BaseRecycleAdapterFaced.ItemViewHolder holder, int position) {
        if (mItems != null) {
            V current = mItems.get(position);
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

    public void setItems(List<V> items){
        mItems = items;
        notifyDataSetChanged();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView wordItemView;

        protected ItemViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.tx_word_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            V selectedItem = mItems.get(getAdapterPosition());
            //callOnClick(v,selectedItem);
            T t = castViewTypeToEntityType(selectedItem);
            context.recyclerViewOnClickHandler(v,t);


        }
    }

    abstract public T castViewTypeToEntityType(V selectedItem);


}
