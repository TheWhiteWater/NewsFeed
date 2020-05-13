package nz.co.redice.newsfeeder.repository.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nz.co.redice.newsfeeder.repository.local.AppDatabase;
import nz.co.redice.newsfeeder.repository.local.dao.EntryDao;
import nz.co.redice.newsfeeder.repository.utils.Constants;

@Module
public class DatabaseModule {

    private final Context mContext;


    public DatabaseModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    AppDatabase provideDatabase() {
        return Room.databaseBuilder(mContext, AppDatabase.class, Constants.DATABASE)
                .fallbackToDestructiveMigration()
                .build();
    }


    @Singleton
    @Provides
    EntryDao provideDao(AppDatabase database) {
        return  database.getDao();
    }
}
