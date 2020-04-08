package com.example.myapplication.activity.wordActivity;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.base.BaseListableAppCompatActivity;
import com.example.myapplication.activity.base.GetItemsExecutorBlock;
import com.example.myapplication.adapter.NativeWordListableAdapter;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.TranslationWordRelationService;
import com.example.myapplication.service.WordService;

import java.util.List;

//public class ShowNativeWordActivity extends AppCompatActivity {
public class ShowNativeWordActivity extends BaseListableAppCompatActivity<Word, WordService, ShowNativeWordActivity, NativeWordListableAdapter> {

        private Word word;
    private Long translationFromLanguageID;
    private Long translationToLanguageID;
    private TranslationWordRelationService translationWordRelationService;

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

        Intent i = getIntent();
        super.setItemService(FactoryUtil.createWordService(getApplication()));
        NativeWordListableAdapter adapter = new NativeWordListableAdapter(ShowNativeWordActivity.this);
        super.setAdapter(adapter);
        super.setContext(ShowNativeWordActivity.this);

        translationWordRelationService=FactoryUtil.createTranslationWordRelationService(getApplication());
    //    this.translationAndLanguages = (TranslationAndLanguages) i.getSerializableExtra("translationAndLanguages");
        this.translationFromLanguageID = (Long) i.getSerializableExtra("translationFromLanguageID");
        this.translationToLanguageID = (Long) i.getSerializableExtra("translationToLanguageID");
        this.word= (Word) i.getSerializableExtra("word");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(word.getWordString());
        setGetItemsExecutor(new GetItemsExecutorBlock<Word>() {
            @Override
            public List<Word> execute() {

                //return translationWordRelationService.translateFromNative(word.getWordID(),translationAndLanguages.getForeignLanguage().getLanguageID());
                return translationWordRelationService.translateFromNative(word.getWordID(),translationToLanguageID);
            }
        });
        getItems();

    }





    @Override
    public void recyclerViewOnClickHandler(View v, Word selectedItem) {
        Toast.makeText(getApplicationContext(), selectedItem.getLabelText(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<Word>() {
            @Override
            public List<Word> execute() {
                //return translationWordRelationService.translateFromNative(word.getWordID(),translationAndLanguages.getForeignLanguage().getLanguageID());
                return translationWordRelationService.translateFromNative(word.getWordID(),translationToLanguageID);

            }
        });
    }

}
