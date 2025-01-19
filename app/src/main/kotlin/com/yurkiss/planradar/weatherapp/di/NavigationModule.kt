package com.yurkiss.planradar.weatherapp.di

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.yurkiss.planradar.weatherapp.MainActivity
import com.yurkiss.planradar.weatherapp.R
import com.yurkiss.planradar.weatherapp.cities.presentation.FavoriteCitiesNavigation
import com.yurkiss.planradar.weatherapp.navigation.FavoriteCitiesNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {

    @Provides
    @ActivityScoped
    fun provideNavigationController(@ActivityContext context: Context): NavController =
        (context as MainActivity).findNavController(R.id.nav_host_fragment_content_main)

}


@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModuleBinds {

    @Binds
    @ActivityScoped
    abstract fun favoriteCitiesNavigator(repo: FavoriteCitiesNavigationImpl): FavoriteCitiesNavigation

}
