package io.github.schreibn.architecture.diagram

import com.structurizr.model.Component
import com.structurizr.model.Model
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.StaticStructureElement

/**
 * Method used simply for structuring of Model definition
 */
fun Model.units(closure: (Model) -> Unit) = closure(this)


/**
 * Method used simply for structuring of Model definition
 */
fun Model.relations(closure: (Model) -> Unit) = closure(this)


infix fun StaticStructureElement.uses(pair: Pair<SoftwareSystem, String>) = this.uses(pair.first, pair.second)!!

