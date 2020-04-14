package com.example.WordCFExam.activity.configureActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.MainActivity;
import com.example.WordCFExam.background.ManualStartCFExamService;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

public class ConfigureMenuActivity extends AppCompatActivity {

    ListView mainListMenu;

    String[] mainListMenuOptions = new String[]{
            "Profile",
            "Language",
            "Translation",
            "CF Exam profile",
            "TopicType",
            "Preference",
            "DB Import / Export"
            };
    Class<?>[] mainListMenuOptionsNavigate = new Class[]{
            ConfigProfileActivity.class,
            ConfigLanguageActivity.class,
            ConfigTranslationActivity.class,
            ConfigCFExamProfileActivity.class,
            ConfigTopicTypeActivity.class,
            ConfigPreferenceActivity.class,
            ConfigDBImportExport.class};

    Class<?>[] mainListMenuNeedProfile = new Class[]{
            ConfigTranslationActivity.class,
            ConfigCFExamProfileActivity.class,
            ConfigTopicTypeActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_menu);
        getSupportActionBar().setTitle("Configure " + "("+
                Session.getStringAttribute(ConfigureMenuActivity.this, SessionNameAttribute.ProfileName, "")
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

                for(int i=0;i<mainListMenuNeedProfile.length;i++) {
                    if (mainListMenuNeedProfile[i].equals(mainListMenuOptionsNavigate[position])
                    && profileID<=0L) {
                        Toast.makeText(getApplicationContext(), "Please select Profile", Toast.LENGTH_LONG).show();
                        return;
                    }
                }





                Intent activity2Intent = new Intent(getApplicationContext(), mainListMenuOptionsNavigate[position]);
                startActivity(activity2Intent);

            }
        });

        ComponentName componentName = startService(new Intent(ConfigureMenuActivity.this, ManualStartCFExamService.class));
        if (componentName!=null) {
            Toast.makeText(getApplicationContext(), "Scheduled Started", Toast.LENGTH_LONG).show();
        }


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

}
