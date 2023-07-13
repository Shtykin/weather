package ru.shtykin.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.shtykin.weatherapp.domain.Repository
import ru.shtykin.weatherapp.domain.usecase.GetCurrentWeatherUseCase

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetCurrentWeatherUseCase(repository: Repository): GetCurrentWeatherUseCase =
        GetCurrentWeatherUseCase(repository)

}