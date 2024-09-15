package com.susee.zap.di
import com.susee.zap.data.repo.MainRepo
import com.susee.zap.data.repo.MainRepoImpl
import com.susee.zap.data.source.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun provideMovieRepository(
        apiService: ApiService,
    ): MainRepo {
        return MainRepoImpl(apiService)
    }
}