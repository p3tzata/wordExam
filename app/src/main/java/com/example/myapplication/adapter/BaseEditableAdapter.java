package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.BaseEditableAppCompatActivity;
import com.example.myapplication.entity.TextLabelable;

import java.util.List;

public abstract class BaseEditableAdapter<C extends BaseEditableAppCompatActivity,I extends TextLabelable,V extends BaseEditableAdapter.ItemViewHolder>  extends RecyclerView.Adapter<BaseEditableAdapter<C,I, V>.ItemViewHolder> {

    private final LayoutInflater mInflater;
    protected C context;
    private List<I> mItems;

    public BaseEditableAdapter(C context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    protected abstract void callOnDeleteMenuSelected(I selectedItem);
    protected abstract void callOnUpdateMenuSelected(I selectedItem);

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseEditableAdapter.ItemViewHolder holder, int position) {
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
        private final TextView wordItemView;

        private ItemViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.tx_word_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            I selectedItem = mItems.get(getAdapterPosition());
            callShowPopUpMenu(v,selectedItem);


        }
    }


    public void callShowPopUpMenu(View v,I selectedItem) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.popup_crud_menu_update_delete);
        //adding click listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //I selectedItem = mItems.get(getAdapterPosition());
                switch (item.getItemId()) {
                    case R.id.menu_delete:

                        callOnDeleteMenuSelected(selectedItem);

                        break;
                    case R.id.menu_update:
                        callOnUpdateMenuSelected(selectedItem);
                }
                return false;
            }
        });

        popupMenu.show();

    }






}
