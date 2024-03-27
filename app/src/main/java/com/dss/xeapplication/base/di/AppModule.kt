package com.dss.xeapplication.base.di

import android.content.Context
import androidx.room.Room
import com.dss.xeapplication.base.ads.GoogleMobileAdsConsentManager
import com.dss.xeapplication.data.respository.CarRepository
import com.dss.xeapplication.data.respository.CarRepositoryImpl
import com.dss.xeapplication.data.room.AppDao
import com.dss.xeapplication.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.dao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }



    @Singleton
    @Provides
    fun provideGoogleMobileAdsConsent(@ApplicationContext context: Context) =
        GoogleMobileAdsConsentManager.getInstance(context)

    @Singleton
    @Provides
    fun provideCarRepositoryImpl(dao: AppDao): CarRepository {
        return CarRepositoryImpl(dao)
    }
}