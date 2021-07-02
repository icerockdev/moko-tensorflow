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
    commonMainImplementation(libs.coroutineWorker)
}


cocoaPods {
    podsProject = file("../ios-app/Pods/Pods.xcodeproj")

    pod("TensorFlowLiteObjC", module = "TFLTensorFlowLite", onlyLink = true)
}
