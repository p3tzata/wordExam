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

import com.example.WordCFExam.R;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseListableAppCompatActivity <
        T,
        S,
        C extends BaseListableAppCompatActivity> extends AppCompatActivity  implements ListableAppCompatActivity<T> {

    protected S itemService;
    protected C context;
    protected Dialog myDialog;
    protected Map<Integer,onMenuItemClickHandlerExecutor> mappingMenuItemHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mappingMenuItemHandler=new HashMap<>();
        onCreateCustom();
    }

    @Override
    public void onResume(){
        super.onResume();
        getItems();

    }

    public void onMenuItemClickHandlerMappingConfig(Map<Integer,onMenuItemClickHandlerExecutor> mapping, MenuItem item, T selectedItem){

    }

    public void handlerViewClick(T selectedItem) {


    }

    public boolean onMenuItemClickHandler(MenuItem item){

        mappingMenuItemHandler.get(item.getItemId()).execute();
        return false;
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

    public C getContext() {
        return context;
    }

    public S getItemService() {
        return itemService;
    }

    public Map<Integer, onMenuItemClickHandlerExecutor> getMappingMenuItemHandler() {
        return mappingMenuItemHandler;
    }

    public void setMappingMenuItemHandler(Map<Integer, onMenuItemClickHandlerExecutor> mappingMenuItemHandler) {
        this.mappingMenuItemHandler = mappingMenuItemHandler;
    }

    public void callShowViewMenu(int popupMenuID, View v, T selectedItem) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        //popupMenu.inflate(R.menu.popup_crud_menu_update_delete);
        popupMenu.inflate(popupMenuID);

        //adding click listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMenuItemClickHandlerMappingConfig(mappingMenuItemHandler,item,selectedItem);
                //I selectedItem = mItems.get(getAdapterPosition());
                return onMenuItemClickHandler(item);

            }
        });

        popupMenu.show();

    }


    public void callShowViewMenu(View v, T selectedItem) {
        callShowViewMenu(R.menu.popup_view_menu_custom,v,selectedItem);
    }



}
