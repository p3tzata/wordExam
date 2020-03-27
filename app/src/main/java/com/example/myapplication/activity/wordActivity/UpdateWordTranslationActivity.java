package com.example.myapplication.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NativeWordTranslateListAdapter;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.entity.dto.WordCreationDTO;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.TranslationWordRelationService;
import com.example.myapplication.utitliy.Session;
import com.example.myapplication.utitliy.SessionNameAttribute;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UpdateWordTranslationActivity extends AppCompatActivity {
    private TranslationWordRelationService translationWordRelationService;
    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;
    private Word word;
    private Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UpdateWordTranslationActivity updateWordTranslationActivity = this;

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_update_word_translation);
        this.translationWordRelationService= FactoryUtil.createTranslationWordRelationService(this.getApplication());
        this.translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) getIntent().getSerializableExtra("translationFromLanguageID");
        this.word = (Word) getIntent().getSerializableExtra("word");
        getSupportActionBar().setTitle("Translation");
        getWords(word);

        FloatingActionButton fab_newWord = findViewById(R.id.fab_newItem);
        fab_newWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLoginDialog();
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
        return true;
    }


    private void callLoginDialog()
    {
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_new_item);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);

        EditText newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                createTranslation(newItem.getText().toString());
                myDialog.dismiss();
            }
        });

        //myDialog.setCancelable(false);
        myDialog.show();
    }


    private void createTranslation(String translation) {
        class GetTasks extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
               // ForeignWithNativeWords translationWordFromForeign = translationWordRelationService.translateFromForeign(foreignWord.getWordID());
                WordCreationDTO wordCreationDTO = new WordCreationDTO();
                wordCreationDTO.setWordString(translation);
                wordCreationDTO.setLanguageID(UpdateWordTranslationActivity.this.translationAndLanguages.getNativeLanguage().getLanguageID());
                wordCreationDTO.setProfileID(Session.getLongAttribute(UpdateWordTranslationActivity.this, SessionNameAttribute.ProfileID,0L));

                translationWordRelationService.createWordRelation(word,wordCreationDTO);
                return null;
            }

            @Override
            protected void onPostExecute(Void voiD) {
                super.onPostExecute(voiD);
                getWords(word);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    private void getWords(Word word) {
        class GetTasks extends AsyncTask<Void, Void, ForeignWithNativeWords> {

            @Override
            protected ForeignWithNativeWords doInBackground(Void... voids) {
                //  wordService.insert(new Word("cska"));
                List<Word> allWords =null;

                    ForeignWithNativeWords translationWordFromForeign = translationWordRelationService.translateFromForeign(word.getWordID());
                    return translationWordFromForeign;                    //wordOldService.findAllWordStringContain(searchString);


            }

            @Override
            protected void onPostExecute(ForeignWithNativeWords tasks) {
                super.onPostExecute(tasks);


                NativeWordTranslateListAdapter adapter = new NativeWordTranslateListAdapter(UpdateWordTranslationActivity.this);

                adapter.setItems(tasks.getNativeWords(), null,null);
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }



    public void deleteRelation(Word nativeWord) {
        class GetTasks extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                translationWordRelationService.deleteNativeTranslation(UpdateWordTranslationActivity.this.word,nativeWord);
                return null;
            }

            @Override
            protected void onPostExecute(Void voiD) {
                Toast.makeText(UpdateWordTranslationActivity.this.getApplicationContext(),"Successfully delete",Toast.LENGTH_SHORT).show();
                getWords(UpdateWordTranslationActivity.this.word);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
