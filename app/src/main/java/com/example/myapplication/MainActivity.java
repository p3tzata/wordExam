package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.activity.ListAllWordActivity;
import com.example.myapplication.activity.NewWordActivity;
import com.example.myapplication.seed.Seed;
import com.example.myapplication.service.WordOldService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private WordOldService wordOldService;


    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wordOldService = new ViewModelProvider(this).get(WordOldService.class);
        setContentView(R.layout.activity_main);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


        final Button button = findViewById(R.id.button_ViewAllWord);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent activity2Intent = new Intent(getApplicationContext(), ListAllWordActivity.class);
                startActivity(activity2Intent);
            }
        });

        final Button btn_deleteAllWords = findViewById(R.id.btn_deleteAll);
        btn_deleteAllWords.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                deleteAllWords();
            }
        });


        Seed seed = new Seed();
        seed.seedDB();


    }



    private void deleteAllWords() {
        class deleteAll extends AsyncTask<Void, Void, Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {
                 return wordOldService.deleteAll();

            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                if (result>0) {
                    Toast.makeText(getApplicationContext(), result + " Words deleted.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Words can not be deleted...", Toast.LENGTH_LONG).show();
                }
            }
        }

        deleteAll gt = new deleteAll();
        gt.execute();
    }






}
