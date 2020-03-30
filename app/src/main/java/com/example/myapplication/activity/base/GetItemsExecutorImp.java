package com.example.myapplication.activity.base;

import java.util.List;

public class GetItemsExecutorImp<T> implements GetItemsExecutor<T>  {

    GetItemsExecutorBlock<T> getItemsExecutorBlock;

    public GetItemsExecutorImp(GetItemsExecutorBlock<T> getItemsExecutorBlock) {
           this.getItemsExecutorBlock=getItemsExecutorBlock;
    }


    public List<T> trigger(){
      return this.getItemsExecutorBlock.execute();
    }


}
