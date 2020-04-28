package com.example.WordCFExam.adapter.exam;

import com.example.WordCFExam.activity.base.BaseListableAppCompatActivityNonFaced;
import com.example.WordCFExam.adapter.BaseRecycleAdapterNonFaced;
import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Word;

public class RandomExamHelpSentenceQuestionnaireNeedProceedAdapter extends
        BaseRecycleAdapterNonFaced<BaseListableAppCompatActivityNonFaced, HelpSentence, BaseRecycleAdapterNonFaced.ItemViewHolder> {


    public Boolean isToForeign;

    public RandomExamHelpSentenceQuestionnaireNeedProceedAdapter(BaseListableAppCompatActivityNonFaced context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(BaseRecycleAdapterNonFaced.ItemViewHolder holder, int position) {
        if (mItems != null) {
            HelpSentence current = mItems.get(position);
            if (isToForeign) {
                holder.wordItemView.setText(current.getSentenceString());
            }  else {
                holder.wordItemView.setText(current.getSentenceTranslation());
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Items");
        }
    }

    public Boolean getToForeign() {
        return isToForeign;
    }

    public void setToForeign(Boolean toForeign) {
        isToForeign = toForeign;
    }
}
