package nz.co.redice.newsfeeder.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import nz.co.redice.newsfeeder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mMainBinding.getRoot());
    }
}
