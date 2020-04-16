package com.example.WordCFExam.activity.configureActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.MainActivity;
import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivity;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.exam.CFExamScheduleEditableAdapter;
import com.example.WordCFExam.adapter.topic.TopicTypeEditableAdapter;
import com.example.WordCFExam.entity.exam.CFExamSchedule;
import com.example.WordCFExam.entity.exam.Topic;
import com.example.WordCFExam.entity.exam.TopicType;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.CFExamScheduleService;
import com.example.WordCFExam.service.exam.TopicTypeService;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;

public class ConfigCFExamScheduleActivity extends
        BaseEditableAppCompatActivity<CFExamSchedule, CFExamScheduleService, ConfigCFExamScheduleActivity, CFExamScheduleEditableAdapter> {

    private Long profileID;
    private String profileName;
    private CFExamScheduleService topicTypeService;
    private Dialog myDialog;


    @Override
    public void onCreateCustom() {
        setContentView(R.layout.activity_base_crudable);
        profileID= Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID,-1L);
        profileName=Session.getStringAttribute(getApplicationContext(),SessionNameAttribute.ProfileName,"");
        this.topicTypeService=FactoryUtil.createCFExamScheduleService(getApplication());
        super.setItemService(topicTypeService);
        CFExamScheduleEditableAdapter adapter = new CFExamScheduleEditableAdapter(ConfigCFExamScheduleActivity.this);
        super.setAdapter(adapter);
        super.setContext(ConfigCFExamScheduleActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CF Exam Schedule " + "("+
                Session.getStringAttribute(ConfigCFExamScheduleActivity.this, SessionNameAttribute.ProfileName, "")
                +")");
        setGetItemsExecutor(new GetItemsExecutorBlock<CFExamSchedule>() {
            @Override
            public List<CFExamSchedule> execute() {
                List<CFExamSchedule> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(profileID, "");
                return allOrderAlphabetic;
            }
        });
        getItems();

    }

    @Override
    public void onResume(){
        super.onResume();
        getSupportActionBar().setTitle("CF Exam Schedule " + "("+
                Session.getStringAttribute(ConfigCFExamScheduleActivity.this, SessionNameAttribute.ProfileName, "")
                +")");

    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<CFExamSchedule>() {
            @Override
            public List<CFExamSchedule> execute() {
                List<CFExamSchedule> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(profileID, contains);
                return allOrderAlphabetic;
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



    @Override
    public void handlerDeleteClick(CFExamSchedule selectedItem) {

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


    public void  handlerCreateUpdateClick(boolean isEditMode, CFExamSchedule item){
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_base_crud_two_item);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/5);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);

        TextView lbl_newItem = (TextView) myDialog.findViewById(R.id.lbl_dialog_new_item);
        TextView lbl_newItem2 = (TextView) myDialog.findViewById(R.id.lbl_dialog_new_item2);
        lbl_newItem.setText("From Hour (0-24)");
        lbl_newItem2.setText("To Hour (0-24)");
        EditText newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);
        EditText newItem2 = (EditText) myDialog.findViewById(R.id.et_dialog_newItem2);
        newItem.setInputType(InputType.TYPE_CLASS_NUMBER);
        newItem2.setInputType(InputType.TYPE_CLASS_NUMBER);

        if (isEditMode && item!=null) {
            newItem.setText(String.valueOf(item.getFromHour()));
            newItem2.setText(String.valueOf(item.getToHour()));
        }

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (isEditMode) {
                    item.setFromHour(Integer.valueOf(newItem.getText().toString()));
                    item.setToHour(Integer.valueOf(newItem2.getText().toString()));
                    updateItem(item);
                    myDialog.dismiss();
                } else {
                    CFExamSchedule topic = new CFExamSchedule();
                    topic.setProfileID(profileID);
                    topic.setFromHour(Integer.valueOf(newItem.getText().toString()));
                    topic.setToHour(Integer.valueOf(newItem2.getText().toString()));
                    createItem(topic);
                    myDialog.dismiss();
                }
            }
        });

        //myDialog.setCancelable(false);
        myDialog.show();
    }

    @Override
    public void recyclerViewOnClickHandler(View v, CFExamSchedule selectedItem) {
        callShowCrudMenu(v,selectedItem);
    }
}
