package com.example.myapplication.activity.exam;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.activity.base.BaseListableAppCompatActivity;
import com.example.myapplication.activity.base.GetItemsExecutorBlock;
import com.example.myapplication.adapter.exam.CFExamQuestionnaireNeedProceedAdapter;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaireCross;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.exam.CFExamWordQuestionnaireService;

import java.util.List;

public class CFExamTopicQuestionnaireNeedProceedActivity
        extends BaseListableAppCompatActivity<CFExamWordQuestionnaireCross, CFExamWordQuestionnaireService, CFExamTopicQuestionnaireNeedProceedActivity, CFExamQuestionnaireNeedProceedAdapter> {


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
        super.setItemService(FactoryUtil.createCFExamQuestionnaireService(getApplication()));
        CFExamQuestionnaireNeedProceedAdapter adapter = new CFExamQuestionnaireNeedProceedAdapter(CFExamTopicQuestionnaireNeedProceedActivity.this);
        super.setAdapter(adapter);
        super.setContext(CFExamTopicQuestionnaireNeedProceedActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List of CF questions");
        setGetItemsExecutor(new GetItemsExecutorBlock<CFExamWordQuestionnaireCross>() {
            @Override
            public List<CFExamWordQuestionnaireCross> execute() {
                List<CFExamWordQuestionnaireCross> allOrderAlphabetic = getItemService().findAllNeedProceed();
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
    public void recyclerViewOnClickHandler(View v, CFExamWordQuestionnaireCross selectedItem) {
        Intent intent = new Intent(getContext(), CFExamWordProceedQuestionActivity.class);
        intent.putExtra("CFExamQuestionnaireCross", selectedItem);
        startActivity(intent);

    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {

    }
}
