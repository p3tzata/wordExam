package com.example.WordCFExam.activity.wordActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.WordCFExam.R;

import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivityNonFaced;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.HelpSentenceEditableAdapter;
import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.HelpSentenceService;

import java.util.List;


public class UpdateWordHelpSentenceActivity extends BaseEditableAppCompatActivityNonFaced<HelpSentence,HelpSentenceService,
        UpdateWordHelpSentenceActivity,HelpSentenceEditableAdapter> {


    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;
    private Long toLanguageID;
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
        this.toLanguageID = (Long) getIntent().getSerializableExtra("translationToLanguageID");
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
        myDialog.setContentView(R.layout.dialog_base_crud_two_item);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/5);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);


        TextView lbl_newItem = (TextView) myDialog.findViewById(R.id.lbl_dialog_new_item);
        TextView lbl_newItem2 = (TextView) myDialog.findViewById(R.id.lbl_dialog_new_item2);
        lbl_newItem.setText("Help Sentence");
        lbl_newItem2.setText("Translation");
        EditText newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);
        EditText newItem2 = (EditText) myDialog.findViewById(R.id.et_dialog_newItem2);


        newItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    myDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });


        if (isEditMode && helpSentence!=null) {
            newItem.setText(helpSentence.getSentenceString());
            newItem2.setText(helpSentence.getSentenceTranslation());
        }

            btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (isEditMode) {
                    helpSentence.setSentenceString(newItem.getText().toString());
                    helpSentence.setSentenceTranslation(newItem2.getText().toString());
                    updateItem(helpSentence);
                    myDialog.dismiss();
                } else {
                    HelpSentence helpSentence = new HelpSentence();
                    helpSentence.setWordID(word.getWordID());
                    helpSentence.setToLanguageID(toLanguageID);
                    helpSentence.setSentenceString(newItem.getText().toString());
                    helpSentence.setSentenceTranslation(newItem2.getText().toString());
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
