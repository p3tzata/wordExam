package com.example.myapplication.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.ListAllWordActivity;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.TranslationAndLanguages;

public class UpdateWordMenuActivity extends AppCompatActivity {

    ListView mainListMenu;
    TextView menuListItem;
    String[] mainListMenuOptions = new String[]{"Basics", "Translation"};
    Class<?>[] mainListMenuOptionsNavigate = new Class[]{UpdateWordBasicActivity.class, UpdateWordTranslationActivity.class};
    Word word;
    private TranslationAndLanguages translationAndLanguages;
    private Long fromLanguageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_word_menu);

        this.word = (Word) getIntent().getSerializableExtra("word");
        this.translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        this.fromLanguageID = (Long) getIntent().getSerializableExtra("translationFromLanguageID");
        getSupportActionBar().setTitle(word.getWordString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(getApplicationContext(), word.getWordString(), Toast.LENGTH_LONG).show();

        mainListMenu = (ListView) findViewById(R.id.updateWordListMenu);
        menuListItem = (TextView) findViewById(R.id.menuListItem);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mainListMenuOptions);
        mainListMenu.setAdapter(adapter);

        mainListMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent activity2Intent = new Intent(getApplicationContext(), mainListMenuOptionsNavigate[position]);

                activity2Intent.putExtra("translationAndLanguages", UpdateWordMenuActivity.this.translationAndLanguages);
                activity2Intent.putExtra("translationFromLanguageID", UpdateWordMenuActivity.this.fromLanguageID);
                activity2Intent.putExtra("word", UpdateWordMenuActivity.this.word);
                startActivity(activity2Intent);

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, ListAllWordActivity.class);
                intent.putExtra("translationAndLanguages", UpdateWordMenuActivity.this.translationAndLanguages);
                intent.putExtra("translationFromLanguageID", UpdateWordMenuActivity.this.fromLanguageID);
                startActivity(intent);
                return true;

        }
        return true;
    }






}
