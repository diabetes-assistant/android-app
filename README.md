# Diabetes Assistant App
[![GitHub license](https://img.shields.io/github/license/diabetes-assistant/diabetes-assistant-app)](https://github.com/diabetes-assistant/diabetes-assistant-app/blob/main/LICENSE)
[![CircleCI](https://img.shields.io/circleci/build/github/diabetes-assistant/diabetes-assistant-app)](https://app.circleci.com/pipelines/github/diabetes-assistant/diabetes-assistant-app)

Diabetes Assistant is a _Android App_ written in _Kotlin_. It is build with _gradle_.

It is an app to help doctors to dynamically create a therapy plan for their diabetes patients.
Patients can use it on top of that to get the right dosage for their therapy and to track their
blood sugar values in order to get a more tailored and accurate therapy plan from their doctors.
 
## Prerequisites
* [Java JDK 8](https://adoptopenjdk.net/installation.html)
* [Android Studio](https://developer.android.com/studio/) (for development)
* Android SDK (comes with Android Studio)

## Usage
### Tests
Will run all tests:
```bash
./gradlew check
```

Will run all tests on a connected device:
```bash
./gradlew connectedCheck
```

### Lint
Will run linter and create lint report:s
```bash
./gradlew lint
```

### Run
To install it on a connected device:
```bash
./gradlew installDebug
```

Else please follow [this guide](https://developer.android.com/studio/run/emulator) to run it in an
emulated environment.
