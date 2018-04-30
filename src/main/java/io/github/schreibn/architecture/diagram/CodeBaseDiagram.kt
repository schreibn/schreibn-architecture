package io.github.schreibn.architecture.diagram

import com.structurizr.Workspace
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.Tags
import com.structurizr.view.Shape
import io.github.schreibn.architecture.aop.Diagram


class CodebaseTags {
    companion object {
        const val ANDROID_SYSTEM = "ANDROID_SYSTEM";
        const val IOS_SYSTEM = "IOS_SYSTEM";
        const val WEB_SYSTEM = "WEB_SYSTEM";
        const val DESKTOP_SYSTEM = "DESKTOP_SYSTEM";
    }
}


@Diagram("codeBase")
class CodeBaseDiagram : AbstractDiagram() {

    override fun produceView(): Workspace {
        val workspace = Workspace("Getting Started", "This is a core diagram test.")
        val model = workspace.model
        model.create {
            // Applications
            val androidApplicationSoftware = model.system("Android application", "Native + Kotlin")
                    .apply { addTags(CodebaseTags.ANDROID_SYSTEM) }
            val iosApplicationSoftware = model.system("IOS application", "React-Native + Kotlin")
                    .apply { addTags(CodebaseTags.IOS_SYSTEM) }
            val webApplicationSoftware = model.system("Web application","React + Kotlin")
                    .apply { addTags(CodebaseTags.WEB_SYSTEM) }
            val desktopApplicationSoftware = model.system("Desktop application", "JavaFX + Kotlin")
                    .apply { addTags(CodebaseTags.ANDROID_SYSTEM) }

            val applications = arrayOf(androidApplicationSoftware,
                    iosApplicationSoftware,
                    webApplicationSoftware,
                    desktopApplicationSoftware)
            val bridges = applications.map { model.system("MVP/MVC Bridge for ${it.name.substringBefore(" ")}","Platform -> Kotlin") }

            val presenterSoftware = model.system("Presenter/Controller", "Kotlin")
            val modelSoftware = model.system("Model", "Kotlin")//Needs future investigations

            relations {
                applications.zip(bridges).forEach {
                    it.first.uses(it.second, "Proxies")
                }
                bridges.forEach {
                    presenterSoftware.uses(it,"Handles")
                }
                modelSoftware.uses(presenterSoftware, "Uses")
            }
        }
        val views = workspace.views
        val contextView = views.createSystemContextView(model.softwareSystems.first(), "contextView", "Schreibn codebase structure")
        contextView.addAllElements()
        contextView.addAllSoftwareSystems()
        contextView.addAllPeople()

        val styles = views.configuration.styles
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd")

        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#000000").shape(Shape.Person)

        return workspace
    }


}