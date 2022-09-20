package vn.vntravel.servicelocator.locator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

inline fun <reified T> inject(named: String = ""): Lazy<T> {
    return ServiceLocatorContext.getServiceLocator().inject(named)
}

inline fun <reified T> get(named: String = ""): T {
    return ServiceLocatorContext.getServiceLocator().get(named)
}

inline fun <reified T : ViewModel> LifecycleOwner.viewModel(): Lazy<T> {
    return when (this) {
        is Fragment -> ServiceLocatorContext.getServiceLocator().getViewModel(this)
        is FragmentActivity -> ServiceLocatorContext.getServiceLocator().getViewModel(this)
        else -> throw IllegalArgumentException("LifecycleOwner must be Fragment or FragmentActivity")
    }
}

inline fun <reified T : ViewModel> Fragment.activityViewModel(): Lazy<T> {
    return ServiceLocatorContext.getServiceLocator().getActivityViewModelLazy(this)
}