package com.example.myapplication.activity.configureActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;

public class ConfigureMenuActivity extends AppCompatActivity {

    ListView mainListMenu;
    TextView menuListItem;
    String[] mainListMenuOptions = new String[]{"Profile",
            "Language",
            "Translation"};
    Class<?>[] mainListMenuOptionsNavigate = new Class[]{ConfigProfileActivity.class,
            ConfigLanguageActivity.class,
            ConfigTranslationActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_menu);
        getSupportActionBar().setTitle("Configure Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainListMenu = (ListView) findViewById(R.id.configureListMenu);
        menuListItem = (TextView) findViewById(R.id.menuListItem);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mainListMenuOptions);
        mainListMenu.setAdapter(adapter);
        mainListMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent activity2Intent = new Intent(getApplicationContext(), mainListMenuOptionsNavigate[position]);
                startActivity(activity2Intent);

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

}
