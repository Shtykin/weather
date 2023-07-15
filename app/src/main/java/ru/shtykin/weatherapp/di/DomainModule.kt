package ru.shtykin.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.shtykin.weatherapp.domain.Repository
import ru.shtykin.weatherapp.domain.usecase.DeleteCityFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.GetAllCitiesFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import ru.shtykin.weatherapp.domain.usecase.GetFlowAllCitiesWeatherUseCase
import ru.shtykin.weatherapp.domain.usecase.InsertCityToDbUseCase

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetCurrentWeatherUseCase(repository: Repository): GetCurrentWeatherUseCase =
        GetCurrentWeatherUseCase(repository)

    @Provides
    fun provideInsertCityToDbUseCase(repository: Repository): InsertCityToDbUseCase =
        InsertCityToDbUseCase(repository)

    @Provides
    fun provideDeleteCityFromDbUseCase(repository: Repository): DeleteCityFromDbUseCase =
        DeleteCityFromDbUseCase(repository)

    @Provides
    fun provideGetAllCitiesFromDbUseCase(repository: Repository): GetAllCitiesFromDbUseCase =
        GetAllCitiesFromDbUseCase(repository)

    @Provides
    fun provideGetFlowAllCitiesWeatherUseCase(repository: Repository): GetFlowAllCitiesWeatherUseCase =
        GetFlowAllCitiesWeatherUseCase(repository)
}