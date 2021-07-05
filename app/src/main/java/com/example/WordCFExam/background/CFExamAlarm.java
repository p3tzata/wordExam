package com.example.WordCFExam.background;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.WordCFExam.R;
import com.example.WordCFExam.CFExamApplication;
import com.example.WordCFExam.activity.MainActivity;
import com.example.WordCFExam.activity.exam.CFExamTopicQuestionnaireNeedProceedActivity;
import com.example.WordCFExam.activity.exam.CFExamWordQuestionnaireNeedProceedActivity;
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
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CFExamAlarm extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
      //  android.os.Debug.waitForDebugger();
        /*
     boolean isInDoNotDisturb = false;
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
 */


        CFExamWordQuestionnaireService cfExamWordQuestionnaireService = CFExamApplication.cfExamWordQuestionnaireService;
        CFExamTopicQuestionnaireService cfExamTopicQuestionnaireService = CFExamApplication.cfExamTopicQuestionnaireService;




        DbExecutorImp<NotificationMessenger> dbExecutor = FactoryUtil.<NotificationMessenger>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<NotificationMessenger>() {
            @Override
            public NotificationMessenger doInBackground() {


                // StringBuilder stringBuilder = new StringBuilder();
                List<Profile> allProfileNeedPassCFExamWord = cfExamWordQuestionnaireService.findAllProfileNeedProceed();
                List<Profile> allProfileNeedProceedCFExamTopic = cfExamTopicQuestionnaireService.findAllProfileNeedProceed();


                NotificationMessenger notificationMessenger = new NotificationMessenger();
/*
                if (allProfileNeedPassCFExamWord.size()>0) {
                stringBuilder.append(profileListToStringMsg("Word Exam: ", allProfileNeedPassCFExamWord));
                 }
                if (allProfileNeedPassCFExamWord.size()>0 && allProfileNeedProceedCFExamTopic.size()>0 ) {
                    stringBuilder.append(" | ");
                }



                if (allProfileNeedProceedCFExamTopic.size()>0) {
                    stringBuilder.append(profileListToStringMsg("Topic Exam: ", allProfileNeedProceedCFExamTopic));
                }

                }

 */

                for (Profile profile :
                        allProfileNeedPassCFExamWord) {
                    String title = String.format("[%s] need to proceed", profile.getLabelText());
                    notificationMessenger.addNotify(CFExamWordQuestionnaireNeedProceedActivity.class,profile, title, "CFExam Word");
                }

                for (Profile profile :
                        allProfileNeedProceedCFExamTopic) {
                    String title = String.format("[%s] need to proceed", profile.getLabelText());
                    notificationMessenger.addNotify(CFExamTopicQuestionnaireNeedProceedActivity.class,profile,  title, "CFExam Topic");
                }

                return notificationMessenger;
                // return stringBuilder.toString();
            }

            @Override
            public void onPostExecute(NotificationMessenger item) {
                Iterator<Notify> notifyIterator = item.getIterator();
                Integer id=1;
                while (notifyIterator.hasNext()) {
                    Notify notify = notifyIterator.next();
                    sendNotification(CFExamAlarm.this.context, id++,notify.getTargetActivity(),notify.getTargetProfile(),notify.getTitle(), notify.getMessage());
                }

                /*
                if (item.length()>0) {
                    sendNotification(CFExamAlarm.this.context,1,"CF Exam need to proceed", item);
                }

                 */
            }

        });
    }





    public void setAlarm(Context context)
    {
       // android.os.Debug.waitForDebugger();

        int searchRateMinute = Session.getIntAttribute(context, SessionNameAttribute.CfExamSearchRateMinute, 1);


        int intervalMilliSecs = 1000 * 60 * searchRateMinute;
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CFExamAlarm.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();


        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                intervalMilliSecs, alarmIntent);
    }

    public void sendNotification(Context context,Integer id,Class<?> targetActivity, Profile targetProfile,String contentTitle, String contentText){


        Intent intent = new Intent(context, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra("targetProfile", targetProfile);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
               // .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(soundUri)
                // Set the intent that will fire when the user taps the notification
               // .setFullScreenIntent(pendingIntent,true) //15.06.2021
                .setContentIntent(pendingIntent) //15.06.2021
                .setAutoCancel(true);

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                    builder.setChannelId(channelId);
                }


                notificationManager.notify(id, builder.build());

            }
        }, 1000);




    }


    public void sendNotification_works(Context mContext) {

        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext.getApplicationContext(), "notify_001");
        Intent ii = new Intent(mContext.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("verseurl");
        bigText.setBigContentTitle("Today's Bible Verse");
        bigText.setSummaryText("Text in detail");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
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
