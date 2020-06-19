package com.example.WordCFExam.adapter.topic;

import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivityNonFaced;
import com.example.WordCFExam.adapter.BaseRecycleAdapterNonFaced;
import com.example.WordCFExam.entity.TopicType;

public class TopicTypeEditableAdapter extends
        BaseRecycleAdapterNonFaced<BaseEditableAppCompatActivityNonFaced, TopicType, BaseRecycleAdapterNonFaced.ItemViewHolder> {

    public TopicTypeEditableAdapter(BaseEditableAppCompatActivityNonFaced context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(BaseRecycleAdapterNonFaced.ItemViewHolder holder, int position) {
        if (mItems != null) {
            TopicType current = mItems.get(position);
            if (current.getTopicTypeID().equals(-1L)) {
                holder.wordItemView.setText(current.getTopicTypeName());
            } else {
                holder.wordItemView.setText(String.format("%s (%d)", current.getLabelText(), current.getTopicTypeID()));
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Items");
        }
    }




}
