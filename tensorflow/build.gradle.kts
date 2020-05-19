/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.kotlinAndroidExtensions)
    plugin(Deps.Plugins.mobileMultiplatform)
    id("maven-publish")
}

group = "dev.icerock.moko"
version = Versions.Libs.MultiPlatform.mokoTensorflow

android {
    compileSdkVersion(Versions.Android.compileSdk)

    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)
    }
}

dependencies {
    mppLibrary(Deps.Libs.MultiPlatform.kotlinStdLib)
    mppLibrary(Deps.Libs.MultiPlatform.serialization)

    mppLibrary(Deps.Libs.MultiPlatform.mokoResources)

    androidLibrary(Deps.Libs.Android.appCompat)
    androidLibrary(Deps.Libs.Android.tensorflowLite)
}

publishing {
    repositories.maven("https://api.bintray.com/maven/icerockdev/moko/moko-tensorflow/;publish=1") {
        name = "bintray"

        credentials {
            username = System.getProperty("BINTRAY_USER")
            password = System.getProperty("BINTRAY_KEY")
        }
    }
}

kotlin {
    targets.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().forEach { target ->
        target.compilations.getByName("main") {
            val tensorFlowLiteC by cinterops.creating {
                defFile(project.file("src/iosMain/def/TensorFlowLiteC.def"))

                val frameworks = listOf(
                    project.file("../sample/ios-app/Pods/TensorFlowLiteC/Frameworks")
                )

                val frameworksOpts = frameworks.map { "-F${it.path}" }
                compilerOpts(*frameworksOpts.toTypedArray())
            }
        }
    }
}
