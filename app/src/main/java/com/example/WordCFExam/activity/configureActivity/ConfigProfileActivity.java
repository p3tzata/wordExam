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
import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivityNonFaced;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.configure.ProfileEditableAdapter;
import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.ProfileService;

import java.util.List;

public class ConfigProfileActivity extends
        BaseEditableAppCompatActivityNonFaced<Profile,ProfileService, ConfigProfileActivity,ProfileEditableAdapter> {

    @Override
    public void onCreateCustom() {
        setContentView(R.layout.activity_base_crudable);
        super.setItemService(FactoryUtil.createProfileService(getApplication()));
        ProfileEditableAdapter adapter = new ProfileEditableAdapter(ConfigProfileActivity.this);
        super.setAdapter(adapter);
        super.setContext(ConfigProfileActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Configure Profiles");
        setGetItemsExecutor(new GetItemsExecutorBlock<Profile>() {
            @Override
            public List<Profile> execute() {
                List<Profile> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(0L,"");
                return allOrderAlphabetic;
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


    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {

        setGetItemsExecutor(new GetItemsExecutorBlock<Profile>() {
            @Override
            public List<Profile> execute() {
                List<Profile> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(0L, contains);
                return allOrderAlphabetic;
            }
        });
    }



    public void handlerDeleteClick(Profile selectedItem) {

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
    public void handlerCreateUpdateClick(boolean isEditMode, Profile selectedItem) {
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_base_crud_item);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

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
            et_newItem.setText(selectedItem.getProfileName());
        }

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (isEditMode) {
                    selectedItem.setProfileName(et_newItem.getText().toString());
                    updateItem(selectedItem);
                    myDialog.dismiss();
                } else {
                    Profile newItem = new Profile();
                    newItem.setProfileName(et_newItem.getText().toString());
                    createItem(newItem);
                    myDialog.dismiss();
                }
            }
        });

        //myDialog.setCancelable(false);
        myDialog.show();
    }


    @Override
    public void recyclerViewOnClickHandler(View v, Profile selectedItem) {
        callShowCrudMenu(v,selectedItem);
    }


}
