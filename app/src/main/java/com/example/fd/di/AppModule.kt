package com.example.fd.di

import com.example.fd.data.AuthenticationRepository
import com.example.fd.data.AuthenticationRepositoryImpl
import com.example.fd.remotedata.RetrofitNapsterServiceApi
import com.example.fd.network.NetworkClient
import com.example.fd.remotedata.RetrofitNapsterService
import com.example.fd.remotedata.RetrofitNapsterServiceImplementation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideAuthRepository(scope: CoroutineScope): AuthenticationRepository = AuthenticationRepositoryImpl(auth = Firebase.auth, scope)

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun providesCoroutineScope(defaultDispatcher: CoroutineDispatcher): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    @Provides
    fun provideNapsterNetworkClient(): NetworkClient = NetworkClient("https://api.napster.com/", true )

    @Provides
    fun provideNullableNapsterServiceApi(networkClient: NetworkClient): RetrofitNapsterServiceApi = networkClient.getService()

    @Provides
    fun provideRetrofitNapsterServiceImplementation(napsterServiceApi: RetrofitNapsterServiceApi): RetrofitNapsterService =
        RetrofitNapsterServiceImplementation(napsterServiceApi)
}