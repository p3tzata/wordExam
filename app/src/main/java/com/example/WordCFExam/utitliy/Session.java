package com.example.WordCFExam.utitliy;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    public static boolean getBooleanAttribute(Context context, SessionNameAttribute sessionNameAttribute, boolean defaultValue) {

        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        boolean value = sharedpreferences.getBoolean(sessionNameAttribute.getValue(), defaultValue);
        return value;
    }


    public static void setBooleanAttribute(Context context, SessionNameAttribute sessionNameAttribute, boolean value ) {
        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(sessionNameAttribute.getValue(),value);
        editor.apply();
    }

    public static long getLongAttribute(Context context, SessionNameAttribute sessionNameAttribute, long defaultValue) {
        
        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        long value = sharedpreferences.getLong(sessionNameAttribute.getValue(), defaultValue);
        return value;
    }


    public static void setLongAttribute(Context context, SessionNameAttribute sessionNameAttribute, long value ) {
        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong(sessionNameAttribute.getValue(),value);
        editor.apply();
    }


    public static String getStringAttribute(Context context, SessionNameAttribute sessionNameAttribute, String defaultValue) {

        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        String value = sharedpreferences.getString(sessionNameAttribute.getValue(), defaultValue);
        return value;
    }


    public static void setStringAttribute(Context context, SessionNameAttribute sessionNameAttribute, String value ) {
        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(sessionNameAttribute.getValue(),value);
        editor.apply();
    }





}
