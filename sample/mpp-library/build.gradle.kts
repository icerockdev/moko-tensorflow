/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
    id("dev.icerock.mobile.multiplatform-resources")
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

    mppLibrary(Deps.Libs.MultiPlatform.mokoResources)
    mppLibrary(Deps.Libs.MultiPlatform.mokoTensorflow)
    mppLibrary(Deps.Libs.MultiPlatform.mokoMedia)

    mppLibrary(Deps.Libs.MultiPlatform.coroutineWorker)
}

multiplatformResources {
    multiplatformResourcesPackage = "dev.icerock.moko.sample.tensorflowtest" //"com.icerockdev.library"
}

cocoaPods {
    podsProject = file("../ios-app/Pods/Pods.xcodeproj")

    pod("TensorFlowLiteObjC", module = "TFLTensorFlowLite", onlyLink = true)
}

kotlin {
    targets
        .filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>()
        .flatMap { it.binaries }
        .filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>()
        .forEach { framework ->
            framework.linkerOpts(
                project.file("../ios-app/Pods/TensorFlowLiteC/Frameworks").path.let { "-F$it" },
                "-framework",
                "TensorFlowLiteC"
            )
        }
}
