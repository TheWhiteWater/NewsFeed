package nz.co.redice.newsfeeder.di.base;


import javax.inject.Singleton;

import dagger.Component;
import nz.co.redice.newsfeeder.repository.Repository;
import nz.co.redice.newsfeeder.di.modules.DatabaseModule;
import nz.co.redice.newsfeeder.di.modules.NetworkModule;
import nz.co.redice.newsfeeder.di.modules.ViewModelModule;
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
