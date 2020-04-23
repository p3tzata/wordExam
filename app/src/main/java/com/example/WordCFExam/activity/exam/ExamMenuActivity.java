package com.example.WordCFExam.activity.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.configureActivity.ConfigCFExamProfileActivity;
import com.example.WordCFExam.activity.configureActivity.ConfigCFExamScheduleActivity;
import com.example.WordCFExam.activity.configureActivity.ConfigDBImportExport;
import com.example.WordCFExam.activity.configureActivity.ConfigLanguageActivity;
import com.example.WordCFExam.activity.configureActivity.ConfigPreferenceActivity;
import com.example.WordCFExam.activity.configureActivity.ConfigProfileActivity;
import com.example.WordCFExam.activity.configureActivity.ConfigTopicTypeActivity;
import com.example.WordCFExam.activity.configureActivity.ConfigTranslationActivity;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

public class ExamMenuActivity extends AppCompatActivity {

    ListView mainListMenu;

    String[] mainListMenuOptions = new String[]{
            "CF Word exam",
            "CF Topic exam",
            "Random Word exam"
            };
    Class<?>[] mainListMenuOptionsNavigate = new Class[]{
            CFExamWordQuestionnaireNeedProceedActivity.class,
            CFExamTopicQuestionnaireNeedProceedActivity.class,
            RandomExamListAllDictionaryActivity.class
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_menu);
        getSupportActionBar().setTitle("Exams " + "("+
                Session.getStringAttribute(ExamMenuActivity.this, SessionNameAttribute.ProfileName, "")
                +")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainListMenu = (ListView) findViewById(R.id.configureListMenu);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mainListMenuOptions);
        mainListMenu.setAdapter(adapter);
        mainListMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Session.setLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID, -1L);
                long profileID = Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID, -1L);





                Intent activity2Intent = new Intent(getApplicationContext(), mainListMenuOptionsNavigate[position]);
                startActivity(activity2Intent);

            }
        });
        /*
        ComponentName componentName = startService(new Intent(ConfigureMenuActivity.this, ManualStartCFExamService.class));
        if (componentName!=null) {
            Toast.makeText(getApplicationContext(), "Scheduled Started", Toast.LENGTH_LONG).show();
        }
        */



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

    @Override
    public void onResume(){
        super.onResume();
        getSupportActionBar().setTitle("Exams " + "("+
                Session.getStringAttribute(ExamMenuActivity.this, SessionNameAttribute.ProfileName, "")
                +")");

    }


}
