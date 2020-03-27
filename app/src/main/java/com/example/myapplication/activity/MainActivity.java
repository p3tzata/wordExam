package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.seed.Seed;
import com.example.myapplication.utitliy.MenuUtility;
import com.example.myapplication.utitliy.Session;
import com.example.myapplication.utitliy.SessionNameAttribute;

public class MainActivity extends AppCompatActivity {


    private Menu mOptionsMenu;

    ListView mainListMenu;
    TextView menuListItem;
    String[] mainListMenuOptions = new String[]{"Dictionaries","Item1"};
    Class<?>[] mainListMenuOptionsNavigate = new Class[]{ListAllDictionary.class,ListAllDictionary.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Main");
        mainListMenu=(ListView)findViewById(R.id.mainListMenu);
        menuListItem=(TextView)findViewById(R.id.menuListItem);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mainListMenuOptions);
        mainListMenu.setAdapter(adapter);

        mainListMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                /*
                String value=adapter.getItem(position);
                Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                */
                Session.setLongAttribute(MainActivity.this, SessionNameAttribute.ProfileID,1L);

                long profileID = Session.getLongAttribute(MainActivity.this, SessionNameAttribute.ProfileID, 0);

                if (profileID==0) {
                    Toast.makeText(getApplicationContext(),"Please select Profile",Toast.LENGTH_SHORT).show();
                } else {

                    Intent activity2Intent = new Intent(getApplicationContext(), mainListMenuOptionsNavigate[position]);
                    startActivity(activity2Intent);
                }
            }
        });


        Seed seed = new Seed(this.getApplication());
        //seed.seedDB();


    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.mOptionsMenu=menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuUtility.checkIsEditMode(this,mOptionsMenu);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       return MenuUtility.onOptionsItemSelected(this,item);
    }





}
