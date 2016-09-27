package clarifai2.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

@SuppressWarnings("unused")
public final class AddInputsFromCSVRequest extends ClarifaiRequest.Builder<List<ClarifaiInput>> {

  @NotNull private final File csvFile;

  public AddInputsFromCSVRequest(@NotNull final BaseClarifaiClient helper, @NotNull File csvFile) {
    super(helper);
    this.csvFile = csvFile;
  }

  @NotNull @Override protected JSONUnmarshaler<List<ClarifaiInput>> unmarshaler() {
    return new JSONUnmarshaler<List<ClarifaiInput>>() {
      @Nullable @Override
      public List<ClarifaiInput> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        throw new RuntimeException("TODO: Use CSV. CSV: " + csvFile.getName());
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    throw new RuntimeException("TODO");
  }
}
