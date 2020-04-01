package com.example.myapplication.activity.configureActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.example.myapplication.R;
import com.example.myapplication.activity.base.BaseEditableAppCompatActivity;
import com.example.myapplication.activity.base.GetItemsExecutorBlock;
import com.example.myapplication.activity.base.onMenuItemClickHandlerExecutor;
import com.example.myapplication.adapter.configure.LanguageEditableAdapter;
import com.example.myapplication.entity.Language;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.LanguageService;

import java.util.List;
import java.util.Map;


public class ConfigLanguageActivity extends BaseEditableAppCompatActivity<Language,LanguageService, ConfigLanguageActivity,LanguageEditableAdapter> {

    @Override
    public void onCreateCustom() {
        setContentView(R.layout.activity_base_crudable);
        super.setItemService(FactoryUtil.createLanguageService(getApplication()));
        LanguageEditableAdapter adapter = new LanguageEditableAdapter(ConfigLanguageActivity.this);
        super.setAdapter(adapter);
        super.setContext(ConfigLanguageActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Configure Languages");
        setGetItemsExecutor(new GetItemsExecutorBlock<Language>() {
            @Override
            public List<Language> execute() {
                List<Language> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(0L, "");
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
    public void handlerDeleteClick(Language selectedItem) {

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
    public void handlerCreateUpdateClick(boolean isEditMode, Language selectedItem) {
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



    @Override
    public void recyclerViewOnClickHandler(View v, Language selectedItem) {
           // callShowCrudMenu(v,selectedItem);
              callCustomLanguageCrudMenu(v,selectedItem);
    }

    private void callCustomLanguageCrudMenu(View v, Language selectedItem) {
        callShowCustomLanguageCrudMenu(R.menu.popup_crud_language,v,selectedItem);
    }

    private void callShowCustomLanguageCrudMenu(int popupMenuID, View v, Language selectedItem) {

        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        //popupMenu.inflate(R.menu.popup_crud_menu_update_delete);
        popupMenu.inflate(popupMenuID);
        //adding click listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMenuItemClickHandlerMappingConfig(mappingMenuItemHandler,item,selectedItem);
                //I selectedItem = mItems.get(getAdapterPosition());
                return onMenuItemClickHandler(item);

            }
        });

        popupMenu.show();


    }
    @Override
    public void onMenuItemClickHandlerMappingConfig(Map<Integer, onMenuItemClickHandlerExecutor> mapping, MenuItem item, Language selectedItem){

        mapping.put( R.id.menu_lang_partOfSpeech,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                Intent intent = new Intent(getContext(), ConfigLanguagePartOfSpeech.class);
                intent.putExtra("language", selectedItem);
                getContext().startActivity(intent);
            }
        });



        mapping.put( R.id.menu_lang_wordForm,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                Intent intent = new Intent(getContext(), ConfigLanguageWordForm.class);
                intent.putExtra("language", selectedItem);
                getContext().startActivity(intent);
            }
        });




        mapping.put( R.id.menu_update,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                getContext().handlerCreateUpdateClick(true,selectedItem);
            }
        });

        mapping.put(R.id.menu_delete, new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                getContext().handlerDeleteClick(selectedItem);
            };
        });


    }


    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<Language>() {
            @Override
            public List<Language> execute() {
                List<Language> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(0L, contains);
                return allOrderAlphabetic;
            }
        });
    }


}
