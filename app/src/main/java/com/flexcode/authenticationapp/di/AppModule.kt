package com.flexcode.authenticationapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.flexcode.authenticationapp.data.local.AuthPreferences
import com.flexcode.authenticationapp.data.remote.ApiService
import com.flexcode.authenticationapp.data.repository.AuthRepositoryImpl
import com.flexcode.authenticationapp.domain.repository.AuthRepository
import com.flexcode.authenticationapp.domain.repository.FPLrepository
import com.flexcode.authenticationapp.domain.use_case.LoginUseCase
import com.flexcode.authenticationapp.util.Constants.AUTH_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context) : DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(AUTH_PREFERENCES)
            }
        )

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideAuthPreferences(dataStore: DataStore<Preferences>) =
        AuthPreferences(dataStore)


    @Provides
    @Singleton
    fun providesApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://fantasy.premierleague.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(
        apiService: ApiService,
        preferences: AuthPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(
            apiService = apiService,
            preferences = preferences
        )
    }

    @Provides
    fun provideFPLRepository(apiService: ApiService): FPLrepository {
        return FPLrepository(apiService)
    }

    @Provides
    @Singleton
    fun providesLoginUseCase(repository: FPLrepository): LoginUseCase {
        return LoginUseCase(repository)
    }


}