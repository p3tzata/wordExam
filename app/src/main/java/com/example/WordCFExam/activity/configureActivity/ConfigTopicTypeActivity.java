package com.example.WordCFExam.activity.configureActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivityNonFaced;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.activity.base.onMenuItemClickHandlerExecutor;
import com.example.WordCFExam.adapter.topic.TopicTypeEditableAdapter;
import com.example.WordCFExam.entity.Topic;
import com.example.WordCFExam.entity.TopicType;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.TopicTypeService;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ConfigTopicTypeActivity extends
        BaseEditableAppCompatActivityNonFaced<TopicType, TopicTypeService, ConfigTopicTypeActivity, TopicTypeEditableAdapter> {

    private Long profileID;
    private String profileName;
    private TopicTypeService topicTypeService;
    private Dialog myDialog;
    private Stack<TopicType> navigatorStack;



    @Override
    public void onCreateCustom() {
        navigatorStack = new Stack<>();

        navigatorStack.push(new TopicType(){{setTopicTypeID(0L);setTopicTypeName("TopicType " + "("+
                Session.getStringAttribute(ConfigTopicTypeActivity.this, SessionNameAttribute.ProfileName, "")
                +")");}});



        setContentView(R.layout.activity_base_crudable);
        profileID= Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID,-1L);
        profileName=Session.getStringAttribute(getApplicationContext(),SessionNameAttribute.ProfileName,"");
        this.topicTypeService=FactoryUtil.createTopicTypeService(getApplication());
        super.setItemService(topicTypeService);
        TopicTypeEditableAdapter adapter = new TopicTypeEditableAdapter(ConfigTopicTypeActivity.this);
        super.setAdapter(adapter);
        super.setContext(ConfigTopicTypeActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(navigatorStack.peek().getTopicTypeName());
        setGetItemsExecutor(new GetItemsExecutorBlock<TopicType>() {
            @Override
            public List<TopicType> execute() {
                List<TopicType> allOrderAlphabetic = getItemService().findAllOrderAlphabeticByParent(profileID, navigatorStack.peek().getTopicTypeID(), "");
                if (!navigatorStack.peek().getTopicTypeID().equals(0L)) {
                    TopicType navigatorTopicType = new TopicType();
                    navigatorTopicType.setTopicTypeName(".. Back");
                    navigatorTopicType.setTopicTypeID(-1L);
                    allOrderAlphabetic.add(0,navigatorTopicType);
                }

                return allOrderAlphabetic;
            }
        });
        getItems();

    }

    @Override
    public void onResume(){
        super.onResume();
        getSupportActionBar().setTitle(navigatorStack.peek().getTopicTypeName());

    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<TopicType>() {
            @Override
            public List<TopicType> execute() {
                List<TopicType> allOrderAlphabetic = getItemService().findAllOrderAlphabeticByParent(profileID, navigatorStack.peek().getTopicTypeID(), "");

                if (!navigatorStack.peek().getTopicTypeID().equals(0L)) {
                    TopicType navigatorTopicType = new TopicType();
                    navigatorTopicType.setTopicTypeName(".. Back");
                    navigatorTopicType.setTopicTypeID(-1L);
                    allOrderAlphabetic.add(0,navigatorTopicType);
                }

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
        myDialog.setContentView(R.layout.dialog_base_crud_two_item);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/7);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);
        EditText et_newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);
        EditText et_newItem2 = (EditText) myDialog.findViewById(R.id.et_dialog_newItem2);

        TextView lbl_dialog_new_item = (TextView) myDialog.findViewById(R.id.lbl_dialog_new_item);
        TextView lbl_dialog_new_item2 = (TextView) myDialog.findViewById(R.id.lbl_dialog_new_item2);
        et_newItem2.setInputType(InputType.TYPE_CLASS_NUMBER );


        lbl_dialog_new_item.setText("Topic Type");
        lbl_dialog_new_item2.setText("Parent Topic Type");

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
            if (selectedItem.getParentTopicTypeID()==null) {
                et_newItem2.setText(null);
            } else {
                et_newItem2.setText(String.valueOf(selectedItem.getParentTopicTypeID()));
            }


        } else {

                lbl_dialog_new_item2.setVisibility(View.INVISIBLE);
                et_newItem2.setVisibility(View.INVISIBLE);

        }


        btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                if (isEditMode) {
                    selectedItem.setTopicTypeName(et_newItem.getText().toString());
                    String et_parentTopicType = et_newItem2.getText().toString();
                    if (et_parentTopicType.length()>0 ) {
                        selectedItem.setParentTopicTypeID(Long.valueOf(et_newItem2.getText().toString()));
                    } else {
                        selectedItem.setParentTopicTypeID(null);
                    }
                    updateItem(selectedItem);
                    myDialog.dismiss();
                } else {
                    TopicType newItem = new TopicType();
                    if (!navigatorStack.peek().getTopicTypeID().equals(0L)) {
                        newItem.setParentTopicTypeID(navigatorStack.peek().getTopicTypeID());
                    }
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
       // callShowCrudMenu(v,selectedItem);
        if (selectedItem.getTopicTypeID().equals(-1L)) {
            navigatorStack.pop();

            getSupportActionBar().setTitle(navigatorStack.peek().getTopicTypeName());
            getItems();
        } else {
            callShowCrudMenu(R.menu.popup_crud_menu_update_delete_custom, v, selectedItem);
        }
    }


    @Override
    public void callShowCrudMenu(int popupMenuID,View v, TopicType selectedItem) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);

        popupMenu.inflate(popupMenuID);
        Menu menu = popupMenu.getMenu();
        menu.getItem(0).setTitle("Child Topic Type");
        menu.getItem(1).setVisible(false);

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
    public void onMenuItemClickHandlerMappingConfig(Map<Integer, onMenuItemClickHandlerExecutor> mapping, MenuItem item, TopicType selectedItem){

        mapping.put( R.id.menu_custom,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                handlerUpdateChildrenClick(true,selectedItem);
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

    private void handlerUpdateChildrenClick(boolean b, TopicType selectedItem) {
        navigatorStack.push(selectedItem);

        //this.selectedParentTopicTypeID=selectedItem.getTopicTypeID();
        getSupportActionBar().setTitle(navigatorStack.peek().getTopicTypeName());
        getItems();

    }





}
