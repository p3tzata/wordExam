package com.example.myapplication.activity;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseEditableAdapter;
import com.example.myapplication.service.BaseNameCrudService;
import com.example.myapplication.service.NameableCrudService;

import java.util.List;

public abstract class BaseEditableAppCompatActivity<T,
        S extends NameableCrudService<T>,
        C extends BaseEditableAppCompatActivity,
        A extends BaseEditableAdapter>
        extends AppCompatActivity implements EditableAppCompatActivity<T> {

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
    }

    public void updateItem(T item) {
        class GetTasks extends AsyncTask<Void, Void, Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {
                Integer updateCount = itemService.update(item);
                return updateCount;
            }

            @Override
            protected void onPostExecute(Integer updatedCount) {
                super.onPostExecute(updatedCount);
                if (updatedCount==1) {
                    Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                }
                getItems();

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    protected void deleteItem(T item) {
        class GetTasks extends AsyncTask<Void, Void, Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {
                Integer deleteCount = itemService.delete(item);
                return deleteCount;
            }

            @Override
            protected void onPostExecute(Integer deleteCount) {
                if (deleteCount>0) {
                    Toast.makeText(context, "Successfully delete", Toast.LENGTH_SHORT).show();
                }
                getItems();
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    public void createItem(T item) {
        class GetTasks extends AsyncTask<Void, Void, Long> {

            @Override
            protected Long doInBackground(Void... voids) {
                Long insertedID = itemService.insert(item);
                return insertedID;
            }

            @Override
            protected void onPostExecute(Long insertedID) {
                super.onPostExecute(insertedID);
                if (insertedID>0L) {
                    Toast.makeText(context, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                }
                getItems();

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

}
