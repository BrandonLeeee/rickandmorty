package com.example.rickandmorty.di

import com.example.rickandmorty.data.remote.CharacterApiService
import com.example.rickandmorty.data.remote.CharacterRetrofit
import com.example.rickandmorty.data.remote.QuizApiService
import com.example.rickandmorty.data.remote.QuizRetrofit
import com.example.rickandmorty.util.SharedState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedState(): SharedState = SharedState()
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCharacterRetrofit(): CharacterRetrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return CharacterRetrofit(retrofit)
    }

    @Provides
    @Singleton
    fun provideQuizRetrofit(): QuizRetrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi-4kj3.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return QuizRetrofit(retrofit)
    }


    @Provides
    @Singleton
    fun provideCharacterApiService(characterRetrofit: CharacterRetrofit): CharacterApiService {
        return characterRetrofit.retrofit.create(CharacterApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuizApiService(quizRetrofit: QuizRetrofit): QuizApiService {
        return quizRetrofit.retrofit.create(QuizApiService::class.java)
    }
}