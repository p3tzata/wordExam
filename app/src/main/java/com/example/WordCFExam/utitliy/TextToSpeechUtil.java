package com.example.WordCFExam.utitliy;

import android.content.Context;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import com.example.WordCFExam.activity.configureActivity.ConfigPreferenceActivity;
import com.example.WordCFExam.entity.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TextToSpeechUtil {

    private Locale locale;
    private Context context;
    private TextToSpeech textToSpeech;

    public TextToSpeechUtil(Language language, Context context) {
        this.locale = Locale.forLanguageTag(language.getLocaleLanguageTag());
        this.context = context;
        textToSpeech = new TextToSpeech(context, status -> {
            if(status != TextToSpeech.ERROR) {
                if (locale!=null) {

                    if (textToSpeech.isLanguageAvailable(locale)==1) {
                        textToSpeech.setLanguage(locale);
                        if (language.getTts_pitch()!=null) {
                            textToSpeech.setPitch(language.getTts_pitch());
                        }
                        if (language.getTts_speechRate()!=null) {
                            textToSpeech.setSpeechRate(language.getTts_speechRate());
                        }
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

    public String[] splitTextToSentences(String text) {
        String[] splitSentences = text.split("[.?!]");
        ArrayList<String> sentenceList = new ArrayList<>();
        for (String sentence : splitSentences) {
            sentenceList.add(sentence.trim());
        }
        String[] sentenceArray = new String[sentenceList.size()];
        return sentenceList.toArray(sentenceArray);
    }

    public void speakSentence(String[] sentences, int index) {
        String sentence = sentences[index];
        textToSpeech.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public boolean isSpeaking(){
        return textToSpeech.isSpeaking();
    }



}
