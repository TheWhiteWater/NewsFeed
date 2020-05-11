package nz.co.redice.newsfeeder.repository.local;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import nz.co.redice.newsfeeder.repository.local.dao.Entry;
import nz.co.redice.newsfeeder.repository.local.dao.EntryDao;
import nz.co.redice.newsfeeder.repository.utils.Constants;


@Database(entities = {Entry.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract EntryDao mEntryDao();


    public static AppDatabase getInstance (Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application, AppDatabase.class, Constants.DATABASE)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}