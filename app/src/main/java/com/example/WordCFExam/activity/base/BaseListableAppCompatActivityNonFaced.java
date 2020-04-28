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
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WordCFExam.R;
import com.example.WordCFExam.adapter.BaseRecycleAdapterNonFaced;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseListableAppCompatActivityNonFaced<
        T,
        S,
        C extends BaseListableAppCompatActivityNonFaced,
        A extends BaseRecycleAdapterNonFaced>
        extends BaseListableAppCompatActivity<T,S,C> implements ListableAppCompatActivity<T> {

    private A adapter;
    private GetItemsExecutorBlock<T> getItemsExecutor;


    public GetItemsExecutorBlock<T> getGetItemsExecutor() {
        return getItemsExecutor;
    }

    public void setGetItemsExecutor(GetItemsExecutorBlock<T> getItemsExecutor) {
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








}
