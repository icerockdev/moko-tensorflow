/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()

        jcenter {
            content {
                includeGroup("org.jetbrains.trove4j")
            }
        }
    }
    dependencies {
        classpath(libs.kotlinGradlePlugin)
        classpath(libs.androidGradlePlugin)
        classpath(libs.mokoGradlePlugin)
        classpath(libs.mokoMultiplatformPlugin)
        classpath(libs.mokoResourcesGenerator)
        classpath(libs.detektGradlePlugin)
    }
}


apply(plugin = "dev.icerock.moko.gradle.publication.nexus")
val mokoVersion = libs.versions.mokoTensorflowVersion.get()
allprojects {
    group = "dev.icerock.moko"
    version = mokoVersion
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}
