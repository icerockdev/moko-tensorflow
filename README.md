![moko-tensorflow](https://user-images.githubusercontent.com/5010169/128705344-f858c4b9-db37-49f7-bb1f-9919f29cb78b.png)  
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) [![Download](https://img.shields.io/maven-central/v/dev.icerock.moko/tensorflow) ](https://repo1.maven.org/maven2/dev/icerock/moko/tensorflow) ![kotlin-version](https://kotlin-version.aws.icerock.dev/kotlin-version?group=dev.icerock.moko&name=tensorflow)

# Mobile Kotlin TensorFlow
This is a Kotlin MultiPlatform library that provides access to [TensorFlow-Lite](https://github.com/tensorflow/tensorflow/tree/master/tensorflow/lite) functionality from
common source set.

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Samples](#samples)
- [Set Up Locally](#set-up-locally)
- [Contributing](#contributing)
- [License](#license)

## Features

## Requirements
- Gradle version 6.8+
- Android API 19+
- iOS version 11.0+

## Installation

root build.gradle  
```groovy
allprojects {
    repositories {
        mavenCentral()
    }
}
```

project build.gradle
```groovy
dependencies {
    commonMainApi("dev.icerock.moko:tensorflow:0.2.1")
}

cocoaPods {
    podsProject = file("../ios-app/Pods/Pods.xcodeproj") // here should be path to Pods xcode project

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
```

Podfile
```ruby
pod 'TensorFlowLiteObjC', '~> 2.2.0'
```

## Usage

First place the model file in the multi-platform resource folder `commonMain/resources/MR/files`.

`common`:
```kotlin
class Classifier(private val interpreter: Interpreter) {

    fun classify(inputData: Any) {
        val inputShape = interpreter.getInputTensor(0).shape
        val inputSize = inputShape[1]

        val result = Array(1) { FloatArray(OUTPUT_CLASSES_COUNT) }
        interpreter.run(listOf(inputData), mapOf(Pair(0, result)))
    }
}
```

Getting shared model file (in `common`):

```kotlin
object ResHolder {
    fun getModelFile(): FileResource {
        return MR.files.mymodel
    }
}
```

`android`:
```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var interpreter: Interpreter

    override fun onCreate(savedInstanceState: Bundle?) {
        interpreter = Interpreter(ResHolder.getModelFile(), InterpreterOptions(2, useNNAPI = true), this)
        val classifier = Classifier(interpreter)
        classifier.classify(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        interpreter.close()
    }
}
```

`iOS`:
```swift
class ViewController: UIViewController {
    private var interpreter: TensorflowInterpreter?

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let options: TensorflowInterpreterOptions = TensorflowInterpreterOptions(numThreads: 2)
        let modelFileRes: ResourcesFileResource = ResHolder().getModelFile()
        
        interpreter = TensorflowInterpreter(fileResource: modelFileRes, options: options)
        let classifier = Classifier(interpreter: interpreter!)

        classifier.classify(data)
    }

    deinit {
        interpreter?.close()
    }
}
```

## Samples
Please see more examples in the [sample directory](sample).

## Set Up Locally 
- The [tensorflow directory](tensorflow) contains the `tensorflow` library;
- The [sample directory](sample) contains sample apps for Android and iOS; plus the mpp-library connected to the apps;
- For local testing a use the `./publishToMavenLocal.sh` script - so that sample apps use the locally published version.

## Contributing
All development (both new features and bug fixes) is performed in the `develop` branch. This way `master` always contains the sources of the most recently released version. Please send PRs with bug fixes to the `develop` branch. Documentation fixes in the markdown files are an exception to this rule. They are updated directly in `master`.

The `develop` branch is pushed to `master` on release.

For more details on contributing please see the [contributing guide](CONTRIBUTING.md).

## License
        
    Copyright 2020 IceRock MAG Inc.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
