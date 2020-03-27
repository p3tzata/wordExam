package com.example.myapplication.activity.configureActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.activity.BaseEditableAppCompatActivity;
import com.example.myapplication.adapter.configure.LanguageEditableAdapter;
import com.example.myapplication.entity.Language;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.LanguageService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ConfigLanguageActivity extends BaseEditableAppCompatActivity<Language,LanguageService, ConfigLanguageActivity,LanguageEditableAdapter> {


    private Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_language);
        super.setItemService(FactoryUtil.createLanguageService(getApplication()));
        LanguageEditableAdapter adapter = new LanguageEditableAdapter(ConfigLanguageActivity.this);
        super.setAdapter(adapter);
        super.setContext(ConfigLanguageActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Configure Languages");
        getItems();
        FloatingActionButton fab_newItem = findViewById(R.id.fab_newItem);
        fab_newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPopUpDialog(false,null);
            }
        });

    }

    @Override
    public void callDeleteConfirmDialog(Language selectedItem) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(selectedItem.getLabelText());


        builder
                //.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteItem(selectedItem);
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

    @Override
    public void callPopUpDialog(boolean isEditMode, Language selectedItem) {
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_language_edit);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/7);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);


        EditText et_newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);
        EditText et_url = (EditText) myDialog.findViewById(R.id.et_dialog_lang_url);

        if (isEditMode && selectedItem!=null) {
            et_newItem.setText(selectedItem.getLabelText());
            et_url.setText(selectedItem.getDefinitionUrl());
        }

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (isEditMode) {
                    selectedItem.setLanguageName(et_newItem.getText().toString());
                    selectedItem.setDefinitionUrl(et_url.getText().toString());
                    updateItem(selectedItem);
                    myDialog.dismiss();
                } else {
                    Language newItem = new Language();
                    newItem.setLanguageName(et_newItem.getText().toString());

                    newItem.setDefinitionUrl(et_url.getText().toString());
                    createItem(newItem);
                    myDialog.dismiss();
                }
            }
        });

        //myDialog.setCancelable(false);
        myDialog.show();
    }
}
