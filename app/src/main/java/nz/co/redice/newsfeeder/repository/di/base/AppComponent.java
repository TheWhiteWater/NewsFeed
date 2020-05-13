package nz.co.redice.newsfeeder.repository.di.base;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import nz.co.redice.newsfeeder.repository.Repository;
import nz.co.redice.newsfeeder.repository.di.DatabaseModule;
import nz.co.redice.newsfeeder.repository.di.NetworkModule;
import nz.co.redice.newsfeeder.repository.di.ViewModelModule;
import nz.co.redice.newsfeeder.view.DetailFragment;
import nz.co.redice.newsfeeder.view.presentation.ListFragment;

@Singleton
@Component
        (modules = {
        NetworkModule.class,
        ViewModelModule.class,
        DatabaseModule.class,
})
public interface AppComponent {

    Repository getRepository();


    void inject(ListFragment listFragment);

    void inject(DetailFragment detailFragment);
}
