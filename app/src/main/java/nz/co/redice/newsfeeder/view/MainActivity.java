package nz.co.redice.newsfeeder.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import nz.co.redice.newsfeeder.databinding.ActivityMainBinding;
import nz.co.redice.newsfeeder.repository.Repository;
import nz.co.redice.newsfeeder.repository.di.base.MyApplication;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mMainBinding;
    @Inject Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mMainBinding.getRoot());

        mRepository = MyApplication.getAppComponent(getApplicationContext()).getRepository();
        mRepository.clearDatabase();
    }
}
