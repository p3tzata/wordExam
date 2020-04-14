package com.example.WordCFExam.activity.exam;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseListableAppCompatActivity;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.exam.CFExamWordQuestionnaireNeedProceedAdapter;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaireCross;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.CFExamWordQuestionnaireService;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;

public class CFExamWordQuestionnaireNeedProceedActivity
        extends BaseListableAppCompatActivity<CFExamWordQuestionnaireCross, CFExamWordQuestionnaireService, CFExamWordQuestionnaireNeedProceedActivity, CFExamWordQuestionnaireNeedProceedAdapter> {


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
        CFExamWordQuestionnaireNeedProceedAdapter adapter = new CFExamWordQuestionnaireNeedProceedAdapter(CFExamWordQuestionnaireNeedProceedActivity.this);
        super.setAdapter(adapter);
        super.setContext(CFExamWordQuestionnaireNeedProceedActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List of CF questions");
        setGetItemsExecutor(new GetItemsExecutorBlock<CFExamWordQuestionnaireCross>() {
            @Override
            public List<CFExamWordQuestionnaireCross> execute() {
                List<CFExamWordQuestionnaireCross> allOrderAlphabetic = getItemService().findAllNeedProceed(Session.getLongAttribute(getContext(), SessionNameAttribute.ProfileID,-1L));
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
