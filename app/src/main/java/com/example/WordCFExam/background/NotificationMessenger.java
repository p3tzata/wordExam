package com.example.WordCFExam.background;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WordCFExam.entity.Profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NotificationMessenger {

    private List<Notify> notifyList;

    public NotificationMessenger() {
        notifyList=new ArrayList<>();
    }


    public void addNotify(Class<? extends AppCompatActivity> targetActivity,Profile targetProfile,String title,String message){
        notifyList.add(new Notify(targetActivity,targetProfile,title,message));
    }

    public List<Notify> getNotifyList() {
        return notifyList;
    }

    public boolean isEmpty(){
        return notifyList.size()==0;
    }

    public Iterator<Notify> getIterator(){
        return notifyList.iterator();
    }

}


class Notify {
    private String title;
    private String message;
    private Profile targetProfile;
    private Class<? extends AppCompatActivity> targetActivity;

    public Notify(Class<? extends AppCompatActivity> targetActivity,Profile targetProfile, String title, String message) {
        this.title = title;
        this.message = message;
        this.targetProfile=targetProfile;
        this.targetActivity=targetActivity;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Profile getTargetProfile() {
        return targetProfile;
    }

    public Class<? extends AppCompatActivity> getTargetActivity() {

        return targetActivity;
    }
}
