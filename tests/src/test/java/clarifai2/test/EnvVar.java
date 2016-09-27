package clarifai2.test;

enum EnvVar {
  CLARIFAI_APP_ID,
  CLARIFAI_APP_SECRET,
  CLARIFAI_API_BASE;

  private final String envVarName;

  EnvVar() {
    this.envVarName = name();
  }

  public String value() {
    return System.getenv(envVarName);
  }
}
