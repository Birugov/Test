package ru.techcrat.test.di.modules

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.techcrat.test.di.annotations.ViewModelKey
import ru.techcrat.test.viewmodels.AreasFragmentViewModel
import ru.techcrat.test.viewmodels.DialogViewModel
import ru.techcrat.test.viewmodels.TestFragmentViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DialogViewModel::class)
    abstract fun bindDialogViewModel(rateFragmentViewModel: DialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AreasFragmentViewModel::class)
    abstract fun bindAreaViewModule(areasFragmentViewModel: AreasFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TestFragmentViewModel::class)
    abstract fun bindTestViewModule(testFragmentViewModel: TestFragmentViewModel): ViewModel

}