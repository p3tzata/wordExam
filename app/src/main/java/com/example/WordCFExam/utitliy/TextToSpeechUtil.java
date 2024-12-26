package com.example.WordCFExam.utitliy;

import android.content.Context;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import com.example.WordCFExam.entity.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class TextToSpeechUtil {

    private Locale locale;
    private Context context;
    private TextToSpeech textToSpeech;

    public TextToSpeechUtil(Language language, Context context) {
        this.locale = Locale.forLanguageTag(language.getLocaleLanguageTag());
        this.context = context;
        textToSpeech = new TextToSpeech(context, status -> {
            if (status != TextToSpeech.ERROR) {
                if (locale != null) {

                    if (textToSpeech.isLanguageAvailable(locale) == 1) {
                        textToSpeech.setLanguage(locale);
                        if (language.getTts_pitch() != null) {
                            textToSpeech.setPitch(language.getTts_pitch());
                        }
                        if (language.getTts_speechRate() != null) {
                            textToSpeech.setSpeechRate(language.getTts_speechRate());
                        }
                    }
                }
            }
        });

    }


    public void speak(String toSpeakText, String toastMsg, Integer queueMode, HashMap<String, String> params) {
        if (toastMsg != null) {
            Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
        }
        textToSpeech.speak(toSpeakText, queueMode, params);

    }

    public void speak(String toSpeakText, String toastMsg) {

        this.speak(toSpeakText, toastMsg, 1, null);

    }


    public void playSilentUtterance(long seconds) {

        textToSpeech.playSilentUtterance(seconds * 1000, 1, null);
    }

    public void shutdown() {
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

    public void stop() {
        textToSpeech.stop();
    }

    public static String[] splitTextToSentences(String text) {
        String[] splitSentences = text.split("[.?!]");
        ArrayList<String> sentenceList = new ArrayList<>();
        for (String sentence : splitSentences) {
            sentenceList.add(sentence.trim());
        }
        String[] sentenceArray = new String[sentenceList.size()];
        return sentenceList.toArray(sentenceArray);
    }

    public void speakSentence(String[] sentences, int index, double miniPauseSec, double commaPauseSec) {

        textToSpeech.speak("", TextToSpeech.QUEUE_FLUSH, null, null);
        String sentence = sentences[index];
        String[] sentenceSplitByNewLines = sentence.split(System.lineSeparator());
        for (int i = 0; i < sentenceSplitByNewLines.length; i++) {
            String sentenceSplitByNewLine = sentenceSplitByNewLines[i];
            String[] chunks = sentenceSplitByNewLine.split(",");
            for (int c = 0; c < chunks.length; c++) {
                if (c > 0) {
                    if (chunks[c].length() > 10 && chunks[c-1].length() > 10) {
                        textToSpeech.playSilentUtterance((long) (miniPauseSec * 10) * 100L, TextToSpeech.QUEUE_ADD, null);
                    } else {
                        textToSpeech.playSilentUtterance((long) (commaPauseSec * 10) * 100L, TextToSpeech.QUEUE_ADD, null);
                    }
                }

                String[] tooManyWords = processToManyWords(chunks[c]);
                int count=0;
                while (count<tooManyWords.length) {
                    textToSpeech.speak(tooManyWords[count], TextToSpeech.QUEUE_ADD, null, null);
                    if (count < tooManyWords.length-1) {
                        textToSpeech.playSilentUtterance((long) (miniPauseSec * 10) * 100L, TextToSpeech.QUEUE_ADD, null);
                    }
                    count++;
                }


            }
            textToSpeech.playSilentUtterance((long) (miniPauseSec * 10) * 100L, TextToSpeech.QUEUE_ADD, null);
        }


        //textToSpeech.playSilentUtterance((long) (miniPauseSec * 10) * 100L, TextToSpeech.QUEUE_ADD, null);
    }

    private String[] processToManyWords(String sentence){
        int maxWords=5;
        ArrayList<String> result = new ArrayList<>();
        String[] chunks = sentence.split(" ");
        StringBuffer temp=new StringBuffer();
        if(chunks.length>maxWords) {
            for(int i=0;i<chunks.length;i++){
                    temp.append(chunks[i]+" ");
                    if (i%maxWords==0 && i/maxWords>0) {
                        result.add(temp.toString());
                        temp=new StringBuffer();

                        //to avoid example:  I am here {pause} now.
                        if (((chunks.length-1-i) / maxWords)<1) {
                            i++;
                            while (i<chunks.length){
                                temp.append(chunks[i]+" ");
                                i++;
                            }
                            break;
                        }
                    }
            }
            //result.add(temp.toString());
            if (temp.length()>0) {
                int lastIndex = result.size() - 1;
                String lastChunk = result.get(lastIndex);
                lastChunk = lastChunk + temp;
                result.set(lastIndex, lastChunk);
            }

        } else {
            result.add(sentence);
        }

        return result.toArray(new String[result.size()]);
    }

    public boolean isSpeaking() {
        return textToSpeech.isSpeaking();
    }


}
