# Cordova Plugin App Icon

[![npm](https://img.shields.io/github/package-json/v/cesarcervantesb/cordova-plugin-app-icon
)](https://github.com/cesarcervantesb/cordova-plugin-app-icon/)
![License](https://img.shields.io/github/license/cesarcervantesb/cordova-plugin-app-icon?color=orange)

This plugin allows change the app icon.

## Installation

Install plugin from npm:

```
npm i @ccervantesb/cordova-plugin-app-icon
```

Or install the latest master version from GitHub:

```
cordova plugin add https://github.com/cesarcervantesb/cordova-plugin-app-icon
```

## Supported Platforms

- Android
- iOS

## Usage

The plugin creates the object cordova.plugins.appIcon and is accessible after the deviceready event has been fired.

```js
document.addEventListener('deviceready', function () {
    // cordova.plugins.appIcon is now available
}, false);
```

## Available methods

- `changeIcon` - Change the application icon.
- `resetIcon` - Reset the application icon.
- `getAppName` - Get the display application icon name. (Activity `alias` for Android or `alternateIconName` for iOS)

# Android configuration

Add all custom icons directly to your Android project in `app/src/res/`.

## Setup ApplicationManifest.xml

Each custom icon is represented by an `<activity-alias>`. You should add all custom icons to the `ApplicationManfest.xml` as child elements of `<application>`. For example:

```xml
<application>
    <activity
        android:name=".MainActivity"
        android:exported="true"
        android:launchMode="singleTask"
        android:configChanges="orientation|keyboardHidden|keyboard|screenSize|locale|smallestScreenSize|screenLayout|uiMode"
        android:label="@string/title_activity_main"
        android:theme="@style/AppTheme.NoActionBarLaunch">

        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>

    </activity>
    <activity-alias
        android:label="Custom Icon"
        android:icon="@drawable/my_custom_icon"
        android:roundIcon="@drawable/my_custom_icon"
        android:name=".customicon"
        android:enabled="false"
        android:exported="true"
        android:targetActivity=".MainActivity">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity-alias>
  
  <!-- additional <activity-alias> -->

<application>
```

# IOS Configuration

> This plugin only changes the main app icon on the device homescreen. The icon in springboard and in other areas will not change and continue to show the original.

> Changing the app icon is only allowed when the app is in the foreground.

## Create the custom icons in assets catalog

- In XCode go to Assets and create the App Icons. (images in 1024x1024px)

> ##### NOTE: The name of the asset should correspond to the name used in the code

## Change build settings

1. Visit Build Settings
2. Search For App icon,
3. Select "yes" for `Include All App Icon Assets`
4. Include a list (in `Alternate App Icon Sets`) with precise names of the app icons in the Assets Catalogue.