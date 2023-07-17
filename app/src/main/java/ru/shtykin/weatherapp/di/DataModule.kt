package ru.shtykin.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.shtykin.weatherapp.data.db.WeatherDataBase
import ru.shtykin.weatherapp.data.db.dao.CityDao
import ru.shtykin.weatherapp.data.mapper.Mapper
import ru.shtykin.weatherapp.data.network.ApiService
import ru.shtykin.weatherapp.data.repository.RepositoryImpl
import ru.shtykin.weatherapp.domain.Repository
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, mapper: Mapper, db: WeatherDataBase): Repository {
        return RepositoryImpl(apiService, mapper, db)
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): WeatherDataBase {
        return Room.databaseBuilder(
            context,
            WeatherDataBase::class.java,
            "db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCityDao(db: WeatherDataBase): CityDao {
        return db.getCityDao()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }



    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    fun provideMapper(): Mapper {
        return Mapper()
    }


}