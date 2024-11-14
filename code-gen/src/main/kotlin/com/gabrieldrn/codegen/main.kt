/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gabrieldrn.codegen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.codegen.color.abstractThemeDoc
import com.gabrieldrn.codegen.color.containerColorMemberDoc
import com.gabrieldrn.codegen.color.deserializeColorTokens
import com.gabrieldrn.codegen.color.model.colortokens2.Theme
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import java.nio.file.Paths
import java.util.Locale
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

private const val PACKAGE_ROOT = "com.gabrieldrn.carbon.foundation.color"

private val sourcePath = Paths.get("carbon/src/commonMain/kotlin")

//private val sourcePath = Paths.get("build/generated/kotlin")
private val layerClass = ClassName(PACKAGE_ROOT, "Layer")
private val themeAbstractionName = ClassName(PACKAGE_ROOT, "Theme")
private val codeIndent = "    "
private val generatedCodeMessage =
    """
        ----------------------------------
        /!\ Generated code. Do not modify.
        ----------------------------------
    """.trimIndent()

private const val componentInterfaceKdoc = "Color tokens for the %s component."

private val undocumentedPublicPropertyAnnotation =
    AnnotationSpec.builder(Suppress::class)
        .addMember("%S", "UndocumentedPublicProperty")
        .build()

private fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
}

fun main() {
    // Load color tokens
    val tokens: Map<String, Theme> = deserializeColorTokens()

    val componentsAbstractions = generateComponentsAbstractions()

    generateThemeAbstraction(componentsAbstractions)
}

private fun generateComponentsAbstractions(): List<ClassName> {

    val componentsClassNames = mutableListOf<ClassName>()

    Theme::class.memberProperties
        .filterNot { it.returnType.jvmErasure == String::class }
        .map { it.returnType.jvmErasure }
        .forEach { componentType ->
            val componentName = componentType.simpleName!!

            val componentInterfaceName = ClassName(
                "$PACKAGE_ROOT." +
                    componentName
                        .substringBefore("Colors")
                        .replaceFirstChar { it.lowercase() },
                componentName
            )

            val componentProperties = componentType.declaredMemberProperties
                .filter { it.returnType.jvmErasure == String::class }
                .map { token ->
                    PropertySpec.builder(token.name, Color::class)
                        .addModifiers(KModifier.ABSTRACT)
                        .build()
                }

            val componentInterfaceSpec = TypeSpec.interfaceBuilder(componentInterfaceName)
                .addKdoc(
                    componentInterfaceKdoc.format(
                        componentName.substringBefore("Colors")
                    )
                )
                .addAnnotation(Immutable::class)
                .addAnnotation(undocumentedPublicPropertyAnnotation)
                .addProperties(componentProperties)
                .build()

            FileSpec.builder(componentInterfaceName)
                .indent(codeIndent)
                .addFileComment(generatedCodeMessage)
                .addType(componentInterfaceSpec)
                .build()
                .writeTo(sourcePath)

            componentsClassNames.add(componentInterfaceName)

            println("${componentInterfaceName.simpleName} interface generated.")
        }

    return componentsClassNames
}

