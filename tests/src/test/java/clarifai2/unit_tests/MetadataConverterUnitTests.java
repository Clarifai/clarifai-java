package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.input.SearchClause;
import clarifai2.dto.PointF;
import clarifai2.dto.Radius;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiURLImage;
import clarifai2.dto.input.SearchHit;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.search.SearchInputsResult;
import clarifai2.grpc.FkClarifaiHttpClient;
import clarifai2.grpc.MetadataConverter;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import com.google.gson.JsonObject;
import com.google.protobuf.ListValue;
import com.google.protobuf.NullValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MetadataConverterUnitTests extends BaseUnitTest {

  @Test public void primitiveFieldsToStruct() {
    assertEquals(
        Struct.newBuilder()
            .putFields("field1", Value.newBuilder().setStringValue("value1").build())
            .putFields("field2", Value.newBuilder().setNumberValue(3).build())
            .putFields("field3", Value.newBuilder().setNumberValue(1.0).build())
            .putFields("field4", Value.newBuilder().setBoolValue(true).build())
            .putFields("field5", Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build()).build(),
        MetadataConverter.jsonObjectToStruct(
            new JSONObjectBuilder()
                .add("field1", "value1")
                .add("field2", 3)
                .add("field3", 1.0)
                .add("field4", true)
                .add("field5", (Character)null).build()
        )
    );
  }

  @Test public void singleArrayToStruct() {
    assertEquals(
        Struct.newBuilder()
            .putFields("field1", Value.newBuilder().setListValue(ListValue.newBuilder()
                    .addValues(Value.newBuilder().setStringValue("value1").build())
                    .addValues(Value.newBuilder().setNumberValue(3).build())
                    .addValues(Value.newBuilder().setNumberValue(1.0).build())
                    .addValues(Value.newBuilder().setBoolValue(true).build())
                    .addValues(Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build()).build())
                    .build()).build(),
        MetadataConverter.jsonObjectToStruct(
            new JSONObjectBuilder().add("field1", new JSONArrayBuilder()
                .add("value1")
                .add(3)
                .add(1.0)
                .add(true)
                .add((Character)null)
            ).build()
        )
    );
  }

  @Test public void singleObjectFieldToStruct() {
    assertEquals(
        Struct.newBuilder()
            .putFields("field1", Value.newBuilder().setStructValue(
                Struct.newBuilder()
                    .putFields("key1", Value.newBuilder().setStringValue("value1").build())
                    .putFields("key2", Value.newBuilder().setNumberValue(3).build())
                    .putFields("key3", Value.newBuilder().setNumberValue(1.0).build())
                    .putFields("key4", Value.newBuilder().setBoolValue(true).build())
                    .putFields("key5", Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build()))
                .build())
            .putFields("field2", Value.newBuilder().setStringValue("value2").build())
            .build(),
        MetadataConverter.jsonObjectToStruct(
            new JSONObjectBuilder()
                .add("field1", new JSONObjectBuilder()
                    .add("key1", "value1")
                    .add("key2", 3)
                    .add("key3", 1.0)
                    .add("key4", true)
                    .add("key5", (Character)null))
                .add("field2", "value2")
                .build()
        )
    );
  }

  @Test public void primitiveFieldsToJsonObject() {
    assertEquals(
        new JSONObjectBuilder()
            .add("field1", "value1")
            .add("field2", 3)
            .add("field3", 1.0)
            .add("field4", true)
            .add("field5", (Character)null).build(),
        MetadataConverter.structToJsonObject(
            Struct.newBuilder()
                .putFields("field1", Value.newBuilder().setStringValue("value1").build())
                .putFields("field2", Value.newBuilder().setNumberValue(3).build())
                .putFields("field3", Value.newBuilder().setNumberValue(1.0).build())
                .putFields("field4", Value.newBuilder().setBoolValue(true).build())
                .putFields("field5", Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build()
                ).build()
        )
    );
  }

  @Test public void singleArrayToJsonObject() {
    assertEquals(
        new JSONObjectBuilder().add("field1", new JSONArrayBuilder()
            .add("value1")
            .add(3)
            .add(1.0)
            .add(true)
            .add((Character)null)
        ).build(),
        MetadataConverter.structToJsonObject(
            Struct.newBuilder()
                .putFields("field1", Value.newBuilder().setListValue(ListValue.newBuilder()
                    .addValues(Value.newBuilder().setStringValue("value1").build())
                    .addValues(Value.newBuilder().setNumberValue(3).build())
                    .addValues(Value.newBuilder().setNumberValue(1.0).build())
                    .addValues(Value.newBuilder().setBoolValue(true).build())
                    .addValues(Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build()).build())
                    .build()).build()
        )
    );
  }

  @Test public void singleObjectFieldToJsonObject() {
    assertEquals(
        new JSONObjectBuilder()
            .add("field1", new JSONObjectBuilder()
                .add("key1", "value1")
                .add("key2", 3)
                .add("key3", 1.0)
                .add("key4", true)
                .add("key5", (Character)null))
            .add("field2", "value2")
            .build(),
        MetadataConverter.structToJsonObject(
            Struct.newBuilder()
                .putFields("field1", Value.newBuilder().setStructValue(
                    Struct.newBuilder()
                        .putFields("key1", Value.newBuilder().setStringValue("value1").build())
                        .putFields("key2", Value.newBuilder().setNumberValue(3).build())
                        .putFields("key3", Value.newBuilder().setNumberValue(1.0).build())
                        .putFields("key4", Value.newBuilder().setBoolValue(true).build())
                        .putFields("key5", Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build()))
                    .build())
                .putFields("field2", Value.newBuilder().setStringValue("value2").build())
                .build()
        )
    );
  }
}
