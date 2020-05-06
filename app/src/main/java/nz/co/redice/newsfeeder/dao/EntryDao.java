package nz.co.redice.newsfeeder.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM Entry")
    Observable<List<Entry>> getAllEntries();

    @Query("SELECT * FROM Entry where uuid = :uuid")
    Single<Entry> getEntry(int uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntry(Entry entry);

    @Query("DELETE FROM Entry")
    void deleteAllEntries();



}
