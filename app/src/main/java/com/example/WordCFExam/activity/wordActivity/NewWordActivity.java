package com.example.WordCFExam.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.MainActivity;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.service.WordService;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    TranslationAndLanguages translationAndLanguages;
    Long fromLanguageID;

    private EditText mEditWordView;

    private WordService wordService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.wordService = new ViewModelProvider(this).get(WordService.class);
        Intent i = getIntent();
        translationAndLanguages = (TranslationAndLanguages) i.getSerializableExtra("translationAndLanguages");
        fromLanguageID = (Long) i.getSerializableExtra("translationFromLanguageID");

        setContentView(R.layout.activity_new_word);
        mEditWordView = findViewById(R.id.edit_word);
        mEditWordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });



        getSupportActionBar().setTitle("From " + translationAndLanguages.getForeignLanguage().getLanguageName());


        final Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveWord();
            }
        });



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


    private void saveWord() {
        final String sWordText = mEditWordView.getText().toString().trim();

        if (sWordText.isEmpty()) {
            mEditWordView.setError("Must not be Empty!");
            mEditWordView.requestFocus();
            return;
        }



        class SaveTask extends AsyncTask<Void, Void, Word> {

            @Override
            protected Word doInBackground(Void... voids) {

                //creating a task
                Word word = new Word();
                word.setWordString(sWordText);
                word.setLanguageID(translationAndLanguages.getForeignLanguage().getLanguageID());
                word.setProfileID(translationAndLanguages.getTranslation().getProfileID());

                //adding to database
                Long wordID = wordService.insert(word);
                Word wordInserted = wordService.findByID(wordID);

                return wordInserted;
            }

            @Override
            protected void onPostExecute(Word word) {
                super.onPostExecute(word);
                if (word!=null) {
                    finish();
                    Intent i = getIntent();
                    Intent intent = new Intent(getApplicationContext(), UpdateWordBasicActivity.class);
                    intent.putExtra("translationAndLanguages", translationAndLanguages);
                    intent.putExtra("translationFromLanguageID", translationAndLanguages.getForeignLanguage().getLanguageID());
                    intent.putExtra("isHaveToStartUpdateWordMenu", true);
                    intent.putExtra("word", word);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Word Saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }



}


