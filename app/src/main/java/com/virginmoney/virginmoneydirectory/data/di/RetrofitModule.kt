package com.virginmoney.virginmoneydirectory.data.di


import com.virginmoney.virginmoneydirectory.data.model.repository.Repository
import com.virginmoney.virginmoneydirectory.data.model.repository.RepositoryImpl
import com.virginmoney.virginmoneydirectory.data.remote.APICall
import com.virginmoney.virginmoneydirectory.data.remote.ApiDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module //state current class is a module
@InstallIn(SingletonComponent::class) // inform the scope of class or items inside
class RetrofitModule {


    @Provides
    fun provideOkHttpInstance(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }


    @Provides
    fun provideRetrofitInstance(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiDetails.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    @Provides
    fun provideAPI(
        retrofit: Retrofit
    ): APICall {
        return retrofit.create(APICall::class.java)
    }

    @Provides
    fun provideRepository(
        apiCall: APICall
    ): Repository {
        return RepositoryImpl(apiCall)
    }

}