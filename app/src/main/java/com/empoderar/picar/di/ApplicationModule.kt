package com.empoderar.picar.di

import android.arch.persistence.room.Room
import android.content.Context
import com.empoderar.picar.App
import com.empoderar.picar.BuildConfig
import com.empoderar.picar.model.persistent.database.AppDatabase
import com.empoderar.picar.model.persistent.network.apply.LoadRequestForm
import com.empoderar.picar.model.persistent.network.apply.LoadRequestPermission
import com.empoderar.picar.model.persistent.network.apply.LoadRequestProject
import com.empoderar.picar.model.persistent.network.apply.LoadRequestUnity
import com.empoderar.picar.model.persistent.network.repository.UsersRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import javax.inject.Singleton
import javax.net.ssl.*

//Repository of Inject
@Module
class ApplicationModule(private val app: App) {
    private val databaseName = "picar_db"
    private val domainPicar = "https://www.globalhiddenodds.com/"

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = app

//    Provide to all application operations Rest
    @Provides @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(domainPicar)
                .client(getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

//    Interceptor of Head Rest out certificate
    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }
                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }
                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
                builder.addInterceptor(loggingInterceptor)
            }
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

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

    @Provides
    fun provideImageDao(database: AppDatabase)=database.imageDao()

    //    Manage the operations Rest
    @Provides
    @Singleton
    fun provideUsersRepository(dataSource: UsersRepository.UsersNetwork):
            UsersRepository = dataSource

    @Provides
    @Singleton
    fun provideLoadRequestPermission(dataSource: LoadRequestPermission.SendRequest):
            LoadRequestPermission = dataSource

    @Provides
    @Singleton
    fun provideLoadRequestUnity(dataSource: LoadRequestUnity.SendRequest):
            LoadRequestUnity = dataSource

    @Provides
    @Singleton
    fun provideLoadRequestProject(dataSource: LoadRequestProject.SendRequest):
            LoadRequestProject = dataSource

    @Provides
    @Singleton
    fun provideLoadRequestForm(dataSource: LoadRequestForm.SendRequest):
            LoadRequestForm = dataSource
}