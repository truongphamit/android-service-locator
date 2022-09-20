package vn.vntravel.servicelocator

import android.util.Log
import androidx.lifecycle.ViewModel

class SampleViewModel(service: SampleService) : ViewModel() {
    init {
        Log.e("service", service.toString())
    }
}