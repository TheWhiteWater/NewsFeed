package nz.co.redice.newsfeeder.repository;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.R;
import nz.co.redice.newsfeeder.repository.local.AppDatabase;

public class Repository {

    private static Repository instance;
    private Context mContext;
    private final AppDatabase db;


    private Repository(Application application) {
        mContext = application;
        db = Room
                .databaseBuilder(mContext, AppDatabase.class,
                        application.getString(R.string.database_name))
                .build();
    }

    public static Repository getInstance(Application application) {
        if (instance == null)
            instance = new Repository(application);
        return instance;
    }

    public void clearDatabase() {
        Completable.fromAction(db.mEntryDao()::deleteAllEntries)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
