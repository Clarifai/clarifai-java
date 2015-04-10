package com.clarifai.samples.clirecognizer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.InfoResult;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.RecognitionResult.StatusCode;
import com.clarifai.api.Tag;

/**
 * A command-line tool that allows you to send images to the Clarifai API for recognition.
 */
public class Main {
  public static void main(String[] filenames) throws IOException {
    if (filenames.length == 0) {
      System.err.println("Please pass 1 or more filenames as arguments.");
      return;
    }

    // Construct a client. This constructor reads the app ID and secret from environment variables.
    ClarifaiClient client = new ClarifaiClient();

    // Request API info. This will contain minimum and maximum image sizes.
    InfoResult info = client.getInfo();

    // Load image data, resizing each image if it doesn't meet the size constraints.
    byte[][] data = new byte[filenames.length][];
    for (int i = 0; i < filenames.length; i++) {
      data[i] = ImageLoader.imageDataFromFile(new File(filenames[i]),
          info.getMinImageSize(), info.getMaxImageSize());
    }

    // Ask the API to recognize the images.
    List<RecognitionResult> results = client.recognize(new RecognitionRequest(data));

    // Print out the results:
    for (int i = 0; i < results.size(); i++) {
      RecognitionResult result = results.get(i);

      // Each result contains a status code indicating whether the recognition succeeded or failed.
      System.out.println("\nResult for " + filenames[i] + ": " + result.getStatusCode());
      if (result.getStatusCode() == StatusCode.OK) {
        for (Tag tag : result.getTags()) {
          // Each tag contains a name and probability assigned to it by the recognition engine.
          System.out.println(String.format("  %s (%.4f)", tag.getName(), tag.getProbability()));
        }
      } else {
        System.out.println("  Status message: " + result.getStatusMessage());
      }
    }
  }
}