private fun generateThemeAbstraction(componentsAbstractions: List<ClassName>) {
    val coreTokens = Theme::class.memberProperties
        .filter { it.returnType == String::class.createType() }
        .map { token ->
            PropertySpec.builder(token.name, Color::class)
                .addModifiers(KModifier.ABSTRACT)
                .build()
        }

    val components = componentsAbstractions.associateBy {
        it.simpleName.replaceFirstChar { c -> c.lowercase() }
    }

    val containerColorFuncSpec = FunSpec.builder("containerColor")
        .addKdoc(containerColorMemberDoc)
        .returns(Color::class)
        .addParameter(
            ParameterSpec.builder("layer", layerClass)
                .defaultValue("Layer.Layer00")
                .build()
        )
        .addStatement(
            """
                return when (layer) {
                  Layer.Layer00 -> background
                  Layer.Layer01 -> layer01
                  Layer.Layer02 -> layer02
                  Layer.Layer03 -> layer03
                }
            """.trimIndent()
        )
        .build()

    val copyAnonymousClass = TypeSpec.anonymousClassBuilder()
        .superclass(themeAbstractionName)
        .apply {
            coreTokens.forEach { token ->
                addProperty(
                    PropertySpec.builder(token.name, Color::class)
                        .initializer(token.name)
                        .addModifiers(KModifier.OVERRIDE)
                        .build()
                )
            }
            components.forEach { (name, className) ->
                addProperty(
                    PropertySpec.builder(name, className)
                        .initializer(name)
                        .addModifiers(KModifier.OVERRIDE)
                        .build()
                )
            }
        }
        .build()

    val copyThemeFuncSpec = FunSpec.builder("copy")
        .returns(themeAbstractionName)
        .addModifiers(KModifier.INTERNAL)
        .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "LongMethod")
                .build()
        )
        .addParameters(
            coreTokens.map { token ->
                ParameterSpec.builder(token.name, Color::class)
                    .defaultValue("this.${token.name}")
                    .build()
            }
        )
        .addParameters(
            components.map { (name, className) ->
                ParameterSpec.builder(name, className)
                    .defaultValue("this.$name")
                    .build()
            }
        )
        .addStatement("return %L", copyAnonymousClass)
        .build()

    val equalsFunSpec = FunSpec.builder("equals")
        .addModifiers(KModifier.OVERRIDE)
        .addParameter("other", Any::class.asTypeName().copy(nullable = true))
        .returns(Boolean::class)
        .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "CognitiveComplexMethod")
                .addMember("%S", "CyclomaticComplexMethod")
                .addMember("%S", "LongMethod")
                .build()
        )
        .addStatement("if (this === other) return true")
        .addStatement("if (other !is %T) return false", themeAbstractionName)
        .apply {
            val statement = "if (%N != other.%N) return false"
            coreTokens.forEach { spec ->
                addStatement(statement, spec.name, spec.name)
            }
            components.forEach { (name, _) ->
                addStatement(statement, name, name)
            }
        }
        .addStatement("return true")
        .build()

    val hashCodeFunSpec = FunSpec.builder("hashCode")
        .addModifiers(KModifier.OVERRIDE)
        .returns(Int::class)
        .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "LongMethod")
                .build()
        )
        .apply {
            coreTokens.forEachIndexed { index, spec ->
                if (index == 0) {
                    addStatement("var result = %N.hashCode()", spec.name)
                } else {
                    addStatement("result = 31 * result + %N.hashCode()", spec.name)
                }
            }
            components.forEach { (name, _) ->
                addStatement("result = 31 * result + %N.hashCode()", name)
            }
        }
        .addStatement("return result")
        .build()

    val themeAbstraction = TypeSpec.classBuilder(themeAbstractionName)
        .addKdoc(abstractThemeDoc)
        .addModifiers(KModifier.ABSTRACT)
        .addAnnotation(Immutable::class)
        .addAnnotation(undocumentedPublicPropertyAnnotation)
        .addProperties(coreTokens)
        .addProperties(
            components.map { (name, className) ->
                PropertySpec.builder(name, className)
                    .addModifiers(KModifier.ABSTRACT)
                    .build()
            }
        )
        .addFunctions(
            listOf(containerColorFuncSpec, copyThemeFuncSpec, equalsFunSpec, hashCodeFunSpec)
        )
        .build()

    FileSpec.builder(PACKAGE_ROOT, themeAbstractionName.simpleName)
        .indent(codeIndent)
        .addFileComment(generatedCodeMessage)
        .addType(themeAbstraction)
        .build()
        .writeTo(sourcePath)

    println("Theme interface generated.")
}
