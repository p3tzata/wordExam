package com.example.myapplication.entity.exam;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;

@Entity(indices = {
                @Index(value = "profileID"),
                @Index(unique = true,value = {"profileID","topicTypeName"}) },

        foreignKeys = {
               @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Profile.class, parentColumns = "profileID", childColumns = "profileID")
        }
)


public class TopicType {

    @PrimaryKey
    private Long topicTypeID;

    @NonNull
    private Long profileID;

    @NonNull
    private String topicTypeName;

    public TopicType() {
    }

    public Long getTopicTypeID() {
        return topicTypeID;
    }

    public void setTopicTypeID(Long topicTypeID) {
        this.topicTypeID = topicTypeID;
    }

    @NonNull
    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(@NonNull Long profileID) {
        this.profileID = profileID;
    }

    @NonNull
    public String getTopicTypeName() {
        return topicTypeName;
    }

    public void setTopicTypeName(@NonNull String topicTypeName) {
        this.topicTypeName = topicTypeName;
    }
}
