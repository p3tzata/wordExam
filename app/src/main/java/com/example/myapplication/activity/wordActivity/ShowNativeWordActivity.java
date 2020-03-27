package com.example.myapplication.activity.wordActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.BaseListableAppCompatActivity;
import com.example.myapplication.activity.configureActivity.ConfigProfileActivity;
import com.example.myapplication.adapter.NativeWordListableAdapter;
import com.example.myapplication.adapter.configure.ProfileEditableAdapter;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.NativeWithForeignWords;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_native_word);
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
        getItems();


    }

    @Override
    public List<Word> getListOfItems() {
        NativeWithForeignWords nativeWithForeignWords = translationWordRelationService.translateFromNative(word.getWordID());
        return nativeWithForeignWords.getForeignWords();
    }




    @Override
    public void recyclerViewOnClickHandler(View v, Word selectedItem) {
        Toast.makeText(getApplicationContext(), selectedItem.getLabelText(), Toast.LENGTH_LONG).show();
    }
}
