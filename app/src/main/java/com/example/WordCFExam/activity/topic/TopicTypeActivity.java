package com.example.WordCFExam.activity.topic;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseListableAppCompatActivityNonFaced;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.exam.CFExamTopicQuestionnaireNeedProceedAdapter;
import com.example.WordCFExam.entity.exam.TopicType;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.TopicTypeService;
import com.example.WordCFExam.utitliy.MenuUtility;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;

public class TopicTypeActivity
        extends BaseListableAppCompatActivityNonFaced<TopicType, TopicTypeService, TopicTypeActivity, CFExamTopicQuestionnaireNeedProceedAdapter> {
    private TopicType topicType;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateCustom() {

        setContentView(R.layout.activity_base_listable);
        super.setItemService(FactoryUtil.createTopicTypeService(getApplication()));
        CFExamTopicQuestionnaireNeedProceedAdapter adapter = new CFExamTopicQuestionnaireNeedProceedAdapter(TopicTypeActivity.this);
        super.setAdapter(adapter);
        super.setContext(TopicTypeActivity.this);
        this.topicType = (TopicType) getIntent().getSerializableExtra("topicType");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List of Topics");
        setGetItemsExecutor(new GetItemsExecutorBlock<TopicType>() {
            @Override
            public List<TopicType> execute() {
                List<TopicType> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(
                        Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID,-1L), "");
                return allOrderAlphabetic;
            }
        });
        getItems();

    }

    @Override
    public void onResume(){
        super.onResume();
        getItems();

    }


    @Override
    public void recyclerViewOnClickHandler(View v, TopicType selectedItem) {
        Intent intent =null;

        if (
                MenuUtility.isEditMode(getApplicationContext())) {
            intent = new Intent(getContext(), TopicEditableActivity.class);

        } else {

            intent = new Intent(getContext(), TopicListableActivity.class);

        }
        intent.putExtra("topicType", selectedItem);
        startActivity(intent);


    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<TopicType>() {
            @Override
            public List<TopicType> execute() {
                List<TopicType> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(
                        Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID,-1L), contains);
                return allOrderAlphabetic;
            }
        });
    }
}
