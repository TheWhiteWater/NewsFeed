package nz.co.redice.newsfeeder.repository.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import nz.co.redice.newsfeeder.repository.local.dao.Entry;
import nz.co.redice.newsfeeder.repository.local.dao.EntryDao;


@Database(entities = {Entry.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EntryDao mEntryDao();

}