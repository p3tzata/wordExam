package com.example.WordCFExam.activity.configureActivity;

import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivityNonFaced;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.configure.LanguagePartOfSpeechEditableAdapter;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.PartOfSpeech;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.PartOfSpeechService;

import java.util.List;

public class ConfigLanguagePartOfSpeech extends BaseEditableAppCompatActivityNonFaced<PartOfSpeech, PartOfSpeechService, ConfigLanguagePartOfSpeech,
        LanguagePartOfSpeechEditableAdapter>
{
    private Language language;

    @Override
    public void onCreateCustom() {
        setContentView(R.layout.activity_base_crudable);
        super.setItemService(FactoryUtil.createPartOfSpeechService(getApplication()));
        LanguagePartOfSpeechEditableAdapter adapter = new LanguagePartOfSpeechEditableAdapter(ConfigLanguagePartOfSpeech.this);
        super.setAdapter(adapter);
        super.setContext(ConfigLanguagePartOfSpeech.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.language= (Language) getIntent().getSerializableExtra("language");
        getSupportActionBar().setTitle("Part of Speech" + " | " + language.getLanguageName());
        setGetItemsExecutor(new GetItemsExecutorBlock<PartOfSpeech>() {
            @Override
            public List<PartOfSpeech> execute() {
                List<PartOfSpeech> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(language.getLanguageID(), "");
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

    @Override
    public void handlerCreateUpdateClick(boolean isEditMode, PartOfSpeech selectedItem) {
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_base_crud_item);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);


        EditText et_newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);
        et_newItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    myDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        if (isEditMode && selectedItem!=null) {
            et_newItem.setText(selectedItem.getName());
        }

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (isEditMode) {
                    selectedItem.setName(et_newItem.getText().toString());
                    updateItem(selectedItem);
                    myDialog.dismiss();
                } else {
                    PartOfSpeech newItem = new PartOfSpeech();
                    newItem.setName(et_newItem.getText().toString());
                    newItem.setLanguageID(language.getLanguageID());
                    createItem(newItem);
                    myDialog.dismiss();
                }
            }
        });

        //myDialog.setCancelable(false);
        myDialog.show();
    }



    @Override
    public void recyclerViewOnClickHandler(View v, PartOfSpeech selectedItem) {
        callShowCrudMenu(v,selectedItem);
    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<PartOfSpeech>() {
            @Override
            public List<PartOfSpeech> execute() {
                List<PartOfSpeech> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(language.getLanguageID(), contains);
                return allOrderAlphabetic;
            }
        });

    }
}
