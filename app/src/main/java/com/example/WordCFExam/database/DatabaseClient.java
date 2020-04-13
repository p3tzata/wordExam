package com.example.WordCFExam.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private WordRoomDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, WordRoomDatabase.class, "WordCFExamDB")
                //.addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) { // From version 1 to version 2
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Remove the table
            database.execSQL("DROP TABLE word_table"); // Use the right table name

            // OR: We could update it, by using an ALTER query

            // OR: If needed, we can create the table again with the required settings
            // database.execSQL("CREATE TABLE IF NOT EXISTS my_table (id INTEGER, PRIMARY KEY(id), ...)")
        }
    };


    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public WordRoomDatabase getAppDatabase() {
        return appDatabase;
    }
}
