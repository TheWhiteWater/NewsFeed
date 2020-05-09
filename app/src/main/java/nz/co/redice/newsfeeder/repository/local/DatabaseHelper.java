package nz.co.redice.newsfeeder.repository.local;

import android.content.Context;

import androidx.room.Room;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class DatabaseHelper {

    private Context mContext;
    private final AppDatabase db;

    public DatabaseHelper(Context context) {
        mContext = context;
        db = Room.databaseBuilder(mContext, AppDatabase.class, "news_api.db").build();
    }


    public void clearDatabase() {
        Completable.fromAction(db.mEntryDao()::deleteAllEntries)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}
