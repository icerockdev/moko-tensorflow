/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

object Versions {
    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 19
    }

    const val kotlin = "1.3.72"
    const val detekt = "1.7.4"

    private const val mokoResources = "0.10.0"

    object Plugins {
        const val android = "3.6.2"

        const val kotlin = Versions.kotlin
        const val mokoResources = Versions.mokoResources
    }

    object Libs {
        object Android {
            const val appCompat = "1.1.0"
            const val tensorflowLite = "2.2.0" // "0.0.0-nightly" ???
        }

        object MultiPlatform {
            const val mokoTensorflow = "0.1.0"
            const val mokoResources = Versions.mokoResources
            const val mokoMedia = "0.4.3"

            const val serialization = "0.20.0"
            const val coroutineWorker = "0.5.0"
        }
    }
}
