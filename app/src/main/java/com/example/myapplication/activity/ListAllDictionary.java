package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TranslationListAdapter;
import com.example.myapplication.adapter.WordListAdapter;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.TranslationService;

import java.util.List;

public class ListAllDictionary extends AppCompatActivity {

    TranslationService translationService;
    List<TranslationAndLanguages> allTranslationByProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_dictionary);
        translationService = FactoryUtil.createTranslationService(getApplication());
        getTranslation();
        String debug=null;

    }

    private void getTranslation() {
        class GetTasks extends AsyncTask<Void, Void, List<TranslationAndLanguages>> {

            @Override
            protected List<TranslationAndLanguages> doInBackground(Void... voids) {
                //  wordService.insert(new Word("cska"));
                List<TranslationAndLanguages> allTranslationByProfile =null;
                allTranslationByProfile = translationService.findAllTransAndLangByProfile(1L);
                return allTranslationByProfile;
            }

            @Override
            protected void onPostExecute(List<TranslationAndLanguages> tasks) {
                super.onPostExecute(tasks);


                TranslationListAdapter adapter = new TranslationListAdapter(getApplicationContext());

                adapter.setWords(tasks);
                RecyclerView recyclerView = findViewById(R.id.rc_list_all_dictionary);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


}
