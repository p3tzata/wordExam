package com.example.myapplication.utitliy;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MenuUtility {

    static public void changeIsEditMode(Context context, MenuItem editModeMenuItem){
        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);


        boolean isEditMode = sharedpreferences.getBoolean("isEditMode", false);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (isEditMode) {
            editor.putBoolean("isEditMode",false);
            editor.apply();
            editModeMenuItem.setTitle("Edit Mode");
            Toast.makeText(context.getApplicationContext(),"Read mode",Toast.LENGTH_LONG).show();
        } else {
            editor.putBoolean("isEditMode",true);
            editor.apply();
            editModeMenuItem.setTitle("Read Mode");
            Toast.makeText(context.getApplicationContext(),"Edit mode",Toast.LENGTH_LONG).show();
        }
    }

    static public void checkIsEditMode(Context context, Menu menu){
        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        MenuItem editModeMenuItem = menu.findItem(R.id.item_editMode);

        boolean isEditMode = sharedpreferences.getBoolean("isEditMode", false);

        if (isEditMode) {
            editModeMenuItem.setTitle("Read Mode");
        } else {
            editModeMenuItem.setTitle("Edit Mode");
        }


    }

    static public boolean onOptionsItemSelected(AppCompatActivity activity, MenuItem menuItem){
        int id = menuItem.getItemId();

        switch (id){
            case R.id.item1:
                Toast.makeText(activity.getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Toast.makeText(activity.getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item_editMode:

                MenuUtility.changeIsEditMode(activity.getApplicationContext(),menuItem);
                return true;
            default:
                return activity.onOptionsItemSelected(menuItem);
        }
    }

    public static boolean isEditMode(Context context) {
        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        boolean isEditMode = sharedpreferences.getBoolean("isEditMode", false);

        return isEditMode;

    }




}
