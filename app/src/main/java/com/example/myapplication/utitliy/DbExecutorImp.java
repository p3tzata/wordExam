package com.example.myapplication.utitliy;

import android.os.AsyncTask;


public  class DbExecutorImp<T> {

    private DbExecutor<T> dbExecutor;
    public DbExecutorImp() {

    }

    public void execute_(DbExecutor<T> dbExecutor) {
        this.dbExecutor=dbExecutor;
        trigger();
    }


    private void trigger() {


    class GetTasks extends AsyncTask<Void, Void, T> {

        @Override
        protected T doInBackground(Void... voids) {

            return DbExecutorImp.this.dbExecutor.doInBackground();

        }

        @Override
        protected void onPostExecute(T item) {
            super.onPostExecute(item);

            DbExecutorImp.this.dbExecutor.onPostExecute(item);

        }
    }

    GetTasks gt = new GetTasks();
    gt.execute();

}


}
