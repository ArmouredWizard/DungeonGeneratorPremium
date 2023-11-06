package uk.co.maddwarf.randomdungeongeneratorpremium.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.InhabitantsRepository
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.InhabitantsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideInhabitantsRepository():InhabitantsRepository = InhabitantsRepositoryImpl()

}