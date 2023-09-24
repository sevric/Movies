package com.severin.movies.di

import android.app.Application
import com.severin.movies.presentation.ui.*
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: FavouritesFragment)
    fun inject(fragment: GenresFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: MoviesByUserSearchQueryFragment)
    fun inject(fragment: MainNavigationFragment)
    fun inject(fragment: MovieFragment)
    fun inject(fragment: MoviesByGenreFragment)
    fun inject(fragment: LoginFragment)//TODO(check if you can use inheritance from Fragment() instead of any special fragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: ResetPasswordFragment)
    fun inject(fragment: PersonalPageFragment)
    fun inject(movieBottomSheetFragment: MovieBottomSheetFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}