/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("dev.icerock.moko.gradle.multiplatform.mobile")
    id("dev.icerock.mobile.multiplatform.cocoapods")
    id("dev.icerock.moko.gradle.publication")
    id("dev.icerock.moko.gradle.stub.javadoc")
    id("dev.icerock.moko.gradle.detekt")
}

dependencies {
    commonMainImplementation(libs.mokoResources)

    androidMainImplementation(libs.appCompat)
    androidMainApi(libs.tensorflowLite)
}

cocoaPods {
    podsProject = file("../sample/ios-app/Pods/Pods.xcodeproj")

    pod("TensorFlowLiteObjC", module = "TFLTensorFlowLite")
}
kotlin.sourceSets.all {
    println("SOURCE_SET_NAME: ${this.name}")
}