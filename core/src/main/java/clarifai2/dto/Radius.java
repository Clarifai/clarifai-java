package clarifai2.dto;

import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("NullableProblems")
@JsonAdapter(Radius.Adapter.class)
@AutoValue
public abstract class Radius {

  Radius() {} // AutoValue instances only

  @NotNull public static Radius of(float value, @NotNull Unit unit) {
    return new AutoValue_Radius(value, unit);
  }

  public abstract float value();
  @NotNull public abstract Unit unit();

  public enum Unit {
    MILE("withinMiles"),
    KILOMETER("withinKilometers"),
    DEGREE("withinDegrees"),
    RADIAN("withinRadians"),;

    @NotNull private final String str;

    Unit(@NotNull String str) {
      this.str = str;
    }

    @Override public String toString() {
      return str;
    }
  }


  static class Adapter extends JSONAdapterFactory<Radius> {
    @Nullable @Override protected Serializer<Radius> serializer() {
      return new Serializer<Radius>() {
        @NotNull @Override public JsonElement serialize(@Nullable Radius value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return new JSONObjectBuilder()
              .add("type", value.unit().toString())
              .add("value", value.value())
              .build();
        }
      };
    }

    @Override public TypeToken<Radius> typeToken() {
      return new TypeToken<Radius>() {};
    }
  }
}