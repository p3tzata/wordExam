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
import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivity;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.activity.base.onMenuItemClickHandlerExecutor;
import com.example.WordCFExam.adapter.ListWordEditableAdapter;
import com.example.WordCFExam.adapter.spinnerAdapter.CFProfileSpinAdapter;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePointCross;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.WordService;
import com.example.WordCFExam.service.exam.CFExamProfilePointService;
import com.example.WordCFExam.service.exam.CFExamProfileService;
import com.example.WordCFExam.service.exam.CFExamWordQuestionnaireService;
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


public class ListWordEditableActivity extends BaseEditableAppCompatActivity<Word, WordService,
        ListWordEditableActivity, ListWordEditableAdapter> {

    TranslationAndLanguages translationAndLanguages;
    CFExamWordQuestionnaireService cfExamWordQuestionnaireService;
    CFExamProfileService cfExamProfileService;
    CFExamProfilePointService cfExamProfilePointService;


    Long fromLanguageID;
    Long toLanguageID;
    private Dialog myDialog;
    private Boolean isSetToCFExam;
    private CFExamWordQuestionnaire cfExamWordQuestionnaire;
    private CFExamProfilePointCross cfExamProfilePointCross;
    private CFProfileSpinAdapter cfProfileSpinAdapter;

    @Override
    public void onCreateCustom() {
        this.isSetToCFExam=false;
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


        String formatTitle="Search %s word";

        if (fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
            getSupportActionBar().setTitle(String.format(formatTitle,translationAndLanguages.getForeignLanguage().getLanguageName()));
        } else {
            getSupportActionBar().setTitle(String.format(formatTitle,translationAndLanguages.getNativeLanguage().getLanguageName()));
        }
        setGetItemsExecutor(new GetItemsExecutorBlock<Word>() {
            @Override
            public List<Word> execute() {
                /*
                List<Topic> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(topicType.getTopicTypeID(), "");
                return allOrderAlphabetic;
                */
                return new ArrayList<Word>();
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

        mapping.put( R.id.menu_update,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {

                Intent activity2Intent=null;

                if (MenuUtility.isEditMode(getApplicationContext()) && fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
                    activity2Intent = new Intent(getApplicationContext(), UpdateWordMenuActivity.class);
                    activity2Intent.putExtra("translationAndLanguages", translationAndLanguages);
                    activity2Intent.putExtra("translationFromLanguageID", fromLanguageID);
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

    private void handlerSetCFExamClick(Word selectedItem) {

        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_set_cfexam);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);
        Button btn_Cancel = (Button) myDialog.findViewById(R.id.btn_dialog_cancel);
        TextView tv_msg = (TextView) myDialog.findViewById(R.id.tv_setCFExamProfile_msg);
        Spinner spn_cfExamProfile = (Spinner) myDialog.findViewById(R.id.spn_cfExamProfile);



        DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<Boolean>() {
            @Override
            public Boolean doInBackground() {
                cfExamWordQuestionnaire = cfExamWordQuestionnaireService.findByWordID(selectedItem.getWordID(), toLanguageID);
                if(cfExamWordQuestionnaire!=null) {
                    cfExamProfilePointCross = cfExamProfilePointService.findCrossByID(cfExamWordQuestionnaire.getCurrentCFExamProfilePointID());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPostExecute(Boolean item) {
                setSetToCFExam(item);

                if (getSetToCFExam()) {
                    DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy HH:mm:ss");
                    String formattedEntryPointDate = dateFormat.format(cfExamWordQuestionnaire.getEntryPointDateTime());
                    String sourceString="This word is set to CF Exam. " +
                            "<br>Profile Name: <i><u>" + cfExamProfilePointCross.getCfExamProfile().getName()+ "</u></i>" +
                            "<br>Point Name: <i><u>" + cfExamProfilePointCross.getCfExamProfilePoint().getLabelText()  + "</u></i>" +
                            "<br>Current Entry Date Point: <i><u>" + formattedEntryPointDate + "</u></i>" +
                            "<br><b>Do you want to UnSet?</b>";
                    tv_msg.setText(Html.fromHtml(sourceString));
                    spn_cfExamProfile.setVisibility(View.INVISIBLE);
                    btn_Submit.setText("UnSet");
                } else {
                    tv_msg.setText("Please choose CF Exam Profile.");
                    spn_cfExamProfile.setVisibility(View.VISIBLE);
                    btn_Submit.setText("Set");

                    DbExecutorImp<List<CFExamProfile>> dbExecutorCFExamProfile = FactoryUtil.<List<CFExamProfile>>createDbExecutor();
                    dbExecutorCFExamProfile.execute_(new DbExecutor<List<CFExamProfile>>() {
                        @Override
                        public List<CFExamProfile> doInBackground() {
                            return cfExamProfileService.findAllOrderAlphabetic(Session.getLongAttribute(getContext(), SessionNameAttribute.ProfileID,-1L),"");
                        }

                        @Override
                        public void onPostExecute(List<CFExamProfile> item) {
                            CFExamProfile cfExamProfileSelect = new CFExamProfile();
                            cfExamProfileSelect.setProfileID(-1L);
                            cfExamProfileSelect.setCFExamProfileID(-1L);
                            cfExamProfileSelect.setName("Select");

                            item.add(0,cfExamProfileSelect);

                            cfProfileSpinAdapter = new CFProfileSpinAdapter(ListWordEditableActivity.this,
                                    //android.R.layout.simple_spinner_item,
                                    R.layout.spinner_item,
                                    item);

                            spn_cfExamProfile.setAdapter(cfProfileSpinAdapter);


                        }
                    });




                }

            }
        });








        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DbExecutorImp<Boolean> dbExecutorCFExamProfile = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutorCFExamProfile.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        if (getSetToCFExam()) {

                            Integer delete = cfExamWordQuestionnaireService.delete(cfExamWordQuestionnaire);
                            if (delete>0) {
                                return true;
                            } else {
                                return false;
                            }

                        } else {
                            int selectedItemPosition = spn_cfExamProfile.getSelectedItemPosition();
                            CFExamProfile selectedCFExamProfile = cfProfileSpinAdapter.getItem(selectedItemPosition);


                            boolean isCreated = cfExamWordQuestionnaireService.create(selectedItem.getWordID(), toLanguageID, selectedCFExamProfile);
                            if (isCreated){
                                return true;
                            } else {
                                return false;
                            }
                        }

                    }

                    @Override
                    public void onPostExecute(Boolean item) {
                        myDialog.dismiss();

                        if (item) {
                            Toast.makeText(getContext(), "Operation is Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });




            }
        });


        myDialog.show();


    }


    public Boolean getSetToCFExam() {
        return isSetToCFExam;
    }

    public void setSetToCFExam(Boolean setToCFExam) {
        isSetToCFExam = setToCFExam;
    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<Word>() {
            @Override
            public List<Word> execute() {
                if (contains.length()<2) {
                    return new ArrayList<Word>();
                }
                return getItemService().findByWordStringContainsAndProfileIDAndLanguageID(contains,translationAndLanguages.getTranslation().getProfileID(),fromLanguageID);

            }
        });
    }




}
