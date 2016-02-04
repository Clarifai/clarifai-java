Clarifai Java Client
====================
[![Build Status](https://travis-ci.org/Clarifai/clarifai-java.svg?branch=master)](https://travis-ci.org/Clarifai/clarifai-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.clarifai/clarifai-api-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.clarifai/clarifai-api-java)

A simple client for the Clarifai image and video recognition API.

Read on to learn how to get started. You may also want to:
* Try the Clarifai demo at: http://clarifai.com/#demo
* Sign up for a free account at: https://developer.clarifai.com/accounts/signup.
* Check out the Javadocs at [javadoc.io](http://www.javadoc.io/doc/com.clarifai/clarifai-api-java)
* Read full Clarifai API documentation at: https://developer.clarifai.com.


Installation
------------

Maven: add the following to the dependencies section of your pom.xml:
```
<dependency>
  <groupId>com.clarifai</groupId>
  <artifactId>clarifai-api-java</artifactId>
  <version>1.2.0</version>
</dependency>
```

Gradle: add the following to the dependencies section of your build.gradle:
```
compile "com.clarifai:clarifai-api-java:1.2.0"
```


Getting Started
---------------
To get a list of tags (objects, concepts, emotions, etc.) for an image or video on the filesystem:
```java
ClarifaiClient clarifai = new ClarifaiClient(APP_ID, APP_SECRET);
List<RecognitionResult> results =
    clarifai.recognize(new RecognitionRequest(new File("kittens.jpg")));

for (Tag tag : results.get(0).getTags()) {
  System.out.println(tag.getName() + ": " + tag.getProbability());
}
```
Each `Tag` in the `RecognitionResult` contains the name of the tag and the probability that the tag
applies to the image or video.  By default, they are in English, but you can change the language by
calling `RecognitionRequest.setLocale()`.

Sending video to the API will cause a list of `VideoSegment` instances to be attached to the 
`RecognitionResult`. Each of these has a timestamp and a list of tags that apply to a short segment
of the video.

The `APP_ID` and `APP_SECRET` parameters passed to the `ClarifaiClient` constructor identify the
application and can be found on the
[applications dashboard](https://developer.clarifai.com/applications/). Alternately, there is a
zero-argument constructor that reads these from the `CLARIFAI_APP_ID` and `CLARIFAI_APP_SECRET`
environment variables.

The input can also be passed as a byte array:
```java
byte[] videoBytes = ...
results = clarifai.recognize(new RecognitionRequest(videoBytes));
```

Or as a publicly-accessible URL:
```java
results = clarifai.recognize(
    new RecognitionRequest("http://www.clarifai.com/img/metro-north.jpg"));
```

Recognition can also be run over batches of input. For example:
```java
File[] imageFiles = {
  new File("kittens.jpg"),
  new File("puppies.png"),
  new File("cubs.gif")
};
results = clarifai.recognize(new RecognitionRequest(imageFiles));
```
This returns a `List` containing a `RecognitionResult` instance for each file.
Alternately, we could have passed byte arrays or URLs. Running recognition in batches is faster and
more efficient than sending each image individually.

How many images can we send in a batch? Let's find out:
```java
InfoResult info = clarifai.getInfo();
System.out.println(info.getMaxBatchSize());  // Prints "128"
```
The limit is currently 128 images, but may change in the future. For video, the limit is currently 1.
The `InfoResult` also contains other useful information about the API like minimum and maximum image sizes.

Occasionally, some of the tags returned by the API may be wrong. In other cases, we may be
missing tags. If you or your users detect this, we encourage you to report the error back
to us. This will help us improve in the areas that you care about. The following request tells us
that the tags "kitten" and "cat" should be added to the image, and "dog" should be removed:
```java
clarifai.sendFeedback(new FeedbackRequest()
    .setDocIds(recognitionResult.getDocId())
    .setAddTags("kitten", "cat")
    .setRemoveTags("dog"));
```
The `docId` is a unique, stable ID for an image, and is returned with every `RecognitionResult`.

For more usage examples, see the [sample code](https://github.com/Clarifai/clarifai-java/tree/master/samples) or
[ClarifaiClientServerTest.java](https://github.com/clarifai/clarifai-java/blob/master/src/test/java/com/clarifai/api/ClarifaiClientServerTest.java).


Requirements
------------
* JDK 6 or later


Android
-------
For a complete example using the Clarifai Java Client from Android, please see our 
[Clarifai Android Starter](https://github.com/Clarifai/clarifai-android-starter) repo.

The client supports Android 2.3.3 (Gingerbread) and later. You'll need to add the
following permission to your `AndroidManifest.xml`:
```
<uses-permission android:name="android.permission.INTERNET" />
```

If you're using ProGuard, make sure the Clarifai API internals are not stripped out by adding the
following to your proguard.cfg:
```
-keep class com.clarifai.api.** { *; }
```
