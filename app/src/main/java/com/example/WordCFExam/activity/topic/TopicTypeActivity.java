package com.example.WordCFExam.activity.topic;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseListableAppCompatActivityNonFaced;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.exam.CFExamTopicQuestionnaireNeedProceedAdapter;
import com.example.WordCFExam.entity.TopicType;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.TopicTypeService;
import com.example.WordCFExam.utitliy.MenuUtility;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;
import java.util.Stack;

public class TopicTypeActivity
        extends BaseListableAppCompatActivityNonFaced<TopicType, TopicTypeService, TopicTypeActivity, CFExamTopicQuestionnaireNeedProceedAdapter> {
    private TopicType topicType;
    private Stack<TopicType> navigatorStack;
    private boolean isRedirected;

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
        navigatorStack = new Stack<>();
        navigatorStack.push(new TopicType(){{setTopicTypeName("List of Topics ");setTopicTypeID(0L);}});



        setContentView(R.layout.activity_base_listable);
        super.setItemService(FactoryUtil.createTopicTypeService(getApplication()));
        CFExamTopicQuestionnaireNeedProceedAdapter adapter = new CFExamTopicQuestionnaireNeedProceedAdapter(TopicTypeActivity.this);
        super.setAdapter(adapter);
        super.setContext(TopicTypeActivity.this);
        this.topicType = (TopicType) getIntent().getSerializableExtra("topicType");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(navigatorStack.peek().getTopicTypeName());
        setGetItemsExecutor(new GetItemsExecutorBlock<TopicType>() {
            @Override
            public List<TopicType> execute() {
                List<TopicType> allOrderAlphabetic = getItemService().findAllOrderAlphabeticByParent(
                        Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID,-1L), navigatorStack.peek().getTopicTypeID(),"");

                if(allOrderAlphabetic.size()==0) {

                    if (!isRedirected) {
                        isRedirected=true;
                        Intent intent = null;

                        if (
                                MenuUtility.isEditMode(getApplicationContext())) {
                            intent = new Intent(getContext(), TopicFacedEditableActivity.class);

                        } else {

                            intent = new Intent(getContext(), TopicFacedListableActivity.class);

                        }
                        intent.putExtra("topicType", navigatorStack.peek());
                        startActivity(intent);
                    }
                }


                if (!navigatorStack.peek().getTopicTypeID().equals(0L)) {
                    TopicType navigatorTopicType = new TopicType();
                    navigatorTopicType.setTopicTypeName(".. Back");
                    navigatorTopicType.setTopicTypeID(-1L);
                    allOrderAlphabetic.add(0,navigatorTopicType);
                }

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
        isRedirected=false;
        if (selectedItem.getTopicTypeID().equals(-1L)) {
            navigatorStack.pop();
            getSupportActionBar().setTitle(navigatorStack.peek().getTopicTypeName());
            getItems();
        } else {

            navigatorStack.push(selectedItem);

            //this.selectedParentTopicTypeID=selectedItem.getTopicTypeID();
            getSupportActionBar().setTitle(navigatorStack.peek().getTopicTypeName());
            getItems();

        }



        /*
        Intent intent =null;

        if (
                MenuUtility.isEditMode(getApplicationContext())) {
            intent = new Intent(getContext(), TopicFacedEditableActivity.class);

        } else {

            intent = new Intent(getContext(), TopicFacedListableActivity.class);

        }
        intent.putExtra("topicType", selectedItem);
        startActivity(intent);
        */

    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<TopicType>() {
            @Override
            public List<TopicType> execute() {
                List<TopicType> allOrderAlphabetic = getItemService().findAllOrderAlphabeticByParent(
                        Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID,-1L),navigatorStack.peek().getTopicTypeID(), contains);


                if (!navigatorStack.peek().equals(0L)) {
                    TopicType navigatorTopicType = new TopicType();
                    navigatorTopicType.setTopicTypeName(".. Back");
                    navigatorTopicType.setTopicTypeID(-1L);
                    allOrderAlphabetic.add(0,navigatorTopicType);
                }

                return allOrderAlphabetic;
            }
        });
    }
}
