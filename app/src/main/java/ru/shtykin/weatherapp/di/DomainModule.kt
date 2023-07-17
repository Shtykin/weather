package ru.shtykin.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.shtykin.weatherapp.domain.Repository
import ru.shtykin.weatherapp.domain.usecase.DeleteAllForecastsFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.DeleteCityFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.GetAllCitiesFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.GetAllForecastsFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import ru.shtykin.weatherapp.domain.usecase.GetFlowAllCitiesWeatherUseCase
import ru.shtykin.weatherapp.domain.usecase.GetForecastWeatherUseCase
import ru.shtykin.weatherapp.domain.usecase.InsertCityToDbUseCase
import ru.shtykin.weatherapp.domain.usecase.InsertForecastToDbUseCase

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetCurrentWeatherUseCase(repository: Repository): GetCurrentWeatherUseCase =
        GetCurrentWeatherUseCase(repository)

    @Provides
    fun provideGetForecastWeatherUseCase(repository: Repository): GetForecastWeatherUseCase =
        GetForecastWeatherUseCase(repository)

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

    @Provides
    fun provideInsertForecastToDbUseCase(repository: Repository): InsertForecastToDbUseCase =
        InsertForecastToDbUseCase(repository)

    @Provides
    fun provideDeleteAllForecastsFromDbUseCase(repository: Repository): DeleteAllForecastsFromDbUseCase =
        DeleteAllForecastsFromDbUseCase(repository)

    @Provides
    fun provideGetAllForecastsFromDbUseCase(repository: Repository): GetAllForecastsFromDbUseCase =
        GetAllForecastsFromDbUseCase(repository)
}