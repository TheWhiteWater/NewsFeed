package nz.co.redice.newsfeeder.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM Entry")
    Observable<List<Entry>> getAllEntries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntry(Entry entry);


}
