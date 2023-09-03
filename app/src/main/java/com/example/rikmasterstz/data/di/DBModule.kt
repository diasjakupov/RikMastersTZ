package com.example.rikmasterstz.data.di

import com.example.rikmasterstz.data.db.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.RealmConfiguration
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Singleton
    @Provides
    fun provideRealmConfig(): RealmConfiguration{
        return RealmConfiguration.Builder()
            .schemaVersion(1)
            .build()
    }


    @Singleton
    @Provides
    fun provideLocalDataSource(
        realmConfiguration: RealmConfiguration
    ): LocalDataSourceImpl{
        return LocalDataSourceImpl(realmConfiguration)
    }
}