package android.reagent.database.di

import android.content.Context
import android.reagent.database.ReagentDatabase
import android.reagent.database.dao.EndpointTestResultDao
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideReagentDatabase(
        @ApplicationContext context: Context
    ): ReagentDatabase {
        return Room.databaseBuilder(
            context,
            ReagentDatabase::class.java,
            "reagent.db"
        ).build()
    }

    @Provides
    fun provideEndpointTestResultDao(
        database: ReagentDatabase
    ): EndpointTestResultDao {
        return database.endpointTestResultDao()
    }
}