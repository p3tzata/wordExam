package com.example.myapplication.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import com.example.myapplication.adapter.UpdWordBasicPartOfSpeechListAdapter;
import com.example.myapplication.adapter.WordTranslateListAdapter;
import com.example.myapplication.adapter.partOfSpeechSpinAdapter;
import com.example.myapplication.entity.PartOfSpeech;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.WordPartOfSpeech;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;
import com.example.myapplication.entity.dto.ForeignWordWithDefPartOfSpeech;
import com.example.myapplication.entity.dto.TranslationAndLanguages;

import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.PartOfSpeechService;
import com.example.myapplication.service.TranslationWordRelationService;
import com.example.myapplication.service.WordPartOfSpeechService;
import com.example.myapplication.service.WordService;

import java.util.List;


public class UpdateWordBasicActivity extends AppCompatActivity {
    private WordService wordService;
    private TranslationWordRelationService translationWordRelationService;
    private PartOfSpeechService partOfSpeechService;
    private WordPartOfSpeechService wordPartOfSpeechService;
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
        this.partOfSpeechService=FactoryUtil.createPartOfSpeechService(getApplication());
        this.wordPartOfSpeechService=FactoryUtil.createWordPartOfSpeechService(getApplication());
        setContentView(R.layout.activity_update_word);
        Intent intent = getIntent();
        this.translationAndLanguages = (TranslationAndLanguages) intent.getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) intent.getSerializableExtra("translationFromLanguageID");


        editTextName = (EditText) findViewById(R.id.et_wordString);
        final Word word = (Word) getIntent().getSerializableExtra("word");
        getWordFromDB(word);
        getNativeWords(word);
        getAllPartOfSpeech(translationAndLanguages.getForeignLanguage().getLanguageID());
        getDefinedPartOfSpeech(word);


        /**/
        findViewById(R.id.btn_UpdateForeignWord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportFormToEntiy(word);
                updateWord();
                String debug=null;
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
                UpdateWordBasicActivity.this.foreignWithNativeWords = foreignWithNativeWords;

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void getWordFromDB(Word word) {
        class GetTasks extends AsyncTask<Void, Void, Word> {

            @Override
            protected Word doInBackground(Void... voids) {
                UpdateWordBasicActivity.this.word = wordService.findByID(word.getWordID());
                return word;
            }

            @Override
            protected void onPostExecute(Word word) {
                super.onPostExecute(word);
                UpdateWordBasicActivity.this.word = word;
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
                //finish();
                //startActivity(new Intent(UpdateWordBasicActivity.this, ListAllWordActivity.class));
            }
        }

        updateAsync ut = new updateAsync();
        ut.execute();

    }

    private void getAllPartOfSpeech(Long languageID) {
        class GetTasks extends AsyncTask<Void, Void, List<PartOfSpeech>> {

            @Override
            protected List<PartOfSpeech> doInBackground(Void... voids) {
                List<PartOfSpeech> partOfSpeeches = partOfSpeechService.findAllByLanguageID(languageID);
                return partOfSpeeches;
            }

            @Override
            protected void onPostExecute(List<PartOfSpeech> partOfSpeeches) {
                super.onPostExecute(partOfSpeeches);
                PartOfSpeech partOfSpeech = new PartOfSpeech();

                partOfSpeech.setPartOfSpeechID(-1L);
                partOfSpeech.setName("Add some definition");

                partOfSpeeches.add(0,partOfSpeech);


                partOfSpeechSpinAdapter adapter = new partOfSpeechSpinAdapter(UpdateWordBasicActivity.this,
                        //android.R.layout.simple_spinner_item,
                        R.layout.spinner_item,
                        partOfSpeeches);
                Spinner spn_partOfSpeech = (Spinner) findViewById(R.id.spn_partOfSpeech);
                spn_partOfSpeech.setAdapter(adapter); // Set the custom adapter to the spinner
                // You can create an anonymous listener to handle the event when is selected an spinner item
                spn_partOfSpeech.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               int position, long id) {
                        // Here you get the current item (a User object) that is selected by its position


                        PartOfSpeech partOfSpeech = adapter.getItem(position);
                        if (!partOfSpeech.getPartOfSpeechID().equals(-1L)) {
                            createDefPartOfSpeech(partOfSpeech);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapter) {  }
                });


            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void getDefinedPartOfSpeech(Word word) {
        class GetTasks extends AsyncTask<Void, Void, ForeignWordWithDefPartOfSpeech> {

            @Override
            protected ForeignWordWithDefPartOfSpeech doInBackground(Void... voids) {

                ForeignWordWithDefPartOfSpeech foreignWordWithDefPartOfSpeech = wordPartOfSpeechService.findForeignWordWithDefPartOfSpeech(word.getWordID());
                return foreignWordWithDefPartOfSpeech;


            }

            @Override
            protected void onPostExecute(ForeignWordWithDefPartOfSpeech tasks) {
                super.onPostExecute(tasks);


                UpdWordBasicPartOfSpeechListAdapter adapter = new UpdWordBasicPartOfSpeechListAdapter(UpdateWordBasicActivity.this);

                adapter.setItems(tasks.getPartOfSpeeches());
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    public void deleteDefPartOfSpeech(PartOfSpeech partOfSpeech)    {
        class GetTasks extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                wordPartOfSpeechService.deleteDefPartOfSpeech(UpdateWordBasicActivity.this.word,partOfSpeech);
                return null;
            }

            @Override
            protected void onPostExecute(Void voiD) {
                Toast.makeText(UpdateWordBasicActivity.this.getApplicationContext(),"Successfully delete",Toast.LENGTH_SHORT).show();
                getDefinedPartOfSpeech(UpdateWordBasicActivity.this.word);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    public void createDefPartOfSpeech(PartOfSpeech partOfSpeech)    {
        class GetTasks extends AsyncTask<Void, Void, Long> {

            @Override
            protected Long doInBackground(Void... voids) {
                WordPartOfSpeech wordPartOfSpeech = new WordPartOfSpeech();
                wordPartOfSpeech.setWordID(word.getWordID());
                wordPartOfSpeech.setPartOfSpeechID(partOfSpeech.getPartOfSpeechID());
                return wordPartOfSpeechService.insert(wordPartOfSpeech);


            }

            @Override
            protected void onPostExecute(Long id) {
                if(!id.equals(-1L)) {
                    Toast.makeText(UpdateWordBasicActivity.this.getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                }
                getDefinedPartOfSpeech(UpdateWordBasicActivity.this.word);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }



}





