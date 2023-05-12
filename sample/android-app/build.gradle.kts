/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("dev.icerock.moko.gradle.android.application")
    id("dev.icerock.moko.gradle.detekt")
}

android {
    dexOptions {
        javaMaxHeapSize = "2g"
    }
    defaultConfig {
        applicationId = "dev.icerock.moko.samples.tensorflow"

        versionCode = 1
        versionName = "0.1.0"
    }
}

dependencies {
    implementation(libs.coroutines)
    implementation(libs.kotlinStdLib)
    implementation(libs.coreKtx)
    implementation(libs.appCompat)
    implementation(libs.constraintLayout)
    implementation(libs.androidDraw)
    implementation(libs.playServices)
    implementation(libs.mokoResources)
    implementation(libs.lifecycleRuntime)

    implementation(projects.sample.mppLibrary)
}
