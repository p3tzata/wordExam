package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.entity.WordOld;
import com.example.myapplication.service.WordOldService;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditWordView;

    private WordOldService wordOldService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.wordOldService = new ViewModelProvider(this).get(WordOldService.class);
        setContentView(R.layout.activity_new_word);
        mEditWordView = findViewById(R.id.edit_word);

        final Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveWord(false);
            }
        });
        final Button btn_saveCls = findViewById(R.id.btn_saveCls);
        btn_saveCls.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveWord(true);
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


    private void saveWord(boolean isClose) {
        final String sWordText = mEditWordView.getText().toString().trim();

        if (sWordText.isEmpty()) {
            mEditWordView.setError("Must not be Empty!");
            mEditWordView.requestFocus();
            return;
        }



        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                WordOld wordOld = new WordOld(sWordText);

                //adding to database
                wordOldService.insert(wordOld);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();


                if (isClose) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    //mEditWordView.setText("");
                    startActivity(new Intent(getApplicationContext(), NewWordActivity.class));
                }

                Toast.makeText(getApplicationContext(), "Word Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }



}


