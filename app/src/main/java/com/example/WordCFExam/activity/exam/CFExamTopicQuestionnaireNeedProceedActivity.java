package com.example.WordCFExam.activity.exam;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseListableAppCompatActivity;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.exam.CFExamTopicQuestionnaireNeedProceedAdapter;
import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaireCross;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.CFExamTopicQuestionnaireService;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;

public class CFExamTopicQuestionnaireNeedProceedActivity
        extends BaseListableAppCompatActivity<CFExamTopicQuestionnaireCross, CFExamTopicQuestionnaireService, CFExamTopicQuestionnaireNeedProceedActivity, CFExamTopicQuestionnaireNeedProceedAdapter> {


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
        super.setItemService(FactoryUtil.createCFExamTopicQuestionnaireService(getApplication()));
        CFExamTopicQuestionnaireNeedProceedAdapter adapter = new CFExamTopicQuestionnaireNeedProceedAdapter(CFExamTopicQuestionnaireNeedProceedActivity.this);
        super.setAdapter(adapter);
        super.setContext(CFExamTopicQuestionnaireNeedProceedActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List of CF questions");
        Profile targetProfile = (Profile) getIntent().getSerializableExtra("targetProfile");
        if (targetProfile!=null) {
            Session.setLongAttribute(getContext(), SessionNameAttribute.ProfileID,targetProfile.getProfileID());
            Session.setStringAttribute(getContext(), SessionNameAttribute.ProfileName,targetProfile.getProfileName());
        }


        setGetItemsExecutor(new GetItemsExecutorBlock<CFExamTopicQuestionnaireCross>() {
            @Override
            public List<CFExamTopicQuestionnaireCross> execute() {
                List<CFExamTopicQuestionnaireCross> allOrderAlphabetic =
                        getItemService().findAllNeedProceed(Session.getLongAttribute(getContext(), SessionNameAttribute.ProfileID,-1L));
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
    public void recyclerViewOnClickHandler(View v, CFExamTopicQuestionnaireCross selectedItem) {
        Intent intent = new Intent(getContext(), CFExamTopicProceedQuestionActivity.class);
        intent.putExtra("CFExamTopicQuestionnaireCross", selectedItem);
        startActivity(intent);

    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {

    }
}
