package com.example.WordCFExam.activity.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.WordCFExam.R;
import com.example.WordCFExam.adapter.BaseRecycleAdapterNonFaced;
import com.example.WordCFExam.entity.TextLabelable;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.base.CrudService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;

public abstract class BaseEditableAppCompatActivityNonFaced<
        T extends TextLabelable,
        S extends CrudService<T>,
        C extends BaseEditableAppCompatActivityNonFaced,
        A extends BaseRecycleAdapterNonFaced>
        extends BaseListableAppCompatActivityNonFaced<T,S,C,A> implements EditableAppCompatActivity<T> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callShowNewItemButton(findViewById(R.id.fab_newItem));
    }

    public void callShowNewItemButton(FloatingActionButton fab_newItem) {
        fab_newItem.setOnClickListener(new View.OnClickListener()   {
        @Override
        public void onClick (View view){
        handlerCreateUpdateClick(false, null);
        }
    });
    }

    public void callShowCrudMenu(int popupMenuID,View v, T selectedItem) {
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

    public void callShowCrudMenu(View v, T selectedItem) {
        callShowCrudMenu(R.menu.popup_crud_menu_update_delete,v,selectedItem);
    }


    @Override
    public void onMenuItemClickHandlerMappingConfig(Map<Integer,onMenuItemClickHandlerExecutor> mapping,MenuItem item,T selectedItem){

        mapping.put( R.id.menu_update,new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                getContext().handlerCreateUpdateClick(true,selectedItem);
            }
        });

        mapping.put(R.id.menu_delete, new onMenuItemClickHandlerExecutor() {
            @Override
            public void execute() {
                getContext().handlerDeleteClick(selectedItem);
            };
        });


    }





    public void handlerDeleteClick(T selectedItem) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(selectedItem.getLabelText());


        builder
                //.setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        deleteItem(selectedItem);


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        alert.setTitle("Are you sure for deleting");
        alert.show();

    }


    public void updateItem(T item) {

        DbExecutorImp<Integer> dbExecutor = FactoryUtil.<Integer>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<Integer>() {
            @Override
            public Integer doInBackground() {
                Integer updateCount = getItemService().update(item);
                return updateCount;
            }

            @Override
            public void onPostExecute(Integer item) {
                if (item==1) {
                    Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                }
                getItems();
            }
        });


    }

    public void deleteItem(T item) {

        DbExecutorImp<Integer> dbExecutor = FactoryUtil.<Integer>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<Integer>() {
            @Override
            public Integer doInBackground() {
                Integer deleteCount=0;
                String errorMsg=null;
                try {
                    deleteCount = getItemService().delete(item);
                } catch (Exception e){
                  errorMsg=e.getMessage();
                }


                return deleteCount;
            }

            @Override
            public void onPostExecute(Integer item) {
                if (item>0) {
                    Toast.makeText(getContext(), "Successfully delete", Toast.LENGTH_SHORT).show();
                } else {

                        Toast.makeText(getContext(), "Something went wrong: ", Toast.LENGTH_SHORT).show();
                 }
                getItems();
            }
        });

    }

    public void createItem(T item) {

        DbExecutorImp<Long> dbExecutor = FactoryUtil.<Long>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<Long>() {
            @Override
            public Long doInBackground() {
                Long insertedID = getItemService().insert(item);
                return insertedID;
            }

            @Override
            public void onPostExecute(Long item) {
                if (item>0L) {
                    Toast.makeText(getContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show();
                }
                getItems();
            }
        });

       }

}
