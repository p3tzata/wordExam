package com.example.myapplication.activity.wordActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

import com.example.myapplication.activity.base.BaseEditableAppCompatActivity;
import com.example.myapplication.activity.base.GetItemsExecutorBlock;
import com.example.myapplication.adapter.HelpSentenceEditableAdapter;
import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.HelpSentenceService;

import java.util.List;


public class UpdateWordHelpSentenceActivity extends BaseEditableAppCompatActivity<HelpSentence,HelpSentenceService,
        UpdateWordHelpSentenceActivity,HelpSentenceEditableAdapter> {


    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;
    private Word word;
    private Dialog myDialog;

    @Override
    public void onCreateCustom() {

        UpdateWordHelpSentenceActivity updateWordTranslationActivity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_base_crudable);
        HelpSentenceEditableAdapter adapter = new HelpSentenceEditableAdapter(UpdateWordHelpSentenceActivity.this);
        super.setAdapter(adapter);
        super.setContext(UpdateWordHelpSentenceActivity.this);
        super.setItemService(FactoryUtil.createHelpSentenceService(getApplication()));

        this.translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) getIntent().getSerializableExtra("translationFromLanguageID");
        this.word = (Word) getIntent().getSerializableExtra("word");
        getSupportActionBar().setTitle(word.getWordString() + " | " + "Sentences");
        setGetItemsExecutor(new GetItemsExecutorBlock<HelpSentence>() {
            @Override
            public List<HelpSentence> execute() {
                List<HelpSentence> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(word.getWordID(), "");
                return allOrderAlphabetic;
            }
        });
        getItems();


    }


    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        UpdateWordHelpSentenceActivity updateWordTranslationActivity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_base_crudable);
        HelpSentenceEditableAdapter adapter = new HelpSentenceEditableAdapter(UpdateWordHelpSentenceActivity.this);
        super.setAdapter(adapter);
        super.setContext(UpdateWordHelpSentenceActivity.this);
        super.setItemService(FactoryUtil.createHelpSentenceService(getApplication()));

        this.translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) getIntent().getSerializableExtra("translationFromLanguageID");
        this.word = (Word) getIntent().getSerializableExtra("word");
        setGetItemsExecutor(new GetItemsExecutorImp<HelpSentence>(new GetItemsExecutorBlock<HelpSentence>() {
            @Override
            public List<HelpSentence> execute() {
                List<HelpSentence> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(0L, "");
                return allOrderAlphabetic;
            }
        }));
        getItems();
        getSupportActionBar().setTitle(word.getWordString()+" Sentences");
        FloatingActionButton fab_newItem = findViewById(R.id.fab_newItem);
        fab_newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerCreateUpdateClick(false,null);
            }
        });

    }
    */





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }



    public void handlerDeleteClick(HelpSentence helpSentence) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(helpSentence.getSentenceString());


        builder
                //.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


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

    public void  handlerCreateUpdateClick(boolean isEditMode,HelpSentence helpSentence){
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_base_crud_item);
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




    @Override
    public void recyclerViewOnClickHandler(View v, HelpSentence selectedItem) {
        callShowCrudMenu(v,selectedItem);
    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<HelpSentence>() {
            @Override
            public List<HelpSentence> execute() {
                List<HelpSentence> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(word.getWordID(), contains);
                return allOrderAlphabetic;
            }
        });
    }


}
