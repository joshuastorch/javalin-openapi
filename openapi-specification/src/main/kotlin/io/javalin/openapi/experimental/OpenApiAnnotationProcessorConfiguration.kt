package io.javalin.openapi.experimental

import javax.lang.model.element.Element
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION

@RequiresOptIn
@Retention(BINARY)
@Target(CLASS, FUNCTION)
annotation class ExperimentalCompileOpenApiConfiguration

@ExperimentalCompileOpenApiConfiguration
interface OpenApiAnnotationProcessorConfigurer {
    fun configure(configuration: OpenApiAnnotationProcessorConfiguration)
}

data class SimpleType @JvmOverloads constructor(
    val type: String,
    val format: String = ""
)

class OpenApiAnnotationProcessorConfiguration {
    var debug: Boolean = false
    var propertyInSchemeFilter: PropertyInSchemeFilter? = null
    var simpleTypeMappings: MutableMap<String, SimpleType> = createDefaultSimpleTypeMappings()
}

private fun createDefaultSimpleTypeMappings(): MutableMap<String, SimpleType> = mutableMapOf(
    "boolean" to SimpleType("boolean"),
    "java.lang.Boolean" to SimpleType("boolean"),

    "byte" to SimpleType("integer", "int32"),
    "java.lang.Byte" to SimpleType("integer", "int32"),
    "short" to SimpleType("integer", "int32"),
    "java.lang.Short" to SimpleType("integer", "int32"),
    "int" to SimpleType("integer", "int32"),
    "java.lang.Integer" to SimpleType("integer", "int32"),
    "long" to SimpleType("integer", "int64"),
    "java.lang.Long" to SimpleType("integer", "int64"),

    "float" to SimpleType("number", "float"),
    "java.lang.Float" to SimpleType("number", "float"),
    "double" to SimpleType("number", "double"),
    "java.lang.Double" to SimpleType("number", "double"),

    "char" to SimpleType("string"),
    "java.lang.Character" to SimpleType("string"),
    "java.lang.String" to SimpleType("string"),
    "java.math.BigDecimal" to SimpleType("string"),
    "java.util.UUID" to SimpleType("string"),
    "org.bson.types.ObjectId" to SimpleType("string"),

    "byte[]" to SimpleType("string", "binary"),
    "java.io.InputStream" to SimpleType("string", "binary"),
    "java.io.File" to SimpleType("string", "binary"),

    "java.util.Date" to SimpleType("string", "date"),
    "java.time.LocalDate" to SimpleType("string", "date"),

    "java.time.LocalDateTime" to SimpleType("string", "date-time"),
    "java.time.Instant" to SimpleType("string", "date-time"),

    "java.lang.Object" to SimpleType("object"),
    "java.util.Map" to SimpleType("object"),
)

fun interface PropertyInSchemeFilter {
    fun filter(context: AnnotationProcessorContext, type: ClassDefinition, property: Element): Boolean
}

