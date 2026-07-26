package android.reagent.data.di

import android.reagent.data.EndpointTestRepositoryImpl
import android.reagent.domain.repository.EndpointTestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEndpointRepository(
        implementation: EndpointTestRepositoryImpl
    ): EndpointTestRepository
}