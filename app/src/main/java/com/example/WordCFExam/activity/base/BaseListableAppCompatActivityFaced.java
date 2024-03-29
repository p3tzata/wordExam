package com.example.WordCFExam.activity.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WordCFExam.R;
import com.example.WordCFExam.adapter.BaseRecycleAdapterFaced;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseListableAppCompatActivityFaced<
        V,
        T,
        S,
        C extends BaseListableAppCompatActivityFaced,
        A extends BaseRecycleAdapterFaced>
        extends BaseListableAppCompatActivity<T,S,C> implements ListableAppCompatActivity<T> {


    private A adapter;
    public Dialog myDialog;
    private GetItemsExecutorBlock<V> getItemsExecutor;

    public GetItemsExecutorBlock<V> getGetItemsExecutor() {
            return getItemsExecutor;
        }

    public void setGetItemsExecutor(GetItemsExecutorBlock<V> getItemsExecutor) {
            this.getItemsExecutor = getItemsExecutor;
        }


    public void setAdapter(A adapter) {
        this.adapter = adapter;
    }



    public A getAdapter() {
        return adapter;
    }

    @Override
    public void getItems() {

        DbExecutorImp<List<V>> dbExecutor = FactoryUtil.<List<V>>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<List<V>>() {
            @Override
            public List<V> doInBackground() {

                List<V> execute = getItemsExecutor.execute();
                return execute;
            }

            @Override
            public void onPostExecute(List<V> item) {
                adapter.setItems(item);
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }
        });



    }







}
