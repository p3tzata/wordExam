package com.example.myapplication.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class WordOld {

    @PrimaryKey
    private Long id;

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public WordOld(String word) {this.mWord = word;}

    public String getWord(){return this.mWord;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setmWord(@NonNull String mWord) {
        this.mWord = mWord;
    }
}
