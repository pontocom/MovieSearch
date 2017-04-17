# Preparing  your Project

### Create the Android Studio project
Fire up your Android Studio application and create a new project. Select the appropriate project and API level
(suggest not to be bellow SDK 5.0), add an Empty Activity (you may accept the defaults options), and hit the finish
button. Wait until Android Studio finishes creating your project.

### Adding need libraries to the project
Add the following two lines to the `build.gradle` file, in the `dependencies` section:

```
compile 'com.android.volley:volley:1.0.0'
compile 'com.github.bumptech.glide:glide:3.7.0'
```

Don't forget to sync your project afterwards.

### Adding the proper permissions to the Manifest
This project will require communication through the Internet, so please open your `AndroidManifest.xmk` file
and add the following permission:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```