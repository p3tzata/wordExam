package com.example.WordCFExam.activity.configureActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.provider.OpenableColumns;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.database.DatabaseClient;
import com.example.WordCFExam.utitliy.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConfigDBImportExport extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;
    String databaseName="WordCFExamDB";
    String packageName="com.example.myapplication";
    String backupDir="/BackupFolderDB";
    File importFileDB=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }




        setContentView(R.layout.activity_config_d_b_import);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Import / Export Database");

        findViewById(R.id.btn_choseFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        findViewById(R.id.btn_exportDB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportDB();
            }
        });

        findViewById(R.id.bnt_importDB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (importFileDB!=null) {
                    handlerImportClick();
                } else {
                    Toast.makeText(getApplicationContext(), "Please choose file to import.",  Toast.LENGTH_SHORT).show();
                }
            }
        });

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
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",  Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String dataColumn = getDataColumn(getApplicationContext(), uri, null, null);
                    String path1 = uri.getPath();
                    String[] split = path1.split(":",2);
                    String s = split[1];
                    File sd = Environment.getExternalStorageDirectory();
                    importFileDB=new File(sd,"/"+s);

                    if (!importFileDB.exists()) {
                        String fileName = getFileName(uri);
                        importFileDB=new File(sd,backupDir + "/"+ fileName);
                    }



                    TextView tv_chosenFileName = (TextView) findViewById(R.id.tv_chosenFileName);
                    if (importFileDB.exists()) {
                        tv_chosenFileName.setText(importFileDB.getName());
                    } else {
                        Toast.makeText(getBaseContext(), "Can not select the file.",
                                Toast.LENGTH_LONG).show();
                    }


                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private String getFileName(Uri uri) throws IllegalArgumentException {
        // Obtain a cursor with information regarding this uri
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }

        cursor.moveToFirst();

        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));

        cursor.close();

        return fileName;
    }


    public void handlerImportClick() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("DO YOU REALLY WANT TO IMPORT "+importFileDB.getName()+"." +
                "\n Current DataBase will be destroyed forever." +
                "\n Please before import make some backup.");


        builder
                //.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        try {
                            if (importDB()) {
                                Toast.makeText(getBaseContext(), "Successfully Imported",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getBaseContext(), "Error Importation",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException e) {
                            Toast.makeText(getBaseContext(), "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        alert.setTitle("Are you sure for importing");
        alert.show();

    }

    private void exportDB() {


        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            Date time = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy_HH:mm:ss");
            String formattedCurrentTime = dateFormat.format(time);


            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + packageName
                        + "//databases//" + databaseName;
                File backupDirDB = new File(sd, backupDir);

                if(!backupDirDB.exists())
                {
                    backupDirDB.mkdir();
                }


                String backupDBPath  = backupDir + "/" + databaseName+"_"+formattedCurrentTime+".db";
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().close();
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), "Successfully Exported: " + backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

    private boolean importDB() throws IOException {

            // Close the SQLiteOpenHelper so it will commit the created empty
            // database to internal storage.

            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().close();
            File newDb = importFileDB;
            File data = Environment.getDataDirectory();
            String  currentDBPath= "//data//" + packageName + "//databases//" + databaseName;
            File oldDb = new File(data, currentDBPath);

            if (newDb.exists() && oldDb.exists()) {
                FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
                // Access the copied database so SQLiteHelper will cache it and mark
                // it as created.

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().close();
                return true;
            } return false;

        }






}
