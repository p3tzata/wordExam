package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TranslationListAdapter;
import com.example.myapplication.adapter.WordListAdapter;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.TranslationService;
import com.example.myapplication.utitliy.MenuUtility;
import com.example.myapplication.utitliy.Session;
import com.example.myapplication.utitliy.SessionNameAttribute;

import java.util.List;

public class ListAllDictionary extends AppCompatActivity {

    TranslationService translationService;
    List<TranslationAndLanguages> allTranslationByProfile;
    private Menu mOptionsMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_dictionary);
        translationService = FactoryUtil.createTranslationService(getApplication());
        getSupportActionBar().setTitle("List of Dictionaries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getTranslation();
        String debug=null;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuUtility.checkIsEditMode(this,mOptionsMenu);
        return super.onPrepareOptionsMenu(menu);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mOptionsMenu=menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

        }

        return MenuUtility.onOptionsItemSelected(this,item);
    }


    private void getTranslation() {
        class GetTasks extends AsyncTask<Void, Void, List<TranslationAndLanguages>> {

            @Override
            protected List<TranslationAndLanguages> doInBackground(Void... voids) {
                //  wordService.insert(new Word("cska"));
                List<TranslationAndLanguages> allTranslationByProfile =null;
                long profileID = Session.getLongAttribute(ListAllDictionary.this, SessionNameAttribute.ProfileID, 0);
                allTranslationByProfile = translationService.findAllTransAndLangByProfile(profileID);
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
