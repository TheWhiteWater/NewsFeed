package nz.co.redice.newsfeeder.repository.di;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import nz.co.redice.newsfeeder.viewmodel.DetailViewModel;
import nz.co.redice.newsfeeder.viewmodel.ListViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel viewModel);
}
