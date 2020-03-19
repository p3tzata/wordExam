package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.adapter.WordListAdapter;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.WordService;

import java.util.List;

public class ListAllWordActivity extends AppCompatActivity {

    private WordService wordService;
    TranslationAndLanguages translationAndLanguages;
    Long fromLanguageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.wordOldService = new ViewModelProvider(this).get(WordOldService.class);
        this.wordService = FactoryUtil.createWordService(this.getApplication());
        setContentView(R.layout.activity_list_all_word);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent i = getIntent();
        this.translationAndLanguages = (TranslationAndLanguages) i.getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) i.getSerializableExtra("translationFromLanguageID");

        String formatTitle="Search %s word";

        if (fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
            getSupportActionBar().setTitle(String.format(formatTitle,translationAndLanguages.getForeignLanguage().getLanguageName()));
        } else {
            getSupportActionBar().setTitle(String.format(formatTitle,translationAndLanguages.getNativeLanguage().getLanguageName()));
        }


        Toast.makeText(getApplicationContext(), "Please use search bar...",Toast.LENGTH_LONG).show();

        getWords(null);

        /*
        SearchView sv_word= (SearchView) findViewById(R.id.sv_word);
        sv_word.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getWords(newText);
                return false;
            }
        });
        */



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
              //  finish();
               // return true;
                Intent intent = new Intent(this, ListAllDictionary.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_word, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search_word);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getWords(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    private void getWords(String searchString) {
        class GetTasks extends AsyncTask<Void, Void, List<Word>> {

            @Override
            protected List<Word> doInBackground(Void... voids) {
              //  wordService.insert(new Word("cska"));
                List<Word> allWords =null;
                if (searchString==null || searchString.length()==0) {
                    //allWords = wordService.findAllOrderAlphabetic();
                    allWords = null;
                } else {

                    allWords = wordService.findByWordStringContainsAndProfileIDAndLanguageID(searchString,translationAndLanguages.getTranslation().getProfileID(),fromLanguageID);
                //wordOldService.findAllWordStringContain(searchString);
                }
                return allWords;
            }

            @Override
            protected void onPostExecute(List<Word> tasks) {
                super.onPostExecute(tasks);


                WordListAdapter adapter = new WordListAdapter(getApplicationContext());

                adapter.setWords(tasks);
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }



}
