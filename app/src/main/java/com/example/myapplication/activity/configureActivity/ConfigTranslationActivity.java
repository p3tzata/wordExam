package com.example.myapplication.activity.configureActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.R;
import com.example.myapplication.activity.BaseEditableAppCompatActivity;
import com.example.myapplication.activity.wordActivity.UpdateWordBasicActivity;
import com.example.myapplication.adapter.configure.ProfileEditableAdapter;
import com.example.myapplication.adapter.configure.TranslationEditableAdapter;
import com.example.myapplication.adapter.spinnerAdapter.LanguageSpinAdapter;
import com.example.myapplication.adapter.spinnerAdapter.WordFormSpinAdapter;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.WordForm;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.LanguageService;
import com.example.myapplication.service.TranslationService;
import com.example.myapplication.utitliy.Session;
import com.example.myapplication.utitliy.SessionNameAttribute;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ConfigTranslationActivity extends
        BaseEditableAppCompatActivity<Translation, TranslationService, ConfigTranslationActivity, TranslationEditableAdapter> {

    private Long profileID;
    private String profileName;
    private List<Language> languages;
    private LanguageService languageService;
    private Dialog myDialog;
    private LanguageSpinAdapter nativeSpinnerAdapter;
    private LanguageSpinAdapter foreignSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_item);
        profileID= Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID,-1L);
        profileName=Session.getStringAttribute(getApplicationContext(),SessionNameAttribute.ProfileName,"");
        this.languageService=FactoryUtil.createLanguageService(getApplication());
        super.setItemService(FactoryUtil.createTranslationService(getApplication()));
        TranslationEditableAdapter adapter = new TranslationEditableAdapter(ConfigTranslationActivity.this);
        super.setAdapter(adapter);
        super.setContext(ConfigTranslationActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(profileName +" | "+ "Translation");

        getItems();
        getAllLanguages();
        FloatingActionButton fab_newItem = findViewById(R.id.fab_newItem);
        fab_newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPopUpDialog(false,null);
            }
        });
    }

    private void getAllLanguages() {
        class GetTasks extends AsyncTask<Void, Void, List<Language>> {

            @Override
            protected List<Language> doInBackground(Void... voids) {

                return languageService.findAllOrderAlphabetic();
            }

            @Override
            protected void onPostExecute(List<Language> items) {
                super.onPostExecute(items);
                //languages=items;
                ArrayList<Language> nativeLanguages = new ArrayList<>(items);
                ArrayList<Language> foreignLanguages = new ArrayList<>(items);

                Language nativeBlankLanguage = new Language();
                nativeBlankLanguage.setLanguageID(-1L);
                nativeBlankLanguage.setLanguageName("Select native Language");

                nativeLanguages.add(0,nativeBlankLanguage);

                Language foreignBlankLanguage = new Language();
                foreignBlankLanguage.setLanguageID(-1L);
                foreignBlankLanguage.setLanguageName("Select foreign Language");
                foreignLanguages.add(0,foreignBlankLanguage);


                nativeSpinnerAdapter = new LanguageSpinAdapter(ConfigTranslationActivity.this,
                        //android.R.layout.simple_spinner_item,
                        R.layout.spinner_item,
                        nativeLanguages);

                foreignSpinnerAdapter = new LanguageSpinAdapter(ConfigTranslationActivity.this,
                        //android.R.layout.simple_spinner_item,
                        R.layout.spinner_item,
                        foreignLanguages);






            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();

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
    public void callDeleteConfirmDialog(Translation selectedItem) {

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
    public void callPopUpDialog(boolean isEditMode, Translation selectedItem) {
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_translation_edit);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/7);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);
        EditText et_newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);


        Spinner spn_item_native = (Spinner) myDialog.findViewById(R.id.spn_nativeLanguage);
        spn_item_native.setAdapter(nativeSpinnerAdapter); // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item



        Spinner spn_item_foreign = (Spinner) myDialog.findViewById(R.id.spn_foreignLanguage);
        spn_item_foreign.setAdapter(foreignSpinnerAdapter); // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item





        if (isEditMode && selectedItem!=null) {
            et_newItem.setText(selectedItem.getLabelText());

            int spinnerForeignPosition = 0;
            if (selectedItem.getForeignLanguageID() != null) {
                for (int i=0;i<foreignSpinnerAdapter.getCount();i++) {
                    if (foreignSpinnerAdapter.getItem(i).getLanguageID().equals(selectedItem.getForeignLanguageID())) {
                        //if (items.get(i).getWordFormID().equals(word.getWordFormID())) {
                        spinnerForeignPosition=i;
                        break;
                    }
                }

                spn_item_foreign.setSelection(spinnerForeignPosition);
            }


            int spinnerNativePosition = 0;
            if (selectedItem.getNativeLanguageID() != null) {
                for (int i=0;i<nativeSpinnerAdapter.getCount();i++) {
                    if (nativeSpinnerAdapter.getItem(i).getLanguageID().equals(selectedItem.getNativeLanguageID())) {
                        //if (items.get(i).getWordFormID().equals(word.getWordFormID())) {
                        spinnerNativePosition=i;
                        break;
                    }
                }

                spn_item_native.setSelection(spinnerNativePosition);
            }



        }

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                int selectedForeignItemPosition = spn_item_foreign.getSelectedItemPosition();
                Language foreignLanguage = foreignSpinnerAdapter.getItem(selectedForeignItemPosition);

                int selectedNativeItemPosition = spn_item_native.getSelectedItemPosition();
                Language nativeLanguage = nativeSpinnerAdapter.getItem(selectedNativeItemPosition);

                if (isEditMode) {
                    selectedItem.setTranslationName(et_newItem.getText().toString());


                    selectedItem.setNativeLanguageID(nativeLanguage.getLanguageID());

                    selectedItem.setForeignLanguageID(foreignLanguage.getLanguageID());


                    updateItem(selectedItem);
                    myDialog.dismiss();
                } else {
                    Translation newItem = new Translation();
                    newItem.setTranslationName(et_newItem.getText().toString());
                    newItem.setNativeLanguageID(nativeLanguage.getLanguageID());
                    newItem.setForeignLanguageID(foreignLanguage.getLanguageID());

                    newItem.setProfileID(profileID);
                    createItem(newItem);
                    myDialog.dismiss();
                }
            }
        });

        //myDialog.setCancelable(false);
        myDialog.show();
    }

    @Override
    public List<Translation> getListOfItems() {
        return getItemService().findAllOrderAlphabetic(profileID);
    }



}
