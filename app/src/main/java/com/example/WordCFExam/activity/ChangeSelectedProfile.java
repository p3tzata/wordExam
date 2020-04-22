package com.example.WordCFExam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.adapter.spinnerAdapter.ProfileSpinAdapter;
import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.ProfileService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.List;

public class ChangeSelectedProfile extends AppCompatActivity {

    private Long currentProfileID;
    private ProfileService profileService;
    private ProfileSpinAdapter profileSpinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_selected_profile);
        currentProfileID = Session.getLongAttribute(this, SessionNameAttribute.ProfileID,-1L);
        profileService = FactoryUtil.createProfileService(getApplication());
        getSupportActionBar().setTitle("Select Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAllProfile();
        findViewById(R.id.btn_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Spinner spn_item = (Spinner) findViewById(R.id.spn_profile);
                int selectedItemPosition = spn_item.getSelectedItemPosition();
                Profile changeToProfile = profileSpinAdapter.getItem(selectedItemPosition);


                if (changeToProfile.getProfileID().equals(-1L)) {
                    Toast.makeText(getApplicationContext(), "Please select Profile", Toast.LENGTH_LONG).show();
                } else {
                    Session.setLongAttribute(getApplicationContext(),SessionNameAttribute.ProfileID,changeToProfile.getProfileID());
                    Session.setStringAttribute(getApplicationContext(),SessionNameAttribute.ProfileName,changeToProfile.getProfileName());
                    Toast.makeText(getApplicationContext(), "Profile changed to: " + changeToProfile.getProfileName(), Toast.LENGTH_LONG).show();
                    finish();
                }

                String debug=null;
            }
        });



    }

    private void getAllProfile() {

        DbExecutorImp<List<Profile>> profileDbExecutor = FactoryUtil.<List<Profile>>createDbExecutor();

        profileDbExecutor.execute_(new DbExecutor<List<Profile>>() {
            @Override
            public List<Profile> doInBackground() {
                return profileService.findAllOrderAlphabetic(0L,"");
            }

            @Override
            public void onPostExecute(List<Profile> items) {

                Profile blankProfile = new Profile();

                blankProfile.setProfileID(-1L);
                blankProfile.setProfileName("...");

                items.add(0,blankProfile);


                profileSpinAdapter = new ProfileSpinAdapter(ChangeSelectedProfile.this,
                        //android.R.layout.simple_spinner_item,
                        R.layout.spinner_item,
                        items);
                Spinner spn_item = (Spinner) findViewById(R.id.spn_profile);
                spn_item.setAdapter(profileSpinAdapter); // Set the custom adapter to the spinner
                // You can create an anonymous listener to handle the event when is selected an spinner item
                int spinnerPosition = 0;
                if (currentProfileID != -1L) {
                    for (int i=0;i<items.size();i++) {
                        if (items.get(i).getProfileID().equals(currentProfileID)) {
                            spinnerPosition=i;
                            break;
                        }
                    }

                    spn_item.setSelection(spinnerPosition);
                }


            }


        });


    }
}
