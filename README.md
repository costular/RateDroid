# RateDroid

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[ ![Download](https://api.bintray.com/packages/costular/maven/ratedroid/images/download.svg) ](https://bintray.com/costular/maven/ratedroid/_latestVersion)

A library which helps you asking users for a review. It manages for you when to ask it depending sessions and includes ready and custom Dialogs to show to your users

## Gradle dependency
[ ![Download](https://api.bintray.com/packages/costular/maven/ratedroid/images/download.svg) ](https://bintray.com/costular/maven/ratedroid/_latestVersion)

You should add this line to your **build.gradle** to get this library.

```groovy
dependencies {
    ...
    implementation 'com.costular:ratedroid:0.2.1'
}
```

## Basic usage

```kotlin
RateDroid
    .with(this) // pass context
    .init() // DON'T forget to intialize
    .showIfNeeded(this) // pass an activity or a fragment. Show the dialog if it should
```

## Advanced usage

You can personalize the library 100%. There are some stuff that you could change. Here there are some of them:

| Variable           | Description                                                                                                 | Default           |
|--------------------|-------------------------------------------------------------------------------------------------------------|-------------------|
| **countDayByDay**      | If it is enabled (true) the library only count a launch as a valid view if a day has passed since last view | true              |
| **launchTimes**        | The times the user should visited the application to show the rate dialog                                   | 5                 |
| **remindIntervalDays** | The days that must elapse to show the rate dialog again when the user press "Remind me later" in the dialog | 3                 |
| **dialog**             | `RateDroidDialog` implementation instance to show as the rate dialog                                          | check screenshots |

You can apply these changes like this:

```kotlin
RateDroid
    .with(this)
    .apply {
        countDayByDay = false // <--
        launchTimes = 10 // <--
        remindIntervalDays = 5 // <--
        dialog = MyRateDialog // <--
    }
    .init()
    .showIfNeeded(this)
```

## Debug

You could need to test and debug your dialog. In that case, you can force to show the dialog.

```kotlin
RateDroid
    .with(this)
    .init(debug = true) // <-- enable debug
    .showIfNeeded(this)
```
