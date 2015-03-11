package com.clarifai.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A request for recognition to be performed on one or more images. See
 * http://developer.clarifai.com for complete documentation on the Clarifai image recognition API.
 */
public class RecognitionRequest extends ClarifaiRequest {
  private static class Item {
    File file;
    byte[] imageBytes;
    String url;

    Item(File file) { this.file = file; }
    Item(byte[] imageBytes) { this.imageBytes = imageBytes; }
    Item(String url) { this.url = url; }

    InputStream getStream() throws IOException {
      if (file != null) {
        return new FileInputStream(file);
      } else if (imageBytes != null) {
        return new ByteArrayInputStream(imageBytes);
      }
      return null;
    }
  }

  private final List<Item> items = new ArrayList<Item>();
  private String model = "default";
  private boolean includeTags = true;
  private boolean includeEmbedding = false;
  private final Multipart multipart = new Multipart();

  /**
   * Constructs a new request for recognition on one or more images
   * @param files Files containing the image data to be recognized
   */
  public RecognitionRequest(File ... files) {
    for (File file : files) {
      items.add(new Item(file));
    }
  }

  /**
   * Constructs a new request for recognition on one or more images. The RecognitionRequest will
   * close the input streams when it is done sending them to the server.
   * @param imageByteArrays byte arrays containing image data to be recognized
   */
  public RecognitionRequest(byte[] ... imageByteArrays) {
    for (byte[] imageBytes : imageByteArrays) {
      items.add(new Item(imageBytes));
    }
  }

  /**
   * Constructs a new request for recognition on one or more images
   * @param urls publicly-accessible URLs for images to be recognized
   */
  public RecognitionRequest(String ... urls) {
    for (String url : urls) {
      items.add(new Item(url));
    }
  }

  /** Returns the name of the model to use for recognition. */
  public String getModel() {
    return model;
  }

  /** Sets the model to use for recognition. */
  public RecognitionRequest setModel(String model) {
    this.model = model;
    return this;
  }

  /** Returns true (default) if tags should be included in the result, or false if not. */
  public boolean getIncludeTags() {
    return includeTags;
  }

  /** Sets whether to include tags in the result. */
  public RecognitionRequest setIncludeTags(boolean includeTags) {
    this.includeTags = includeTags;
    return this;
  }

  /** Returns true if embeddings should be included in the result, or false (default) if not. */
  public boolean getIncludeEmbedding() {
    return includeEmbedding;
  }

  /** Sets whether to include embeddings in the result. */
  public RecognitionRequest setIncludeEmbedding(boolean includeEmbedding) {
    this.includeEmbedding = includeEmbedding;
    return this;
  }

  @Override String getContentType() {
    return "multipart/form-data; boundary=" + multipart.getBoundary();
  }

  @Override void writeContent(OutputStream out) throws IOException {
    StringBuilder op = new StringBuilder();
    if (includeTags) {
      op.append("tag");
    }
    if (includeEmbedding) {
      op.append(op.length() > 0 ? "," : "").append("embed");
    }
    multipart.start(out);
    multipart.writeParameter("op", op.toString());
    multipart.writeParameter("model", model);
    for (Item item : items) {
      if (item.url != null) {
        multipart.writeParameter("url", item.url);
      } else {
        InputStream in = item.getStream();
        try {
          multipart.writeMedia("encoded_data", "media", in);
        } finally {
          in.close();
        }
      }
    }
    multipart.finish();
  }
}
