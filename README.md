# Restaurants App

## Overview

This is an Android application created using Jetpack and Kotlin. 

## Running the app

- 1 - Download the project, and open it in Android Studio

- 2 - Build the app

- 3 - Enjoy!


## Using the Deep Link

This example application provides a deep link that can be run from the terminal, which will launch the app and bring the user to the specified restaurant identifier.

```shell
adb shell am start -W -a android.intent.action.VIEW -d "https://www.restaurantsapp.details.com/2" com.cdavey.restaurantsapp
```