/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */


enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()

        jcenter {
            content {
                includeGroup("org.jetbrains.kotlinx")
                includeGroup("org.tensorflow")
            }
        }
        maven { url = uri("https://jitpack.io") }
    }
}

includeBuild("tensorflow-build-logic")

include(":tensorflow")
include(":sample:android-app")
include(":sample:mpp-library")
