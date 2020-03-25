package com.example.myapplication.activity.wordActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HelpSentenceEditableAdapter;
import com.example.myapplication.adapter.WordTranslateListAdapter;
import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.entity.dto.WordCreationDTO;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.HelpSentenceService;
import com.example.myapplication.service.TranslationWordRelationService;
import com.example.myapplication.utitliy.Session;
import com.example.myapplication.utitliy.SessionNameAttribute;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UpdateWordHelpSentenceActivity extends AppCompatActivity {
    private HelpSentenceService helpSentenceService;
    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;
    private Word word;
    private Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UpdateWordHelpSentenceActivity updateWordTranslationActivity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_word_translation);
        this.helpSentenceService= FactoryUtil.createHelpSentenceService(this.getApplication());
        this.translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) getIntent().getSerializableExtra("translationFromLanguageID");
        this.word = (Word) getIntent().getSerializableExtra("word");

        getItems(word);
        getSupportActionBar().setTitle(word.getWordString()+" Sentences");
        FloatingActionButton fab_newItem = findViewById(R.id.fab_newItem);
        fab_newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLoginDialog(false,null);
            }
        });

    }

    public void callDeleteConfirmDialog(HelpSentence helpSentence) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(helpSentence.getSentenceString());


        builder
                //.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
/*
                        if (context instanceof UpdateWordHelpSentenceActivity) {
                            UpdateWordHelpSentenceActivity context = (UpdateWordHelpSentenceActivity) HelpSentenceEditableAdapter.this.context;
                            context.deleteItem(selectedItem);
                        }*/
                        deleteItem(helpSentence);


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        alert.setTitle("Are you sure for deleting");
        alert.show();

    }

    public void callLoginDialog(boolean isEditMode,HelpSentence helpSentence)
    {
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_new_item);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);


        EditText newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);

        if (isEditMode && helpSentence!=null) {
            newItem.setText(helpSentence.getSentenceString());
        }

            btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (isEditMode) {
                    helpSentence.setSentenceString(newItem.getText().toString());
                    updateItem(helpSentence);
                    myDialog.dismiss();
                } else {
                    HelpSentence helpSentence = new HelpSentence();
                    helpSentence.setWordID(word.getWordID());
                    helpSentence.setSentenceString(newItem.getText().toString());
                    createItem(helpSentence);
                    myDialog.dismiss();
                }
            }
        });

        //myDialog.setCancelable(false);
        myDialog.show();
    }


    private void createItem(HelpSentence item) {
        class GetTasks extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
               helpSentenceService.insert(item);
               return null;
            }

            @Override
            protected void onPostExecute(Void voiD) {
                super.onPostExecute(voiD);
                getItems(word);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    private void updateItem(HelpSentence item) {
        class GetTasks extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                helpSentenceService.update(item);
                return null;
            }

            @Override
            protected void onPostExecute(Void voiD) {
                super.onPostExecute(voiD);
                getItems(word);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }




    private void getItems(Word word) {
        class GetTasks extends AsyncTask<Void, Void, List<HelpSentence>> {

            @Override
            protected List<HelpSentence> doInBackground(Void... voids) {

                List<HelpSentence> helpSentenceServiceAllByWordID = helpSentenceService.findAllByWordID(word.getWordID());
                return helpSentenceServiceAllByWordID;


            }

            @Override
            protected void onPostExecute(List<HelpSentence> items) {
                super.onPostExecute(items);

                HelpSentenceEditableAdapter adapter = new HelpSentenceEditableAdapter(UpdateWordHelpSentenceActivity.this);

                adapter.setItems(items);
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }



    public void deleteItem(HelpSentence item) {
        class GetTasks extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                helpSentenceService.delete(item);
                return null;
            }

            @Override
            protected void onPostExecute(Void voiD) {
                Toast.makeText(UpdateWordHelpSentenceActivity.this.getApplicationContext(),"Successfully delete",Toast.LENGTH_SHORT).show();
                getItems(UpdateWordHelpSentenceActivity.this.word);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
