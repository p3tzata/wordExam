package com.example.WordCFExam.utitliy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.ChangeSelectedProfile;
import com.example.WordCFExam.activity.configureActivity.ConfigureMenuActivity;
import com.example.WordCFExam.background.ManualStartCFExamService;

public class MenuUtility {

    static public void changeIsEditMode(Context context, MenuItem editModeMenuItem){
        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);


        //boolean isEditMode = sharedpreferences.getBoolean("isEditMode", false);
        boolean isEditMode = Session.getBooleanAttribute(context,SessionNameAttribute.IsEditMode,false);


        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (isEditMode) {
            //editor.putBoolean("isEditMode",false);
            //editor.apply();
            Session.setBooleanAttribute(context,SessionNameAttribute.IsEditMode,false);

            editModeMenuItem.setTitle("Edit Mode");
            Toast.makeText(context.getApplicationContext(),"Read mode",Toast.LENGTH_LONG).show();
        } else {
            //editor.putBoolean("isEditMode",true);
            //editor.apply();
            Session.setBooleanAttribute(context,SessionNameAttribute.IsEditMode,true);
            editModeMenuItem.setTitle("Read Mode");
            Toast.makeText(context.getApplicationContext(),"Edit mode",Toast.LENGTH_LONG).show();
        }
    }

    static public void checkIsEditMode(Context context, Menu menu){
        //SharedPreferences sharedpreferences;
        //sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        MenuItem editModeMenuItem = menu.findItem(R.id.item_editMode);

        //boolean isEditMode = sharedpreferences.getBoolean("isEditMode", false);
        boolean isEditMode =Session.getBooleanAttribute(context, SessionNameAttribute.IsEditMode,false);

        if (isEditMode) {
            editModeMenuItem.setTitle("Read Mode");
        } else {
            editModeMenuItem.setTitle("Edit Mode");
        }


    }

    static public boolean onOptionsItemSelected(AppCompatActivity activity, MenuItem menuItem){
        int id = menuItem.getItemId();

        switch (id){
            case R.id.item_changeProfile:
                Intent ProfileSelectIntent = new Intent(activity, ChangeSelectedProfile.class);
                activity.startActivity(ProfileSelectIntent);
                return true;
            case R.id.item_Configure:

                if (MenuUtility.isEditMode(activity)) {
                    Intent activity2Intent = new Intent(activity, ConfigureMenuActivity.class);
                    activity.startActivity(activity2Intent);
                } else {
                    Toast.makeText(activity, "Please select EDIT mode", Toast.LENGTH_LONG).show();
                }

                return true;
            case R.id.item_editMode:

                MenuUtility.changeIsEditMode(activity.getApplicationContext(),menuItem);
                return true;

            case R.id.item_startSchedule:
                ComponentName componentName = activity.startService(new Intent(activity, ManualStartCFExamService.class));
                if (componentName!=null) {
                    Toast.makeText(activity.getApplicationContext(), "Scheduled Started", Toast.LENGTH_LONG).show();
                }

                return true;





            default:
                return activity.onOptionsItemSelected(menuItem);
        }
    }

    public static boolean isEditMode(Context context) {
        //SharedPreferences sharedpreferences;
        //sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        //boolean isEditMode = sharedpreferences.getBoolean("isEditMode", false);
        boolean isEditMode =Session.getBooleanAttribute(context, SessionNameAttribute.IsEditMode,false);
        return isEditMode;

    }




}
