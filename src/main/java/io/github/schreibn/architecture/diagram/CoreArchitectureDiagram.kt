package io.github.schreibn.architecture.diagram

import com.structurizr.Workspace
import com.structurizr.model.*
import com.structurizr.view.Shape
import com.structurizr.view.Styles
import com.structurizr.view.SystemContextView
import com.structurizr.view.ViewSet
import io.github.schreibn.architecture.aop.Diagram


class ArchitectureTags{
    companion object {
        const val MONGO= "MONGO"
    }
}

@Diagram("coreDiagram")
class CoreArchitectureDiagram : AbstractDiagram() {

    override fun produceView(): Workspace {
        val workspace = Workspace("Getting Started", "This is a core diagram test.")
        val model = workspace.model
        var softwareSystem: SoftwareSystem? = null

        model.create {
            //Model elements TODO: Replace with closures in future, provide best way to share elements in different closures
            val user = addPerson("User", "User that wants to be happy making nodes")
            val mobileSoftwareSystem = system("Mobile", "Mobile application both for IOS and Android")
            val webSoftwareSystem = system("Web", "Web application")
            val loadBalancerSoftwareSystem = system("LoadBalancer", "Distributes load on nodes")
            val restApiSoftwareSystem = system("Rest API", "Single stateless entrance for I/O")
            val tcpApiSoftwareSystem = system("TCP Api", "Used to handle fast document changes with auto-saving")
            val webHooksSoftwareSystem = system("Webhooks", "Provides flexibility in actions")
            val thirdPartySoftwareSystem = system("3d party", "E.g some AWS Lambda hooks")
            val mongoDbClusterSoftwareSystem = system("MongoDB cluster", "Provides dynamic data storage with horizontal scaling").apply { addTags(ArchitectureTags.MONGO) }

            softwareSystem = restApiSoftwareSystem


            relations {
                arrayOf(mobileSoftwareSystem, webSoftwareSystem, thirdPartySoftwareSystem).forEach {
                    user uses (it to "Uses")
                    it.uses(loadBalancerSoftwareSystem, "Works")

                }


                loadBalancerSoftwareSystem uses (restApiSoftwareSystem to "1..*")
                loadBalancerSoftwareSystem uses (tcpApiSoftwareSystem to "1..*")
                webHooksSoftwareSystem uses (thirdPartySoftwareSystem to "Dispatch")
                restApiSoftwareSystem uses (webHooksSoftwareSystem to "Dispatch")
                tcpApiSoftwareSystem uses (mongoDbClusterSoftwareSystem to "Stores")
                restApiSoftwareSystem uses (mongoDbClusterSoftwareSystem to "Stores")
            }

        }
        val views = workspace.views
        val contextView = views.createSystemContextView(softwareSystem, "contextView", "Distributed Schreibn architecture")
        contextView.addAllElements()
        contextView.addAllSoftwareSystems()
        contextView.addAllPeople()

        val styles = views.configuration.styles
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd")
        styles.addElementStyle(ArchitectureTags.MONGO).color("#000000").background("#008000")
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#000000").shape(Shape.Person)
        return workspace
    }
}

