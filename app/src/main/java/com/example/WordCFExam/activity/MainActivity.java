package com.example.WordCFExam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.exam.CFExamTopicQuestionnaireNeedProceedActivity;
import com.example.WordCFExam.activity.exam.CFExamWordQuestionnaireNeedProceedActivity;
import com.example.WordCFExam.activity.exam.RandomExamListAllDictionaryActivity;
import com.example.WordCFExam.activity.topic.TopicTypeActivity;
import com.example.WordCFExam.background.NotificationActivity;
import com.example.WordCFExam.background.NotificationPublisher;
import com.example.WordCFExam.seed.Seed;
import com.example.WordCFExam.utitliy.MenuUtility;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

public class MainActivity extends AppCompatActivity {
/*TODO


listable and editable recycler position

Scheduler

Testing.

 */


    private Menu mOptionsMenu;

    ListView mainListMenu;

    String[] mainListMenuOptions = new String[]{
            "Dictionaries",
            "Topics",
            "CF Word exam",
            "CF Topic exam",
            "Random Word exam"};
    Class<?>[] mainListMenuOptionsNavigate = new Class[]{
            ListAllDictionary.class,
            TopicTypeActivity.class,
            CFExamWordQuestionnaireNeedProceedActivity.class,
            CFExamTopicQuestionnaireNeedProceedActivity.class,
            RandomExamListAllDictionaryActivity.class
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Main");
        mainListMenu=(ListView)findViewById(R.id.mainListMenu);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mainListMenuOptions);
        mainListMenu.setAdapter(adapter);

        mainListMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                long profileID = Session.getLongAttribute(MainActivity.this, SessionNameAttribute.ProfileID, -1L);

                if (profileID==0) {
                    Toast.makeText(getApplicationContext(),"Please select Profile",Toast.LENGTH_SHORT).show();
                } else {

                    Intent activity2Intent = new Intent(getApplicationContext(), mainListMenuOptionsNavigate[position]);
                    startActivity(activity2Intent);
                }
            }
        });


        Seed seed = new Seed(this.getApplication());

        seed.seedDB();


    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.mOptionsMenu=menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuUtility.checkIsEditMode(this,mOptionsMenu);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       return MenuUtility.onOptionsItemSelected(this,item);
    }






}
