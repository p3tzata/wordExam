package com.example.myapplication.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.ListAllWordActivity;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;
import com.example.myapplication.entity.dto.TranslationAndLanguages;

import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.TranslationWordRelationService;
import com.example.myapplication.service.WordService;


public class UpdateWordActivity extends AppCompatActivity {
    private WordService wordService;
    private TranslationWordRelationService translationWordRelationService;
    private EditText editTextName;
    private Word word;
    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;
    private ForeignWithNativeWords foreignWithNativeWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.wordService = new ViewModelProvider(this).get(WordService.class);
        this.translationWordRelationService = FactoryUtil.createTranslationWordRelationService(getApplication());
        setContentView(R.layout.activity_update_word);
        Intent intent = getIntent();
        this.translationAndLanguages = (TranslationAndLanguages) intent.getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) intent.getSerializableExtra("translationFromLanguageID");
        Toast.makeText(getApplicationContext(), "translationAndLanguages" + this.translationAndLanguages.getForeignLanguage().getLanguageName()   , Toast.LENGTH_LONG).show();

        editTextName = (EditText) findViewById(R.id.et_wordString);
        final Word word = (Word) getIntent().getSerializableExtra("word");
        getWordFromDB(word);
        getNativeWords(word);



        /**/
        findViewById(R.id.btn_UpdateForeignWord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportFormToEntiy(word);
                updateWord();
            }
        });


    }

    private void exportFormToEntiy(Word word) {
        word.setWordString(editTextName.getText().toString());


    }

    private void loadForm() {
        editTextName.setText(this.word.getWordString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getNativeWords(Word foreignWord) {
        class GetTasks extends AsyncTask<Void, Void, ForeignWithNativeWords> {

            @Override
            protected ForeignWithNativeWords doInBackground(Void... voids) {
                ForeignWithNativeWords translationWordFromForeign = translationWordRelationService.translateFromForeign(foreignWord.getWordID());
                return translationWordFromForeign;
            }

            @Override
            protected void onPostExecute(ForeignWithNativeWords foreignWithNativeWords) {
                super.onPostExecute(foreignWithNativeWords);
                UpdateWordActivity.this.foreignWithNativeWords = foreignWithNativeWords;

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }



    private void getWordFromDB(Word word) {
        class GetTasks extends AsyncTask<Void, Void, Word> {

            @Override
            protected Word doInBackground(Void... voids) {
                UpdateWordActivity.this.word = wordService.findByID(word.getWordID());
                return word;
            }

            @Override
            protected void onPostExecute(Word word) {
                super.onPostExecute(word);
                UpdateWordActivity.this.word = word;
                loadForm();

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    private void updateWord() {

        class updateAsync extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                wordService.update(word);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateWordActivity.this, ListAllWordActivity.class));
            }
        }

        updateAsync ut = new updateAsync();
        ut.execute();

    }
}






