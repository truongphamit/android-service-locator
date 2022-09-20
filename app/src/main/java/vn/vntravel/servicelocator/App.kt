package vn.vntravel.servicelocator

import android.app.Application
import vn.vntravel.servicelocator.locator.get
import vn.vntravel.servicelocator.locator.module
import vn.vntravel.servicelocator.locator.startServiceLocator

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startServiceLocator {
            val appModule = module {
                single { SampleService() }
            }
            val viewModelModule = module {
                viewModel { SampleViewModel(get()) }
            }
            modules(arrayListOf(appModule, viewModelModule))
        }
    }
}