package com.example.WordCFExam.activity.exam;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseListableAppCompatActivity;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.exam.RandomExamQuestionnaireNeedProceedAdapter;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.RandomExamWordQuestionnaireService;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;

public class RandomExamQuestionnaireNeedProceedActivity
        extends BaseListableAppCompatActivity<Word, RandomExamWordQuestionnaireService, RandomExamQuestionnaireNeedProceedActivity, RandomExamQuestionnaireNeedProceedAdapter> {

    TranslationAndLanguages translationAndLanguages;
    Language fromLanguage;
    Language toLanguage;

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
        super.setItemService(FactoryUtil.createRandomExamWordQuestionnaireService(getApplication()));
        RandomExamQuestionnaireNeedProceedAdapter adapter = new RandomExamQuestionnaireNeedProceedAdapter(RandomExamQuestionnaireNeedProceedActivity.this);
        super.setAdapter(adapter);
        super.setContext(RandomExamQuestionnaireNeedProceedActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List of Random questions");
        translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        fromLanguage=(Language) getIntent().getSerializableExtra("fromLanguage");;
        toLanguage=(Language) getIntent().getSerializableExtra("toLanguage");
        setGetItemsExecutor(new GetItemsExecutorBlock<Word>() {
            @Override
            public List<Word> execute() {


                int countNumber=2;
                long profileID = Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID, -1L);
                List<Word> allOrderAlphabetic=null;
                if (translationAndLanguages.getForeignLanguage().getLanguageID().equals(
                        fromLanguage.getLanguageID())) {
                    allOrderAlphabetic = getItemService().findAllForeignRandom(profileID, fromLanguage.getLanguageID(), toLanguage.getLanguageID(), countNumber);

                } else {
                    allOrderAlphabetic = getItemService().findAllNativeRandom(profileID, fromLanguage.getLanguageID(), toLanguage.getLanguageID(), countNumber);
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
    public void recyclerViewOnClickHandler(View v, Word selectedItem) {
        Intent intent = new Intent(getContext(), RandomExamWordProceedQuestionActivity.class);
        intent.putExtra("word", selectedItem);
        intent.putExtra("fromLanguage",fromLanguage);
        intent.putExtra("toLanguage",toLanguage);

        startActivity(intent);

    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {

    }
}
