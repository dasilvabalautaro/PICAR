package com.empoderar.picar.di

import android.arch.persistence.room.Room
import android.content.Context
import com.empoderar.picar.App
import com.empoderar.picar.BuildConfig
import com.empoderar.picar.model.persistent.caching.Constants
import com.empoderar.picar.model.persistent.database.AppDatabase
import com.empoderar.picar.model.persistent.network.apply.*
import com.empoderar.picar.model.persistent.network.interfaces.PicarWebService
import com.empoderar.picar.model.persistent.network.repository.UsersRepository
import com.empoderar.picar.presentation.plataform.NetworkHandler
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.security.cert.CertificateException
import javax.inject.Singleton
import javax.net.ssl.*
import com.empoderar.picar.model.exception.NoNetworkException



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
                .client(getUnsafeOkHttpClient().build()) // createClient()
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
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

    @Provides
    @Singleton
    fun provideWebService(networkHandler: NetworkHandler): PicarWebService{
        val baseUrl = Constants.urlBase

        val builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        val okHttpClientBuilder =   getUnsafeOkHttpClient() //OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor { chain ->
            val connected = networkHandler.isConnected
            if (connected!!) {
                return@addInterceptor chain.proceed(chain.request())

            } else {
                throw NoNetworkException()
            }
        }

        return builder.client(okHttpClientBuilder.build())
                .build()
                .create<PicarWebService>(PicarWebService::class.java)
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

    @Provides
    fun provideTypeFormDao(database: AppDatabase) = database.typeFormDao()

    @Provides
    fun provideContentFormDao(database: AppDatabase) = database.contentFormDao()

    @Provides
    fun provideBodyFormDao(database: AppDatabase) = database.bodyFormDao()

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

    @Provides
    @Singleton
    fun provideLoadRequestTypeForm(dataSource: LoadRequestTypeForm.SendRequest):
            LoadRequestTypeForm = dataSource

    @Provides
    @Singleton
    fun provideLoadRequestContentForm(dataSource: LoadRequestContentForm.SendRequest):
            LoadRequestContentForm = dataSource

    @Provides
    @Singleton
    fun provideLoadRequestPostForm(dataSource: LoadRequestPostForm.SendRequest):
            LoadRequestPostForm = dataSource
}