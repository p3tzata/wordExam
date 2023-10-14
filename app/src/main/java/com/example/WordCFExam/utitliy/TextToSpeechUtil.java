package com.example.WordCFExam.utitliy;

import android.content.Context;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import com.example.WordCFExam.activity.configureActivity.ConfigPreferenceActivity;

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

                        if (textToSpeech.isLanguageAvailable(locale)==1) {
                            textToSpeech.setLanguage(locale);
                        }
                    }
                }
            }
        });
        String ttsRateString = Session.getStringAttribute(
                context, SessionNameAttribute.TextToSpeechRate,"1");


        textToSpeech.setSpeechRate(Float.valueOf(ttsRateString));

    }


    public void speak(String toSpeakText, String toastMsg, Integer queueMode, HashMap<String,String> params  ) {
        if (toastMsg!=null) {
            Toast.makeText(context, toastMsg,Toast.LENGTH_SHORT).show();
        }
        textToSpeech.speak(toSpeakText,queueMode,params);

    }

    public void speak(String toSpeakText, String toastMsg) {

       this.speak(toSpeakText,toastMsg,1, null);

    }


    public void playSilentUtterance(long seconds) {

        textToSpeech.playSilentUtterance(seconds*1000,1, null);
    }
    public void shutdown() {
        textToSpeech.stop();
        textToSpeech.shutdown();

    }

    public void stop() {
        textToSpeech.stop();


    }








}
