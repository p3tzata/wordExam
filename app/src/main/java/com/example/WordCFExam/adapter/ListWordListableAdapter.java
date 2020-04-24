package com.example.WordCFExam.adapter;

import com.example.WordCFExam.activity.base.BaseListableAppCompatActivityFaced;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.WordCFExamCross;

public class ListWordListableAdapter extends
        BaseRecycleAdapterFaced<BaseListableAppCompatActivityFaced, WordCFExamCross,Word, BaseRecycleAdapterFaced.ItemViewHolder> {

    public ListWordListableAdapter(BaseListableAppCompatActivityFaced context) {
        super(context);
    }

    @Override
    public Word castViewTypeToEntityType(WordCFExamCross selectedItem) {
        Word word = new Word();
        word.setWordString(selectedItem.getWord().getWordString());
        word.setWordFormID(selectedItem.getWord().getWordFormID());
        word.setLanguageID(selectedItem.getWord().getLanguageID());
        word.setProfileID(selectedItem.getWord().getProfileID());
        word.setWordID(selectedItem.getWord().getWordID());
        return word;
    }

}
