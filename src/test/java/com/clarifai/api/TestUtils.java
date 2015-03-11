package com.clarifai.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestUtils {
  public static byte[] loadResource(String path) {
    try {
      InputStream in = TestUtils.class.getResourceAsStream(path);
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      byte[] buf = new byte[4096];
      while (true) {
        int numRead = in.read(buf);
        if (numRead < 0) {
          return out.toByteArray();
        }
        out.write(buf, 0, numRead);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static File writeTempFile(String name, byte[] content) throws IOException {
    File testFile = File.createTempFile(name, "");
    FileOutputStream out = new FileOutputStream(testFile);
    out.write(content);
    out.close();
    return testFile;
  }
}
