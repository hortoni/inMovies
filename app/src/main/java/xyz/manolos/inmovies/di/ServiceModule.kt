package xyz.manolos.inmovies.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.manolos.inmovies.service.ApiKeyInterceptor
import xyz.manolos.inmovies.service.MovieService
import javax.inject.Singleton

@Module
object ServiceModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideQuestionService(): MovieService = Retrofit
        .Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(
            OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            ).addInterceptor(ApiKeyInterceptor())
            .build()

        )
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MovieService::class.java)

}