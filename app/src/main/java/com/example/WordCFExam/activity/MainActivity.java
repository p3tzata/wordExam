package com.example.WordCFExam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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
import com.example.WordCFExam.activity.exam.ExamMenuActivity;
import com.example.WordCFExam.activity.exam.RandomExamListAllDictionaryActivity;
import com.example.WordCFExam.activity.topic.TopicTypeActivity;
import com.example.WordCFExam.activity.wordActivity.ListAllDictionary;
import com.example.WordCFExam.background.CFExamAlarm;
import com.example.WordCFExam.background.ManualStartCFExamService;
import com.example.WordCFExam.background.NotificationActivity;
import com.example.WordCFExam.utitliy.DBUtil;
import com.example.WordCFExam.utitliy.MenuUtility;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
/*TODO


listable and editable recycler position

Scheduler

Testing.

 */


    private Menu mOptionsMenu;
    private String CHANNEL_ID="1";
    ListView mainListMenu;

    String[] mainListMenuOptions = new String[]{
            "Words",
            "Topics",
            "Exams"
};
    Class<?>[] mainListMenuOptionsNavigate = new Class[]{
            ListAllDictionary.class,
            TopicTypeActivity.class,
            ExamMenuActivity.class
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);




        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WAKE_LOCK}, 1);
        }



        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Main " + "("+
                Session.getStringAttribute(MainActivity.this, SessionNameAttribute.ProfileName, "")
                +")");



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

        //createNotificationChannel();
        //sendNotification();
       // setAlarm();

     //   Session.setIntAttribute(getApplicationContext(),SessionNameAttribute.CfExamEnabledFromHour,7);
     //   Session.setIntAttribute(getApplicationContext(),SessionNameAttribute.CfExamEnabledToHour,20);
        DBUtil seed = new DBUtil(this.getApplication());

        seed.seedDB();


    }

    @Override
    public void onResume(){
        super.onResume();
        getSupportActionBar().setTitle("Main " + "("+
                Session.getStringAttribute(MainActivity.this, SessionNameAttribute.ProfileName, "")
                +")");

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


    private void sendNotification(){
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setFullScreenIntent(pendingIntent,true)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());

    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        int sdkInt = Build.VERSION.SDK_INT;
        int o = Build.VERSION_CODES.O;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test channel name";
            String description = "test channel desc";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setAlarm(){
        AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, CFExamAlarm.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 10, alarmIntent);
    }




}
