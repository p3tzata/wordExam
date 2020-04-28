package com.example.WordCFExam.activity.exam;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseListableAppCompatActivityNonFaced;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.exam.RandomExamHelpSentenceQuestionnaireNeedProceedAdapter;
import com.example.WordCFExam.adapter.exam.RandomExamWordQuestionnaireNeedProceedAdapter;
import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.RandomExamCounter;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.RandomExamHelpSentenceQuestionnaireService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;

public class RandomExamHelpSentenceQuestionnaireNeedProceedActivity
        extends BaseListableAppCompatActivityNonFaced<HelpSentence, RandomExamHelpSentenceQuestionnaireService, RandomExamHelpSentenceQuestionnaireNeedProceedActivity, RandomExamHelpSentenceQuestionnaireNeedProceedAdapter> {

    TranslationAndLanguages translationAndLanguages;
    Language fromLanguage;
    Language toLanguage;
    Long profileID;

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
        super.setItemService(FactoryUtil.createRandomExamHelpSentenceQuestionnaireService(getApplication()));
        RandomExamHelpSentenceQuestionnaireNeedProceedAdapter adapter = new RandomExamHelpSentenceQuestionnaireNeedProceedAdapter(RandomExamHelpSentenceQuestionnaireNeedProceedActivity.this);
        super.setAdapter(adapter);
        super.setContext(RandomExamHelpSentenceQuestionnaireNeedProceedActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List of Random questions");
        translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        fromLanguage=(Language) getIntent().getSerializableExtra("fromLanguage");;
        toLanguage=(Language) getIntent().getSerializableExtra("toLanguage");
        profileID = Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID, -1L);        DbExecutorImp<RandomExamCounter> dbExecutor = FactoryUtil.<RandomExamCounter>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<RandomExamCounter>() {
            @Override
            public RandomExamCounter doInBackground() {
                if (translationAndLanguages.getForeignLanguage().getLanguageID().equals(
                        fromLanguage.getLanguageID())){
                    return getItemService().findAllForeignRandomCounter(profileID,fromLanguage.getLanguageID(),toLanguage.getLanguageID());

                } else {
                    return getItemService().findAllNativeRandomCounter(profileID,fromLanguage.getLanguageID(),toLanguage.getLanguageID());

                }



            }

            @Override
            public void onPostExecute(RandomExamCounter item) {
                String format = String.format("Random questions (%d / %d)", item.getPoints(), item.getTotal());
                getSupportActionBar().setTitle(format);
            }
        });




        setGetItemsExecutor(new GetItemsExecutorBlock<HelpSentence>() {
            @Override
            public List<HelpSentence> execute() {


                int countNumber=5;
                long profileID = Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID, -1L);
                List<HelpSentence> allOrderAlphabetic=null;
                if (translationAndLanguages.getForeignLanguage().getLanguageID().equals(
                        fromLanguage.getLanguageID())){
                        allOrderAlphabetic = getItemService().findAllForeignRandom(profileID, fromLanguage.getLanguageID(), toLanguage.getLanguageID(), countNumber);
                        adapter.setToForeign(true);
                } else {
                    allOrderAlphabetic = getItemService().findAllNativeRandom(profileID, fromLanguage.getLanguageID(), toLanguage.getLanguageID(), countNumber);
                    adapter.setToForeign(false);
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

        DbExecutorImp<RandomExamCounter> dbExecutor = FactoryUtil.<RandomExamCounter>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<RandomExamCounter>() {
            @Override
            public RandomExamCounter doInBackground() {
                if (translationAndLanguages.getForeignLanguage().getLanguageID().equals(
                        fromLanguage.getLanguageID())){
                    return getItemService().findAllForeignRandomCounter(profileID,fromLanguage.getLanguageID(),toLanguage.getLanguageID());

                } else {
                    return getItemService().findAllNativeRandomCounter(profileID,fromLanguage.getLanguageID(),toLanguage.getLanguageID());

                }



            }

            @Override
            public void onPostExecute(RandomExamCounter item) {
                String format = String.format("Random questions (%d / %d)", item.getPoints(), item.getTotal());
                getSupportActionBar().setTitle(format);
            }
        });


    }


    @Override
    public void recyclerViewOnClickHandler(View v, HelpSentence selectedItem) {
        Intent intent = new Intent(getContext(), RandomExamHelpSentenceProceedQuestionActivity.class);
        intent.putExtra("helpSentence", selectedItem);
        intent.putExtra("fromLanguage",fromLanguage);
        intent.putExtra("toLanguage",toLanguage);

        startActivity(intent);

    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {

    }
}
