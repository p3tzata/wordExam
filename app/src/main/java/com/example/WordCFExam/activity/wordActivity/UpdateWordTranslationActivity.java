package com.example.WordCFExam.activity.wordActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivityFaced;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.activity.base.onMenuItemClickHandlerExecutor;
import com.example.WordCFExam.adapter.ListWordEditableAdapter;
import com.example.WordCFExam.adapter.spinnerAdapter.CFProfileSpinAdapter;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.entity.dto.WordCFExamCross;
import com.example.WordCFExam.entity.dto.WordCreationDTO;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePointCross;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaireCross;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.TranslationWordRelationService;
import com.example.WordCFExam.service.WordService;
import com.example.WordCFExam.service.exam.CFExamProfilePointService;
import com.example.WordCFExam.service.exam.CFExamProfileService;
import com.example.WordCFExam.service.exam.CFExamWordQuestionnaireService;
import com.example.WordCFExam.utitliy.CFExamHelper;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.MenuUtility;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UpdateWordTranslationActivity extends BaseEditableAppCompatActivityFaced<WordCFExamCross,Word, WordService,
        UpdateWordTranslationActivity, ListWordEditableAdapter> {

    private TranslationWordRelationService translationWordRelationService;
    TranslationAndLanguages translationAndLanguages;
    CFExamWordQuestionnaireService cfExamWordQuestionnaireService;
    CFExamProfileService cfExamProfileService;
    CFExamProfilePointService cfExamProfilePointService;
    Long fromLanguageID;
    Long toLanguageID;
    CFExamHelper cfExamHelper;

    private Dialog myDialog;

    private Word word;

    @Override
    public void onCreateCustom() {

        UpdateWordTranslationActivity updateWordTranslationActivity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_base_crudable);
        ListWordEditableAdapter adapter = new ListWordEditableAdapter(UpdateWordTranslationActivity.this);
        super.setAdapter(adapter);
        super.setContext(UpdateWordTranslationActivity.this);
        super.setItemService(FactoryUtil.createWordService(getApplication()));
        this.translationWordRelationService= FactoryUtil.createTranslationWordRelationService(this.getApplication());
        this.translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) getIntent().getSerializableExtra("translationFromLanguageID");
        this.word = (Word) getIntent().getSerializableExtra("word");
        getSupportActionBar().setTitle(word.getWordString() + " | " + "Translations");
        this.toLanguageID = (Long) getIntent().getSerializableExtra("translationToLanguageID");

        this.cfExamHelper = new CFExamHelper<UpdateWordTranslationActivity>
                (UpdateWordTranslationActivity.this.getApplication(),UpdateWordTranslationActivity.this, new Dialog(this) ,fromLanguageID);


        getSupportActionBar().setTitle(word.getWordString() + " | " + "Translations");


        setGetItemsExecutor(new GetItemsExecutorBlock<WordCFExamCross>() {
            @Override
            public List<WordCFExamCross> execute() {

                List<WordCFExamCross> wordCFExamCrosses = translationWordRelationService.translateFromForeignCFExamCross(word.getWordID(), toLanguageID);
                return wordCFExamCrosses;
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

    public void handlerDeleteClick(Word item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(item.getLabelText());


        builder
                //.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        deleteItem(item);


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

    public void  handlerCreateUpdateClick(boolean isEditMode,Word selectedItem){
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_base_crud_item);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/7);

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

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        WordCreationDTO wordCreationDTO = new WordCreationDTO();
                        wordCreationDTO.setWordString(et_newItem.getText().toString());
                        wordCreationDTO.setLanguageID(UpdateWordTranslationActivity.this.translationAndLanguages.getNativeLanguage().getLanguageID());
                        wordCreationDTO.setProfileID(Session.getLongAttribute(UpdateWordTranslationActivity.this, SessionNameAttribute.ProfileID,0L));

                        boolean wordRelationStatus = translationWordRelationService.createWordRelation(word, wordCreationDTO);
                        return wordRelationStatus;
                    }

                    @Override
                    public void onPostExecute(Boolean item) {
                        if (item) {
                            Toast.makeText(getContext(), "Successfully add", Toast.LENGTH_SHORT).show();
                            myDialog.dismiss();
                        } else {
                            myDialog.dismiss();
                            Toast.makeText(getContext(), "Something went wrong: ", Toast.LENGTH_SHORT).show();
                        }
                        getItems();
                    }
                });

            }
        });

        //myDialog.setCancelable(false);
        myDialog.show();


    }

    @Override
    public void deleteItem(Word item) {
        DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<Boolean>() {
            @Override
            public Boolean doInBackground() {
                Boolean aBoolean = translationWordRelationService.deleteNativeTranslation(UpdateWordTranslationActivity.this.word, item);
                return aBoolean;
            }

            @Override
            public void onPostExecute(Boolean item) {
                if (item) {
                    Toast.makeText(getContext(), "Successfully delete", Toast.LENGTH_SHORT).show();
                    UpdateWordTranslationActivity.this.myDialog.dismiss();
                } else {
                    UpdateWordTranslationActivity.this.myDialog.dismiss();
                    Toast.makeText(getContext(), "Something went wrong: ", Toast.LENGTH_SHORT).show();
                }
                getItems();
            }
        });

    }


    @Override
    public void recyclerViewOnClickHandler(View v, Word selectedItem) {
        callShowCrudMenu(v,selectedItem);
    }

    @Override
    public void callShowCrudMenu(View v, Word selectedItem) {
        callShowCrudMenu(R.menu.popup_crud_menu_update_delete_custom,v,selectedItem);
    }

    @Override
    public void callShowCrudMenu(int popupMenuID,View v, Word selectedItem) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);

        popupMenu.inflate(popupMenuID);
        Menu menu = popupMenu.getMenu();
        menu.getItem(0).setTitle("Set/Unset CF exam");
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);

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
    public void onMenuItemClickHandlerMappingConfig(Map<Integer, onMenuItemClickHandlerExecutor> mapping, MenuItem item, Word selectedItem){

        mapping.put( R.id.menu_custom,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                handlerSetCFExamClick(selectedItem);
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
    public void handlerViewClick(Word selectedItem) {
        Intent activity2Intent=null;
        if (translationAndLanguages.getForeignLanguage().getLanguageID().equals(fromLanguageID)) {
            activity2Intent = new Intent(getContext(), ShowForeignWordActivity.class);
            activity2Intent.putExtra("translationToLanguageID", translationAndLanguages.getNativeLanguage().getLanguageID());
        } else {
            activity2Intent = new Intent(getContext(), ShowNativeWordActivity.class);
            activity2Intent.putExtra("translationToLanguageID", translationAndLanguages.getForeignLanguage().getLanguageID());
        }
        activity2Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity2Intent.putExtra("translationAndLanguages", translationAndLanguages);
        activity2Intent.putExtra("translationFromLanguageID", fromLanguageID);
        activity2Intent.putExtra("word", selectedItem);
        startActivity(activity2Intent);
    }

    private void handlerSetCFExamClick(Word selectedItem) {
      cfExamHelper.handlerSetCFExamClick(selectedItem);
    }


    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<WordCFExamCross>() {
            @Override
            public List<WordCFExamCross> execute() {

                List<WordCFExamCross> wordCFExamCrosses = translationWordRelationService.translateFromForeignCFExamCross(word.getWordID(), toLanguageID);
                return wordCFExamCrosses;
            }
        });
    }




}
