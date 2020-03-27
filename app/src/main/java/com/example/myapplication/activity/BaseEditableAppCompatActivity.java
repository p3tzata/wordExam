package com.example.myapplication.activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecycleAdapter;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.NameableCrudService;
import com.example.myapplication.utitliy.DbExecutor;
import com.example.myapplication.utitliy.DbExecutorImp;

public abstract class BaseEditableAppCompatActivity<T,
        S extends NameableCrudService<T>,
        C extends BaseEditableAppCompatActivity,
        A extends BaseRecycleAdapter>
 //       extends AppCompatActivity implements EditableAppCompatActivity<T> {
        extends BaseListableAppCompatActivity<T,S,C,A> implements EditableAppCompatActivity<T> {
/*
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

    }
*/


    public void callShowCrudMenu(View v, T selectedItem) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.inflate(R.menu.popup_crud_menu_update_delete);
        //adding click listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //I selectedItem = mItems.get(getAdapterPosition());
                switch (item.getItemId()) {
                    case R.id.menu_delete:

                        getContext().handlerDeleteClick(selectedItem);

                        break;
                    case R.id.menu_update:
                        getContext().handlerCreateUpdateClick(true,selectedItem);

                }
                return false;
            }
        });

        popupMenu.show();

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

    protected void deleteItem(T item) {

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
