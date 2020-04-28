package com.example.WordCFExam.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.WordCFExam.R;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.service.WordService;

public class UpdateWordMenuActivity extends AppCompatActivity {
    private WordService wordService;
    ListView mainListMenu;

    String[] mainListMenuOptions = new String[]{"Basic",
            "Translation",
            "Help sentence",
            "Open Url link"};

    AdapterView.OnItemClickListener[]  OnItemClickListenerArray= new AdapterView.OnItemClickListener[mainListMenuOptions.length];


    Word word;
    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;
    private Long toLanguageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_word_menu);
        this.wordService = new ViewModelProvider(this).get(WordService.class);
        this.word = (Word) getIntent().getSerializableExtra("word");
        this.translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) getIntent().getSerializableExtra("translationFromLanguageID");
        this.toLanguageID = (Long) getIntent().getSerializableExtra("translationToLanguageID");
        getSupportActionBar().setTitle(word.getWordString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        seedOnClickListener();

        mainListMenu = (ListView) findViewById(R.id.updateWordListMenu);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mainListMenuOptions);
        mainListMenu.setAdapter(adapter);

        mainListMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                OnItemClickListenerArray[position].onItemClick(adapterView,view,position,l);

            }
        });


    }


    @Override
    public void onResume(){
        super.onResume();
        getWordFromDB(word.getWordID());
        getSupportActionBar().setTitle(word.getWordString());
    }

    private void seedOnClickListener(){
        OnItemClickListenerArray[0] = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent activity2Intent = new Intent(getApplicationContext(), UpdateWordBasicActivity.class);

                activity2Intent.putExtra("translationAndLanguages", UpdateWordMenuActivity.this.translationAndLanguages);
                activity2Intent.putExtra("translationFromLanguageID", UpdateWordMenuActivity.this.fromLanguageID);
                activity2Intent.putExtra("translationToLanguageID", UpdateWordMenuActivity.this.toLanguageID);
                activity2Intent.putExtra("word", UpdateWordMenuActivity.this.word);
                startActivity(activity2Intent);
            }


        };

        OnItemClickListenerArray[1] = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent activity2Intent = new Intent(getApplicationContext(), UpdateWordTranslationActivity.class);

                activity2Intent.putExtra("translationAndLanguages", UpdateWordMenuActivity.this.translationAndLanguages);
                activity2Intent.putExtra("translationFromLanguageID", UpdateWordMenuActivity.this.fromLanguageID);
                activity2Intent.putExtra("translationToLanguageID", UpdateWordMenuActivity.this.toLanguageID);
                activity2Intent.putExtra("word", UpdateWordMenuActivity.this.word);
                startActivity(activity2Intent);
            }


        };

        OnItemClickListenerArray[2] = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent activity2Intent = new Intent(getApplicationContext(), UpdateWordHelpSentenceActivity.class);

                activity2Intent.putExtra("translationAndLanguages", UpdateWordMenuActivity.this.translationAndLanguages);
                activity2Intent.putExtra("translationFromLanguageID", UpdateWordMenuActivity.this.fromLanguageID);
                activity2Intent.putExtra("translationToLanguageID", UpdateWordMenuActivity.this.toLanguageID);
                activity2Intent.putExtra("word", UpdateWordMenuActivity.this.word);
                startActivity(activity2Intent);
            }


        };
        OnItemClickListenerArray[3] = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    final String url = String.format(translationAndLanguages.getForeignLanguage().getDefinitionUrl(), word.getWordString());
                    String urlString = url;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    try {
                        getApplicationContext().startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        // Chrome browser presumably not installed and open Kindle Browser
                        intent.setPackage("com.amazon.cloud9");
                        getApplicationContext().startActivity(intent);
                    }

                } catch (Exception ex) {
                   ;
                }
            }
        };



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /*
                Intent intent = new Intent(this, ListAllWordActivity.class);
                intent.putExtra("translationAndLanguages", UpdateWordMenuActivity.this.translationAndLanguages);
                intent.putExtra("translationFromLanguageID", UpdateWordMenuActivity.this.fromLanguageID);
                startActivity(intent);

                 */
                finish();
                return true;

        }
        return true;
    }


    private void getWordFromDB(Long wordID) {
        class GetTasks extends AsyncTask<Void, Void, Word> {

            @Override
            protected Word doInBackground(Void... voids) {
                word = wordService.findByID(wordID);
                return word;
            }

            @Override
            protected void onPostExecute(Word word) {
                super.onPostExecute(word);
                UpdateWordMenuActivity.this.word = word;


            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }



}
