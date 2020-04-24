package com.example.WordCFExam.adapter;

import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivityFaced;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.WordCFExamCross;

public class ListWordEditableAdapter extends
        BaseRecycleAdapterFaced<BaseEditableAppCompatActivityFaced, WordCFExamCross,Word, BaseRecycleAdapterFaced.ItemViewHolder> {

    public ListWordEditableAdapter(BaseEditableAppCompatActivityFaced context) {
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
