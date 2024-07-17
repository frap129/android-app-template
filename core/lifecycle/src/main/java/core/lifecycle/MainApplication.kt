package core.lifecycle

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MainApplication : Application() {
    /**
     * A Koin module containing a reference to the MainApplication class.
     *
     * @property appModule Koin Module for the Application object
     */
    private val appModule = module {
        single<MainApplication> { this@MainApplication }
    }

    /**
     * A Koin module containing references to each repository class used
     * throughout the project.
     *
     * @property repoModule Koin Module for Repositories
     */
    private val repoModule = module {
    }

    /**
     * A Koin module containing references to each ViewModel class used
     * throughout the project.
     *
     * @property viewModelModule Koin Module for ViewModels
     */
    private val viewModelModule = module {
    }

    override fun attachBaseContext(base: Context) {
        startKoin {
            androidContext(base)
            androidLogger()
            modules(appModule, repoModule, viewModelModule)
        }
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        // Set up Timber
        when (BuildConfig.DEBUG) {
            true -> Timber.plant(Timber.DebugTree())
            false -> Timber.plant(ReleaseTree)
        }
    }

    private object ReleaseTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            // TODO: Configure reporting for release builds
        }
    }
}
