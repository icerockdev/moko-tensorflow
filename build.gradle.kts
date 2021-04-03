/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import java.util.Base64

plugins {
    plugin(Deps.Plugins.detekt) apply false
}

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()

        jcenter {
            content {
                includeGroup("org.jetbrains.trove4j")
            }
        }
    }
    dependencies {
        with(Deps.Plugins) {
            listOf(
                androidApplication,
                androidLibrary,
                kotlinMultiplatform,
                kotlinKapt,
                kotlinAndroid,
                mokoResources
            ).forEach { plugin(it) }
        }
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()

        maven { url = uri("https://jitpack.io") }
        maven { url = uri("http://dl.bintray.com/lukaville/maven") }

        jcenter {
            content {
                includeGroup("org.jetbrains.trove4j")
                includeGroup("org.jetbrains.kotlinx")
                includeGroup("org.tensorflow")
            }
        }
    }

    apply(plugin = Deps.Plugins.detekt.id)

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        toolVersion = Versions.detekt
        input.setFrom("src/commonMain/kotlin", "src/androidMain/kotlin", "src/iosMain/kotlin")
    }

    dependencies {
        "detektPlugins"(Deps.Libs.Jvm.detektFormatting)
    }

    plugins.withId(Deps.Plugins.mavenPublish.id) {
        group = "dev.icerock.moko"
        version = Versions.Libs.MultiPlatform.mokoTensorflow

        val javadocJar by tasks.registering(Jar::class) {
            archiveClassifier.set("javadoc")
        }

        configure<PublishingExtension> {
            repositories.maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
                name = "OSSRH"

                credentials {
                    username = System.getenv("OSSRH_USER")
                    password = System.getenv("OSSRH_KEY")
                }
            }

            publications.withType<MavenPublication> {
                // Stub javadoc.jar artifact
                artifact(javadocJar.get())

                // Provide artifacts information requited by Maven Central
                pom {
                    name.set("MOKO tensorflow")
                    description.set("Tensorflow Lite bindings for mobile (android & ios) Kotlin Multiplatform development")
                    url.set("https://github.com/icerockdev/moko-tensorflow")
                    licenses {
                        license {
                            url.set("https://github.com/icerockdev/moko-tensorflow/blob/master/LICENSE.md")
                        }
                    }

                    developers {
                        developer {
                            id.set("Tetraquark")
                            name.set("Vladislav Areshkin")
                            email.set("vareshkin@icerockdev.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:ssh://github.com/icerockdev/moko-tensorflow.git")
                        developerConnection.set("scm:git:ssh://github.com/icerockdev/moko-tensorflow.git")
                        url.set("https://github.com/icerockdev/moko-tensorflow")
                    }
                }
            }

            apply(plugin = Deps.Plugins.signing.id)

            configure<SigningExtension> {
                val signingKeyId: String? = System.getenv("SIGNING_KEY_ID")
                val signingPassword: String? = System.getenv("SIGNING_PASSWORD")
                val signingKey: String? = System.getenv("SIGNING_KEY")?.let { base64Key ->
                    String(Base64.getDecoder().decode(base64Key))
                }
                if (signingKeyId != null) {
                    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
                    sign(publications)
                }
            }
        }
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
