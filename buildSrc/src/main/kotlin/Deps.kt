/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

object Deps {
    object Plugins {
        val androidApplication = PluginDesc(id = "com.android.application")
        val androidLibrary = PluginDesc(id = "com.android.library")

        val kotlinMultiplatform = PluginDesc(id = "org.jetbrains.kotlin.multiplatform")
        val kotlinKapt = PluginDesc(id = "kotlin-kapt")
        val kotlinAndroid = PluginDesc(id = "kotlin-android")
        val kotlinAndroidExtensions = PluginDesc(id = "kotlin-android-extensions")

        val mobileMultiplatform = PluginDesc(id = "dev.icerock.mobile.multiplatform")
        val mokoResources = PluginDesc(
            id = "dev.icerock.mobile.multiplatform-resources",
            module = "dev.icerock.moko:resources-generator:${Versions.Plugins.mokoResources}"
        )

        val detekt = PluginDesc(id = "io.gitlab.arturbosch.detekt", version = Versions.detekt)
    }

    object Libs {
        object Android {
            val kotlinStdLib = AndroidLibrary(
                name = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
            )
            val appCompat = AndroidLibrary(
                name = "androidx.appcompat:appcompat:${Versions.Libs.Android.appCompat}"
            )
            val constraintLayout = AndroidLibrary(
                name = "androidx.constraintlayout:constraintlayout:1.1.3"
            )
            val coreKtx = AndroidLibrary(
                name = "androidx.core:core-ktx:1.1.0"
            )
            val playServices = AndroidLibrary(
                name = "com.google.android.gms:play-services-tasks:17.0.0"
            )
            val androidDraw = AndroidLibrary(
                name = "com.github.divyanshub024:AndroidDraw:v0.1"
            )

            val tensorflowLite = AndroidLibrary(
                name = "org.tensorflow:tensorflow-lite:${Versions.Libs.Android.tensorflowLite}"
            )
        }

        object MultiPlatform {
            val kotlinStdLib = MultiPlatformLibrary(
                android = Android.kotlinStdLib.name,
                common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
            )
            val mokoTensorflow = MultiPlatformLibrary(
                common = "dev.icerock.moko:tensorflow:${Versions.Libs.MultiPlatform.mokoTensorflow}",
                iosX64 = "dev.icerock.moko:tensorflow-iosx64:${Versions.Libs.MultiPlatform.mokoTensorflow}",
                iosArm64 = "dev.icerock.moko:tensorflow-iosarm64:${Versions.Libs.MultiPlatform.mokoTensorflow}"
            )
            val mokoResources = MultiPlatformLibrary(
                common = "dev.icerock.moko:resources:${Versions.Libs.MultiPlatform.mokoResources}",
                iosX64 = "dev.icerock.moko:resources-iosx64:${Versions.Libs.MultiPlatform.mokoResources}",
                iosArm64 = "dev.icerock.moko:resources-iosarm64:${Versions.Libs.MultiPlatform.mokoResources}"
            )
            val mokoMedia = MultiPlatformLibrary(
                common = "dev.icerock.moko:media:${Versions.Libs.MultiPlatform.mokoMedia}",
                iosX64 = "dev.icerock.moko:media-iosx64:${Versions.Libs.MultiPlatform.mokoMedia}",
                iosArm64 = "dev.icerock.moko:media-iosarm64:${Versions.Libs.MultiPlatform.mokoMedia}"
            )

            val coroutineWorker = MultiPlatformLibrary(
                common = "com.autodesk:coroutineworker:${Versions.Libs.MultiPlatform.coroutineWorker}"
            )
        }

        object Jvm {
            const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}"
        }
    }
}
