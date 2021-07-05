/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("android-base-convention")
    id("detekt-convention")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform.android-manifest")
    id("dev.icerock.mobile.multiplatform.ios-framework")
    id("dev.icerock.mobile.multiplatform.cocoapods")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    android()
    ios()
}

dependencies {
    commonMainImplementation(libs.kotlinStdLib)
    commonMainImplementation(libs.mokoResources)
    commonMainImplementation(libs.mokoMedia)
    commonMainApi(projects.tensorflow)
    commonMainImplementation(libs.coroutines)
}

multiplatformResources {
    multiplatformResourcesPackage = "dev.icerock.moko.sample.tensorflowtest"
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