package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecycleAdapter;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.NameableCrudService;
import com.example.myapplication.utitliy.DbExecutor;
import com.example.myapplication.utitliy.DbExecutorImp;

import java.util.List;

public abstract class BaseListableAppCompatActivity<T,
        S extends NameableCrudService<T>,
        C extends BaseListableAppCompatActivity,
        A extends BaseRecycleAdapter>
        extends AppCompatActivity implements ListableAppCompatActivity<T> {

    private S itemService;
    private C context;
    private A adapter;

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

    public List<T> getListOfItems(){
        List<T> foundItems = itemService.findAllOrderAlphabetic();
        return foundItems;
    }

    protected void getItems() {

        DbExecutorImp<List<T>> dbExecutor = FactoryUtil.<List<T>>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<List<T>>() {
            @Override
            public List<T> doInBackground() {
                return getListOfItems();
            }

            @Override
            public void onPostExecute(List<T> item) {
                adapter.setItems(item);
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }
        });

        /*
        class GetTasks extends AsyncTask<Void, Void, List<T>> {

            @Override
            protected List<T> doInBackground(Void... voids) {
              return getListOfItems();
            }

            @Override
            protected void onPostExecute(List<T> items) {
                super.onPostExecute(items);



                adapter.setItems(items);
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
        */

    }


}
