package com.example.WordCFExam.background;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.WordCFExam.R;
import com.example.WordCFExam.CFExamApplication;
import com.example.WordCFExam.activity.MainActivity;
import com.example.WordCFExam.dao.CFExamTopicQuestionnaireDao;
import com.example.WordCFExam.dao.CFExamWordQuestionnaireDao;
import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.CFExamTopicQuestionnaireService;
import com.example.WordCFExam.service.exam.CFExamWordQuestionnaireService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CFExamAlarm extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
       // android.os.Debug.waitForDebugger();
        boolean isInDoNotDisturb = false;
        DateFormat dateFormat = new SimpleDateFormat("HH");
        String formattedEntryPointDate = dateFormat.format(Calendar.getInstance().getTime());
        int currentHour=Integer.valueOf(formattedEntryPointDate);
        int enabledFromHour= Session.getIntAttribute(context, SessionNameAttribute.CfExamEnabledFromHour,6);
        int enabledToHour=Session.getIntAttribute(context, SessionNameAttribute.CfExamEnabledToHour,22);


        if ( enabledFromHour<=enabledToHour && (currentHour<enabledFromHour || currentHour>enabledToHour) )  {
            isInDoNotDisturb=true;
        }

        if ( enabledFromHour>enabledToHour && (currentHour<enabledFromHour && currentHour>enabledToHour) )  {
            isInDoNotDisturb=true;
        }

        if (isInDoNotDisturb) {
            return;
        }



        CFExamWordQuestionnaireService cfExamWordQuestionnaireService = CFExamApplication.cfExamWordQuestionnaireService;
        CFExamTopicQuestionnaireService cfExamTopicQuestionnaireService = CFExamApplication.cfExamTopicQuestionnaireService;




        DbExecutorImp<String> dbExecutor = FactoryUtil.<String>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<String>() {
            @Override
            public String doInBackground() {


                StringBuilder stringBuilder = new StringBuilder();
                List<Profile> allProfileNeedPassCFExamWord = cfExamWordQuestionnaireService.findAllProfileNeedProceed();
                List<Profile> allProfileNeedProceedCFExamTopic = cfExamTopicQuestionnaireService.findAllProfileNeedProceed();
                if (allProfileNeedPassCFExamWord.size()>0) {
                    stringBuilder.append(profileListToStringMsg("Word Exam: ", allProfileNeedPassCFExamWord));
                }

                if (allProfileNeedPassCFExamWord.size()>0 && allProfileNeedProceedCFExamTopic.size()>0 ) {
                    stringBuilder.append(" | ");
                }

                if (allProfileNeedProceedCFExamTopic.size()>0) {
                    stringBuilder.append(profileListToStringMsg("Topic Exam: ", allProfileNeedProceedCFExamTopic));
                }


                return stringBuilder.toString();
            }

            @Override
            public void onPostExecute(String item) {
                if (item.length()>0) {
                    sendNotification("CF Exam need to proceed", item);
                }
            }
        });










    }

    public void setAlarm(Context context)
    {
        //android.os.Debug.waitForDebugger();
        int searchRateMinute = Session.getIntAttribute(context, SessionNameAttribute.CfExamSearchRateMinute, 1);
        int intervalMilliSecs = 1000 * 60 * searchRateMinute;
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CFExamAlarm.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();


        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                intervalMilliSecs, alarmIntent);
    }

    private void sendNotification(String contentTitle, String contentText){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
               // .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(soundUri)
                // Set the intent that will fire when the user taps the notification
                .setFullScreenIntent(pendingIntent,true)
                .setAutoCancel(true);

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                notificationManager.notify(1, builder.build());

            }
        }, 1000);




    }

    private String profileListToStringMsg(String examName, List<Profile> list) {
        StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(examName);
            for(int i=0; i<list.size();i++) {

                if (i==0) {
                    stringBuilder.append(list.get(i).getProfileName());
                } else {
                    stringBuilder.append(", " + list.get(i).getProfileName());
                }

            }


        return stringBuilder.toString();

    }
}
