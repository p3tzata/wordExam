package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.WordListAdapter;
import com.example.myapplication.entity.WordOld;
import com.example.myapplication.service.WordOldService;

import java.util.List;

public class ListAllWordActivity extends AppCompatActivity {

    private WordOldService wordOldService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wordOldService = new ViewModelProvider(this).get(WordOldService.class);
        setContentView(R.layout.activity_list_all_word);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWords(null);

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


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
              //  finish();
               // return true;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }




    private void getWords(String searchString) {
        class GetTasks extends AsyncTask<Void, Void, List<WordOld>> {

            @Override
            protected List<WordOld> doInBackground(Void... voids) {
              //  wordService.insert(new Word("cska"));
                List<WordOld> allWordOlds =null;
                if (searchString==null) {
                    allWordOlds = wordOldService.getAllWords();
                } else {
                    allWordOlds = wordOldService.findAllWordStringContain(searchString);
                }
                return allWordOlds;
            }

            @Override
            protected void onPostExecute(List<WordOld> tasks) {
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
