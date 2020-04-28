package com.example.WordCFExam.activity.wordActivity;

import android.app.Application;
import android.app.Dialog;
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
import com.example.WordCFExam.activity.base.BaseListableAppCompatActivityFaced;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.activity.base.onMenuItemClickHandlerExecutor;
import com.example.WordCFExam.adapter.ListWordListableAdapter;
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
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListWordListableActivity
        extends BaseListableAppCompatActivityFaced<WordCFExamCross,Word, WordService, ListWordListableActivity, ListWordListableAdapter> {

    TranslationAndLanguages translationAndLanguages;
    Long fromLanguageID;
    Long toLanguageID;

    private Boolean isSetToCFExam;
    private CFExamWordQuestionnaireCross cfExamWordQuestionnaireCross;
    private CFExamProfilePointCross cfExamProfilePointCross;
    private CFProfileSpinAdapter cfProfileSpinAdapter;
    private CFExamWordQuestionnaireService cfExamWordQuestionnaireService;
    private CFExamProfileService cfExamProfileService;
    private CFExamProfilePointService cfExamProfilePointService;
    private Dialog myDialog;
    CFExamHelper cfExamHelper;
    public Boolean getSetToCFExam() {
        return isSetToCFExam;
    }

    public void setSetToCFExam(Boolean setToCFExam) {
        isSetToCFExam = setToCFExam;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateCustom() {

        setContentView(R.layout.activity_base_listable);
        super.setItemService(FactoryUtil.createWordService(getApplication()));
        ListWordListableAdapter adapter = new ListWordListableAdapter(ListWordListableActivity.this);
        super.setAdapter(adapter);
        super.setContext(ListWordListableActivity.this);

        Intent i = getIntent();
        this.translationAndLanguages = (TranslationAndLanguages) i.getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) i.getSerializableExtra("translationFromLanguageID");
        this.toLanguageID = (Long) i.getSerializableExtra("translationToLanguageID");
        this.cfExamWordQuestionnaireService=FactoryUtil.createCFExamQuestionnaireService(getApplication());

        this.cfExamProfilePointService = FactoryUtil.createCFExamProfilePointService(getApplication());
        this.cfExamProfileService=FactoryUtil.createCFExamProfileService(getApplication());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String formatTitle="Search %s word";

        this.cfExamHelper = new CFExamHelper<ListWordListableActivity>
                (ListWordListableActivity.this.getApplication(),ListWordListableActivity.this, new Dialog(this) ,toLanguageID);



        if (fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
            getSupportActionBar().setTitle(String.format(formatTitle,translationAndLanguages.getForeignLanguage().getLanguageName()));
        } else {
            getSupportActionBar().setTitle(String.format(formatTitle,translationAndLanguages.getNativeLanguage().getLanguageName()));
        }
        setGetItemsExecutor(new GetItemsExecutorBlock<WordCFExamCross>() {
            @Override
            public List<WordCFExamCross> execute() {
                   return new ArrayList<WordCFExamCross>();
            }
        });

    }




    @Override
    public void onResume(){
        super.onResume();
        getItems();

    }


    @Override
    public void recyclerViewOnClickHandler(View v, Word selectedItem) {

        callShowViewMenu(v, selectedItem);
    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<WordCFExamCross>() {
            @Override
            public List<WordCFExamCross> execute() {
                if (contains.length()<2) {
                    return new ArrayList<WordCFExamCross>();
                }
                //
                List<WordCFExamCross> byWordStringContainsAndProfileIDAndLanguageIDCFExamCross = getItemService().findByWordStringContainsAndProfileIDAndLanguageIDCFExamCross(contains, translationAndLanguages.getTranslation().getProfileID(), fromLanguageID, toLanguageID);
                return byWordStringContainsAndProfileIDAndLanguageIDCFExamCross;
            }
        });
    }

    @Override
    public void onMenuItemClickHandlerMappingConfig(Map<Integer, onMenuItemClickHandlerExecutor> mapping, MenuItem item, Word selectedItem) {

        mapping.put( R.id.popupView_vew,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
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


        });

        mapping.put(R.id.popupView_custom, new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                handlerSetCFExamClick(selectedItem);
            };
        });

    }

    private void handlerSetCFExamClick(Word selectedItem) {

            cfExamHelper.handlerSetCFExamClick(selectedItem);

    }

    @Override
    public void callShowViewMenu(int popupMenuID, View v, Word selectedItem) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        //popupMenu.inflate(R.menu.popup_crud_menu_update_delete);
        popupMenu.inflate(popupMenuID);
        Menu menu = popupMenu.getMenu();
        menu.getItem(1).setTitle("Set/Unset CF exam");

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

}
