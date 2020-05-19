/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
}

android {
    compileSdkVersion(Versions.Android.compileSdk)

    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)
    }
}

setupFramework(exports = emptyList())

dependencies {
    mppLibrary(Deps.Libs.MultiPlatform.kotlinStdLib)
    mppLibrary(Deps.Libs.MultiPlatform.serialization)
}

cocoaPods {
    podsProject = file("../ios-app/Pods/Pods.xcodeproj")

    pod("mokoTensorflow", onlyLink = true)
}

kotlin {
    targets
        .filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>()
        .flatMap { it.binaries }
        .filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>()
        .forEach { framework ->
            framework.linkerOpts(
                project.file("../sample/ios-app/Pods/TensorFlowLiteC/Frameworks").path.let { "-F$it" }
            )
        }
}
