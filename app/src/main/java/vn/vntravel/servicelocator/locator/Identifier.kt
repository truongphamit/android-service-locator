package vn.vntravel.servicelocator.locator
import kotlin.reflect.KClass

data class Identifier(
    val instanceClass: KClass<*>,
    val named: String
)