package com.example.WordCFExam.utitliy;

import android.app.Application;
import android.app.Dialog;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.base.ListableAppCompatActivity;
import com.example.WordCFExam.adapter.spinnerAdapter.CFProfileSpinAdapter;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePointCross;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaireCross;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.CFExamProfilePointService;
import com.example.WordCFExam.service.exam.CFExamProfileService;
import com.example.WordCFExam.service.exam.CFExamWordQuestionnaireService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CFExamHelper<C extends ListableAppCompatActivity> {

    public CFExamHelper(Application application,C context,Dialog myDialog, Long toLanguageID) {
        this.application = application;
        this.cfExamWordQuestionnaireService=FactoryUtil.createCFExamQuestionnaireService(application);
        this.cfExamProfilePointService = FactoryUtil.createCFExamProfilePointService(application);
        this.toLanguageID=toLanguageID;
        this.cfExamProfileService=FactoryUtil.createCFExamProfileService(application);
        this.myDialog=myDialog;
        this.context = context;

    }

    public Boolean getSetToCFExam() {
        return isSetToCFExam;
    }

    public void setSetToCFExam(Boolean setToCFExam) {
        isSetToCFExam = setToCFExam;
    }
    private Boolean isSetToCFExam;
    private CFExamProfileService cfExamProfileService;
    private Long toLanguageID;
    private CFExamProfilePointCross cfExamProfilePointCross;
    private CFExamWordQuestionnaireCross cfExamWordQuestionnaireCross;
    private Application application;
    private CFExamWordQuestionnaireService cfExamWordQuestionnaireService;
    private CFExamProfilePointService cfExamProfilePointService;
    private CFProfileSpinAdapter cfProfileSpinAdapter;
    private C context;
    private Dialog myDialog;

    public void handlerSetCFExamClick(Word selectedItem) {

        //this.myDialog = new Dialog(application.getApplicationContext());
        myDialog.setContentView(R.layout.dialog_set_cfexam);
        DisplayMetrics metrics = application.getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

        Button btn_Submit = (Button) myDialog.findViewById(R.id.btn_dialog_newItem);
        Button btn_Cancel = (Button) myDialog.findViewById(R.id.btn_dialog_cancel);
        TextView tv_msg = (TextView) myDialog.findViewById(R.id.tv_setCFExamProfile_msg);
        Spinner spn_cfExamProfile = (Spinner) myDialog.findViewById(R.id.spn_cfExamProfile);



        DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<Boolean>() {
            @Override
            public Boolean doInBackground() {
                cfExamWordQuestionnaireCross = cfExamWordQuestionnaireService.findByWordID(selectedItem.getWordID(), toLanguageID);
                if(cfExamWordQuestionnaireCross!=null) {
                    cfExamProfilePointCross = cfExamProfilePointService.findCrossByID(cfExamWordQuestionnaireCross.getCfExamQuestionnaire().getCurrentCFExamProfilePointID());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPostExecute(Boolean item) {
                setSetToCFExam(item);

                if (getSetToCFExam()) {
                    DateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy HH:mm:ss");
                    String formattedEntryPointDate = dateFormat.format(cfExamWordQuestionnaireCross.getCfExamQuestionnaire().getEntryPointDateTime());
                    String sourceString="This word is set to CF Exam. Details: " +
                            "<br>To Language: <i><u>" + cfExamWordQuestionnaireCross.getLanguage().getLanguageName()+ "</u></i>" +
                            "<br>Profile Name: <i><u>" + cfExamProfilePointCross.getCfExamProfile().getName()+ "</u></i>" +
                            "<br>Point Name: <i><u>" + cfExamProfilePointCross.getCfExamProfilePoint().getLabelText()  + "</u></i>" +
                            "<br>Entry Date Point: <i><u>" + formattedEntryPointDate + "</u></i>" +
                            "<br><br><b>Do you want to UnSet?</b>";
                    tv_msg.setText(Html.fromHtml(sourceString));
                    spn_cfExamProfile.setVisibility(View.INVISIBLE);
                    btn_Submit.setText("UnSet");
                } else {
                    tv_msg.setText("Please choose CF Exam Profile.");
                    spn_cfExamProfile.setVisibility(View.VISIBLE);
                    btn_Submit.setText("Set");

                    DbExecutorImp<List<CFExamProfile>> dbExecutorCFExamProfile = FactoryUtil.<List<CFExamProfile>>createDbExecutor();
                    dbExecutorCFExamProfile.execute_(new DbExecutor<List<CFExamProfile>>() {
                        @Override
                        public List<CFExamProfile> doInBackground() {
                            return cfExamProfileService.findAllOrderAlphabetic(Session.getLongAttribute(application.getApplicationContext(), SessionNameAttribute.ProfileID,-1L),"");
                        }

                        @Override
                        public void onPostExecute(List<CFExamProfile> item) {
                            CFExamProfile cfExamProfileSelect = new CFExamProfile();
                            cfExamProfileSelect.setProfileID(-1L);
                            cfExamProfileSelect.setCFExamProfileID(-1L);
                            cfExamProfileSelect.setName("...");

                            item.add(0,cfExamProfileSelect);

                            cfProfileSpinAdapter = new CFProfileSpinAdapter(application.getApplicationContext(),
                                    //android.R.layout.simple_spinner_item,
                                    R.layout.spinner_item,
                                    item);

                            spn_cfExamProfile.setAdapter(cfProfileSpinAdapter);


                        }
                    });




                }

            }
        });








        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DbExecutorImp<Boolean> dbExecutorCFExamProfile = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutorCFExamProfile.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        if (getSetToCFExam()) {

                            Integer delete = cfExamWordQuestionnaireService.delete(cfExamWordQuestionnaireCross.getCfExamQuestionnaire());
                            if (delete>0) {
                                return true;
                            } else {
                                return false;
                            }

                        } else {
                            int selectedItemPosition = spn_cfExamProfile.getSelectedItemPosition();
                            CFExamProfile selectedCFExamProfile = cfProfileSpinAdapter.getItem(selectedItemPosition);


                            boolean isCreated = cfExamWordQuestionnaireService.create(selectedItem.getWordID(), toLanguageID, selectedCFExamProfile);
                            if (isCreated){
                                return true;
                            } else {
                                return false;
                            }
                        }

                    }

                    @Override
                    public void onPostExecute(Boolean item) {
                        myDialog.dismiss();

                        if (item) {
                            Toast.makeText(application.getApplicationContext(), "Operation is Successfully", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(application.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                        }

                        context.getItems();


                    }
                });




            }
        });


        myDialog.show();


    }

}
