# Microfrontends Architecture for Android

This repository demonstrates how to architect an Android app as microfrontends.

Microfrontends allow developers to work on smaller, more manageable pieces of
an application. Each microfrontend can be developed and tested independently, reducing the risk of
breaking other parts of the application. This can also lead to faster development times, as
developers can focus on specific features without worrying about the entire application.

In this demo, each feature contributes UI(Activity/Fragment) to the ApplicationComponent, and the
app is an aggregation composed of the feature's UI. Each feature can also launch independently to
speed up the development and testing process. To launch a features, comment out the rest of the feature modules from the `settings.gradle` file. (TODO: Add a script to automate this process.)

This app has three feature modules that display a list of IoT devices to communicate. The features
talk with the AWS IoT service to send and receive messages.

![img](https://github.com/ripplearc/ripplearc.github.io/blob/main/images/Android/microfrontends/microfrontends_screenshot.jpg)

## Structure

The app module is a skeleton that hosts the ApplicationComponent.
The feature modules are placed under the features directory and cannot depend on each other.

- iot-roster: Displays a list of IoT devices that are connected to the AWS IoT service. The user can select a device to view its status.
- iot-test: Adjust the IoT devices' status by sending messages.
- iot-dynamic-histogram: A dummy feature that demonstrates how to load a feature dynamically.

## Dynamic Feature Delivery
The microfrontends architecture also supports dynamic feature delivery, as shown in the
iot-dynamic-historagram feature. Dynamic feature delivery allows you to load features on demand,
reducing the app's initial download size and providing a more seamless experience for the user.

## AWS Integration
This repository integrates with the AWS IoT service to demonstrate how to communicate with IoT
devices. The features use the aws-android-sdk-pinpoint library to send and receive messages from the
AWS IoT service.
