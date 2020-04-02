# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.ripplearc.heavy.iot.dynamic.histogram.feature.IotHistogramFeatureImpl {
    com.ripplearc.heavy.iot.dynamic.histogram.feature.IotHistogramFeatureImpl$FeatureProvider Provider;
}

-keep class com.ripplearc.heavy.iot.dynamic.histogram.feature.IotHistogramFeatureImpl$FeatureProvider {
    *;
}

## BEGIN AWS
# https://github.com/aws-amplify/aws-sdk-android/blob/master/Proguard.md

# Class names are needed in reflection
-keepnames class com.amazonaws.**
-keepnames class com.amazon.**
# Request handlers defined in request.handlers
-keep class com.amazonaws.services.**.*Handler
# The following are referenced but aren't required to run
-dontwarn com.fasterxml.jackson.**
-dontwarn org.apache.commons.logging.**
# Android 6.0 release removes support for the Apache HTTP client
-dontwarn org.apache.http.**
# The SDK has several references of Apache HTTP client
-dontwarn com.amazonaws.http.**
-dontwarn com.amazonaws.metrics.**

## END AWS


## BEGIN GSON
# https://github.com/vimeo/vimeo-networking-java/issues/144
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class com.google.gson.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.ripplearc.heavy.data.** { *; }
-keepclassmembers enum * { *; }

## END GSON
