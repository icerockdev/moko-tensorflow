import org.jetbrains.kotlin.gradle.plugin.extraProperties

/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("dev.icerock.moko.gradle.multiplatform.mobile")
    id("dev.icerock.mobile.multiplatform.ios-framework")
    id("dev.icerock.mobile.multiplatform.cocoapods")
    id("dev.icerock.mobile.multiplatform-resources")
    id("dev.icerock.moko.gradle.detekt")
}


dependencies {
    commonMainImplementation(libs.kotlinStdLib)
    commonMainImplementation(libs.coroutines)

    commonMainImplementation(libs.mokoResources)
    commonMainImplementation(libs.mokoMedia)

    commonMainApi(projects.tensorflow)
}

multiplatformResources {
    multiplatformResourcesPackage = "dev.icerock.moko.sample.tensorflowtest"
}

cocoaPods {
    podsProject = file("../ios-app/Pods/Pods.xcodeproj")

    pod("TensorFlowLiteObjC", module = "TFLTensorFlowLite", onlyLink = true)
}

framework {
    export(projects.tensorflow)

}


kotlin.sourceSets.all {
    println("MPP_LIBRARY->SOURCE_SET_NAME: ${this.name}")
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