package vn.vntravel.servicelocator.locator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class Module internal constructor() {
    val services = mutableMapOf<Identifier, Lazy<*>>()

    inline fun <reified T> single(named: String = "", noinline instance: () -> T): Identifier {
        val identifier = Identifier(T::class, named)
        val lazyInstance = lazy(instance)
        updateDefinitions(identifier, lazyInstance)
        return identifier
    }

    inline fun <reified T> factory(named: String = "", noinline instance: () -> T): Identifier {
        val identifier = Identifier(T::class, named)
        val lazyInstance = factoryOf(instance)
        updateDefinitions(identifier, lazyInstance)
        return identifier
    }

    inline fun <reified T : ViewModel> viewModel(noinline instance: () -> T): Identifier {
        val identifier = Identifier(T::class, "")
        factory(instance = instance)
        return identifier
    }

    fun <T> updateDefinitions(identifier: Identifier, instance: Lazy<T>) {
        check(!services.containsKey(identifier)) { "Cannot inject 2 instance with same definition" }
        services[identifier] = instance as Lazy<*>
    }

//    inline fun <reified T> get(named: String = ""): T {
//        val identifier = Identifier(T::class, named)
//        return resolveDependency(identifier)
//    }
//
//    inline fun <reified T: ViewModel> getViewModel(viewModelStore: ViewModelStoreOwner): T {
//        val providers = ViewModelProvider(viewModelStore, viewModelFactory())
//        return providers[T::class.java]
//    }

//    inline fun <reified T : ViewModel> LifecycleOwner.viewModel(): T {
//        val providers = when (this) {
//            is Fragment -> ViewModelProvider(this, viewModelFactory())
//            is FragmentActivity -> ViewModelProvider(this, viewModelFactory())
//            else -> throw IllegalArgumentException("LifecycleOwner must be Fragment or FragmentActivity")
//        }
//        return providers[T::class.java]
//    }
//
//    inline fun <reified T : ViewModel> Fragment.activityViewModel(): T {
//        val providers = ViewModelProvider(this, viewModelFactory())
//        return providers[T::class.java]
//    }

//    @Suppress("UNCHECKED_CAST")
//    fun <T> resolveDependency(identifier: Identifier): T {
//        val lazy = services[identifier]
//            ?: throw IllegalStateException(
//                "No instance found based on the class ${identifier.instanceClass}" +
//                        if (identifier.named.isNotEmpty()) "named ${identifier.named}" else ""
//            )
//
//        return lazy.value as T
//    }
//
//    fun viewModelFactory() = object : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return resolveViewModel(Identifier(modelClass.kotlin, ""))
//        }
//    }
//
//    fun <T : ViewModel> resolveViewModel(identifier: Identifier): T {
//        return resolveDependency(identifier)
//    }
}

fun module(config: Module.() -> Unit): Module {
    return Module().apply(config)
}