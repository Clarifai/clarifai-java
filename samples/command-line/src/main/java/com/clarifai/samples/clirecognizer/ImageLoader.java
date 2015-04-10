package com.clarifai.samples.clirecognizer;

import static java.awt.RenderingHints.KEY_INTERPOLATION;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

/** Helper class for loading image data and ensuring it meets the size requirements for the API. */
public class ImageLoader {
  /**
   * Loads image data from a file, resizing it to meet size constraints if necessary.
   *
   * @param file file containing image data
   * @param minSize minimum image size accepted by the API
   * @param maxSize maximum image size accepted by the API
   * @return bytes for the given image meeting the size constraints
   */
  public static byte[] imageDataFromFile(File file, int minSize, int maxSize) throws IOException {
    // Read the image.
    byte[] imageBytes = Files.readAllBytes(file.toPath());
    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
    if (image == null) {
      throw new IOException("Unable to read image: " + file);
    }

    // Determine if the image needs to be resized.
    int width = image.getWidth();
    int height = image.getHeight();
    float minRatio = (float)minSize / Math.min(width, height);
    float maxRatio = (float)maxSize / Math.max(width, height);
    if (maxRatio < 1.0) {
      // Downsample so the longer edge is maxSize.
      return encodeJpeg(resize(image, Math.max(minSize, Math.round(maxRatio * width)),
          Math.max(minSize, Math.round(maxRatio * height))));
    } else if (minRatio > 1.0) {
      // Upsample so the shorter edge is minSize.
      return encodeJpeg(resize(image, Math.min(maxSize, Math.round(minRatio * width)),
          Math.min(maxSize, Math.round(minRatio * height))));
    } else {
      return imageBytes;  // The image was already appropriately sized.
    }
  }

  private static BufferedImage resize(BufferedImage image, int targetWidth, int targetHeight) {
    // For better quality, run iteratively when downsampling by more than a factor of 2.
    int width = image.getWidth();
    int height = image.getHeight();
    while (targetWidth < width / 2 && targetHeight < height / 2) {
      image = resizeOnce(image, width / 2, height / 2);
      width /= 2;
      height /= 2;
    }
    return resizeOnce(image, targetWidth, targetHeight);
  }

  private static BufferedImage resizeOnce(BufferedImage image, int targetWidth, int targetHeight) {
    BufferedImage result = new BufferedImage(targetWidth, targetHeight, image.getType());
    Graphics2D g = result.createGraphics();
    g.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR);
    float aspectRatio = (float)image.getWidth() / image.getHeight();
    float targetAspectRatio = (float)targetWidth / targetHeight;
    if (targetAspectRatio > aspectRatio) {
      g.drawImage(image, 0, 0, targetWidth, Math.round(targetWidth / aspectRatio), null);
    } else {
      g.drawImage(image, 0, 0, Math.round(targetHeight * aspectRatio), targetHeight, null);
    }
    g.dispose();
    return result;
  }

  private static byte[] encodeJpeg(BufferedImage image) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ImageIO.write(image, "jpeg", out);
    return out.toByteArray();
  }
}
