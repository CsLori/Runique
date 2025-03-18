package cslori.runique

import android.app.Application
import com.cslori.auth.data.di.authDataModule
import com.cslori.auth.presentation.di.authViewModelModule
import com.cslori.core.data.di.coreDataModule
import com.cslori.run.presentation.di.runPresentationModule
import cslori.runique.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RuniqueApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RuniqueApp)
            modules(
                listOf(
                    appModule,
                    authDataModule,
                    authViewModelModule,
                    coreDataModule,
                    runPresentationModule
                )
            )
        }
    }
}