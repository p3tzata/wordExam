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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.seed.Seed;
import com.example.myapplication.service.WordOldService;
import com.example.myapplication.utitliy.MenuUtility;
import com.example.myapplication.utitliy.Session;
import com.example.myapplication.utitliy.SessionNameAttribute;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private WordOldService wordOldService;
    private Menu mOptionsMenu;

    ListView mainListMenu;
    TextView menuListItem;
    String[] mainListMenuOptions = new String[]{"Dictionaries","Item1"};
    Class<?>[] mainListMenuOptionsNavigate = new Class[]{ListAllDictionary.class,ListAllDictionary.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wordOldService = new ViewModelProvider(this).get(WordOldService.class);
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


        Seed seed = new Seed();
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

    private void deleteAllWords() {
        class deleteAll extends AsyncTask<Void, Void, Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {
                 return wordOldService.deleteAll();

            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                if (result>0) {
                    Toast.makeText(getApplicationContext(), result + " Words deleted.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Words can not be deleted...", Toast.LENGTH_LONG).show();
                }
            }
        }

        deleteAll gt = new deleteAll();
        gt.execute();
    }






}
