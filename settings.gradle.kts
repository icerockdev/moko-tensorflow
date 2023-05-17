/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

rootProject.name = "moko-tensorflow"

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()

        // for moko-media dependency MaterialFilePicker
        maven { url = uri("https://jitpack.io") }
    }
}


include(":tensorflow")
include(":sample:android-app")
include(":sample:mpp-library")
