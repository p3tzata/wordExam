package com.example.myapplication.activity;

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
import com.example.myapplication.entity.WordOld;
import com.example.myapplication.service.WordOldService;



public class UpdateWordActivity extends AppCompatActivity {
    private WordOldService wordOldService;
    private EditText editTextName;
    private WordOld wordOld;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.wordOldService = new ViewModelProvider(this).get(WordOldService.class);
        setContentView(R.layout.activity_update_word);

        editTextName = (EditText) findViewById(R.id.editTextName);
        final Long word_id = (Long) getIntent().getSerializableExtra("word_id");
        getWordFromDB(word_id);



        /**/
        findViewById(R.id.btn_Update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportFormToEntiy(wordOld);
                updateWord();
            }
        });


    }

    private void exportFormToEntiy(WordOld wordOld) {
        wordOld.setmWord(editTextName.getText().toString());


    }

    private void loadForm() {
        editTextName.setText(this.wordOld.getWord());
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

    private void getWordFromDB(Long word_id) {
        class GetTasks extends AsyncTask<Void, Void, WordOld> {

            @Override
            protected WordOld doInBackground(Void... voids) {
                UpdateWordActivity.this.wordOld = wordOldService.findById(word_id);
                return wordOld;
            }

            @Override
            protected void onPostExecute(WordOld word) {
                super.onPostExecute(word);
                UpdateWordActivity.this.wordOld = word;
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
                wordOldService.update(wordOld);
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






