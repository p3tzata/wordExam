package com.example.myapplication.activity.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecycleAdapter;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.base.NameableCrudService;
import com.example.myapplication.utitliy.DbExecutor;
import com.example.myapplication.utitliy.DbExecutorImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseListableAppCompatActivity<T,
        S,
        C extends BaseListableAppCompatActivity,
        A extends BaseRecycleAdapter>
        extends AppCompatActivity implements ListableAppCompatActivity<T> {

    private S itemService;
    private C context;
    private A adapter;
    public Dialog myDialog;
    public SearchView searchView;
    private GetItemsExecutorBlock<T> getItemsExecutor;

    protected Map<Integer,onMenuItemClickHandlerExecutor> mappingMenuItemHandler;

    public GetItemsExecutorBlock<T> getGetItemsExecutor() {
        return getItemsExecutor;
    }

    public void setGetItemsExecutor(GetItemsExecutorBlock<T> getItemsExecutor) {
        this.getItemsExecutor = getItemsExecutor;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mappingMenuItemHandler=new HashMap<>();
        onCreateCustom();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_word, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search_word);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onSearchBarGetItemsExecutorHandler(newText);

                getItems();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    public void setItemService(S itemService) {
        this.itemService = itemService;
    }

    public void setContext(C context) {
        this.context = context;
    }

    public void setAdapter(A adapter) {
        this.adapter = adapter;
    }

    public C getContext() {
        return context;
    }

    public S getItemService() {
        return itemService;
    }

    public A getAdapter() {
        return adapter;
    }

    protected void getItems() {

        DbExecutorImp<List<T>> dbExecutor = FactoryUtil.<List<T>>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<List<T>>() {
            @Override
            public List<T> doInBackground() {

                List<T> execute = getItemsExecutor.execute();
                return execute;
            }

            @Override
            public void onPostExecute(List<T> item) {
                adapter.setItems(item);
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }
        });



    }


    public Map<Integer, onMenuItemClickHandlerExecutor> getMappingMenuItemHandler() {
        return mappingMenuItemHandler;
    }

    public void setMappingMenuItemHandler(Map<Integer, onMenuItemClickHandlerExecutor> mappingMenuItemHandler) {
        this.mappingMenuItemHandler = mappingMenuItemHandler;
    }
}
