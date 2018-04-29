package io.github.schreibn.architecture.diagram

import com.structurizr.model.*

inline fun Model.create(closure: Model.(Model) -> Unit) {
    closure(this)
}

operator fun <T : Element> Set<T>.get(name: String) : T {
    return this.find { it.name == name }!!
}

/**
 * Method used simply for structuring of Model definition
 */
fun Model.units(closure: Model.(Model) -> Unit) = closure(this)


/**
 * Method used simply for structuring of Model definition
 */
fun Model.relations(closure: Model.(Model) -> Unit) = closure(this)


infix fun StaticStructureElement.uses(pair: Pair<SoftwareSystem, String>) = this.uses(pair.first, pair.second)!!

