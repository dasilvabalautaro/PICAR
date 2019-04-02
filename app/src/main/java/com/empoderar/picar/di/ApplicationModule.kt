package com.empoderar.picar.di

import android.arch.persistence.room.Room
import android.content.Context
import com.empoderar.picar.App
import com.empoderar.picar.BuildConfig
import com.empoderar.picar.model.persistent.database.AppDatabase
import com.empoderar.picar.model.persistent.network.repository.UsersRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Repository of Inject
@Module
class ApplicationModule(private val app: App) {
    private val databaseName = "picar_db"
    private val domainSecursos = "https://www.hiddenodds.com/"

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = app

//    Provide to all application operations Rest
    @Provides @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(domainSecursos)
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

//    Interceptor of Head Rest
    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

//    Provide access the database
    @Provides fun provideAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    databaseName).allowMainThreadQueries().build()

//    Provide access the table User
    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Provides
    fun provideUnityDao(database: AppDatabase) = database.unityDao()

    @Provides
    fun provideProjectDao(database: AppDatabase) = database.projectDao()

    @Provides
    fun provideMunicipalityDao(database: AppDatabase) = database.municipalityDao()

    @Provides
    fun provideRoleDao(database: AppDatabase) = database.roleDao()

    @Provides
    fun provideFormDao(database: AppDatabase) = database.formDao()

//    Manage the operations Rest of User
    @Provides
    @Singleton
    fun provideUsersRepository(dataSource: UsersRepository.UsersNetwork):
            UsersRepository = dataSource
}