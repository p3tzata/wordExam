package com.example.WordCFExam.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WordCFExam.R;

import com.example.WordCFExam.adapter.UpdWordBasicPartOfSpeechListAdapter;
import com.example.WordCFExam.adapter.spinnerAdapter.CFProfileSpinAdapter;
import com.example.WordCFExam.adapter.spinnerAdapter.WordFormSpinAdapter;
import com.example.WordCFExam.adapter.spinnerAdapter.PartOfSpeechSpinAdapter;
import com.example.WordCFExam.entity.PartOfSpeech;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.WordForm;
import com.example.WordCFExam.entity.WordPartOfSpeech;
import com.example.WordCFExam.entity.dto.ForeignWithNativeWords;
import com.example.WordCFExam.entity.dto.ForeignWordWithDefPartOfSpeech;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;

import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePointCross;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaireCross;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.PartOfSpeechService;
import com.example.WordCFExam.service.TranslationWordRelationService;
import com.example.WordCFExam.service.WordFormService;
import com.example.WordCFExam.service.WordPartOfSpeechService;
import com.example.WordCFExam.service.WordService;
import com.example.WordCFExam.service.exam.CFExamProfilePointService;
import com.example.WordCFExam.service.exam.CFExamProfileService;
import com.example.WordCFExam.service.exam.CFExamWordQuestionnaireService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class UpdateWordBasicActivity extends AppCompatActivity {
    private WordService wordService;
    private TranslationWordRelationService translationWordRelationService;
    private PartOfSpeechService partOfSpeechService;
    private WordFormService wordFormServiceService;
    private WordPartOfSpeechService wordPartOfSpeechService;
    private EditText editTextName;
    private Word word;
    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;
    private Long toLanguageID;
    private ForeignWithNativeWords foreignWithNativeWords;
    private WordFormSpinAdapter adapter;
    private Dialog myDialog;
    private CFExamWordQuestionnaireCross cfExamWordQuestionnaireCross;
    CFExamWordQuestionnaireService cfExamWordQuestionnaireService;
    CFExamProfileService cfExamProfileService;
    CFExamProfilePointService cfExamProfilePointService;
    private CFExamProfilePointCross cfExamProfilePointCross;
    private CFProfileSpinAdapter cfProfileSpinAdapter;
    private Boolean isSetToCFExam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.wordService = new ViewModelProvider(this).get(WordService.class);
        this.translationWordRelationService = FactoryUtil.createTranslationWordRelationService(getApplication());
        this.wordFormServiceService = FactoryUtil.createWordFormService(getApplication());
        this.partOfSpeechService=FactoryUtil.createPartOfSpeechService(getApplication());
        this.wordPartOfSpeechService=FactoryUtil.createWordPartOfSpeechService(getApplication());
        setContentView(R.layout.activity_update_word);
        getSupportActionBar().setTitle("Basic");
        Intent intent = getIntent();
        this.translationAndLanguages = (TranslationAndLanguages) intent.getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) intent.getSerializableExtra("translationFromLanguageID");

        if (fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
            toLanguageID=translationAndLanguages.getNativeLanguage().getLanguageID();
        }


        this.cfExamWordQuestionnaireService=FactoryUtil.createCFExamQuestionnaireService(getApplication());
        this.cfExamProfilePointService = FactoryUtil.createCFExamProfilePointService(getApplication());
        this.cfExamProfileService=FactoryUtil.createCFExamProfileService(getApplication());

        editTextName = (EditText) findViewById(R.id.et_wordString);
        final Word word = (Word) getIntent().getSerializableExtra("word");
        getWordFromDB(word);
        getNativeWords(word);
        getAllWordForm(translationAndLanguages.getForeignLanguage().getLanguageID());
        getAllPartOfSpeech(translationAndLanguages.getForeignLanguage().getLanguageID());
        getDefinedPartOfSpeech(word);


        /**/
        findViewById(R.id.btn_UpdateForeignWord).setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                exportFormToEntiy(word);
                updateWord();
                String debug=null;
            }
        });
        findViewById(R.id.btn_setUnsetCFExam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerSetCFExamClick(word);
            }
        });




    }

    private void exportFormToEntiy(Word word) {
        word.setWordString(editTextName.getText().toString());
        Spinner spn_item = (Spinner) findViewById(R.id.spn_wordForm);
        int selectedItemPosition = spn_item.getSelectedItemPosition();
        WordForm wordForm = adapter.getItem(selectedItemPosition);


        if (!wordForm.getWordFormID().equals(-1L)) {
            word.setWordFormID(wordForm.getWordFormID());
        } else {
            word.setWordFormID(null);
        }



    }

    private void loadForm() {
        editTextName.setText(this.word.getWordString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getNativeWords(Word foreignWord) {
        class GetTasks extends AsyncTask<Void, Void, ForeignWithNativeWords> {

            @Override
            protected ForeignWithNativeWords doInBackground(Void... voids) {
                ForeignWithNativeWords translationWordFromForeign = translationWordRelationService.translateFromForeign(foreignWord.getWordID());
                return translationWordFromForeign;
            }

            @Override
            protected void onPostExecute(ForeignWithNativeWords foreignWithNativeWords) {
                super.onPostExecute(foreignWithNativeWords);
                UpdateWordBasicActivity.this.foreignWithNativeWords = foreignWithNativeWords;

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void getWordFromDB(Word word) {
        class GetTasks extends AsyncTask<Void, Void, Word> {

            @Override
            protected Word doInBackground(Void... voids) {
                UpdateWordBasicActivity.this.word = wordService.findByID(word.getWordID());
                return word;
            }

            @Override
            protected void onPostExecute(Word word) {
                super.onPostExecute(word);
                UpdateWordBasicActivity.this.word = word;
                loadForm();

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void updateWord() {

        class updateAsync extends AsyncTask<Void, Void, Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {


                return wordService.update(word);

            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);

                if (result>0) {
                    finish();
                   // Intent activity2Intent = new Intent(UpdateWordBasicActivity.this, UpdateWordMenuActivity.class);
                   // activity2Intent.putExtra("translationAndLanguages", UpdateWordBasicActivity.this.translationAndLanguages);
                   // activity2Intent.putExtra("translationFromLanguageID", UpdateWordBasicActivity.this.fromLanguageID);
                   // activity2Intent.putExtra("word", word);
                    //startActivity(activity2Intent);
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
        }

        updateAsync ut = new updateAsync();
        ut.execute();

    }

    private void getAllPartOfSpeech(Long languageID) {
        class GetTasks extends AsyncTask<Void, Void, List<PartOfSpeech>> {

            @Override
            protected List<PartOfSpeech> doInBackground(Void... voids) {
                List<PartOfSpeech> partOfSpeeches = partOfSpeechService.findAllByLanguageID(languageID);
                return partOfSpeeches;
            }

            @Override
            protected void onPostExecute(List<PartOfSpeech> partOfSpeeches) {
                super.onPostExecute(partOfSpeeches);
                PartOfSpeech partOfSpeech = new PartOfSpeech();

                partOfSpeech.setPartOfSpeechID(-1L);
                partOfSpeech.setName("...");

                partOfSpeeches.add(0,partOfSpeech);


                PartOfSpeechSpinAdapter adapter = new PartOfSpeechSpinAdapter(UpdateWordBasicActivity.this,
                        //android.R.layout.simple_spinner_item,
                        R.layout.spinner_item,
                        partOfSpeeches);
                Spinner spn_partOfSpeech = (Spinner) findViewById(R.id.spn_partOfSpeech);
                spn_partOfSpeech.setAdapter(adapter); // Set the custom adapter to the spinner
                // You can create an anonymous listener to handle the event when is selected an spinner item
                spn_partOfSpeech.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               int position, long id) {
                        // Here you get the current item (a User object) that is selected by its position


                        PartOfSpeech partOfSpeech = adapter.getItem(position);
                        if (!partOfSpeech.getPartOfSpeechID().equals(-1L)) {
                            createDefPartOfSpeech(partOfSpeech);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapter) {  }
                });


            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void getAllWordForm(Long languageID) {
        class GetTasks extends AsyncTask<Void, Void, List<WordForm>> {

            @Override
            protected List<WordForm> doInBackground(Void... voids) {

                List<WordForm> wordForms = wordFormServiceService.findAllByLanguageID(languageID);
                return wordForms;
            }

            @Override
            protected void onPostExecute(List<WordForm> items) {
                super.onPostExecute(items);
                WordForm wordForm = new WordForm();

                wordForm.setWordFormID(-1L);
                wordForm.setWordFormName("...");

                items.add(0,wordForm);


                adapter = new WordFormSpinAdapter(UpdateWordBasicActivity.this,
                        //android.R.layout.simple_spinner_item,
                        R.layout.spinner_item,
                        items);
                Spinner spn_item = (Spinner) findViewById(R.id.spn_wordForm);
                spn_item.setAdapter(adapter); // Set the custom adapter to the spinner
                // You can create an anonymous listener to handle the event when is selected an spinner item
                int spinnerPosition = 0;
                if (word.getWordFormID() != null) {
                    for (int i=0;i<items.size();i++) {
                        if (items.get(i).getWordFormID().equals(word.getWordFormID())) {
                            spinnerPosition=i;
                            break;
                        }
                    }

                    spn_item.setSelection(spinnerPosition);
                }

                spn_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               int position, long id) {
                        // Here you get the current item (a User object) that is selected by its position






                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapter) {  }
                });


            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void getDefinedPartOfSpeech(Word word) {
        class GetTasks extends AsyncTask<Void, Void, ForeignWordWithDefPartOfSpeech> {

            @Override
            protected ForeignWordWithDefPartOfSpeech doInBackground(Void... voids) {

                ForeignWordWithDefPartOfSpeech foreignWordWithDefPartOfSpeech = wordPartOfSpeechService.findForeignWordWithDefPartOfSpeech(word.getWordID());
                return foreignWordWithDefPartOfSpeech;


            }

            @Override
            protected void onPostExecute(ForeignWordWithDefPartOfSpeech tasks) {
                super.onPostExecute(tasks);


                UpdWordBasicPartOfSpeechListAdapter adapter = new UpdWordBasicPartOfSpeechListAdapter(UpdateWordBasicActivity.this);

                adapter.setItems(tasks.getPartOfSpeeches());
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    public void deleteDefPartOfSpeech(PartOfSpeech partOfSpeech)    {
        class GetTasks extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                wordPartOfSpeechService.deleteDefPartOfSpeech(UpdateWordBasicActivity.this.word,partOfSpeech);
                return null;
            }

            @Override
            protected void onPostExecute(Void voiD) {
                Toast.makeText(UpdateWordBasicActivity.this.getApplicationContext(),"Successfully delete",Toast.LENGTH_SHORT).show();
                getDefinedPartOfSpeech(UpdateWordBasicActivity.this.word);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    public void createDefPartOfSpeech(PartOfSpeech partOfSpeech)    {
        class GetTasks extends AsyncTask<Void, Void, Long> {

            @Override
            protected Long doInBackground(Void... voids) {
                WordPartOfSpeech wordPartOfSpeech = new WordPartOfSpeech();
                wordPartOfSpeech.setWordID(word.getWordID());
                wordPartOfSpeech.setPartOfSpeechID(partOfSpeech.getPartOfSpeechID());
                return wordPartOfSpeechService.insert(wordPartOfSpeech);


            }

            @Override
            protected void onPostExecute(Long id) {
                if(!id.equals(-1L)) {
                    Toast.makeText(UpdateWordBasicActivity.this.getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                }
                getDefinedPartOfSpeech(UpdateWordBasicActivity.this.word);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void handlerSetCFExamClick(Word selectedItem) {

        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_set_cfexam);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);
        Button btn_Cancel = (Button) myDialog.findViewById(R.id.btn_dialog_cancel);
        TextView tv_msg = (TextView) myDialog.findViewById(R.id.tv_setCFExamProfile_msg);
        Spinner spn_cfExamProfile = (Spinner) myDialog.findViewById(R.id.spn_cfExamProfile);



        DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<Boolean>() {
            @Override
            public Boolean doInBackground() {
                cfExamWordQuestionnaireCross = cfExamWordQuestionnaireService.findByWordID(selectedItem.getWordID(), toLanguageID);
                if(cfExamWordQuestionnaireCross!=null) {
                    cfExamProfilePointCross = cfExamProfilePointService.findCrossByID(cfExamWordQuestionnaireCross.getCfExamQuestionnaire().getCurrentCFExamProfilePointID());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPostExecute(Boolean item) {
                setSetToCFExam(item);

                if (getSetToCFExam()) {
                    DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy HH:mm:ss");
                    String formattedEntryPointDate = dateFormat.format(cfExamWordQuestionnaireCross.getCfExamQuestionnaire().getEntryPointDateTime());
                    String sourceString="This word is set to CF Exam. " +
                            "<br>To Language: <i><u>" + cfExamWordQuestionnaireCross.getLanguage().getLanguageName()+ "</u></i>" +
                            "<br>Profile Name: <i><u>" + cfExamProfilePointCross.getCfExamProfile().getName()+ "</u></i>" +
                            "<br>Point Name: <i><u>" + cfExamProfilePointCross.getCfExamProfilePoint().getLabelText()  + "</u></i>" +
                            "<br>Date Point: <i><u>" + formattedEntryPointDate + "</u></i>" +
                            "<br><b>Do you want to UnSet?</b>";
                    tv_msg.setText(Html.fromHtml(sourceString));
                    spn_cfExamProfile.setVisibility(View.INVISIBLE);
                    btn_Submit.setText("UnSet");
                } else {
                    tv_msg.setText("Please choose CF Exam Profile.");
                    spn_cfExamProfile.setVisibility(View.VISIBLE);
                    btn_Submit.setText("Set");

                    DbExecutorImp<List<CFExamProfile>> dbExecutorCFExamProfile = FactoryUtil.<List<CFExamProfile>>createDbExecutor();
                    dbExecutorCFExamProfile.execute_(new DbExecutor<List<CFExamProfile>>() {
                        @Override
                        public List<CFExamProfile> doInBackground() {
                            return cfExamProfileService.findAllOrderAlphabetic(Session.getLongAttribute(UpdateWordBasicActivity.this, SessionNameAttribute.ProfileID,-1L),"");
                        }

                        @Override
                        public void onPostExecute(List<CFExamProfile> item) {
                            CFExamProfile cfExamProfileSelect = new CFExamProfile();
                            cfExamProfileSelect.setProfileID(-1L);
                            cfExamProfileSelect.setCFExamProfileID(-1L);
                            cfExamProfileSelect.setName("Select");

                            item.add(0,cfExamProfileSelect);

                            cfProfileSpinAdapter = new CFProfileSpinAdapter(UpdateWordBasicActivity.this,
                                    //android.R.layout.simple_spinner_item,
                                    R.layout.spinner_item,
                                    item);

                            spn_cfExamProfile.setAdapter(cfProfileSpinAdapter);


                        }
                    });




                }

            }
        });








        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DbExecutorImp<Boolean> dbExecutorCFExamProfile = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutorCFExamProfile.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        if (getSetToCFExam()) {

                            Integer delete = cfExamWordQuestionnaireService.delete(cfExamWordQuestionnaireCross.getCfExamQuestionnaire());
                            if (delete>0) {
                                return true;
                            } else {
                                return false;
                            }

                        } else {
                            int selectedItemPosition = spn_cfExamProfile.getSelectedItemPosition();
                            CFExamProfile selectedCFExamProfile = cfProfileSpinAdapter.getItem(selectedItemPosition);


                            boolean isCreated = cfExamWordQuestionnaireService.create(selectedItem.getWordID(), toLanguageID, selectedCFExamProfile);
                            if (isCreated){
                                return true;
                            } else {
                                return false;
                            }
                        }

                    }

                    @Override
                    public void onPostExecute(Boolean item) {
                        myDialog.dismiss();

                        if (item) {
                            Toast.makeText(UpdateWordBasicActivity.this, "Operation is Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UpdateWordBasicActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });




            }
        });


        myDialog.show();


    }


    public Boolean getSetToCFExam() {
        return isSetToCFExam;
    }

    public void setSetToCFExam(Boolean setToCFExam) {
        isSetToCFExam = setToCFExam;
    }


}






