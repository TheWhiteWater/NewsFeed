package nz.co.redice.newsfeeder.repository.local.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM Entry")
    LiveData<List<Entry>> getAllEntries();

    @Query("SELECT * FROM Entry where category = :category")
    LiveData<List<Entry>> getAllEntries(String category);

    @Query("SELECT * FROM Entry where uuid = :uuid")
    LiveData<Entry> getEntry(int uuid);

    @Query("SELECT * FROM Entry where category = :category")
    Single<Entry> getEntryByCategory(String category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntry(Entry entry);

    @Query("DELETE FROM Entry")
    void deleteAllEntries();





}
