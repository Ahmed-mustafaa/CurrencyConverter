package ahmed.praicticing.currencyconvertor.di

import ahmed.praicticing.currencyconvertor.Util.DispatcherProvider
import ahmed.praicticing.currencyconvertor.data.models.CurrencyAPI
import ahmed.praicticing.currencyconvertor.main.DefaultMainRepository
import ahmed.praicticing.currencyconvertor.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton
private const val BASE_URL = "https://api.exchangeratesapi.io/"
@Module
@InstallIn(ApplicationComponentManager::class)
object AppModule {
    @Singleton
    @Provides
    fun proviceCurrencyApi( ):CurrencyAPI = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyAPI::class.java)

    @Singleton
    @Provides
    fun providesMainRepository(api: CurrencyAPI): MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun providesDispatches():DispatcherProvider = object : DispatcherProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}