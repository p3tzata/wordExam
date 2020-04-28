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
import android.widget.Button;
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
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePointCross;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaireCross;
import com.example.WordCFExam.factory.FactoryUtil;
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


public class ListWordEditableActivity extends BaseEditableAppCompatActivityFaced<WordCFExamCross,Word, WordService,
        ListWordEditableActivity, ListWordEditableAdapter> {

    TranslationAndLanguages translationAndLanguages;
    CFExamWordQuestionnaireService cfExamWordQuestionnaireService;
    CFExamProfileService cfExamProfileService;
    CFExamProfilePointService cfExamProfilePointService;

    CFExamHelper cfExamHelper;
    Long fromLanguageID;
    Long toLanguageID;


    @Override
    public void onCreateCustom() {

        ListWordEditableActivity updateWordTranslationActivity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_base_crudable);
        ListWordEditableAdapter adapter = new ListWordEditableAdapter(ListWordEditableActivity.this);
        super.setAdapter(adapter);
        super.setContext(ListWordEditableActivity.this);
        super.setItemService(FactoryUtil.createWordService(getApplication()));
        this.cfExamWordQuestionnaireService=FactoryUtil.createCFExamQuestionnaireService(getApplication());
        this.cfExamProfilePointService = FactoryUtil.createCFExamProfilePointService(getApplication());
        this.cfExamProfileService=FactoryUtil.createCFExamProfileService(getApplication());
        Intent i = getIntent();
        this.translationAndLanguages = (TranslationAndLanguages) i.getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) i.getSerializableExtra("translationFromLanguageID");
        this.toLanguageID = (Long) i.getSerializableExtra("translationToLanguageID");
        this.cfExamHelper = new CFExamHelper<ListWordEditableActivity>
                (ListWordEditableActivity.this.getApplication(),ListWordEditableActivity.this, new Dialog(this) ,toLanguageID);

        String formatTitle="Search %s word";

        if (fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
            getSupportActionBar().setTitle(String.format(formatTitle,translationAndLanguages.getForeignLanguage().getLanguageName()));
        } else {
            getSupportActionBar().setTitle(String.format(formatTitle,translationAndLanguages.getNativeLanguage().getLanguageName()));
        }
        setGetItemsExecutor(new GetItemsExecutorBlock<WordCFExamCross>() {
            @Override
            public List<WordCFExamCross> execute() {
                /*
                List<Topic> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(topicType.getTopicTypeID(), "");
                return allOrderAlphabetic;
                */
                return new ArrayList<WordCFExamCross>();
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

    public void  handlerCreateUpdateClick(boolean isEditMode,Word item){

        Intent intent = new Intent(getApplicationContext(), UpdateWordMenuActivity.class);
        intent.putExtra("translationAndLanguages", translationAndLanguages);
        intent.putExtra("translationFromLanguageID", fromLanguageID);
        intent.putExtra("translationToLanguageID", toLanguageID);
        intent.putExtra("word", item);
        startActivity(intent);

    }

    @Override
    public void callShowNewItemButton(FloatingActionButton fab_newItem) {
        fab_newItem.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(getApplicationContext(), NewWordActivity.class);
                intent.putExtra("translationAndLanguages",translationAndLanguages);
                intent.putExtra("translationFromLanguageID",translationAndLanguages.getForeignLanguage().getLanguageID());
                intent.putExtra("translationToLanguageID",translationAndLanguages.getNativeLanguage().getLanguageID());
                startActivityForResult(intent, 1);
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

        mapping.put( R.id.menu_view,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                handlerViewClick(selectedItem);
            }
        });


        mapping.put( R.id.menu_update,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {

                Intent activity2Intent=null;

                if (MenuUtility.isEditMode(getApplicationContext()) && fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
                    activity2Intent = new Intent(getApplicationContext(), UpdateWordMenuActivity.class);
                    activity2Intent.putExtra("translationAndLanguages", translationAndLanguages);
                    activity2Intent.putExtra("translationFromLanguageID", fromLanguageID);
                    activity2Intent.putExtra("translationToLanguageID", toLanguageID);
                    activity2Intent.putExtra("word", selectedItem);
                    startActivity(activity2Intent);
                } else {

                    Toast.makeText(getContext(), "This function is Disabled", Toast.LENGTH_SHORT).show();

                }



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
                if (contains.length()<2) {
                    return new ArrayList<WordCFExamCross>();
                }
                //return getItemService().findByWordStringContainsAndProfileIDAndLanguageID(contains,translationAndLanguages.getTranslation().getProfileID(),fromLanguageID);
                return getItemService().findByWordStringContainsAndProfileIDAndLanguageIDCFExamCross(contains,translationAndLanguages.getTranslation().getProfileID(),fromLanguageID,toLanguageID);
            }
        });
    }




}
