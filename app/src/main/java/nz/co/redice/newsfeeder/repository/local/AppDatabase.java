package nz.co.redice.newsfeeder.repository.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import nz.co.redice.newsfeeder.repository.local.dao.Entry;
import nz.co.redice.newsfeeder.repository.local.dao.EntryDao;


@Database(entities = {Entry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EntryDao mEntryDao();

    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "appDatabase")
                    .build();
        }
        return instance;
    }

}
