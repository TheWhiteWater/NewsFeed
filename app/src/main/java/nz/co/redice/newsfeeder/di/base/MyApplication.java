package nz.co.redice.newsfeeder.di.base;

import android.app.Application;
import android.content.Context;

import nz.co.redice.newsfeeder.di.modules.DatabaseModule;
import nz.co.redice.newsfeeder.di.modules.NetworkModule;


public class MyApplication extends Application {

    private AppComponent mComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerAppComponent.builder()
                .databaseModule(new DatabaseModule(this))
                .build();

    }


    public static AppComponent getAppComponent(Context context) {

        return ((MyApplication) context.getApplicationContext()).mComponent;
    }
}
