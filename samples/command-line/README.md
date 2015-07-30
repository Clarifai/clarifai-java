Command Line Sample
===================

A simple example using the ClarifaiClient to perform image recognition on files from the
local filesystem or publicly accessible URLs.

Usage:
```
mvn clean package

export CLARIFAI_APP_ID=your_client_id
export CLARIFAI_APP_SECRET=your_client_secret

java -jar target/command-line-sample-1-jar-with-dependencies.jar test.jpg
```

The client ID and secret can be obtained from the
[applications dashboard](https://developer.clarifai.com/applications/).
