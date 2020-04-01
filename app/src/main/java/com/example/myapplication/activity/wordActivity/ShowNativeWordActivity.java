package com.example.myapplication.activity.wordActivity;

import android.content.Intent;
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

    TranslationAndLanguages translationAndLanguages;
    private Word word;
    private Long translationFromLanguageID;
    private TranslationWordRelationService translationWordRelationService;

    @Override
    public void onCreateCustom() {

        setContentView(R.layout.activity_base_listable);
        Intent i = getIntent();
        super.setItemService(FactoryUtil.createWordService(getApplication()));
        NativeWordListableAdapter adapter = new NativeWordListableAdapter(ShowNativeWordActivity.this);
        super.setAdapter(adapter);
        super.setContext(ShowNativeWordActivity.this);

        translationWordRelationService=FactoryUtil.createTranslationWordRelationService(getApplication());
        this.translationAndLanguages = (TranslationAndLanguages) i.getSerializableExtra("translationAndLanguages");
        translationFromLanguageID = (Long) i.getSerializableExtra("translationFromLanguageID");
        this.word= (Word) i.getSerializableExtra("word");
        getSupportActionBar().setTitle(word.getWordString());
        setGetItemsExecutor(new GetItemsExecutorBlock<Word>() {
            @Override
            public List<Word> execute() {
                //NativeWithForeignWords nativeWithForeignWords = translationWordRelationService.translateFromNative1(word.getWordID());
                return translationWordRelationService.translateFromNative(word.getWordID(),translationAndLanguages.getForeignLanguage().getLanguageID());
//                return nativeWithForeignWords.getForeignWords();
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
                return translationWordRelationService.translateFromNative(word.getWordID(),translationAndLanguages.getForeignLanguage().getLanguageID());

            }
        });
    }

}
