package com.polotika.androidunittesting.di

import android.content.Context
import androidx.room.Room
import com.polotika.androidunittesting.data.local.ShoppingDao
import com.polotika.androidunittesting.data.local.ShoppingItem
import com.polotika.androidunittesting.data.local.ShoppingItemDatabase
import com.polotika.androidunittesting.data.remote.PixabayAPI
import com.polotika.androidunittesting.repositories.DataRepository
import com.polotika.androidunittesting.repositories.DataRepositoryImpl
import com.polotika.androidunittesting.utils.Constants.BASE_URL
import com.polotika.androidunittesting.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(database: ShoppingItemDatabase) = database.dao()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DataRepositoryImpl(dao, api) as DataRepository

    @Singleton
    @Provides
    fun providePixabayApi():PixabayAPI{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
            BASE_URL).build().create(PixabayAPI::class.java)
    }
}