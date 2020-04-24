package com.example.WordCFExam.activity.configureActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivity;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.topic.TopicTypeEditableAdapter;
import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.entity.exam.TopicType;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.TopicTypeService;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;

public class ConfigTopicTypeActivity extends
        BaseEditableAppCompatActivity<TopicType, TopicTypeService, ConfigTopicTypeActivity, TopicTypeEditableAdapter> {

    private Long profileID;
    private String profileName;
    private TopicTypeService topicTypeService;
    private Dialog myDialog;


    @Override
    public void onCreateCustom() {
        setContentView(R.layout.activity_base_crudable);
        profileID= Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID,-1L);
        profileName=Session.getStringAttribute(getApplicationContext(),SessionNameAttribute.ProfileName,"");
        this.topicTypeService=FactoryUtil.createTopicTypeService(getApplication());
        super.setItemService(topicTypeService);
        TopicTypeEditableAdapter adapter = new TopicTypeEditableAdapter(ConfigTopicTypeActivity.this);
        super.setAdapter(adapter);
        super.setContext(ConfigTopicTypeActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TopicType " + "("+
                Session.getStringAttribute(this, SessionNameAttribute.ProfileName, "")
                +")");
        setGetItemsExecutor(new GetItemsExecutorBlock<TopicType>() {
            @Override
            public List<TopicType> execute() {
                List<TopicType> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(profileID, "");
                return allOrderAlphabetic;
            }
        });
        getItems();

    }

    @Override
    public void onResume(){
        super.onResume();
        getSupportActionBar().setTitle("TopicType " + "("+
                Session.getStringAttribute(this, SessionNameAttribute.ProfileName, "")
                +")");

    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<TopicType>() {
            @Override
            public List<TopicType> execute() {
                List<TopicType> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(profileID, contains);
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
    public void handlerDeleteClick(TopicType selectedItem) {

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
    public void handlerCreateUpdateClick(boolean isEditMode, TopicType selectedItem) {
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


        if (isEditMode && selectedItem!=null) {
            et_newItem.setText(selectedItem.getLabelText());

        }

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                if (isEditMode) {
                    selectedItem.setTopicTypeName(et_newItem.getText().toString());
                    updateItem(selectedItem);
                    myDialog.dismiss();
                } else {
                    TopicType newItem = new TopicType();

                    newItem.setTopicTypeName(et_newItem.getText().toString());
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
    public void recyclerViewOnClickHandler(View v, TopicType selectedItem) {
        callShowCrudMenu(v,selectedItem);
    }
}
