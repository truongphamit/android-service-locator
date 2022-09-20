package vn.vntravel.servicelocator.locator

object ServiceLocatorContext {
    private val serviceLocator = ServiceLocator()

    fun modules(modules: List<Module>) {
        serviceLocator.loadModules(modules)
    }

    fun getServiceLocator() = serviceLocator
}

fun startServiceLocator(block: ServiceLocatorContext.() -> Unit) =
    ServiceLocatorContext.apply(block)