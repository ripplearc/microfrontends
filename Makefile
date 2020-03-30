SHELL := /bin/bash

log:help

## feature_list   : Display the feature list and select one to start
feature_list:
	adb shell am start -W -a android.intent.action.VIEW -d "http://ripplearc.com/"

## iot-roster   : Start from feature iot roster
iot-roster:
	adb shell am start -W -a android.intent.action.VIEW -d "http://ripplearc.com/roster"

## iot-test   : Start from feature iot test
iot-test:
	adb shell am start -W -a android.intent.action.VIEW -d "http://ripplearc.com/test"

## iot-histogram	: Start from dynamic feature iot histogram
iot-histogram:
	adb shell am start -W -a android.intent.action.VIEW -d "http://ripplearc.com/histogram"

## clear_gradle_cache: Clear build cache of current project
clear_gradle_cache:
	./gradlew cleanBuildCache

## feature_list: Pop up a menu to select a feature to launch
feature_list:
	 adb shell am start -W -a android.intent.action.VIEW -d "http://ripplearc.com"


help: Makefile
	sed -n "s/^##//p" $<
