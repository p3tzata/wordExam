package com.example.myapplication.activity.configureActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.BaseEditableAppCompatActivity;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.activity.wordActivity.UpdateWordHelpSentenceActivity;
import com.example.myapplication.adapter.HelpSentenceEditableAdapter;
import com.example.myapplication.adapter.configure.LanguageEditableAdapter;
import com.example.myapplication.adapter.configure.ProfileEditableAdapter;
import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Word;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.ProfileService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ConfigProfileActivity extends
        BaseEditableAppCompatActivity<Profile,ProfileService, ConfigProfileActivity,ProfileEditableAdapter> {

    //private Profile selectedItem;
    //private ProfileService itemService;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_profile);
        super.setItemService(FactoryUtil.createProfileService(getApplication()));
        ProfileEditableAdapter adapter = new ProfileEditableAdapter(ConfigProfileActivity.this);
        super.setAdapter(adapter);
        super.setContext(ConfigProfileActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Configure Profiles");
        getItems();
        FloatingActionButton fab_newItem = findViewById(R.id.fab_newItem);
        fab_newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerCreateUpdateClick(false,null);
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
        myDialog.setContentView(R.layout.dialog_new_item);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);


        EditText et_newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);

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
