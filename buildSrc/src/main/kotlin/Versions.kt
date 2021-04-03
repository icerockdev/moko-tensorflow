/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

object Versions {
    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 19
    }

    const val kotlin = "1.4.31"
    const val detekt = "1.15.0"

    private const val mokoResources = "0.15.1"

    object Plugins {
        const val android = "4.1.1"

        const val kotlin = Versions.kotlin
        const val mokoResources = Versions.mokoResources
    }

    object Libs {
        object Android {
            const val appCompat = "1.1.0"
            const val tensorflowLite = "2.2.0"
        }

        object MultiPlatform {
            const val mokoTensorflow = "0.1.1"
            const val mokoResources = Versions.mokoResources
            const val mokoMedia = "0.6.2"

            const val coroutineWorker = "0.6.3"
        }
    }
}
