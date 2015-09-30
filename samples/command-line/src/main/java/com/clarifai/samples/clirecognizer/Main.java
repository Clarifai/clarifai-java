package com.clarifai.samples.clirecognizer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.RecognitionResult.StatusCode;
import com.clarifai.api.Tag;

/**
 * A command-line tool that allows you to send images to the Clarifai API for recognition.
 */
public class Main {
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Please pass 1 or more filenames or URLs as arguments.");
      return;
    }

    // Construct a client. This constructor reads the app ID and secret from environment variables.
    ClarifaiClient client = new ClarifaiClient();

    // Send the file bytes or URLs up to the Clarifai API.
    List<RecognitionResult> results;
    if (args[0].toLowerCase().startsWith("http:") || args[0].toLowerCase().startsWith("https:")) {
      // Pass the URLs to the recognition API.
      results = client.recognize(new RecognitionRequest(args));
    } else {
      // Pass the files to the recognition API.
      File[] files = new File[args.length];
      for (int i = 0; i < args.length; i++) {
        files[i] = new File(args[i]);
      }
      results = client.recognize(new RecognitionRequest(files));
    }

    // Print out the results:
    for (int i = 0; i < results.size(); i++) {
      RecognitionResult result = results.get(i);

      // Each result contains a status code indicating whether the recognition succeeded or failed.
      System.out.println("\nResult for " + args[i] + ": " + result.getStatusCode());
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
