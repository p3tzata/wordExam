package com.example.WordCFExam.activity.configureActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivityNonFaced;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.activity.base.onMenuItemClickHandlerExecutor;
import com.example.WordCFExam.adapter.configure.CFExamProfileEditableAdapter;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.CFExamProfileService;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;
import java.util.Map;

public class ConfigCFExamProfileActivity extends
        BaseEditableAppCompatActivityNonFaced<CFExamProfile, CFExamProfileService, ConfigCFExamProfileActivity, CFExamProfileEditableAdapter> {

    private Long profileID;
    private String profileName;
    private CFExamProfileService cfExamProfileService;
    private Dialog myDialog;


    @Override
    public void onCreateCustom() {
        setContentView(R.layout.activity_base_crudable);
        profileID= Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID,-1L);
        profileName=Session.getStringAttribute(getApplicationContext(),SessionNameAttribute.ProfileName,"");
        this.cfExamProfileService=FactoryUtil.createCFExamProfileService(getApplication());
        super.setItemService(cfExamProfileService);
        CFExamProfileEditableAdapter adapter = new CFExamProfileEditableAdapter(ConfigCFExamProfileActivity.this);
        super.setAdapter(adapter);
        super.setContext(ConfigCFExamProfileActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CFExamProfile " + "("+
                Session.getStringAttribute(this, SessionNameAttribute.ProfileName, "")
                +")");
        setGetItemsExecutor(new GetItemsExecutorBlock<CFExamProfile>() {
            @Override
            public List<CFExamProfile> execute() {
                List<CFExamProfile> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(profileID, "");
                return allOrderAlphabetic;
            }
        });
        getItems();


    }


    @Override
    public void onResume(){
        super.onResume();
        getSupportActionBar().setTitle("CFExamProfile " + "("+
                Session.getStringAttribute(this, SessionNameAttribute.ProfileName, "")
                +")");

    }

    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<CFExamProfile>() {
            @Override
            public List<CFExamProfile> execute() {
                List<CFExamProfile> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(profileID, contains);
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
    public void handlerDeleteClick(CFExamProfile selectedItem) {

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
    public void handlerCreateUpdateClick(boolean isEditMode, CFExamProfile selectedItem) {
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
                    selectedItem.setName(et_newItem.getText().toString());
                    updateItem(selectedItem);
                    myDialog.dismiss();
                } else {
                    CFExamProfile newItem = new CFExamProfile();

                    newItem.setName(et_newItem.getText().toString());
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
    public void recyclerViewOnClickHandler(View v, CFExamProfile selectedItem) {
        callShowCrudMenu(v,selectedItem);
    }
    @Override
    public void callShowCrudMenu(View v, CFExamProfile selectedItem) {
        callShowCrudMenu(R.menu.popup_crud_menu_update_delete_custom,v,selectedItem);
    }

    @Override
    public void callShowCrudMenu(int popupMenuID,View v, CFExamProfile selectedItem) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);

        popupMenu.inflate(popupMenuID);
        Menu menu = popupMenu.getMenu();
        menu.getItem(0).setTitle("Edit Points");

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
    public void onMenuItemClickHandlerMappingConfig(Map<Integer,onMenuItemClickHandlerExecutor> mapping, MenuItem item, CFExamProfile selectedItem){

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

    private void handlerUpdateChildrenClick(boolean b, CFExamProfile selectedItem) {
        Intent intent = new Intent(this, ConfigCFExamProfilePointActivity.class);
        intent.putExtra("CFExamProfile", selectedItem);
        startActivity(intent);

    }


}
