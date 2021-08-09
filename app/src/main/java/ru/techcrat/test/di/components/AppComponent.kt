package ru.techcrat.test.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.techcrat.test.BaseApplication
import ru.techcrat.test.di.modules.ViewModelFactoryModule
import ru.techcrat.test.di.modules.ViewModelModule
import ru.techcrat.test.ui.AreasListFragment
import ru.techcrat.test.ui.NameDialogFragment
import ru.techcrat.test.ui.TestFragment
import ru.techcrat.test.viewmodels.DialogViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: BaseApplication?) {
    }

    fun inject(viewModel:DialogViewModel)

    fun inject(fragment:NameDialogFragment)

    fun inject(fragment: AreasListFragment)

    fun inject(fragment: TestFragment)

}
