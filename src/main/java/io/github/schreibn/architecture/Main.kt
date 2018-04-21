package io.github.schreibn.architecture

import com.structurizr.io.plantuml.PlantUMLWriter
import io.github.schreibn.architecture.aop.Diagram
import io.github.schreibn.architecture.diagram.AbstractDiagram
import net.sourceforge.plantuml.SourceStringReader
import org.reflections.Reflections
import java.io.File
import java.io.StringWriter

val stringWriter = StringWriter()
val plantUMLWriter = PlantUMLWriter()

fun main(args: Array<String>) {

    plantUMLWriter.addSkinParam("rectangleFontColor", "#ffffff");
    plantUMLWriter.addSkinParam("rectangleStereotypeFontColor", "#ffffff");

    val outputDir = File("output").apply {
        if(!this.exists()) {
            this.mkdir()
        }
    }

    val reflections = Reflections("io.github.schreibn.architecture.diagram")
    val diagramClasses = reflections.getTypesAnnotatedWith(Diagram::class.java)
    diagramClasses.map { it.newInstance() }
            .forEach {
                val javaClass = it.javaClass
                val diagramAnnotation = javaClass.getAnnotation(Diagram::class.java)
                val fileName = diagramAnnotation.value
                plantUMLWriter.write((it as AbstractDiagram).produceView(), stringWriter);
                val reader = SourceStringReader(stringWriter.toString())
                reader.outputImage(File(outputDir,"$fileName.svg"))
            }
}