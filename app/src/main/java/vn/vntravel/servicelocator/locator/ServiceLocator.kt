package vn.vntravel.servicelocator.locator

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class ServiceLocator internal constructor() {
    private val services = mutableMapOf<Identifier, Lazy<*>>()

    fun loadModules(modules: List<Module>) {
        modules.forEach(::registerModule)
    }

    private fun registerModule(module: Module) {
        this.services.putAll(module.services)
    }

    inline fun <reified T> get(named: String = ""): T {
        val identifier = Identifier(T::class, named)
        return resolveDependency(identifier)
    }

    inline fun <reified T> inject(named: String = ""): Lazy<T> {
        val identifier = Identifier(T::class, named)
        return lazy { resolveDependency(identifier) }
    }

    inline fun <reified T : ViewModel> getViewModel(viewModelStore: ViewModelStoreOwner): Lazy<T> {
        return lazy {
            val providers = ViewModelProvider(viewModelStore, viewModelFactory())
            providers[T::class.java]
        }
    }

    inline fun <reified T : ViewModel> getActivityViewModelLazy(fragment: Fragment): Lazy<T> {
        return lazy {
            val providers =
                ViewModelProvider(fragment.requireActivity().viewModelStore, viewModelFactory())
            providers[T::class.java]
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> resolveDependency(identifier: Identifier): T {
        val lazy = services[identifier] ?: throw IllegalStateException(
            "No instance found based on the class ${identifier.instanceClass}" + if (identifier.named.isNotEmpty()) "named ${identifier.named}" else ""
        )

        return lazy.value as T
    }

    fun viewModelFactory() = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return resolveViewModel(Identifier(modelClass.kotlin, ""))
        }
    }

    fun <T : ViewModel> resolveViewModel(identifier: Identifier): T {
        return resolveDependency(identifier)
    }
}
