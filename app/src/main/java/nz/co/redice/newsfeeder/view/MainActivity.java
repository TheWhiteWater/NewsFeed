package nz.co.redice.newsfeeder.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import nz.co.redice.newsfeeder.databinding.ActivityMainBinding;
import nz.co.redice.newsfeeder.repository.local.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mMainBinding;
    private DatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mMainBinding.getRoot());

        mDbHelper = new DatabaseHelper(this);
        mDbHelper.clearDatabase();
    }
}
