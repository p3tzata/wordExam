package com.example.WordCFExam.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.PartOfSpeech;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.WordForm;
import com.example.WordCFExam.entity.dto.ForeignWordWithDefPartOfSpeech;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.entity.dto.WordCFExamCross;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.HelpSentenceService;
import com.example.WordCFExam.service.LanguageService;
import com.example.WordCFExam.service.PartOfSpeechService;
import com.example.WordCFExam.service.TranslationWordRelationService;
import com.example.WordCFExam.service.WordFormService;
import com.example.WordCFExam.service.WordPartOfSpeechService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.TextToSpeechUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ShowForeignWordActivity extends AppCompatActivity {

    private TranslationAndLanguages translationAndLanguages;
    private Word word;
    private Long translationFromLanguageID;
    private Long translationToLanguageID;
    private TextToSpeechUtil textToSpeechUtil;

    private TranslationWordRelationService translationWordRelationService;
    private PartOfSpeechService partOfSpeechService;
    private WordFormService wordFormService;
    private WordPartOfSpeechService wordPartOfSpeechService;
    private LanguageService languageService;
    private HelpSentenceService helpSentenceService;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_show_foreign_word);
        this.translationWordRelationService = FactoryUtil.createTranslationWordRelationService(getApplication());
        this.wordFormService = FactoryUtil.createWordFormService(getApplication());
        this.partOfSpeechService=FactoryUtil.createPartOfSpeechService(getApplication());
        this.wordPartOfSpeechService=FactoryUtil.createWordPartOfSpeechService(getApplication());
        this.languageService=FactoryUtil.createLanguageService(getApplication());
        this.helpSentenceService=FactoryUtil.createHelpSentenceService(getApplication());
        this.translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        this.translationFromLanguageID = (Long) getIntent().getSerializableExtra("translationFromLanguageID");
        this.translationToLanguageID = (Long) getIntent().getSerializableExtra("translationToLanguageID");
        this.word= (Word) getIntent().getSerializableExtra("word");
        getSupportActionBar().setTitle(word.getWordString());
        Locale locale=null;
        try {
            locale = Locale.forLanguageTag(translationAndLanguages.getForeignLanguage().getLocaleLanguageTag());
            textToSpeechUtil =new TextToSpeechUtil(locale,getApplicationContext());
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Warning: Can not identify Language Locate Tag",Toast.LENGTH_LONG).show();
        }




        findViewById(R.id.btn_pronunciation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textToSpeechUtil!=null) {
                    String toSpeak = word.getWordString();
                    textToSpeechUtil.speak(toSpeak,toSpeak);
                }


            }
        });





        getNativeWords(word);
        getPartOfSpeech(word);
        getHelpSentence(word);
        getWordForm(word);
        getLanguage(word);




    }


    private void getLanguage(Word word) {


        DbExecutorImp<Language> dbExecutor = FactoryUtil.<Language>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<Language>() {
            @Override
            public Language doInBackground() {

                return languageService.findByID(word.getLanguageID());


            }

            @Override
            public void onPostExecute(Language item) {
                if (item==null) {
                    return;
                }
                try {
                    final String url = String.format(item.getDefinitionUrl(), word.getWordString());
                    findViewById(R.id.btn_openLink).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String urlString = url;
                            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setPackage("com.android.chrome");
                            try {
                                getApplicationContext().startActivity(intent);
                            } catch (ActivityNotFoundException ex) {
                                // Chrome browser presumably not installed and open Kindle Browser
                                intent.setPackage("com.amazon.cloud9");
                                getApplicationContext().startActivity(intent);
                            }
                        }
                    });


                } catch(Exception ex) {
                    ;
                }
                                    /*
                    TextView textView = (TextView) findViewById(R.id.tv_url);
                    //textView.setClickable(true);
                    //textView.setMovementMethod(LinkMovementMethod.getInstance());
                    String text = "<a href='"+ url+ "'> "+ word.getWordString() +" </a>";
                    textView.setText(Html.fromHtml(text));
                    textView.setAutoLinkMask(Linkify.WEB_URLS);
                    */


            }
        });



    }



    private void getWordForm(Word word) {


        DbExecutorImp<WordForm> dbExecutor = FactoryUtil.<WordForm>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<WordForm>() {
            @Override
            public WordForm doInBackground() {
                if (word.getWordFormID()!=null) {
                    return wordFormService.findByID(word.getWordFormID());
                }
                return null;

            }

            @Override
            public void onPostExecute(WordForm item) {
                if (item==null) {
                    return;
                }

                List<String> StringList=new ArrayList<>();

                StringList.add(item.getWordFormName());

                View header1 = getLayoutInflater().inflate(R.layout.arraylist_header, null);
                TextView headerTextView1  = (TextView) header1.findViewById(R.id.arrayListHeader);
                ListView viewList = (ListView) findViewById(R.id.show_foreign_wordForm);
                final ArrayAdapter<String> translationAdapter = new ArrayAdapter<String>(ShowForeignWordActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, StringList);
                headerTextView1.setText("Word Form");
                viewList.addHeaderView(header1);
                viewList.setAdapter(translationAdapter);
                viewList.setFocusable(false);
                Utility utility = new Utility();
                utility.setListViewHeightBasedOnChildren(viewList);



            }
        });



    }

    private void getHelpSentence(Word word) {

        DbExecutorImp<List<HelpSentence>> dbExecutor = FactoryUtil.<List<HelpSentence>>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<List<HelpSentence>>() {
            @Override
            public List<HelpSentence> doInBackground() {

                List<HelpSentence> allOrderAlphabetic = helpSentenceService.findAllOrderAlphabetic(word.getWordID(), "");
                return allOrderAlphabetic;

            }

            @Override
            public void onPostExecute(List<HelpSentence> item) {
                if (item==null) {
                    return;
                }
                List<String> helpSentenceStringList=new ArrayList<>();
                Iterator<HelpSentence> iterator = item.iterator();
                while(iterator.hasNext()) {
                    helpSentenceStringList.add(iterator.next().getSentenceString());
                }
                View header1 = getLayoutInflater().inflate(R.layout.arraylist_header, null);
                TextView headerTextView1  = (TextView) header1.findViewById(R.id.arrayListHeader);
                ListView viewList = (ListView) findViewById(R.id.show_foreign_helpSentence);
                final ArrayAdapter<String> translationAdapter = new ArrayAdapter<String>(ShowForeignWordActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, helpSentenceStringList);
                headerTextView1.setText("Help Sentences");
                viewList.addHeaderView(header1);
                viewList.setAdapter(translationAdapter);
                viewList.setFocusable(false);
                viewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        if (textToSpeechUtil!=null) {

                            textToSpeechUtil.speak(selectedItem,"speaking");
                        }

                    }

                });


                Utility utility = new Utility();
                utility.setListViewHeightBasedOnChildren(viewList);



            }
        });

    }

    private void getPartOfSpeech(Word word) {
        DbExecutorImp<ForeignWordWithDefPartOfSpeech> dbExecutor = FactoryUtil.<ForeignWordWithDefPartOfSpeech>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<ForeignWordWithDefPartOfSpeech>() {
            @Override
            public ForeignWordWithDefPartOfSpeech doInBackground() {

                ForeignWordWithDefPartOfSpeech foreignWordWithDefPartOfSpeech = wordPartOfSpeechService.findForeignWordWithDefPartOfSpeech(word.getWordID());
                return foreignWordWithDefPartOfSpeech;

            }

            @Override
            public void onPostExecute(ForeignWordWithDefPartOfSpeech item) {
                if (item==null) {
                    return;
                }
                List<String> partOfSpeechStringList=new ArrayList<>();
                Iterator<PartOfSpeech> iterator = item.getPartOfSpeeches().iterator();
                while(iterator.hasNext()) {
                    partOfSpeechStringList.add(iterator.next().getName());
                }
                View header1 = getLayoutInflater().inflate(R.layout.arraylist_header, null);
                TextView headerTextView1  = (TextView) header1.findViewById(R.id.arrayListHeader);
                ListView viewList = (ListView) findViewById(R.id.show_foreign_partOfSpeech);
                final ArrayAdapter<String> translationAdapter = new ArrayAdapter<String>(ShowForeignWordActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, partOfSpeechStringList);
                headerTextView1.setText("Part of Speech");
                viewList.addHeaderView(header1);
                viewList.setAdapter(translationAdapter);
                viewList.setFocusable(false);
                Utility utility = new Utility();
                utility.setListViewHeightBasedOnChildren(viewList);


            }
        });

    }

    private void getNativeWords(Word foreignWord) {
        DbExecutorImp<List<WordCFExamCross>> dbExecutor = FactoryUtil.<List<WordCFExamCross>>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<List<WordCFExamCross>>() {
            @Override
            public List<WordCFExamCross> doInBackground() {


                return translationWordRelationService.translateFromForeignCFExamCross(foreignWord.getWordID(),translationToLanguageID);

            }

            @Override
            public void onPostExecute(List<WordCFExamCross> item) {
                if (item==null) {
                    return;
                }
                    List<String> nativeWordStringList=new ArrayList<>();
                Iterator<WordCFExamCross> iterator = item.iterator();
                while(iterator.hasNext()) {
                    nativeWordStringList.add(iterator.next().getLabelText());
                }
                View header1 = getLayoutInflater().inflate(R.layout.arraylist_header, null);
                TextView headerTextView1  = (TextView) header1.findViewById(R.id.arrayListHeader);
                ListView viewList = (ListView) findViewById(R.id.show_foreign_translation);
                final ArrayAdapter<String> translationAdapter = new ArrayAdapter<String>(ShowForeignWordActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, nativeWordStringList);
                headerTextView1.setText("Translations");
                viewList.addHeaderView(header1);
                viewList.setAdapter(translationAdapter);
                viewList.setFocusable(false);
                Utility utility = new Utility();
                utility.setListViewHeightBasedOnChildren(viewList);


            }
        });
    }


    public class Utility {

        public  void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }


}
