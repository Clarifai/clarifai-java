package clarifai2.grpc;

import clarifai2.exception.ClarifaiException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.protobuf.ListValue;
import com.google.protobuf.NullValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetadataConverter {
  public static Struct jsonObjectToStruct(JsonObject jsonObject) {
    Struct.Builder struct = Struct.newBuilder();
    for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
      struct.putFields(entry.getKey(), jsonElementToValue(entry.getValue()));
    }
    return struct.build();
  }

  private static Value jsonElementToValue(JsonElement value) {
    if (value.isJsonObject()) {
      JsonObject valueAsJsonObject = value.getAsJsonObject();
      return Value.newBuilder().setStructValue(jsonObjectToStruct(valueAsJsonObject)).build();
    } else if (value.isJsonArray()) {
      JsonArray valueAsJsonArray = value.getAsJsonArray();

      List<Value> values = new ArrayList<>();
      for (JsonElement jsonElement : valueAsJsonArray) {
        values.add(jsonElementToValue(jsonElement));
      }

      return Value.newBuilder().setListValue(ListValue.newBuilder().addAllValues(values).build()).build();
    } else if (value.isJsonNull()) {
      return Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build();
    } else if (value.getAsJsonPrimitive().isBoolean()) {
      return Value.newBuilder().setBoolValue(value.getAsBoolean()).build();
    } else if (value.getAsJsonPrimitive().isString()) {
      return Value.newBuilder().setStringValue(value.getAsString()).build();
    } else if (value.getAsJsonPrimitive().isNumber()) {
      if (isNumberFloat(value)) {
        return Value.newBuilder().setNumberValue(value.getAsFloat()).build();
      } else {
        return Value.newBuilder().setNumberValue(value.getAsInt()).build();
      }
    } else {
      throw new ClarifaiException("Unknown metadata JsonObject field type.");
    }
  }

  private static boolean isNumberFloat(JsonElement value) {
    try {
        new Integer(value.getAsString());
        return false;
    } catch (NumberFormatException nfe) {
        return true;
    }
  }

  public static JsonObject structToJsonObject(Struct metadata) {
    JsonObject jsonObject = new JsonObject();
    for (Map.Entry<String, Value> entry : metadata.getFieldsMap().entrySet()) {
      jsonObject.add(entry.getKey(), valueToJsonElement(entry.getValue()));
    }
    return jsonObject;
  }

  private static JsonElement valueToJsonElement(Value value) {
    if (value.hasStructValue()) {
      return structToJsonObject(value.getStructValue());
    } else if (value.hasListValue()) {
      JsonArray values = new JsonArray();
      for (Value listElement : value.getListValue().getValuesList()) {
        values.add(valueToJsonElement(listElement));
      }
      return values;
    } else if (value.getKindCase().equals(Value.KindCase.NULL_VALUE)) {
      return JsonNull.INSTANCE;
    } else if (value.getKindCase().equals(Value.KindCase.BOOL_VALUE)) {
      return new JsonPrimitive(value.getBoolValue());
    } else if (value.getKindCase().equals(Value.KindCase.STRING_VALUE)) {
      return new JsonPrimitive(value.getStringValue());
    } else if (value.getKindCase().equals(Value.KindCase.NUMBER_VALUE)) {
      return new JsonPrimitive(value.getNumberValue());
    } else {
      throw new ClarifaiException("Unknown metadata Value field type.");
    }
  }
}
