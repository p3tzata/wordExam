package com.example.WordCFExam.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
            "Help sentence"};
    Class<?>[] mainListMenuOptionsNavigate = new Class[]{UpdateWordBasicActivity.class,
            UpdateWordTranslationActivity.class,
            UpdateWordHelpSentenceActivity.class};
    Word word;
    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_word_menu);
        this.wordService = new ViewModelProvider(this).get(WordService.class);
        this.word = (Word) getIntent().getSerializableExtra("word");
        this.translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) getIntent().getSerializableExtra("translationFromLanguageID");
        getSupportActionBar().setTitle(word.getWordString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mainListMenu = (ListView) findViewById(R.id.updateWordListMenu);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mainListMenuOptions);
        mainListMenu.setAdapter(adapter);

        mainListMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent activity2Intent = new Intent(getApplicationContext(), mainListMenuOptionsNavigate[position]);

                activity2Intent.putExtra("translationAndLanguages", UpdateWordMenuActivity.this.translationAndLanguages);
                activity2Intent.putExtra("translationFromLanguageID", UpdateWordMenuActivity.this.fromLanguageID);
                activity2Intent.putExtra("word", UpdateWordMenuActivity.this.word);
                startActivity(activity2Intent);

            }
        });


    }


    @Override
    public void onResume(){
        super.onResume();
        getWordFromDB(word.getWordID());
        getSupportActionBar().setTitle(word.getWordString());
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
