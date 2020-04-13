package com.example.WordCFExam.activity.topic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivity;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.activity.base.onMenuItemClickHandlerExecutor;
import com.example.WordCFExam.adapter.spinnerAdapter.CFProfileSpinAdapter;
import com.example.WordCFExam.adapter.topic.TopicEditableAdapter;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePointCross;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaire;
import com.example.WordCFExam.entity.exam.Topic;
import com.example.WordCFExam.entity.exam.TopicType;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.CFExamProfilePointService;
import com.example.WordCFExam.service.exam.CFExamProfileService;
import com.example.WordCFExam.service.exam.CFExamTopicQuestionnaireService;
import com.example.WordCFExam.service.exam.TopicService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TopicEditableActivity extends BaseEditableAppCompatActivity<Topic, TopicService,
        TopicEditableActivity, TopicEditableAdapter> {


    private TopicType topicType;
    private Dialog myDialog;
    private Boolean isSetToCFExam;
    private CFExamTopicQuestionnaire cfExamTopicQuestionnaire;
    private CFExamProfilePointCross cfExamProfilePointCross;
    private CFProfileSpinAdapter cfProfileSpinAdapter;
    private CFExamTopicQuestionnaireService cfExamTopicQuestionnaireService;
    private CFExamProfileService cfExamProfileService;
    private CFExamProfilePointService cfExamProfilePointService;


    @Override
    public void onCreateCustom() {

        TopicEditableActivity updateWordTranslationActivity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_base_crudable);
        TopicEditableAdapter adapter = new TopicEditableAdapter(TopicEditableActivity.this);
        super.setAdapter(adapter);
        super.setContext(TopicEditableActivity.this);
        super.setItemService(FactoryUtil.createTopicService(getApplication()));

        this.cfExamTopicQuestionnaireService=FactoryUtil.createCFExamTopicQuestionnaireService(getApplication());
        this.cfExamProfilePointService = FactoryUtil.createCFExamProfilePointService(getApplication());
        this.cfExamProfileService=FactoryUtil.createCFExamProfileService(getApplication());

        this.topicType = (TopicType) getIntent().getSerializableExtra("topicType");
        getSupportActionBar().setTitle(topicType.getLabelText());
        setGetItemsExecutor(new GetItemsExecutorBlock<Topic>() {
            @Override
            public List<Topic> execute() {
                /*
                List<Topic> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(topicType.getTopicTypeID(), "");
                return allOrderAlphabetic;
                */
                 return new ArrayList<Topic>();
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

    public void handlerDeleteClick(Topic item) {

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

    public void  handlerCreateUpdateClick(boolean isEditMode,Topic item){
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_base_crud_two_item);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/5);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);

        TextView lbl_newItem = (TextView) myDialog.findViewById(R.id.lbl_dialog_new_item);
        TextView lbl_newItem2 = (TextView) myDialog.findViewById(R.id.lbl_dialog_new_item2);
        lbl_newItem.setText("Question");
        lbl_newItem2.setText("Answer");
        EditText newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);
        EditText newItem2 = (EditText) myDialog.findViewById(R.id.et_dialog_newItem2);

        if (isEditMode && item!=null) {
            newItem.setText(item.getTopicQuestion());
            newItem2.setText(item.getTopicAnswer());
        }

            btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (isEditMode) {
                    item.setTopicQuestion(newItem.getText().toString());
                    item.setTopicAnswer(newItem2.getText().toString());
                    updateItem(item);
                    myDialog.dismiss();
                } else {
                    Topic topic = new Topic();
                    topic.setTopicTypeID(topicType.getTopicTypeID());
                    topic.setTopicQuestion(newItem.getText().toString());
                    topic.setTopicAnswer(newItem2.getText().toString());
                    createItem(topic);
                    myDialog.dismiss();
                }
            }
        });

        //myDialog.setCancelable(false);
        myDialog.show();
    }




    @Override
    public void recyclerViewOnClickHandler(View v, Topic selectedItem) {
        callShowCrudMenu(v,selectedItem);
    }

    @Override
    public void callShowCrudMenu(View v, Topic selectedItem) {
        callShowCrudMenu(R.menu.popup_crud_menu_update_delete_custom,v,selectedItem);
    }

    @Override
    public void callShowCrudMenu(int popupMenuID,View v, Topic selectedItem) {
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
    public void onMenuItemClickHandlerMappingConfig(Map<Integer, onMenuItemClickHandlerExecutor> mapping, MenuItem item, Topic selectedItem){

        mapping.put( R.id.menu_custom,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                handlerSetCFExamClick(selectedItem);
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



    private void handlerSetCFExamClick(Topic selectedItem) {

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
                cfExamTopicQuestionnaire = cfExamTopicQuestionnaireService.findByTopicID(selectedItem.getTopicID());
                if(cfExamTopicQuestionnaire!=null) {
                    cfExamProfilePointCross = cfExamProfilePointService.findCrossByID(cfExamTopicQuestionnaire.getCurrentCFExamProfilePointID());
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
                    String formattedEntryPointDate = dateFormat.format(cfExamTopicQuestionnaire.getEntryPointDateTime());
                    String sourceString="This topic is set to CF Exam. " +
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

                            cfProfileSpinAdapter = new CFProfileSpinAdapter(TopicEditableActivity.this,
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

                            Integer delete = cfExamTopicQuestionnaireService.delete(cfExamTopicQuestionnaire);
                            if (delete>0) {
                                return true;
                            } else {
                                return false;
                            }

                        } else {
                            int selectedItemPosition = spn_cfExamProfile.getSelectedItemPosition();
                            CFExamProfile selectedCFExamProfile = cfProfileSpinAdapter.getItem(selectedItemPosition);


                            boolean isCreated = cfExamTopicQuestionnaireService.create(selectedItem.getTopicID(), selectedCFExamProfile);
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
        setGetItemsExecutor(new GetItemsExecutorBlock<Topic>() {
            @Override
            public List<Topic> execute() {
                if (contains.length()<2) {
                    return new ArrayList<Topic>();
                }
                List<Topic> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(topicType.getTopicTypeID(), contains);
                return allOrderAlphabetic;
            }
        });
    }


}
