package com.example.WordCFExam.utitliy;

import android.content.Context;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TextToSpeechUtil {

    private Locale locale;
    private Context context;
    private TextToSpeech textToSpeech;

    public TextToSpeechUtil(Locale locale, Context context) {
        this.locale = locale;
        this.context = context;
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    if (locale!=null) {
                        textToSpeech.setLanguage(locale);
                    }
                }
            }
        });
    }


    public void speak(String toSpeakText, String toastMsg, Integer queueMode, HashMap<String,String> params  ) {
        if (toastMsg!=null) {
            Toast.makeText(context, toastMsg,Toast.LENGTH_SHORT).show();
        }
        textToSpeech.speak(toSpeakText,queueMode,params);

    }

    public void speak(String toSpeakText, String toastMsg) {

       this.speak(toSpeakText,toastMsg,TextToSpeech.QUEUE_FLUSH, null);

    }








}
