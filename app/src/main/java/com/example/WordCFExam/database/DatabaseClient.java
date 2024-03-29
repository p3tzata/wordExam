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
                .addMigrations(MIGRATION_15_16)
                //.fallbackToDestructiveMigration()
                .build();
    }

    static final Migration MIGRATION_15_16 = new Migration(15, 16) { // From version 1 to version 2
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Remove the table
            //database.execSQL("DROP INDEX main.index_TopicType_profileID_topicTypeName"); // Use the right table name

            database.execSQL("ALTER TABLE 'language' ADD COLUMN 'tts_speechRate' REAL");

            // OR: We could update it, by using an ALTER query

            // OR: If needed, we can create the table again with the required settings
            // database.execSQL("CREATE TABLE IF NOT EXISTS my_table (id INTEGER, PRIMARY KEY(id), ...)")
        }
    };
    static final Migration MIGRATION_14_15 = new Migration(14, 15) { // From version 1 to version 2
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Remove the table
            //database.execSQL("DROP INDEX main.index_TopicType_profileID_topicTypeName"); // Use the right table name

              database.execSQL("ALTER TABLE 'language' ADD COLUMN 'tts_pitch' REAL");

            // OR: We could update it, by using an ALTER query

            // OR: If needed, we can create the table again with the required settings
            // database.execSQL("CREATE TABLE IF NOT EXISTS my_table (id INTEGER, PRIMARY KEY(id), ...)")
        }
    };
    static final Migration MIGRATION_13_14 = new Migration(13, 14) { // From version 1 to version 2
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Remove the table
            //database.execSQL("DROP INDEX main.index_TopicType_profileID_topicTypeName"); // Use the right table name
            database.execSQL("ALTER TABLE 'language' ADD COLUMN 'tts_voice' TEXT");
          //  database.execSQL("ALTER TABLE 'language' ADD COLUMN 'tts_pitch' NUMBER");

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
