package com.clarifai.api;

/** A tag returned by the recognition API. */
public class Tag {
  private final String name;
  private final double probability;

  Tag(String name, double probability) {
    this.name = name;
    this.probability = probability;
  }

  /**
   * Returns the name of the tag. The name is in English and is drawn from a large vocabulary.
   * The name may consist of more than one word.
   */
  public String getName() {
    return name;
  }

  /** Returns the probability that this tag is associated with the input image. */
  public double getProbability() {
    return probability;
  }

  @Override public String toString() {
    return "[Tag " + name + ": " + probability + "]";
  }
}
