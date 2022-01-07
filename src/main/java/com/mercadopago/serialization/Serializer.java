package com.mercadopago.serialization;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mercadopago.net.MPResource;

/** Serializer class, responsible for objects serialization and deserialization. */
public class Serializer {

  private static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

  private static final Gson GSON =
      new GsonBuilder()
          .setDateFormat(DATE_FORMAT_ISO8601)
          .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
          .create();

  /**
   * Method responsible for deserialize objects.
   *
   * @param clazz class.
   * @param jsonObject json object.
   * @param <T> class type.
   * @return object.
   */
  public static <T extends MPResource> T deserializeFromJson(Class<T> clazz, String jsonObject) {
    return GSON.fromJson(jsonObject, clazz);
  }

  /**
   * Method responsible for serialize objects.
   *
   * @param resource resource.
   * @param <T> class type.
   * @return JsonObject.
   */
  public static <T> JsonObject serializeToJson(T resource) {
    return (JsonObject) GSON.toJsonTree(resource);
  }
}