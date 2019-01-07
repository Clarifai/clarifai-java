package clarifai2.grpc;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;

import java.util.Date;


public class DateTimeConverter {
  public static Date timestampToDate(Timestamp timestamp) {
    return new Date(timestamp.getSeconds() * 1000);
  }

  public static Timestamp dateToTimestamp(Date dateTime) {
//    return Timestamps.fromMillis(dateTime.getTime())
    return Timestamp.newBuilder()
        .setSeconds(dateTime.getTime())
        .build();
  }
}
