package nz.co.redice.newsfeeder.repository.di.base;

import android.app.Application;
import android.content.Context;

import nz.co.redice.newsfeeder.repository.di.DatabaseModule;
import nz.co.redice.newsfeeder.repository.di.NetworkModule;


public class MyApplication extends Application {

    private AppComponent mComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerAppComponent.builder()
                .databaseModule(new DatabaseModule(this))
                .networkModule(new NetworkModule())
                .build();

    }


    public static AppComponent getAppComponent(Context context) {

        return ((MyApplication) context.getApplicationContext()).mComponent;
    }
}
