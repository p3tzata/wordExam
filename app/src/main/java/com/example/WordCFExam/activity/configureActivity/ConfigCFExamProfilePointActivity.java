package com.example.WordCFExam.activity.configureActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.BaseEditableAppCompatActivity;
import com.example.WordCFExam.activity.base.GetItemsExecutorBlock;
import com.example.WordCFExam.adapter.configure.CFExamProfilePointEditableAdapter;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.CFExamProfilePointService;

import java.util.List;


public class ConfigCFExamProfilePointActivity extends
        BaseEditableAppCompatActivity<CFExamProfilePoint, CFExamProfilePointService, ConfigCFExamProfilePointActivity, CFExamProfilePointEditableAdapter> {

    private CFExamProfile CFExamProfile;

    private CFExamProfilePointService cfExamProfilePointService;
    private Dialog myDialog;


    @Override
    public void onCreateCustom() {
        setContentView(R.layout.activity_base_crudable);
        CFExamProfile= (CFExamProfile) getIntent().getSerializableExtra("CFExamProfile");
        this.cfExamProfilePointService=FactoryUtil.createCFExamProfilePointService(getApplication());
        super.setItemService(cfExamProfilePointService);
        CFExamProfilePointEditableAdapter adapter = new CFExamProfilePointEditableAdapter(ConfigCFExamProfilePointActivity.this);
        super.setAdapter(adapter);
        super.setContext(ConfigCFExamProfilePointActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(CFExamProfile.getName() +" | "+ "Points");
        setGetItemsExecutor(new GetItemsExecutorBlock<CFExamProfilePoint>() {
            @Override
            public List<CFExamProfilePoint> execute() {
                List<CFExamProfilePoint> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(CFExamProfile.getCFExamProfileID(), "");
                return allOrderAlphabetic;
            }
        });
        getItems();


    }



    @Override
    public void onSearchBarGetItemsExecutorHandler(String contains) {
        setGetItemsExecutor(new GetItemsExecutorBlock<CFExamProfilePoint>() {
            @Override
            public List<CFExamProfilePoint> execute() {
                List<CFExamProfilePoint> allOrderAlphabetic = getItemService().findAllOrderAlphabetic(CFExamProfile.getProfileID(), contains);
                return allOrderAlphabetic;
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



    @Override
    public void handlerDeleteClick(CFExamProfilePoint selectedItem) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(selectedItem.getLabelText());


        builder
                //.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        deleteItem(selectedItem);


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        alert.setTitle("Are you sure for deleting");
        alert.show();

    }


    @Override
    public void handlerCreateUpdateClick(boolean isEditMode, CFExamProfilePoint selectedItem) {
        this.myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_cfexam_profile_point_edit);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/5);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);
        EditText et_newItem = (EditText) myDialog.findViewById(R.id.et_dialog_newItem);
        EditText et_period = (EditText) myDialog.findViewById(R.id.et_dialog_period);
        RadioGroup radioGroupPeriod = (RadioGroup) myDialog.findViewById(R.id.rg_periodType);
        CheckBox chb_looped = (CheckBox) myDialog.findViewById(R.id.chkb_dialog_edit_cfprofile_point_loop);


        if (isEditMode && selectedItem!=null) {

            long calcPeriod=0L;
            et_newItem.setText(selectedItem.getName());
            if (selectedItem.getIsLoopRepeat()) {
                chb_looped.setChecked(true);
            }
            Long lastOfPeriodInMinute = selectedItem.getLastOfPeriodInMinute();
            long tempCalc = lastOfPeriodInMinute / 44640L;
            if ((tempCalc)>1L) {
                calcPeriod=lastOfPeriodInMinute/44640L;
                radioGroupPeriod.check(myDialog.findViewById(R.id.rg_period_month).getId());
            } else {
                tempCalc=lastOfPeriodInMinute/1440L;
                if ((tempCalc)>0L) {
                    calcPeriod=lastOfPeriodInMinute/1440L;
                    radioGroupPeriod.check(myDialog.findViewById(R.id.rg_period_day).getId());
                } else {
                    calcPeriod=lastOfPeriodInMinute;
                    radioGroupPeriod.check(myDialog.findViewById(R.id.rg_period_minute).getId());
                }

            }




            et_period.setText(String.valueOf(calcPeriod));


        }

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                long calcPeriod = 0L;
                boolean isOnError=false;

               try {

                int checkedRadioButtonId = radioGroupPeriod.getCheckedRadioButtonId();
                if (checkedRadioButtonId == myDialog.findViewById(R.id.rg_period_minute).getId()) {
                    calcPeriod = Long.valueOf(et_period.getText().toString());
                } else if (checkedRadioButtonId == myDialog.findViewById(R.id.rg_period_day).getId()) {
                    calcPeriod = Long.valueOf(et_period.getText().toString()) * 1440L;
                } else {
                    calcPeriod = Long.valueOf(et_period.getText().toString()) * 44640L;
                }
               } catch (NumberFormatException ex) {
                   isOnError=true;
                   Toast.makeText(getContext(), "Invalid Number: " + et_period.getText().toString(), Toast.LENGTH_SHORT).show();
              }

                if (!isOnError) {
                    if (isEditMode) {
                        selectedItem.setName(et_newItem.getText().toString());
                        selectedItem.setLastOfPeriodInMinute(calcPeriod);
                        selectedItem.setIsLoopRepeat(chb_looped.isChecked());

                        updateItem(selectedItem);
                        myDialog.dismiss();
                    } else {
                        CFExamProfilePoint newItem = new CFExamProfilePoint();

                        newItem.setName(et_newItem.getText().toString());
                        newItem.setLastOfPeriodInMinute(calcPeriod);
                        newItem.setCFExamProfileID(CFExamProfile.getCFExamProfileID());
                        newItem.setIsLoopRepeat(chb_looped.isChecked());
                        createItem(newItem);
                        myDialog.dismiss();
                    }
                }
            }
        });


        myDialog.show();
    }

    @Override
    public void recyclerViewOnClickHandler(View v, CFExamProfilePoint selectedItem) {
        callShowCrudMenu(v,selectedItem);
    }


}
