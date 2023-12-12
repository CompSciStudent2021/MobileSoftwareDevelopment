package com.example.assignment;

import android.os.AsyncTask;

public class InsertAsyncTask extends AsyncTask<Treasure, Void, Void> {
    private DatabaseDao databaseDao;

    public InsertAsyncTask(DatabaseDao dao) {
        this.databaseDao = dao;
    }

    @Override
    protected Void doInBackground(Treasure... treasures) {
        for (Treasure treasure : treasures) {
            databaseDao.insert(treasure); // Perform insertion on a background thread for each treasure
        }
        return null;
    }
}
