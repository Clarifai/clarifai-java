package clarifai2.unit_tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class BaseUnitTest {

  private JsonParser parser = new JsonParser();

  @NotNull protected String readResourceFile(String fileName) throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    String filePath = classLoader.getResource(fileName).getFile();

    String responseString;
    try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
      StringBuilder sb = new StringBuilder();
      String line = br.readLine();

      while (line != null) {
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
      }
      responseString = sb.toString();
    }
    return responseString;
  }

  protected void assertJsonEquals(String json1, String json2) {
    JsonElement o1 = parser.parse(json1);
    JsonElement o2 = parser.parse(json2);
    assertEquals(o1, o2);
  }
}
